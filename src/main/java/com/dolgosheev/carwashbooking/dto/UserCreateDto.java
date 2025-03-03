package com.dolgosheev.carwashbooking.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    private String phone;
    private String email;
    private String password;
}

