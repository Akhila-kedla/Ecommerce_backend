package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.OrderDTO;
import com.ecommerce.ecommerce_backend.dto.mapper.DTOMapper;
import com.ecommerce.ecommerce_backend.entity.Order;
import com.ecommerce.ecommerce_backend.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @Test
    void checkoutTest() throws Exception {

        Order order = new Order();
        order.setId(1L);
        order.setPaymentMode("UPI");

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);

        when(orderService.checkout(1L, "UPI")).thenReturn(order);

        try (MockedStatic<DTOMapper> mocked = mockStatic(DTOMapper.class)) {

            mocked.when(() -> DTOMapper.toOrderDTO(order)).thenReturn(orderDTO);

            mockMvc.perform(post("/api/orders/checkout/1")
                    .param("paymentMode", "UPI")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
        }
    }
}
