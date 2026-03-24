package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderDto;
import com.back.cafe.domain.order.dto.OrderStatusUpdateRequest;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.service.AdminOrderService;
import com.back.cafe.global.rsData.RsData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 주문 상태 제어 API를 담당하는 컨트롤러
 * - 주문 취소
 * - 주문 상태 변경
 */
@RestController
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
     * @return 전체 주문 목록
     */
    @GetMapping
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
}