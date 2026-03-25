package com.back.cafe.domain.product.repository;

import com.back.cafe.domain.product.entity.Product;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 로킹 기법으로 동시성 제어
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    // 락 획득 타임아웃 설정 (5초 동안 대기 후 실패 시 예외 발생)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")})
    // 전용 조회 쿼리
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdWithLock(@Param("id") Long id);
}
