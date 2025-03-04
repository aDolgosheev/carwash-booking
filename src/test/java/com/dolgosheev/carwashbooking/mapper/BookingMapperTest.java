package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.BookingCreateDto;
import com.dolgosheev.carwashbooking.dto.BookingDto;
import com.dolgosheev.carwashbooking.entity.Booking;
import com.dolgosheev.carwashbooking.entity.BookingStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingMapperTest {
    private BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @Test
    public void testFromCreateDto() {
        BookingCreateDto createDto = new BookingCreateDto(1L, 2L, LocalDateTime.now().plusDays(1), true);
        Booking booking = bookingMapper.fromCreateDto(createDto);
        assertNotNull(booking.getUser());
        assertEquals(createDto.getUserId(), booking.getUser().getId());
        assertNotNull(booking.getService());
        assertEquals(createDto.getServiceId(), booking.getService().getId());
        assertEquals(createDto.getBookingTime(), booking.getBookingTime());
        assertEquals(createDto.getPrepayment(), booking.getPrepayment());
    }

    @Test
    public void testToDto() {
        Booking booking = new Booking();
        booking.setBookingTime(LocalDateTime.now().plusDays(1));
        booking.setPrepayment(true);
        booking.setStatus(BookingStatus.BOOKED);
        // Устанавливаем объекты с id для пользователя и услуги
        booking.setUser(new com.dolgosheev.carwashbooking.entity.User(1L, "1234567890", "test@example.com", "pass", "ROLE_USER"));
        booking.setService(new com.dolgosheev.carwashbooking.entity.ServiceType(2L, "Мойка", 30, null));

        BookingDto dto = bookingMapper.toDto(booking);
        assertEquals(booking.getUser().getId(), dto.getUserId());
        assertEquals(booking.getService().getId(), dto.getServiceId());
        assertEquals(booking.getBookingTime(), dto.getBookingTime());
        assertEquals(booking.getPrepayment(), dto.getPrepayment());
        assertEquals(booking.getStatus(), dto.getStatus());
    }
}
