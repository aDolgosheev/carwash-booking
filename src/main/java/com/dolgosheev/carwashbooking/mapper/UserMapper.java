package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.UserCreateDto;
import com.dolgosheev.carwashbooking.dto.UserDto;
import com.dolgosheev.carwashbooking.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User fromCreateDto(UserCreateDto dto);
}