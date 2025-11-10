package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.CartDTO;
import com.ecommerce.ecommerce_backend.dto.mapper.DTOMapper;
import com.ecommerce.ecommerce_backend.entity.Cart;
import com.ecommerce.ecommerce_backend.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> get(@PathVariable Long userId) {
        Cart cart = cartService.getUserCart(userId);
        return ResponseEntity.ok(DTOMapper.toCartDTO(cart));
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartDTO> add(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(DTOMapper.toCartDTO(cart));
    }

    @PutMapping("/{userId}/update/{productId}")
    public ResponseEntity<CartDTO> update(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Cart cart = cartService.updateItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(DTOMapper.toCartDTO(cart));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<CartDTO> remove(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        Cart cart = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(DTOMapper.toCartDTO(cart));
    }
}
