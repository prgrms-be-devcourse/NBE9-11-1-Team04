package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderDto;
import com.back.cafe.domain.order.dto.OrderStatusUpdateRequest;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.service.AdminOrderService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 관리자 주문 상태 제어 API를 담당하는 컨트롤러
 * - 주문 취소
 * - 주문 상태 변경
 */
@RestController
@Tag(name = "관리자 주문 API", description = "주문 관리를 위한 API")
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    /**
     * 관리자 주문 취소 API
     *
     * @param orderId 취소할 주문 ID
     * @return 취소 처리 후 수정된 주문 정보 응답
     */
    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 취소", description = "주문ID를 조회하여 주문을 삭제합니다")
    public RsData<OrderDto> cancelOrder(@PathVariable Long orderId) {
        Order order = adminOrderService.cancelOrder(orderId);

        return new RsData<>(
                "주문이 취소되었습니다.",
                "200-1",
                new OrderDto(order)
        );
    }

    /**
     * 관리자 주문 상태 변경 API
     *
     * @param orderId 상태를 변경할 주문 ID
     * @param request 변경할 상태값 요청 DTO
     * @return 상태 변경 후 수정된 주문 정보 응답
     */
    @PutMapping("/{orderId}")
    @Operation(summary = "주문 상태 변경" , description = "주문ID와 변경할 상태를 받아 기존 주문의 상태를 변경합니다")
    public RsData<OrderDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request
    ) {
        Order order = adminOrderService.updateOrderStatus(orderId, request.getStatus());

        return new RsData<>(
                "주문 상태가 변경되었습니다.",
                "200-1",
                new OrderDto(order)
        );
    }

    /**
     * 전체 주문 목록 조회
     * GET /api/v1/orders?page=0&size=10
     * @param pageable 페이지 정보
     * @return 응답 데이터
     */
    @GetMapping
    @Operation(summary = "전체 주문 조회", description = "현재 사용자가 주문한 모든 주문들을 전체 조회합니다")
    public RsData<Page<OrderDto>> getOrders(
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        Page<OrderDto> orders = adminOrderService.findAll(pageable);
        return new RsData<>(
                "모든 주문을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }

    /**
     * 기간 별 주문 목록 조회
     * GET /api/v1/orders/period?startDate=2026-03-01&endDate=2026-03-24
     * @param pageable 페이지 정보
     * @return 응답 데이터
     */
    @GetMapping("/period")
    @Operation(summary = "기간 별 주문 목록 조회", description = "기간 별 주문 목록을 조회합니다")
    public RsData<Page<OrderDto>> getOrdersByPeriod(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        //시작일과 종료일 다음날값을 LocalDateTime으로 타입 변환
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        Page<OrderDto> orders = adminOrderService.findByOrderedAt(startDateTime, endDateTime, pageable);

        return new RsData<>(
                "기간 별 주문 목록을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }

    /**
     * 유저 별 주문 목록 조회
     * GET /api/v1/orders/user/{userId}
     * @param userId 유저 아이디
     * @param pageable 페이지 정보
     * @return 유저 별 주문 목록
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "사용자 별 주문 목록 조회", description = "각 사용자별로 주문한 주문 목록을 조회합니다")
    public RsData<Page<OrderDto>> getOrdersByUserId(
            @PathVariable("userId") long userId,
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<OrderDto> orders = adminOrderService.findByUserId(userId,pageable);
        return new RsData<>(
                "유저 별 주문 목록을 성공적으로 조회하였습니다.",
                "200-1",
                orders
        );
    }

}