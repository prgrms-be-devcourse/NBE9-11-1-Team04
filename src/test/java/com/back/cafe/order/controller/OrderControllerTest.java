package com.back.cafe.order.controller;

import com.back.cafe.domain.order.controller.OrderController;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.repository.OrderRepository;
import com.back.cafe.domain.order.service.OrderService;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("주문 신규 생성 테스트")
    void t1() throws Exception {

        long userId = 1;
        long targetId = 1;

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 1, "quantity": 2 },
                                            { "productId": 2, "quantity": 3 }
                                          ],
                                          "userId":%d
                                        }
                                        """.formatted(userId))
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createUpdateOrder"))
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 주문이 생성되었습니다.".formatted(targetId)))
                .andExpect(jsonPath("$.data.id").value(targetId))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==1)].quantity").value(2))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==2)].quantity").value(3))
                .andExpect(jsonPath("$.data.status").value("PENDING"));
    }

    @Test
    @DisplayName("주문 추가 생성 테스트")
    void t2() throws Exception {

        long userId = 3;
        long targetId = 2;
        mvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "orderProductRequests": [
                                    { "productId": 1, "quantity": 2 },
                                    { "productId": 2, "quantity": 3 }
                                  ],
                                  "userId":%d
                                }
                                """.formatted(userId))
        );
        Thread.sleep(1000);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 1, "quantity": 1 },
                                            { "productId": 4, "quantity": 2 }
                                          ],
                                          "userId":%d
                                        }
                                        """.formatted(userId))
                )
                .andDo(print());
        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createUpdateOrder"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 추가주문이 완료되었습니다.".formatted(targetId)))
                .andExpect(jsonPath("$.data.id").value(targetId))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==1)].quantity").value(3))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==2)].quantity").value(3))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==4)].quantity").value(2))
                .andExpect(jsonPath("$.data.status").value("PENDING"));
    }
    @Test
    @DisplayName("전날 오후 2시 이전 주문 테스트")
    void t3() throws Exception{
        long userId = 5;
        long targetId = 3;

        // 테스트를 위한 기존 주문
        mvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "orderProductRequests": [
                                    { "productId": 1, "quantity": 2 },
                                    { "productId": 2, "quantity": 3 }
                                  ],
                                  "userId":%d
                                }
                                """.formatted(userId))
        );

        // 테스트 시간과 별도로 주문 시간 변경 -> 전날 오후 1시 (전날 오후 2시 이전)
        LocalDateTime yesterday1PM = LocalDateTime.now()
                .minusDays(1)
                .with(LocalTime.of(13, 0));

        orderRepository.flush();
        Order order = orderService.findOrder(userId).get();
        order.setCreatedAt(yesterday1PM);
        orderRepository.save(order);

        // 신규 주문
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 2, "quantity": 1 },
                                            { "productId": 3, "quantity": 2 }
                                          ],
                                          "userId":%d
                                        }
                                        """.formatted(userId))
                )
                .andDo(print());
        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createUpdateOrder"))
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 주문이 생성되었습니다.".formatted(targetId+1)))
                .andExpect(jsonPath("$.data.id").value(targetId+1))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==2)].quantity").value(1))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==3)].quantity").value(2))
                .andExpect(jsonPath("$.data.status").value("PENDING"));
    }
    @Test
    @DisplayName("내 주문 조회 테스트")
    void t4() throws Exception{
        long userId = 6;
        long targetId = 5;

        // 테스트를 위한 기존 주문
        mvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "orderProductRequests": [
                                    { "productId": 1, "quantity": 2 },
                                    { "productId": 2, "quantity": 3 }
                                  ],
                                  "userId":%d
                                }
                                """.formatted(userId))
                );

        // userId에 맞는 주문 조회
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders/user/%d".formatted(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrderByUser"))
                .andExpect(jsonPath("$.orderDtos[0].id").value(targetId))
                .andExpect(jsonPath("$.orderDtos[0].orderProducts[?(@.productId==1)].quantity").value(2))
                .andExpect(jsonPath("$.orderDtos[0].orderProducts[?(@.productId==2)].quantity").value(3))
                .andExpect(jsonPath("$.orderDtos[0].status").value("PENDING"));
    }


    @Test
    @DisplayName("주문 생성 시 상품 재고 감소 검증")
    void t5() throws Exception {
        // 테스트용 상품 생성 (재고 10개)
        Product p1 = productRepository.save(new Product("아메리카노", "커피", 4000L, 10, "설명", "url"));
        Product p2 = productRepository.save(new Product("라떼", "커피", 4500L, 10, "설명", "url"));

        long userId = 100;

        // 주문 수행 (아메리카노 2개, 라떼 3개 주문)
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": %d, "quantity": 2 },
                                            { "productId": %d, "quantity": 3 }
                                          ],
                                          "userId": %d
                                        }
                                        """.formatted(p1.getId(), p2.getId(), userId))
                );

        // 성공 응답(201-1) 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("201-1"));

        // DB 재고가 정확히 깎였는지 확인
        Product P1 = productRepository.findById(p1.getId()).get();
        Product P2 = productRepository.findById(p2.getId()).get();

        assertThat(P1.getStock()).isEqualTo(8);  // 10 - 2
        assertThat(P2.getStock()).isEqualTo(7);  // 10 - 3
    }

    @Test
    @DisplayName("재고 부족 시 주문 실패 검증 (400 응답)")
    void t6() throws Exception {
        // 재고가 1개인 상품 생성
        Product p = productRepository.save(new Product("희귀템", "ETC", 10000L, 1, "설명", "url"));
        long userId = 101;

        // 2개 주문 시도 (재고 부족 발생 -> 핸들러가 400 반환)
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": %d, "quantity": 2 }
                                          ],
                                          "userId": %d
                                        }
                                        """.formatted(p.getId(), userId))
                )
                .andDo(print());

        // 핸들러가 메시지에 "재고"가 있어 400(BadRequest)을 뱉는지 확인
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("400-1"))
                .andExpect(jsonPath("$.msg").value("재고 부족"));

        // 롤백 검증: 재고가 깎이지 않고 1인지 확인
        Product P1 = productRepository.findById(p.getId()).get();
        assertThat(P1.getStock()).isEqualTo(1);

    }

    @Test
    @DisplayName("동시 주문 발생 시 재고 정합성 검증")
    void t7_concurrency() throws Exception {
        // 1. 준비: 재고가 100개인 상품 생성
        Product p = productRepository.save(new Product("동시성테스트상품", "커피", 4000L, 100, "설명", "url"));
        long productId = p.getId();
        long userId = 102;

        int threadCount = 100; // 동시에 보낼 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount); // 모든 스레드가 끝날 때까지 대기하기 위한 장치

        // 100명이 동시에 API 호출
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    mvc.perform(
                            post("/api/v1/orders")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": %d, "quantity": 1 }
                                          ],
                                          "userId": %d
                                        }
                                        """.formatted(productId, userId))
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // 작업 완료 신호
                }
            });
        }

        latch.await(); // 모든 요청이 끝날 때까지 대기

        // 최종 재고 확인
        Product updatedProduct = productRepository.findById(productId).get();
        assertThat(updatedProduct.getStock()).isEqualTo(0);
    }

}
