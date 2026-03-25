package com.back.cafe.domain.product.controller;

import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.product.dto.ProductModifyRequest;
import com.back.cafe.domain.product.service.ProductService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "상품 API", description = "상품 관리를 위한 API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "상품 목록 조회",description = "전체 상품목록을 조회합니다")
    public List<ProductDto> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "상품 단건 조회",description = "상품ID로 상품을 단건 조회합니다")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    record ProductCreateReq(
            @NotBlank String name,
            @NotBlank String category,
            @NotNull Long price,
            @NotNull Integer stock,
            @NotBlank String description,
            @NotBlank String imageUrl
    ) {
    }

    @PostMapping
    @Operation(summary = "신규 상품 생성", description = "관리자가 상품을 신규 추가 합니다.")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "관리자 상품 제거",description = "관리자가 기존의 상품을 ID를 받아 삭제합니다.")
    public RsData<Void> deleteProduct(@PathVariable long id) {

        productService.delete(id);

        return new RsData<>(
                "%d번 상품이 삭제 되었습니다".formatted(id),
                "200-1"
        );
    }


    @PutMapping("/{id}")
    @Operation(summary = "관리자 상품 수정", description = "관리자가 기존의 상품ID와 수정할 정보를 받아 수정합니다")
    public RsData<ProductDto> modify(
            @PathVariable Long id,
            @RequestBody @Valid ProductModifyRequest reqBody
    ) {
        ProductDto productDto = productService.modify(id,reqBody.toServiceDto());

        return new RsData<>(
                "%d번 상품이 수정되었습니다.".formatted(productDto.id()),
                "200-1",
                productDto
        );

    }
}
