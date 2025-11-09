package com.mgnt.events.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mgnt.events.services.AuthenticationService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private AuthenticationService _authenticationService;

  @InjectMocks
  private AuthenticationController _authenticationController;

  @BeforeAll
  void setup() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());
  }
}
