package com.back.cafe.domain.product;

import com.back.cafe.domain.product.controller.ProductController;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 설정 자동화
@ActiveProfiles("test")
@Transactional
@Import(TestProductInitData.class)
public class ApiV1ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 다건 조회")
    void t1() throws Exception {

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/products")
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProducts"))
                .andExpect(status().isOk());

        resultActions
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].id", containsInRelativeOrder(1, 3)))
                .andExpect(jsonPath("$[2].id").value(3))
//                .andExpect(jsonPath("$[2].created_at").exists())
//                .andExpect(jsonPath("$[2].modified_at").exists())
                .andExpect(jsonPath("$[2].name").value("케이크"))
                .andExpect(jsonPath("$[2].category").value("DESSERT"))
                .andExpect(jsonPath("$[2].price").value(6500L))
                .andExpect(jsonPath("$[2].stock").value(20))
                .andExpect(jsonPath("$[2].description").value("달콤한 디저트"));
    }

    @Test
    @DisplayName("상품 단건 조회")
    void t2() throws Exception {

        long targetId = 2;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/products/%d".formatted(targetId))
                )
                .andDo(print());

        Product product = productRepository.findById(targetId).get();

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProduct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("라떼"))
                .andExpect(jsonPath("$.category").value("COFFEE"))
                .andExpect(jsonPath("$.price").value(5000L))
                .andExpect(jsonPath("$.stock").value(50))
                .andExpect(jsonPath("$.description").value("고소한 라떼"));

    }
}
