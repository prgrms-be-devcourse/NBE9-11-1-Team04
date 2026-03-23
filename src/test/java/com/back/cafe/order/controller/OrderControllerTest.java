package com.back.cafe.order.controller;

import com.back.cafe.domain.order.controller.OrderController;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.repository.OrderRepository;
import com.back.cafe.domain.order.service.OrderService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("주문 신규 생성 테스트")
    void t1() throws Exception {

        long userId = 1;
        long targetId = 1;

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders/user/%d".formatted(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 1, "quantity": 2 },
                                            { "productId": 2, "quantity": 3 }
                                          ]
                                        }
                                        """)
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
                .andExpect(jsonPath("$.data.status").value("order-completed"));
    }

    @Test
    @DisplayName("주문 추가 생성 테스트")
    void t2() throws Exception {

        long userId = 3;
        long targetId = 2;
        mvc.perform(post("/api/v1/orders/user/%d".formatted(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "orderProductRequests": [
                                    { "productId": 1, "quantity": 2 },
                                    { "productId": 2, "quantity": 3 }
                                  ]
                                }
                                """));
        Thread.sleep(1000);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders/user/%d".formatted(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 1, "quantity": 1 },
                                            { "productId": 4, "quantity": 2 }
                                          ]
                                        }
                                        """)
                )
                .andDo(print());
        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createUpdateOrder"))
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 추가주문이 완료되었습니다.".formatted(targetId)))
                .andExpect(jsonPath("$.data.id").value(targetId))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==1)].quantity").value(3))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==2)].quantity").value(3))
                .andExpect(jsonPath("$.data.orderProducts[?(@.productId==4)].quantity").value(2))
                .andExpect(jsonPath("$.data.status").value("order-completed"));
    }
    @Test
    @DisplayName("전날 오후 2시 이전 주문 테스트")
    void t3() throws Exception{
        long userId = 5;
        long targetId = 3;

        // 테스트를 위한 기존 주문
        mvc.perform(post("/api/v1/orders/user/%d".formatted(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "orderProductRequests": [
                                    { "productId": 1, "quantity": 2 },
                                    { "productId": 2, "quantity": 3 }
                                  ]
                                }
                                """));

        // 테스트 시간과 별도로 주문 시간 변경 -> 전날 오후 1시 (전날 오후 2시 이전)
        LocalDateTime yesterday1PM = LocalDateTime.now()
                .minusDays(1)
                .with(LocalTime.of(13, 0));

        orderRepository.flush();
        Order order = orderService.findOrder(userId).get();
        order.setCreated_at(yesterday1PM);
        orderRepository.save(order);

        // 신규 주문
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/orders/user/%d".formatted(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": 2, "quantity": 1 },
                                            { "productId": 3, "quantity": 2 }
                                          ]
                                        }
                                        """)
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
                .andExpect(jsonPath("$.data.status").value("order-completed"));
    }

}
