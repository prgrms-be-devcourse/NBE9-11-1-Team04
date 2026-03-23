package com.back.cafe.domain.product.controller;
import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.service.ProductService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    record ProductCreateReq(
            @NotBlank String name,
            @NotBlank String category,
            @NotNull Long price,
            @NotNull Integer stock,
            @NotBlank String description,
            @NotBlank String imageUrl
    ) {}

    @PostMapping
    @Transactional
    @Operation(summary = "상품 생성")
    public RsData<ProductDto> createProduct(
            @RequestBody @Valid ProductCreateReq req
    ) {
        ProductDto productDto = productService.create(
                req.name(),
                req.category(),
                req.price(),
                req.stock(),
                req.description(),
                req.imageUrl()
        );

        return new RsData<>(
                "%d번 상품이 생성되었습니다".formatted(productDto.id()),
                "201-1",
                productDto
        );
    }
}
