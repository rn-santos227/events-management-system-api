package com.mgnt.events.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

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
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.roles.RoleSummary;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.services.UserService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private UserService _userService;

  @InjectMocks
  private UserController _userController;

  @BeforeEach
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_userController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnUsers() throws Exception {
    UserResponse _response = new UserResponse(
      42L,
      Mocks.Users.EMAIL_JANE,
      Mocks.Users.FIRST_NAME_JANE,
      Mocks.Users.LAST_NAME_JANE,
      Mocks.Users.PHONE_PRIMARY,
      true,
      new RoleSummary(5L, Mocks.Roles.ROLE_NAME_MANAGER),
      LocalDateTime.now(),
      LocalDateTime.now(),
      null
    );
    when(_userService.findAll(10)).thenReturn(List.of(_response));
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    _mockMvc.perform(delete(Routes.USERS + Routes.APPEND_ID, 11L)).andExpect(status().isNoContent());
    verify(_userService).delete(eq(11L));
  }
}
