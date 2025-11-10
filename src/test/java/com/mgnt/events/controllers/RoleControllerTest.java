package com.mgnt.events.controllers;

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
import com.mgnt.events.constants.Mocks;
import com.mgnt.events.responses.privileges.PrivilegeSummary;
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
  }
}
