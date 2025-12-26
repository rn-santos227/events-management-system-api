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
import com.mgnt.events.requests.personnel.PersonnelRequest;
import com.mgnt.events.responses.personnel.PersonnelResponse;
import com.mgnt.events.services.PersonnelService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class PersonnelControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private PersonnelService _personnelService;

  @InjectMocks
  private PersonnelController _personnelController;

  @BeforeEach
  void setup() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_personnelController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnPersonnelResponses() throws Exception {
    UUID personnelId = UUID.randomUUID();
    List<PersonnelResponse> responses = List.of(
      new PersonnelResponse(
        personnelId,
        Mocks.Personnel.FULL_NAME,
        Mocks.Personnel.CONTACT_NUMBER,
        Mocks.Personnel.EMAIL,
        Mocks.Personnel.ROLE,
        LocalDateTime.now(),
        LocalDateTime.now()
      )
    );

    when(_personnelService.findAll(5, 0)).thenReturn(responses);

    _mockMvc
      .perform(
        get(Routes.PERSONNEL)
          .param(Queries.LIMIT, Mocks.Personnel.PAGINATION_LIMIT)
          .param(Queries.PAGE, Mocks.Personnel.PAGINATION_PAGE)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(personnelId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Personnel.FULL_NAME));
  }

  @Test
  void create_ShouldForwardRequestToService() throws Exception {
    PersonnelRequest request = new PersonnelRequest(
      Mocks.Personnel.FULL_NAME,
      Mocks.Personnel.CONTACT_NUMBER,
      Mocks.Personnel.EMAIL,
      Mocks.Personnel.ROLE
    );

    UUID personnelId = UUID.randomUUID();
    PersonnelResponse response = new PersonnelResponse(
      personnelId,
      request.fullName(),
      request.contactNumber(),
      request.email(),
      request.role(),
      LocalDateTime.now(),
      LocalDateTime.now()
    );

    when(_personnelService.create(any(PersonnelRequest.class))).thenReturn(response);

    _mockMvc
      .perform(
        post(Routes.PERSONNEL)
        .contentType(RequestValidators.requireNonNull(MediaType.APPLICATION_JSON, "Media Type"))
        .content(RequestValidators.requireNonNull(_objectMapper.writeValueAsString(request), "Request"))
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath(JsonPaths.ID).value(personnelId.toString()))
      .andExpect(jsonPath(JsonPaths.NAME).value(Mocks.Personnel.FULL_NAME));

    ArgumentCaptor<PersonnelRequest> captor = ArgumentCaptor.forClass(PersonnelRequest.class);
    verify(_personnelService, times(1)).create(captor.capture());
    PersonnelRequest captured = captor.getValue();
    assertThat(captured.fullName()).isEqualTo(Mocks.Personnel.FULL_NAME);
    assertThat(captured.contactNumber()).isEqualTo(Mocks.Personnel.CONTACT_NUMBER);
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    UUID personnelId = UUID.randomUUID();
    _mockMvc
      .perform(delete(Routes.PERSONNEL + Routes.APPEND_ID, personnelId))
      .andExpect(status().isNoContent());
    verify(_personnelService).delete(RequestValidators.requireNonNull(personnelId, "Personnel ID"));
  }
}
