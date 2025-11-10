package com.mgnt.events.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.events.services.RoleService;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private RoleService _roleService;

  @InjectMocks
  private RoleController _roleController;

  @BeforeEach
  void setUp() {

  }
}
