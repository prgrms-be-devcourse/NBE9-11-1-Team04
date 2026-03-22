package com.back.cafe;

import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CafeApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Transactional
	@Rollback(false) // 테스트가 끝나도 DB에서 지우지 않음 (H2 사용 시 유용)
	void 상품_데이터_생성_테스트() {
		// 1. Given (데이터 준비)
		Product product = new Product("아메리카노", "COFFEE", 4500L, 100, "맛있는 커피");

		// 2. When (실행)
		productRepository.save(product);

		// 3. Then (검증)
		Product savedProduct = productRepository.findById(product.getId()).orElse(null);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getName()).isEqualTo("아메리카노");

		System.out.println("생성된 상품 ID: " + savedProduct.getId());
	}
}