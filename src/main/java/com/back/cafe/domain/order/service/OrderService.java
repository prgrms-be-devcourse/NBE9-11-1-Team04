package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 일반 주문 생성 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    /**
     * 사용자 주문을 생성하고 DB에 저장한다.
     *
     * @param userId 주문한 사용자 ID
     * @param orderProductRequests 주문 상품 목록
     * @return 생성된 주문 엔티티
     */
    @Transactional
    public Order createOrder(Long userId, List<OrderProductDto> orderProductRequests) {
        Order order = new Order(userId);

        for (OrderProductDto dto : orderProductRequests) {
            // 요청 DTO를 주문 상품 엔티티로 변환한 뒤 주문에 추가
            OrderProduct orderProduct = new OrderProduct(dto.productId(), dto.quantity());
            order.addOrderProduct(orderProduct);
        }

        orderRepository.save(order);
        return order;
    }
}