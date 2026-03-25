package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.AdminOrderResponseDto;
import com.back.cafe.domain.order.dto.OrderStatusUpdateRequest;
import com.back.cafe.domain.order.service.AdminOrderService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@Tag(name = "관리자 주문 API", description = "주문 관리를 위한 API")
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 취소", description = "주문 ID를 기준으로 주문을 취소합니다.")
    public RsData<AdminOrderResponseDto> cancelOrder(@PathVariable Long orderId) {
        AdminOrderResponseDto order = adminOrderService.cancelOrder(orderId);

        return new RsData<>(
                "주문이 취소되었습니다.",
                "200-1",
                order
        );
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "주문 상태 변경", description = "주문 ID와 변경할 상태를 받아 주문 상태를 변경합니다.")
    public RsData<AdminOrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request
    ) {
        AdminOrderResponseDto order = adminOrderService.updateOrderStatus(orderId, request.getStatus());

        return new RsData<>(
                "주문 상태가 변경되었습니다.",
                "200-1",
                order
        );
    }

    @GetMapping
    @Operation(summary = "전체 주문 조회", description = "전체 주문 목록을 조회합니다.")
    public RsData<Page<AdminOrderResponseDto>> getOrders(
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<AdminOrderResponseDto> orders = adminOrderService.findAll(pageable);

        return new RsData<>(
                "모든 주문을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }

    @GetMapping("/period")
    @Operation(summary = "기간별 주문 조회", description = "지정한 기간의 주문 목록을 조회합니다.")
    public RsData<Page<AdminOrderResponseDto>> getOrdersByPeriod(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        Page<AdminOrderResponseDto> orders = adminOrderService.findByOrderedAt(startDateTime, endDateTime, pageable);

        return new RsData<>(
                "기간별 주문 목록을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "사용자별 주문 조회", description = "특정 사용자의 주문 목록을 조회합니다.")
    public RsData<Page<AdminOrderResponseDto>> getOrdersByUserId(
            @PathVariable("userId") long userId,
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<AdminOrderResponseDto> orders = adminOrderService.findByUserId(userId, pageable);

        return new RsData<>(
                "유저별 주문 목록을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }
}
