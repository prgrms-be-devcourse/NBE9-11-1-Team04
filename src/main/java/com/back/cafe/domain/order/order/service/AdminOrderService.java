package com.back.cafe.domain.order.order.service;

import com.back.cafe.domain.order.order.entity.Order;
import com.back.cafe.domain.order.order.entity.OrderStatus;
import com.back.cafe.domain.order.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        order.changeStatus(OrderStatus.CANCELLED);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        order.changeStatus(orderStatus);
    }
}