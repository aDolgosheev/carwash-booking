package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.ServiceTypeCreateDto;
import com.dolgosheev.carwashbooking.dto.ServiceTypeDto;
import com.dolgosheev.carwashbooking.entity.ServiceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {
    ServiceTypeDto toDto(ServiceType serviceType);
    ServiceType fromCreateDto(ServiceTypeCreateDto serviceTypeCreateDto);
}