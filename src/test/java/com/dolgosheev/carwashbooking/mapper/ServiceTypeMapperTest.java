package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.ServiceTypeCreateDto;
import com.dolgosheev.carwashbooking.dto.ServiceTypeDto;
import com.dolgosheev.carwashbooking.entity.ServiceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTypeMapperTest {
    private ServiceTypeMapper serviceTypeMapper = Mappers.getMapper(ServiceTypeMapper.class);

    @Test
    public void testFromCreateDto() {
        ServiceTypeCreateDto createDto = new ServiceTypeCreateDto("Мойка", 30, new BigDecimal("10.00"));
        ServiceType serviceType = serviceTypeMapper.fromCreateDto(createDto);
        assertEquals(createDto.getName(), serviceType.getName());
        assertEquals(createDto.getDuration(), serviceType.getDuration());
        assertEquals(createDto.getPrice(), serviceType.getPrice());
    }

    @Test
    public void testToDto() {
        ServiceType serviceType = new ServiceType(1L, "Мойка", 30, new BigDecimal("10.00"));
        ServiceTypeDto dto = serviceTypeMapper.toDto(serviceType);
        assertEquals(serviceType.getId(), dto.getId());
        assertEquals(serviceType.getName(), dto.getName());
        assertEquals(serviceType.getDuration(), dto.getDuration());
        assertEquals(serviceType.getPrice(), dto.getPrice());
    }
}
