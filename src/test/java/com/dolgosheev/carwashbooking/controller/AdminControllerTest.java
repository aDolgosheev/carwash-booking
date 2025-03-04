package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.dto.ServiceTypeCreateDto;
import com.dolgosheev.carwashbooking.dto.ServiceTypeDto;
import com.dolgosheev.carwashbooking.mapper.ServiceTypeMapper;
import com.dolgosheev.carwashbooking.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddService() throws Exception {
        ServiceTypeCreateDto createDto = new ServiceTypeCreateDto("Мойка", 30, new BigDecimal("10.00"));
        ServiceTypeDto responseDto = new ServiceTypeDto(1L, "Мойка", 30, new BigDecimal("10.00"));

        // Мокаем преобразования и вызов сервиса
        when(serviceTypeMapper.fromCreateDto(any(ServiceTypeCreateDto.class))).thenReturn(null);
        when(adminService.addService(any())).thenReturn(null);
        when(serviceTypeMapper.toDto(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/admin/services")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetAllServices() throws Exception {
        ServiceTypeDto service1 = new ServiceTypeDto(1L, "Мойка", 30, new BigDecimal("10.00"));
        ServiceTypeDto service2 = new ServiceTypeDto(2L, "Химчистка", 60, new BigDecimal("20.00"));
        List<ServiceTypeDto> services = Arrays.asList(service1, service2);

        // Для простоты мокаем последовательное возвращение значений
        when(adminService.getAllServices()).thenReturn(null);
        when(serviceTypeMapper.toDto(any())).thenReturn(service1, service2);

        mockMvc.perform(get("/api/admin/services"))
                .andExpect(status().isOk());
    }
}
