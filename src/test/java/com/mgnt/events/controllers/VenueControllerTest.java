package com.mgnt.events.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.events.services.VenueService;

@ExtendWith(MockitoExtension.class)
public class VenueControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private VenueService _venueService;

  @InjectMocks
  private VenueController _venueController;

}
