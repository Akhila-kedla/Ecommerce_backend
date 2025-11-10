package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.User;
import com.ecommerce.ecommerce_backend.exception.BadRequestException;
import com.ecommerce.ecommerce_backend.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;


    @Test
    void testRegisterUserSuccess() {

        User u = new User();
        u.setEmail("test@gmail.com");
        u.setPassword("123");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("ENCODED");

        when(userRepository.save(any(User.class))).thenReturn(u);

        User saved = userService.registerUser(u);

        assertNotNull(saved);
        verify(userRepository, times(1)).save(any());
    }


    @Test
    void testRegisterUser_DuplicateEmail_ThrowsException() {

        User u = new User();
        u.setEmail("test@gmail.com");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> userService.registerUser(u)
        );
    }
}
