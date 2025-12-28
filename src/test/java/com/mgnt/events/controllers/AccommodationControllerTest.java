package com.mgnt.events.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mgnt.events.constants.JsonPaths;
import com.mgnt.events.constants.Mocks;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.enums.AccommodationType;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
import com.mgnt.events.services.AccommodationService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class AccommodationControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private AccommodationService _accommodationService;

  @InjectMocks
  private AccommodationController _accommodationController;


  @BeforeEach
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_accommodationController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnAccommodationResponses() throws Exception {
    UUID accommodationId = UUID.randomUUID();
    List<AccommodationResponse> responses = List.of(
      new AccommodationResponse(
        accommodationId,
        Mocks.Accommodations.NAME,
        Mocks.Accommodations.ADDRESS,
        Mocks.Accommodations.CONTACT_PERSON,
        Mocks.Accommodations.CONTACT_NUMBER,
        Mocks.Accommodations.EMAIL,
        AccommodationType.HOTEL,
        new BigDecimal(Mocks.Accommodations.LATITUDE),
        new BigDecimal(Mocks.Accommodations.LONGITUDE),
        null,
        LocalDateTime.now(),
        LocalDateTime.now()
      )
    );


    when(_accommodationService.findAll(5, 1)).thenReturn(responses);

    _mockMvc
      .perform(
        get(Routes.ACCOMMODATIONS)
          .param(Queries.LIMIT, Mocks.Accommodations.PAGINATION_LIMIT)
          .param(Queries.PAGE, Mocks.Accommodations.PAGINATION_PAGE)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(accommodationId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Accommodations.NAME));
  }

  @Test
  void create_ShouldForwardRequestToService() throws Exception {

  }
}
