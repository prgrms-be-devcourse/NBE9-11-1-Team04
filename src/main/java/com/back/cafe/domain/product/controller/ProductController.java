package com.back.cafe.domain.product.controller;

import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import com.back.cafe.domain.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ProductDto getProduct(@PathVariable Long id){
        return productService.findProductById(id);
    }
}
