package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderDto;
import com.back.cafe.domain.order.dto.OrderListDto;
import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.service.OrderService;
import com.back.cafe.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    record OrderWriteReqBody(
            @NotEmpty
            List<OrderProductDto> orderProductRequests
    ){}

    // 주문 생성
    @PostMapping("/user/{userId}")
    public RsData<OrderDto> createUpdateOrder(
            @PathVariable Long userId,
            @RequestBody @Valid OrderWriteReqBody reqBody
    ){
        Order order = orderService.doOrder(userId, reqBody.orderProductRequests);
        boolean created = order.getCreatedAt() == order.getModifiedAt();
        String msg = created?"번 주문이 생성되었습니다.":"번 추가주문이 완료되었습니다.";
        String resultCode = created?"201-1":"200-1";
        return new RsData<>(
                "%d%s".formatted(order.getId(),msg),
                resultCode,
                new OrderDto(order)
        );
    }

    // 내 주문 조회 (유저별 주문 조회)
    @GetMapping("/user/{userId}")
    public OrderListDto getOrderByUser(
            @PathVariable Long userId
    ){
        List<OrderDto>orderDtos = orderService.findByUserId(userId);
        return new OrderListDto(orderDtos);
    }

}
