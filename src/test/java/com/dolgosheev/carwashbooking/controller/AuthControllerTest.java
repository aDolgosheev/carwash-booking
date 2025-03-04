package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    // Мокаем сервис аутентификации
    @MockBean
    private com.dolgosheev.carwashbooking.service.AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLogin() throws Exception {
        User loginRequest = new User();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        // Мокаем вызов authenticationManager.authenticate
        Mockito.doNothing().when(authenticationManager).authenticate(
                Mockito.any(UsernamePasswordAuthenticationToken.class)
        );
        // Мокаем генерацию токена
        Mockito.when(jwtUtil.generateToken(Mockito.any())).thenReturn("dummy-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("dummy-jwt-token"));
    }
}
