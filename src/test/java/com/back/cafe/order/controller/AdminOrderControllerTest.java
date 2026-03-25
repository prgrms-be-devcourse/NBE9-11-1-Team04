package com.back.cafe.order.controller;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mvc;



    @Test
    @DisplayName("관리자 주문 목록 조회")
    void getAllOrdersTest() throws Exception {

        // 1페이지(0)에 10개씩 가져오라고 실제 API에 요청
        mockMvc.perform(get("/api/v1/admin/orders")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.data.content[*].id", containsInRelativeOrder(30,21)))//내림차순으로 잘 정렬됬는지
                //값들이 잘 들어오느지
                .andExpect(jsonPath("$.data.content[0].userId").value(3))
                .andExpect(jsonPath("$.data.content.length()").value(10))
                .andExpect(jsonPath("$.data.content[2].status").value("PENDING"));

    }

    @Test
    @DisplayName("유저 별 주문 목록 조회")
    void getUsersOrdersTest() throws Exception {

        // 1페이지(0)에 10개씩 가져오라고 실제 API에 요청
        mockMvc.perform(get("/api/v1/admin/orders/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                //1번 유저의 주문 목록만 잘 조회했는지
                .andExpect(jsonPath("$.data.content[0].userId").value(1))
                .andExpect(jsonPath("$.data.content[1].userId").value(1))
                .andExpect(jsonPath("$.data.content[2].userId").value(1))
                .andExpect(jsonPath("$.data.content[3].userId").value(1))
                .andExpect(jsonPath("$.data.content[4].userId").value(1))
                .andExpect(jsonPath("$.data.content[5].userId").value(1))
                .andExpect(jsonPath("$.data.content[6].userId").value(1))
                .andExpect(jsonPath("$.data.content[7].userId").value(1))
                .andExpect(jsonPath("$.data.content.length()").value(10))
                .andExpect(jsonPath("$.data.content[2].status").value("PENDING"));

    }

    @Test
    @DisplayName("관리자 주문 취소 시 재고 복구")
    void adminOrderCancelRestoreStock() throws Exception {
        // 재고가 10개인 상품 생성
        Product p = productRepository.save(new Product("취소용상품", "커피", 4000L, 10, "설명", "url"));
        long productId = p.getId();
        long userId = 103;

        // 1단계: 먼저 주문을 생성하여 재고를 깎음 (10 -> 7)
        String orderResponse = mvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "orderProductRequests": [
                                { "productId": %d, "quantity": 3 }
                              ],
                              "userId": %d
                            }
                            """.formatted(productId, userId))
        ).andReturn().getResponse().getContentAsString();

        // 생성된 주문 ID 추출
        long orderId = JsonPath.parse(orderResponse).read("$.data.id", Long.class);

        // 재고가 7개인지 중간 확인
        assertThat(productRepository.findById(productId).get().getStock()).isEqualTo(7);

        // 2단계: 관리자 API로 주문 취소 호출
        ResultActions resultActions = mvc.perform(
                delete("/api/v1/admin/orders/%d".formatted(orderId))
        ).andDo(print());

        // 취소 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문이 취소되었습니다."))
                .andExpect(jsonPath("$.data.status").value("CANCELLED")); // 주문 상태가 취소인지 확인

        // 재고 복구 확인 (10개)
        Product restoredProduct = productRepository.findById(productId).get();
        assertThat(restoredProduct.getStock()).isEqualTo(10);
    }

}