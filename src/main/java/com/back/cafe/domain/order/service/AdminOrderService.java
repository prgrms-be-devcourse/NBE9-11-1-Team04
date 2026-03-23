package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderStatus;
import com.back.cafe.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 관리자 주문 상태 제어 비즈니스 로직을 담당하는 서비스
 */
@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 주문을 취소 상태로 변경한다.
     *
     * @param orderId 취소할 주문 ID
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        order.setStatus(OrderStatus.CANCELLED.name());
    }

    /**
     * 주문 상태를 전달받은 상태값으로 변경한다.
     * 입력값은 enum으로 한 번 검증한 뒤 문자열로 저장한다.
     *
     * @param orderId 상태를 변경할 주문 ID
     * @param status 변경할 상태값 문자열
     */
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(orderStatus.name());
    }
}