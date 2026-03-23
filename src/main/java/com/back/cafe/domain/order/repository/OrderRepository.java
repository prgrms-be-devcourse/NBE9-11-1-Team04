package com.back.cafe.domain.order.repository;

import com.back.cafe.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 엔티티 DB 접근을 담당하는 리포지토리
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}