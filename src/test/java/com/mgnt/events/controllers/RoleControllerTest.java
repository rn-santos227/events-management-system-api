package com.mgnt.events.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.mgnt.events.requests.roles.RoleRequest;
import com.mgnt.events.responses.privileges.PrivilegeSummary;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private RoleService _roleService;

  @InjectMocks
  private RoleController _roleController;

  @BeforeEach
  void setup() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_roleController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_shoudReturnRoleResponses() throws Exception {
    Set<PrivilegeSummary> _privileges = new LinkedHashSet<>(
      List.of(new PrivilegeSummary(1L, 
        Mocks.Roles.PRIVILEGE_MANAGE_USERS, 
        Mocks.Roles.PRIVILEGE_ACTION_WRITE, 
        Mocks.Roles.PRIVILEGE_SCOPE_USERS
      ))
    );

    List<RoleResponse> responses = List.of(
      new RoleResponse(1L, Mocks.Roles.NAME_ADMIN, _privileges, LocalDateTime.now(), LocalDateTime.now())
    );
    when(_roleService.findAll(5)).thenReturn(responses);
    _mockMvc
      .perform(get(Routes.ROLES).param(Queries.LIMIT, Mocks.Roles.PAGINATION))
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(1))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Roles.NAME_ADMIN))
      .andExpect(jsonPath(JsonPaths.INDEX_0_PRIVILEGES).value(Mocks.Roles.PRIVILEGE_MANAGE_USERS));
  }

  @Test
  void create_ShouldForwardRequestToService() throws Exception {
    RoleRequest request = new RoleRequest(Mocks.Roles.NAME_ADMIN, Set.of(1L, 2L));
    RoleResponse response = new RoleResponse(1L, Mocks.Roles.NAME_ADMIN, Set.of(), LocalDateTime.now(), LocalDateTime.now());
    when(_roleService.create(any(RoleRequest.class))).thenReturn(response);
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    _mockMvc.perform(delete(Routes.ROLES + Routes.APPEND_ID, 7L)).andExpect(status().isNoContent());
    verify(_roleService).delete(eq(7L));
  }
}
