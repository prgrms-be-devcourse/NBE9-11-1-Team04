package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    @Transactional
    public Order doOrder(Long userId, List<OrderProductDto> orderProductRequests){
        Optional<Order> existOrder = findOrder(userId);
        if(existOrder.isPresent()){
            return modifyOrder(userId, orderProductRequests);
        }
        return createOrder(userId, orderProductRequests);
    }

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

    @Transactional
    public Order modifyOrder(Long userId, List<OrderProductDto> orderProductRequests) {
        Order order = findOrder(userId).get(); //기존 order
        for(OrderProductDto dto : orderProductRequests){ // OrderProductDto -> OrderProduct 생성/추가, Order에 반영
            if(!order.checkProduct(dto.productId()).isEmpty()){  //이미 갖고 있는 product라면 quantity만 +
                OrderProduct orderProduct = order.checkProduct(dto.productId()).get();
                orderProduct.setQuantity(orderProduct.getQuantity()+dto.quantity());

            }else{ //없다면 생성
                OrderProduct newProduct = new OrderProduct(dto.productId(), dto.quantity());
                order.addOrderProduct(newProduct);
            }
        }
        orderRepository.save(order);
        return order;
    }

    public Optional<Order> findOrder(Long userId){
        //여기서 시간까지 거를 수 있도록
        return orderRepository.findByUserId(userId);
    }

}
