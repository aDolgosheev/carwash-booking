package com.dolgosheev.carwashbooking.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Пользователь, сделавший бронирование
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Выбранная услуга
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceType service;

    // Время бронирования
    private LocalDateTime bookingTime;

    // Флаг предоплаты
    private Boolean prepayment;

    // Статус бронирования (BOOKED, CANCELLED, COMPLETED)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
