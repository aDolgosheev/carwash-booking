package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");
        user.setPhone("1234567890");

        // Предположим, что пароль шифруется в "encodedPassword"
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        User savedUser = new User(1L, user.getPhone(), user.getEmail(), "encodedPassword", "ROLE_USER");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.register(user);

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getRole());
        assertEquals("encodedPassword", result.getPassword());

        // Можно использовать ArgumentCaptor для проверки модифицированного объекта
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("ROLE_USER", userCaptor.getValue().getRole());
    }

    @Test
    void testFindByEmailOrPhone_whenEmailFound() {
        String identifier = "test@example.com";
        User user = new User(1L, "1234567890", identifier, "password", "ROLE_USER");
        when(userRepository.findByEmail(identifier)).thenReturn(Optional.of(user));

        Optional<User> result = authService.findByEmailOrPhone(identifier);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByEmailOrPhone_whenEmailNotFoundButPhoneFound() {
        String identifier = "1234567890";
        when(userRepository.findByEmail(identifier)).thenReturn(Optional.empty());
        User user = new User(2L, identifier, "test2@example.com", "password", "ROLE_USER");
        when(userRepository.findByPhone(identifier)).thenReturn(Optional.of(user));

        Optional<User> result = authService.findByEmailOrPhone(identifier);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByEmailOrPhone_whenNeitherFound() {
        String identifier = "nonexistent";
        when(userRepository.findByEmail(identifier)).thenReturn(Optional.empty());
        when(userRepository.findByPhone(identifier)).thenReturn(Optional.empty());

        Optional<User> result = authService.findByEmailOrPhone(identifier);

        assertFalse(result.isPresent());
    }
}