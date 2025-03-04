package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.UserCreateDto;
import com.dolgosheev.carwashbooking.dto.UserDto;
import com.dolgosheev.carwashbooking.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToDto() {
        User user = new User(1L, "1234567890", "test@example.com", "password", "ROLE_USER");
        UserDto dto = userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhone(), dto.getPhone());
        assertEquals(user.getRole(), dto.getRole());
    }

    @Test
    public void testFromCreateDto() {
        UserCreateDto createDto = new UserCreateDto("1234567890", "test@example.com", "password");
        User user = userMapper.fromCreateDto(createDto);
        assertEquals(createDto.getEmail(), user.getEmail());
        assertEquals(createDto.getPhone(), user.getPhone());
        assertEquals(createDto.getPassword(), user.getPassword());
    }
}
