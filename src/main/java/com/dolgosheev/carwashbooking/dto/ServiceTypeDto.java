package com.dolgosheev.carwashbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceTypeDto {
    private Long id;
    private String name;
    private Integer duration;
    private BigDecimal price;
}
