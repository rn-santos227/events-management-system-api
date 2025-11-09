package com.mgnt.events.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class TestControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @InjectMocks
  private TestController _testController;

  @BeforeEach()
  void setup() {
    
  }
}
