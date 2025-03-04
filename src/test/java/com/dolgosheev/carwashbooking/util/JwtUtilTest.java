package com.dolgosheev.carwashbooking.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        // Устанавливаем тестовые значения
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "MyJwtSecretKey123456");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", 3600000L); // 1 час
    }

    @Test
    public void testGenerateAndExtractUsername() {
        UserDetails userDetails = User.withUsername("test@example.com")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtUtil.extractUsername(token);
        assertEquals("test@example.com", username);
    }

    @Test
    public void testValidateToken() {
        UserDetails userDetails = User.withUsername("test@example.com")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }
}
