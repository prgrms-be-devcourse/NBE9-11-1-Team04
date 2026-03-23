package com.back.cafe.domain.product.controller;
import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "ApiV1ProductController", description = "상품 API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<ProductDto> getProducts(){
        return productService.findAll();
    }

   @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public ProductDto getProduct(@PathVariable Long id){
        return productService.findById(id);
    }
}
