package com.back.cafe.global.initData;

import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.repository.OrderRepository;
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
public class OrderInitData {

    @Autowired
    @Lazy
    private OrderInitData self; //  프록시 객체 주입

    private final OrderRepository orderRepository;


    @Bean
    @Profile("test")
    public ApplicationRunner initDataRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (orderRepository.count() > 0) return;

        Order p1 = new Order(1L);
        Order p2 = new Order(2L);
        Order p3 = new Order(3L);

        orderRepository.saveAll(List.of(p1, p2, p3));

        // 여기에 추가 로직(예: 리뷰 생성 등)이 들어와도 하나의 트랜잭션으로 묶임
        System.out.println("OrderInitData: 인 초기 데이터 생성 완료");
    }
}