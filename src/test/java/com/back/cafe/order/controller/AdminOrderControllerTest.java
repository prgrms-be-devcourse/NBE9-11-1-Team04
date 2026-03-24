package com.back.cafe.order.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;




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
    @DisplayName("유저 별 주문 목록 조회")
    void getOrdersByPeriodTest() throws Exception {

        // 1페이지(0)에 10개씩 가져오라고 실제 API에 요청
        mockMvc.perform(get("/api/v1/admin/orders/p")
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

}