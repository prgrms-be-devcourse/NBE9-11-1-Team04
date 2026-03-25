package com.back.cafe.order.controller;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.repository.UserRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("관리자 주문 목록 조회")
    void getAllOrdersTest() throws Exception {
        mockMvc.perform(get("/api/v1/admin/orders")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.data.content[*].id", containsInRelativeOrder(30, 21)))
                .andExpect(jsonPath("$.data.content[0].userId").value(3))
                .andExpect(jsonPath("$.data.content.length()").value(10))
                .andExpect(jsonPath("$.data.content[2].status").value("PENDING"));
    }

    @Test
    @DisplayName("유저별 주문 목록 조회")
    void getUsersOrdersTest() throws Exception {
        mockMvc.perform(get("/api/v1/admin/orders/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
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
    @DisplayName("관리자 주문 조회 응답에 address와 productName 포함")
    void adminOrderResponseContainsAddressAndProductName() throws Exception {
        userRepository.save(new SiteUser("seed1@example.com", "서울시 중구 1", "10001"));
        userRepository.save(new SiteUser("seed2@example.com", "서울시 중구 2", "10002"));
        userRepository.save(new SiteUser("seed3@example.com", "서울시 중구 3", "10003"));

        SiteUser user = userRepository.save(
                new SiteUser("admin-test@example.com", "서울시 마포구 테스트로 101", "04123")
        );
        Product product = productRepository.save(
                new Product("테스트 원두", "COFFEE", 12000L, 20, "테스트 상품", "image-url")
        );

        mvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "orderProductRequests": [
                                            { "productId": %d, "quantity": 2 }
                                          ],
                                          "userId": %d
                                        }
                                        """.formatted(product.getId(), user.getId()))
                )
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/admin/orders/user/%d".formatted(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].userId").value(user.getId()))
                .andExpect(jsonPath("$.data.content[0].address").value("서울시 마포구 테스트로 101"))
                .andExpect(jsonPath("$.data.content[0].orderProducts[0].productId").value(product.getId()))
                .andExpect(jsonPath("$.data.content[0].orderProducts[0].productName").value("테스트 원두"))
                .andExpect(jsonPath("$.data.content[0].orderProducts[0].quantity").value(2));
    }

    @Test
    @DisplayName("관리자 주문 취소 시 재고 복구")
    void adminOrderCancelRestoreStock() throws Exception {
        SiteUser user = userRepository.save(
                new SiteUser("cancel-test@example.com", "서울시 성동구 취소로 3", "04711")
        );
        Product p = productRepository.save(new Product("취소대상상품", "커피", 4000L, 10, "설명", "url"));
        long productId = p.getId();

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
                                        """.formatted(productId, user.getId()))
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        long orderId = JsonPath.parse(orderResponse).read("$.data.id", Long.class);

        assertThat(productRepository.findById(productId).get().getStock()).isEqualTo(7);

        ResultActions resultActions = mvc.perform(
                delete("/api/v1/admin/orders/%d".formatted(orderId))
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문이 취소되었습니다."))
                .andExpect(jsonPath("$.data.status").value("CANCELLED"));

        Product restoredProduct = productRepository.findById(productId).get();
        assertThat(restoredProduct.getStock()).isEqualTo(10);
    }
}
