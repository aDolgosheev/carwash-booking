package com.dolgosheev.carwashbooking.dto;

import com.dolgosheev.carwashbooking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {
    private Long id;
    private Long userId;
    private Long serviceId;
    private LocalDateTime bookingTime;
    private Boolean prepayment;
    private BookingStatus status;
}
