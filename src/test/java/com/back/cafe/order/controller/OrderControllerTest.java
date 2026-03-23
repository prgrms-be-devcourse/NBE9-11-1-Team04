package com.back.cafe.order.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

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
                .andExpect(handler().methodName("createOrder"))
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 주문이 생성되었습니다.".formatted(targetId)))
                .andExpect(jsonPath("$.data.id").value(targetId))
                .andExpect(jsonPath("$.data.orderProducts[0].productId").value(1))
                .andExpect(jsonPath("$.data.orderProducts[0].quantity").value(2))
                .andExpect(jsonPath("$.data.orderProducts[1].productId").value(2))
                .andExpect(jsonPath("$.data.orderProducts[1].quantity").value(3))
                .andExpect(jsonPath("$.data.status").value("order-completed"));
    }
}
