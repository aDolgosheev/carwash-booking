package com.dolgosheev.carwashbooking.integration;

import com.dolgosheev.carwashbooking.dto.ServiceTypeCreateDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
public class AdminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddServiceAndGetAllServices() throws Exception {
        // Создаем услугу
        ServiceTypeCreateDto serviceCreateDto = new ServiceTypeCreateDto("Test Service", 30, new BigDecimal("12.50"));
        String serviceResponse = mockMvc.perform(post("/api/admin/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        JsonNode serviceJson = objectMapper.readTree(serviceResponse);
        Long serviceId = serviceJson.get("id").asLong();
        assertNotNull(serviceId);

        // Получаем список всех услуг
        mockMvc.perform(get("/api/admin/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()));
    }
}
