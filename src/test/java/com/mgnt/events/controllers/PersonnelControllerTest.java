package com.mgnt.events.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.events.services.PersonnelService;

@ExtendWith(MockitoExtension.class)
public class PersonnelControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private PersonnelService _personnelService;

  @InjectMocks
  private PersonnelController _personnelController;
}
