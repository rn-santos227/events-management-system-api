package com.mgnt.events.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;
}
