package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.entity.Cart;
import com.ecommerce.ecommerce_backend.entity.User;
import com.ecommerce.ecommerce_backend.service.CartService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security in unit test
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartService cartService;

    private Cart emptyCart() {
        Cart cart = new Cart();
        cart.setId(1L);

        User u = new User();
        u.setId(1L);
        u.setEmail("test@example.com");
        u.setName("Test");
        u.setPassword("x"); 
        cart.setUser(u);

        cart.setCartItems(new ArrayList<>()); 
        cart.setTotalPrice(0.0);
        return cart;
    }

    @Test
    void testGetCart() throws Exception {
        when(cartService.getUserCart(1L)).thenReturn(emptyCart());

        mockMvc.perform(get("/api/cart/1"))
               .andDo(print())               
               .andExpect(status().isOk());
    }

    @Test
    void testAddToCart() throws Exception {
        when(cartService.addProductToCart(1L, 2L, 1)).thenReturn(emptyCart());

        mockMvc.perform(post("/api/cart/1/add/2").param("quantity", "1"))
               .andDo(print())
               .andExpect(status().isOk());
    }
}
