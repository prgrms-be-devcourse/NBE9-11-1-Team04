package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


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