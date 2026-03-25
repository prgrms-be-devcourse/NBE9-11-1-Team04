package com.back.cafe.domain.order.service;

import com.back.cafe.domain.order.dto.AdminOrderProductDto;
import com.back.cafe.domain.order.dto.AdminOrderResponseDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.entity.OrderProduct;
import com.back.cafe.domain.order.entity.OrderStatus;
import com.back.cafe.domain.order.repository.OrderRepository;
import com.back.cafe.domain.product.entity.Product;
import com.back.cafe.domain.product.repository.ProductRepository;
import com.back.cafe.domain.product.service.ProductService;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public AdminOrderResponseDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        order.setStatus(OrderStatus.CANCELLED.name());
        productService.restoreStock(order.getOrderProducts());

        return toDto(order);
    }

    @Transactional
    public AdminOrderResponseDto updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(orderStatus.name());

        return toDto(order);
    }

    public Page<AdminOrderResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::toDto);
    }

    public Page<AdminOrderResponseDto> findByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(this::toDto);
    }

    public Page<AdminOrderResponseDto> findByOrderedAt(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        return orderRepository.findByCreatedAtBetween(start, end, pageable)
                .map(this::toDto);
    }

    private AdminOrderResponseDto toDto(Order order) {
        String address = userRepository.findById(order.getUserId())
                .map(SiteUser::getAddress)
                .orElse(null);

        List<Long> productIds = order.getOrderProducts().stream()
                .map(OrderProduct::getProductId)
                .distinct()
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<AdminOrderProductDto> orderProducts = order.getOrderProducts().stream()
                .map(orderProduct -> new AdminOrderProductDto(
                        orderProduct.getProductId(),
                        getProductName(productMap, orderProduct.getProductId()),
                        orderProduct.getQuantity()
                ))
                .toList();

        return new AdminOrderResponseDto(
                order.getId(),
                order.getUserId(),
                address,
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus(),
                orderProducts
        );
    }

    private String getProductName(Map<Long, Product> productMap, Long productId) {
        Product product = productMap.get(productId);
        return product != null ? product.getName() : null;
    }
}
