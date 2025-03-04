package com.dolgosheev.carwashbooking.integration;

import com.dolgosheev.carwashbooking.dto.BookingCreateDto;
import com.dolgosheev.carwashbooking.dto.ServiceTypeCreateDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
public class BookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBooking() throws Exception {
        // Предполагаем, что пользователь уже зарегистрирован и имеет id = 1.
        // Если требуется, можно добавить шаг регистрации здесь.

        // Создаем услугу через endpoint администрирования
        ServiceTypeCreateDto serviceCreateDto = new ServiceTypeCreateDto("Booking Service", 30, new BigDecimal("15.00"));
        String serviceResponse = mockMvc.perform(post("/api/admin/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCreateDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Извлекаем id созданной услуги
        JsonNode serviceJson = objectMapper.readTree(serviceResponse);
        Long serviceId = serviceJson.get("id").asLong();
        assertNotNull(serviceId);

        // Создаем бронирование
        BookingCreateDto bookingCreateDto = new BookingCreateDto(1L, serviceId, LocalDateTime.now().plusDays(1), true);

        String bookingResponse = mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        JsonNode bookingJson = objectMapper.readTree(bookingResponse);
        assertNotNull(bookingJson.get("id"));
    }

    @Test
    public void testGetUserBookings() throws Exception {
        // Предполагаем, что у пользователя с id=1 уже есть бронирования.
        // Выполняем GET запрос для получения бронирований пользователя.
        mockMvc.perform(get("/api/bookings/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
