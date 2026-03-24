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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestProductInitData.class)
class ApiProductCRUDContollerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 생성")
    void t1() throws Exception {

        long beforeCount = productRepository.count();

        String requestBody = """
                {
                  "name": "아메리카노",
                  "category": "COFFEE",
                  "price": 4500,
                  "stock": 100,
                  "description": "진한 아메리카노",
                  "imageUrl": "https://picsum.photos/300"
                }
                """;

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("createProduct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("4번 상품이 생성되었습니다"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("아메리카노"))
                .andExpect(jsonPath("$.data.category").value("COFFEE"))
                .andExpect(jsonPath("$.data.price").value(4500))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andExpect(jsonPath("$.data.description").value("진한 아메리카노"))
                .andExpect(jsonPath("$.data.imageUrl").value("https://picsum.photos/300"));

        assertThat(productRepository.count()).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("관리자 상품 제거")
    void t2() throws Exception{
        long beforeCount = productRepository.count();
        long targetId = 1L;
        ResultActions resultActions= mvc.perform(
                delete("/api/v1/products/%d".formatted(targetId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("deleteProduct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 상품이 삭제 되었습니다".formatted(targetId)));

        Product product = productRepository.findById(targetId).orElse(null);
        assertThat(product).isNull();

        assertThat(productRepository.count()+1).isEqualTo(beforeCount);
    }
}
