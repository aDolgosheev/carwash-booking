package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.dto.BookingCreateDto;
import com.dolgosheev.carwashbooking.dto.BookingDto;
import com.dolgosheev.carwashbooking.entity.BookingStatus;
import com.dolgosheev.carwashbooking.mapper.BookingMapper;
import com.dolgosheev.carwashbooking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private BookingMapper bookingMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBooking() throws Exception {
        BookingCreateDto bookingCreateDto = new BookingCreateDto(1L, 1L, LocalDateTime.now().plusDays(1), true);

        // Создаём dummy-объекты для entity и response DTO
        com.dolgosheev.carwashbooking.entity.Booking bookingEntity = new com.dolgosheev.carwashbooking.entity.Booking();
        bookingEntity.setPrepayment(true);
        bookingEntity.setBookingTime(bookingCreateDto.getBookingTime());
        bookingEntity.setStatus(BookingStatus.BOOKED);

        BookingDto bookingDto = new BookingDto(1L, 1L, 1L, bookingCreateDto.getBookingTime(), true, BookingStatus.BOOKED);

        when(bookingMapper.fromCreateDto(any(BookingCreateDto.class))).thenReturn(bookingEntity);
        when(bookingService.createBooking(any(com.dolgosheev.carwashbooking.entity.Booking.class))).thenReturn(bookingEntity);
        when(bookingMapper.toDto(any(com.dolgosheev.carwashbooking.entity.Booking.class))).thenReturn(bookingDto);

        mockMvc.perform(post("/api/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
