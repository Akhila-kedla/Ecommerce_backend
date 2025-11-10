package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.OrderDTO;
import com.ecommerce.ecommerce_backend.dto.mapper.DTOMapper;
import com.ecommerce.ecommerce_backend.entity.Order;
import com.ecommerce.ecommerce_backend.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<OrderDTO> checkout(
            @PathVariable Long userId,
            @RequestParam String paymentMode) {

        Order order = orderService.checkout(userId, paymentMode);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> userOrders(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getUserOrders(userId)
                .stream()
                .map(DTOMapper::toOrderDTO)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status) {

        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }
}
