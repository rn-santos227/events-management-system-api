package com.mgnt.events.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.events.services.VehicleService;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private VehicleService _vehicleService;

  @InjectMocks
  private VehicleController _vehicleController;


  @BeforeEach
  void setUp() {

  }
}
