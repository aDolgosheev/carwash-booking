package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        // Шифруем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public Optional<User> findByEmailOrPhone(String identifier) {
        Optional<User> userOpt = userRepository.findByEmail(identifier);
        return userOpt.isPresent() ? userOpt : userRepository.findByPhone(identifier);
    }
}
