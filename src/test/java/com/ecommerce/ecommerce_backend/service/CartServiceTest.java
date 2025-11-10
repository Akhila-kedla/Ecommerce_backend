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
class CartServiceTest {

    @Mock CartRepository cartRepo;
    @Mock ProductRepository productRepo;
    @Mock CartItemRepository cartItemRepo;
    @Mock UserRepository userRepo;

    @InjectMocks CartService cartService;

    @Test
    void testGetOrCreateCart() {

        User u = new User();
        u.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(cartRepo.findByUser(u)).thenReturn(Optional.empty());
        when(cartRepo.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart c = cartService.getOrCreateCart(1L);

        assertNotNull(c);
        assertEquals(u, c.getUser());
    }
}
