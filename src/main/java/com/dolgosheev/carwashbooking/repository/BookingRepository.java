package com.dolgosheev.carwashbooking.repository;

import com.dolgosheev.carwashbooking.entity.Booking;
import com.dolgosheev.carwashbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);
}
