package com.back.cafe.domain.product;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@TestConfiguration // 테스트 환경용 Bean
public class TestProductInitData {

    @Bean
    public ApplicationRunner initProductTestData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) return;

            Product p1 = new Product("아메리카노", "COFFEE", 4500L, 100, "맛있는 커피", "https://picsum.photos/200");
            Product p2 = new Product("라떼", "COFFEE", 5000L, 50, "고소한 라떼", "https://picsum.photos/200");
            Product p3 = new Product("케이크", "DESSERT", 6500L, 20, "달콤한 디저트", "https://picsum.photos/200");

            productRepository.saveAll(List.of(p1, p2, p3));

            System.out.println("== TestInitData: 테스트용 상품 데이터 생성 완료 ==");
        };
    }
}