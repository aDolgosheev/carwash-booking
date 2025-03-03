package com.dolgosheev.carwashbooking.repository;

import com.dolgosheev.carwashbooking.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
