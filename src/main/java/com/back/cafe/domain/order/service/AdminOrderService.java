package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.OrderDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderStatus;
import com.back.cafe.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
     * 주문을 취소 상태로 변경한 뒤, 변경된 주문 정보를 반환한다.
     *
     * @param orderId 취소할 주문 ID
     * @return 상태가 변경된 주문 객체
     */
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        order.setStatus(OrderStatus.CANCELLED.name());
        return order;
    }

    /**
     * 주문 상태를 전달받은 상태값으로 변경한 뒤, 변경된 주문 정보를 반환한다.
     * 입력값은 enum으로 한 번 검증한 뒤 문자열로 저장한다.
     *
     * @param orderId 상태를 변경할 주문 ID
     * @param status 변경할 상태값 문자열
     * @return 상태가 변경된 주문 객체
     */
    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(orderStatus.name());

        return order;
    }

    /**
     * 전체 주문 목록 조회 + 페이징 로직
     * @param pageable 페이지 정보
     * @return 전체 주문 정보 Dto List
     */
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable) // Page인터페이스 자체적으로 map() 메서드를 지원
                .map(OrderDto::new);
    }

    /**
     * 유저 별 주문 목록 조회 + 페이징 로직
     * @param userId 유저 아이디
     * @param pageable 페이지 정보
     * @return 유져 별 주문 정보 Dto Page
     */
    public Page<OrderDto> findByUserId(Long userId, Pageable pageable){

        return orderRepository.findByUserId(userId,pageable)
                .map(OrderDto::new);
    }

    /**
     *
     * @param start 시작
     * @param end 끝
     * @param pageable 페이지 정보
     * @return 기간 별 주문 정보 Dto Page
     */
    public Page<OrderDto> findByOrderedAt(LocalDateTime start, LocalDateTime end, Pageable pageable){
        // 논리적인 날짜 오류 방어 로직 추가
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
        return orderRepository.findByCreatedAtBetween(start,end,pageable)
                .map(OrderDto::new);
    }

}