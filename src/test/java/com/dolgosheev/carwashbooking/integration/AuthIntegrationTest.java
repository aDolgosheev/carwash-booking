package com.dolgosheev.carwashbooking.integration;

import com.dolgosheev.carwashbooking.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserRegistrationAndLogin() throws Exception {
        // Создаем нового пользователя для регистрации.
        User user = new User();
        user.setEmail("integration@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        // Регистрируем пользователя
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь зарегистрирован"));

        // Выполняем логин: здесь используется тот же объект user,
        // предполагается, что пароль совпадает.
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                // Проверяем, что получен непустой JWT-токен
                .andExpect(content().string(Matchers.not(Matchers.isEmptyOrNullString())));
    }
}
