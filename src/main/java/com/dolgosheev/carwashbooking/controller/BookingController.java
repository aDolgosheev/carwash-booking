package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.dto.BookingDto;
import com.dolgosheev.carwashbooking.entity.Booking;
import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.mapper.BookingMapper;
import com.dolgosheev.carwashbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(bookingMapper.toDto(createdBooking));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable Long userId) {
        // В реальном приложении идентификация пользователя должна производиться через JWT
        User user = new User();
        user.setId(userId);
        List<BookingDto> bookings = bookingService.getBookingsByUser(user)
                .stream().map(bookingMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }
}
