package com.dolgosheev.carwashbooking.controller;

import com.dolgosheev.carwashbooking.dto.ServiceTypeDto;
import com.dolgosheev.carwashbooking.entity.ServiceType;
import com.dolgosheev.carwashbooking.mapper.ServiceTypeMapper;
import com.dolgosheev.carwashbooking.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ServiceTypeMapper serviceTypeMapper;

    @PostMapping("/services")
    public ResponseEntity<?> addService(@RequestBody ServiceTypeDto serviceTypeDto) {
        ServiceType serviceType = serviceTypeMapper.toEntity(serviceTypeDto);
        ServiceType savedService = adminService.addService(serviceType);
        return ResponseEntity.ok(serviceTypeMapper.toDto(savedService));
    }

    @GetMapping("/services")
    public ResponseEntity<?> getAllServices() {
        List<ServiceTypeDto> services = adminService.getAllServices()
                .stream().map(serviceTypeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(services);
    }

    // Дополнительные методы для аналитики, управления скидками и пр.
}
