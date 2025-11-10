package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.Product;
import com.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock ProductRepository productRepo;

    @InjectMocks ProductService productService;

    @Test
    void testGetProductByIdSuccess() {

        Product p = new Product();
        p.setId(1L);

        when(productRepo.findById(1L)).thenReturn(Optional.of(p));

        Product found = productService.getProductById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }


    @Test
    void testGetProductByIdNotFound() {
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(1L));
    }
}
