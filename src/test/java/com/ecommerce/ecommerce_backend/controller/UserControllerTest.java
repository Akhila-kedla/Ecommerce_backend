package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.UserDTO;
import com.ecommerce.ecommerce_backend.entity.User;
import com.ecommerce.ecommerce_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @Test
    void testGetUser() throws Exception {

        User u = new User();
        u.setId(1L);
        when(userService.getUserById(1L)).thenReturn(u);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteUser() throws Exception {

        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}
