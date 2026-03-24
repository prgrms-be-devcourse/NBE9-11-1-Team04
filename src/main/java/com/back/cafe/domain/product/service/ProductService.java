package com.back.cafe.domain.product.service;

import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public ProductDto create(String name, String category, Long price, int stock, String description, String imageUrl){
        Product product = new Product(name,category,price,stock,description,imageUrl);
        return ProductDto.from(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하고자 하는 상품이 없습니다."));

        productRepository.delete(product);
    }

    @Transactional
    public ProductDto modify(Long id,String name, String category, Long price, int stock, String description, String imageUrl){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("수정하고자 하는 상품이 존재하지 않습니다."));
        product.update(name,category,price,stock,description,imageUrl);
        return ProductDto.from(product);


    }
}
