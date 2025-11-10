package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.*;
import com.ecommerce.ecommerce_backend.repository.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock OrderRepository orderRepo;
    @Mock CartRepository cartRepo;
    @Mock CartItemRepository cartItemRepo;
    @Mock ProductRepository productRepo;
    @Mock UserRepository userRepo;
    @Mock OrderItemRepository orderItemRepo;

    @InjectMocks OrderService orderService;


    @Test
    void testCheckout_EmptyCart_ThrowsException() {

        User u = new User();
        u.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(cartRepo.findByUser(u)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> orderService.checkout(1L, "UPI"));
    }
}
