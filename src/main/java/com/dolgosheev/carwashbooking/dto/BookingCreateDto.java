package com.dolgosheev.carwashbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCreateDto {
    // Идентификатор пользователя и услуги передаются в виде id,
    // поскольку сущности связываются через внешние ключи
    private Long userId;
    private Long serviceId;
    private LocalDateTime bookingTime;
    private Boolean prepayment;
}
