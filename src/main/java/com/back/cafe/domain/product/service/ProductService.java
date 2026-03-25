package com.back.cafe.domain.product.service;

import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.dto.ServiceDto.ServiceCreateProductDto;
import com.back.cafe.domain.product.dto.ServiceDto.ServiceModifyProductDto;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::from)
                // 예외 처리 : 상품이 없는 경우.
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다. ID: " + id));
    }

    @Transactional
    public ProductDto create(ServiceCreateProductDto serviceCreateProductDto){
        Product product = new Product(
                serviceCreateProductDto.name(),
                serviceCreateProductDto.category(),
                serviceCreateProductDto.price(),
                serviceCreateProductDto.stock(),
                serviceCreateProductDto.description(),
                serviceCreateProductDto.imageUrl()
        );
        return ProductDto.from(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하고자 하는 상품이 없습니다."));

        productRepository.delete(product);
    }

    @Transactional
    public ProductDto modify(Long id, ServiceModifyProductDto serviceModifyProductDto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("수정하고자 하는 상품이 존재하지 않습니다."));
        product.update(
                serviceModifyProductDto.name(),
                serviceModifyProductDto.category(),
                serviceModifyProductDto.price(),
                serviceModifyProductDto.stock(),
                serviceModifyProductDto.description(),
                serviceModifyProductDto.imageUrl());
        return ProductDto.from(product);

    }

    @Transactional
    public void restoreStock(List<OrderProduct> orderProducts) {
        List<OrderProduct> sortedProducts = orderProducts.stream()
                .sorted(Comparator.comparing(OrderProduct::getProductId))
                .toList();

        sortedProducts.forEach(orderProduct -> {
            Product product = productRepository.findByIdWithLock(orderProduct.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + orderProduct.getProductId()));

            // 엔티티에 미리 만들어둔 increaseStock 호출
            product.increaseStock(orderProduct.getQuantity());
        });
    }

    @Transactional
    public void reduceStock(List<OrderProductDto> orderProductRequests) {
        List<OrderProductDto> sortedRequests = orderProductRequests.stream()
                .sorted(Comparator.comparing(OrderProductDto::productId))
                .toList();

        sortedRequests.forEach(req -> {
            Product product = productRepository.findByIdWithLock(req.productId())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + req.productId()));

            // 엔티티의 decreaseStock 호출 (여기서 재고 부족 예외가 터짐)
            product.decreaseStock(req.quantity());
        });
    }
}
