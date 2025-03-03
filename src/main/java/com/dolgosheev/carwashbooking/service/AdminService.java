package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.ServiceType;
import com.dolgosheev.carwashbooking.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceType addService(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public List<ServiceType> getAllServices() {
        return serviceTypeRepository.findAll();
    }

    // Дополнительные методы для редактирования услуг, управления скидками, аналитики и т.д.
}
