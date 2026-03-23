package com.back.cafe.domain.order.repository;

import com.back.cafe.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.Optional;
/**
 * 주문 엔티티 DB 접근을 담당하는 리포지토리
 */
=======
import java.util.List;

>>>>>>> 3ff72b8 (feature:#2 주문 시간으로 신규/추가 주문 구분)
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}