package com.ecommerce.ecommerce_backend.dto.mapper;

import com.ecommerce.ecommerce_backend.dto.*;
import com.ecommerce.ecommerce_backend.entity.*;

import java.util.stream.Collectors;

public class DTOMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public static ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageurl());
        dto.setRating(product.getRating());
        return dto;
    }

    public static CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        return dto;
    }

    public static CartDTO toCartDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setItems(
                cart.getCartItems()
                        .stream()
                        .map(DTOMapper::toCartItemDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public static OrderItemDTO toOrderItemDTO(OrderItem oi) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(oi.getProduct().getId());
        dto.setProductName(oi.getProduct().getName());
        dto.setQuantity(oi.getQuantity());
        dto.setPrice(oi.getPrice());
        return dto;
    }

    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setPaymentStatus(order.getPaymentStatus().name());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setPaymentMode(order.getPaymentMode());
        dto.setOrderDate(order.getOrderDate());
        dto.setItems(
                order.getOrderItems()
                        .stream()
                        .map(DTOMapper::toOrderItemDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
