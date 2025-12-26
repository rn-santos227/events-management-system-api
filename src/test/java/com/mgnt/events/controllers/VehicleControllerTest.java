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
import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;
import com.mgnt.events.requests.vehicles.VehicleRequest;
import com.mgnt.events.responses.vehicles.VehiclePersonnelSummary;
import com.mgnt.events.responses.vehicles.VehicleResponse;
import com.mgnt.events.services.VehicleService;
import com.mgnt.events.util.RequestValidators;

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
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_vehicleController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnVehicleResponses() throws Exception {
    UUID vehicleId = UUID.randomUUID();
    UUID personnelId = UUID.randomUUID();
    List<VehicleResponse> responses = List.of(
      new VehicleResponse(
        vehicleId,
        Mocks.Vehicles.NAME,
        VehicleType.VAN,
        VehicleStatus.AVAILABLE,
        Mocks.Vehicles.PLATE_NUMBER,
        Mocks.Vehicles.CONTACT_NUMBER,
        new VehiclePersonnelSummary(
          personnelId,
          Mocks.Vehicles.ASSIGNED_PERSONNEL_NAME,
          Mocks.Vehicles.ASSIGNED_PERSONNEL_CONTACT,
          Mocks.Vehicles.ASSIGNED_PERSONNEL_FUNCTION
        ),
        LocalDateTime.now(),
        LocalDateTime.now()
      )
    );

    when(_vehicleService.findAll(5, 0)).thenReturn(responses);

    _mockMvc
      .perform(
        get(Routes.VEHICLES)
          .param(Queries.LIMIT, Mocks.Vehicles.PAGINATION_LIMIT)
          .param(Queries.PAGE, Mocks.Vehicles.PAGINATION_PAGE)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(vehicleId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Vehicles.NAME));
  }

  @Test
  void create_ShouldForwardRequestToService() throws Exception {
    UUID assignedPersonnelId = UUID.randomUUID();
    VehicleRequest request = new VehicleRequest(
      Mocks.Vehicles.NAME,
      VehicleType.VAN,
      VehicleStatus.AVAILABLE,
      Mocks.Vehicles.PLATE_NUMBER,
      Mocks.Vehicles.CONTACT_NUMBER,
      assignedPersonnelId
    );

    UUID vehicleId = UUID.randomUUID();
    VehicleResponse response = new VehicleResponse(
      vehicleId,
      request.name(),
      request.type(),
      request.status(),
      request.plateNumber(),
      request.contactNumber(),
      new VehiclePersonnelSummary(
        assignedPersonnelId,
        Mocks.Vehicles.ASSIGNED_PERSONNEL_NAME,
        Mocks.Vehicles.ASSIGNED_PERSONNEL_CONTACT,
        Mocks.Vehicles.ASSIGNED_PERSONNEL_FUNCTION
      ),
      LocalDateTime.now(),
      LocalDateTime.now()
    );

    when(_vehicleService.create(any(VehicleRequest.class))).thenReturn(response);
    _mockMvc
      .perform(
        post(Routes.VEHICLES)
        .contentType(RequestValidators.requireNonNull(MediaType.APPLICATION_JSON, "Media Type"))
        .content(RequestValidators.requireNonNull(_objectMapper.writeValueAsString(request), "Request"))
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath(JsonPaths.ID).value(vehicleId.toString()))
      .andExpect(jsonPath(JsonPaths.NAME).value(Mocks.Vehicles.NAME));

    ArgumentCaptor<VehicleRequest> captor = ArgumentCaptor.forClass(VehicleRequest.class);
    verify(_vehicleService, times(1)).create(captor.capture());
    VehicleRequest captured = captor.getValue();
    assertThat(captured.name()).isEqualTo(Mocks.Vehicles.NAME);
    assertThat(captured.assignedPersonnelId()).isEqualTo(assignedPersonnelId);
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    UUID vehicleId = UUID.randomUUID();
    _mockMvc
      .perform(delete(Routes.VEHICLES + Routes.APPEND_ID, vehicleId))
      .andExpect(status().isNoContent());
    verify(_vehicleService).delete(RequestValidators.requireNonNull(vehicleId, "Vehicle ID"));
  }
}
