package com.back.cafe.global.initData;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProductInitData {

    @Autowired
    @Lazy
    private ProductInitData self; //  프록시 객체 주입
    private final ProductRepository productRepository;

    @Bean
    @Profile("!test")
    public ApplicationRunner initDataRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (productRepository.count() > 0) return;

        Product p1 = new Product("아메리카노", "COFFEE", 4500L, 100, "시원한 커피", "https://picsum.photos/200");
        Product p2 = new Product("카페라떼", "COFFEE", 5000L, 50, "고소한 라떼", "https://picsum.photos/200");
        Product p3 = new Product("치즈케이크", "DESSERT", 6500L, 20, "부드러운 케이크", "https://picsum.photos/200");

        productRepository.saveAll(List.of(p1, p2, p3));

        // 여기에 추가 로직(예: 리뷰 생성 등)이 들어와도 하나의 트랜잭션으로 묶임
        System.out.println("ProductInitData: 상품 초기 데이터 생성 완료");
    }
}