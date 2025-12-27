package com.mgnt.events.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mgnt.events.constants.JsonPaths;
import com.mgnt.events.constants.Mocks;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.enums.VenueType;
import com.mgnt.events.requests.venues.VenueRequest;
import com.mgnt.events.responses.venues.VenueResponse;
import com.mgnt.events.services.VenueService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class VenueControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private VenueService _venueService;

  @InjectMocks
  private VenueController _venueController;

  @BeforeEach
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_venueController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnVenueResponses() throws Exception {
    UUID venueId = UUID.randomUUID();
    List<VenueResponse> responses = List.of(
      new VenueResponse(
        venueId,
        Mocks.Venues.NAME,
        Mocks.Venues.ADDRESS,
        Mocks.Venues.CONTACT_PERSON,
        Mocks.Venues.CONTACT_NUMBER,
        Mocks.Venues.EMAIL,
        VenueType.CONFERENCE_HALL,
        new BigDecimal(Mocks.Venues.LATITUDE),
        new BigDecimal(Mocks.Venues.LONGITUDE),
        null,
        LocalDateTime.now(),
        LocalDateTime.now()
      )
    );

    when(_venueService.findAll(5, 1)).thenReturn(responses);
    _mockMvc
      .perform(
        get(Routes.VENUES)
          .param(Queries.LIMIT, Mocks.Venues.PAGINATION_LIMIT)
          .param(Queries.PAGE, Mocks.Venues.PAGINATION_PAGE)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(venueId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Venues.NAME));
  }

  @Test
  void create_ShouldForwardRequestToService() throws Exception {
    VenueRequest request = new VenueRequest(
      Mocks.Venues.NAME,
      Mocks.Venues.ADDRESS,
      Mocks.Venues.CONTACT_PERSON,
      Mocks.Venues.CONTACT_NUMBER,
      Mocks.Venues.EMAIL,
      VenueType.CONFERENCE_HALL,
      new BigDecimal(Mocks.Venues.LATITUDE),
      new BigDecimal(Mocks.Venues.LONGITUDE),
      null
    );
  }
}
