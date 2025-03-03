package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.Booking;
import com.dolgosheev.carwashbooking.entity.BookingStatus;
import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.BOOKED);
        // Здесь можно добавить логику проверки доступного времени, скидок и уведомлений
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));
        // Если отмена менее чем за 3 часа – можно добавить штрафное действие
        if (booking.getBookingTime().minusHours(3).isBefore(LocalDateTime.now())) {
            booking.setStatus(BookingStatus.CANCELLED);
            // Логика для блокировки предоплаты при частых отменах может быть реализована на уровне пользователя
        }
        return bookingRepository.save(booking);
    }
}
