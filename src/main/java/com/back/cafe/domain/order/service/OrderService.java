package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        //시간대 맞는 유저만 필터링 아직 안함!
        LocalDateTime start,end;
        LocalDateTime today2pm = LocalDateTime.now().with(LocalTime.of(14, 0, 0, 0)); //오늘 2시
        if (LocalDateTime.now().isAfter(today2pm)) {
            start = today2pm;
            end = today2pm.plusDays(1);
        } else {
            start = today2pm.minusDays(1);
            end = today2pm;
        }
        return orderRepository.findByUserId(userId).stream()
                .filter(order-> {
                    LocalDateTime created_at = order.getCreated_at();
                    return created_at.isAfter(start) && created_at.isBefore(end);
                })
                .findFirst();
    }

}
