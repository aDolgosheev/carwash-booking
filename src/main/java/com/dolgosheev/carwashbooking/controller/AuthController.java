package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.service.AuthService;
import com.dolgosheev.carwashbooking.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Аутентификация по email или телефону
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final String token = jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(loginRequest.getEmail())
                        .password(loginRequest.getPassword())
                        .authorities("ROLE_USER")
                        .build()
        );
        return ResponseEntity.ok(token);
    }
}
