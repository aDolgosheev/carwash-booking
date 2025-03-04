package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.ServiceType;
import com.dolgosheev.carwashbooking.repository.ServiceTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddService() {
        ServiceType serviceType = new ServiceType(null, "Мойка кузова", 30, new BigDecimal("10.00"));
        ServiceType savedServiceType = new ServiceType(1L, "Мойка кузова", 30, new BigDecimal("10.00"));
        when(serviceTypeRepository.save(serviceType)).thenReturn(savedServiceType);

        ServiceType result = adminService.addService(serviceType);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(serviceTypeRepository).save(serviceType);
    }

    @Test
    void testGetAllServices() {
        ServiceType service1 = new ServiceType(1L, "Мойка", 30, new BigDecimal("10.00"));
        ServiceType service2 = new ServiceType(2L, "Химчистка", 60, new BigDecimal("20.00"));
        List<ServiceType> services = Arrays.asList(service1, service2);
        when(serviceTypeRepository.findAll()).thenReturn(services);

        List<ServiceType> result = adminService.getAllServices();

        assertEquals(2, result.size());
        verify(serviceTypeRepository).findAll();
    }
}
