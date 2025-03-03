package com.dolgosheev.carwashbooking.mapper;

import com.dolgosheev.carwashbooking.dto.BookingCreateDto;
import com.dolgosheev.carwashbooking.dto.BookingDto;
import com.dolgosheev.carwashbooking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toDto(Booking booking);

    @Mapping(target = "id", ignore = true)
    // Создаём объект User с указанным id; остальные поля оставляем null
    @Mapping(target = "user", expression = "java(new User(bookingCreateDto.getUserId(), null, null, null, null))")
    // Создаём объект ServiceType с указанным id; остальные поля оставляем null
    @Mapping(target = "service", expression = "java(new ServiceType(bookingCreateDto.getServiceId(), null, null, null))")
    Booking fromCreateDto(BookingCreateDto bookingCreateDto);
}