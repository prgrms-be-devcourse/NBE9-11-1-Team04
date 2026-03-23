package com.back.cafe.global.initData;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProductInitData {

    @Bean
    @Profile("!test")
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) return;

            Product p1 = new Product("아메리카노", "COFFEE", 4500L, 100, "시원한 커피");
            Product p2 = new Product("카페라떼", "COFFEE", 5000L, 50, "고소한 라떼");
            Product p3 = new Product("치즈케이크", "DESSERT", 6500L, 20, "부드러운 케이크");

            productRepository.saveAll(List.of(p1, p2, p3));
            System.out.println("InitData: 상품 초기 데이터 생성");
        };
    }
}