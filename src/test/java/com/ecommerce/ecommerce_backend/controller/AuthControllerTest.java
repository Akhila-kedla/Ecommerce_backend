package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.service.UserService;
import com.ecommerce.ecommerce_backend.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;  


    @Test
    void testRegister() throws Exception {

        when(userService.registerUser(any(User.class)))
                .thenReturn(new User());

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content("{\"email\":\"test@gmail.com\",\"password\":\"123\"}"))
                .andExpect(status().isOk());
    }
}
