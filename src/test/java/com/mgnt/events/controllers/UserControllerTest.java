package com.mgnt.events.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.mgnt.events.requests.users.UserCreateRequest;
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
    UUID userId = UUID.fromString(Mocks.Users.ID_JANE);
    UUID roleId = UUID.fromString(Mocks.Roles.ID_ADMIN);
    UserResponse _response = new UserResponse(
      userId,
      Mocks.Users.EMAIL_JANE,
      Mocks.Users.FIRST_NAME_JANE,
      Mocks.Users.LAST_NAME_JANE,
      Mocks.Users.PHONE_PRIMARY,
      true,
      new RoleSummary(roleId, Mocks.Roles.ROLE_NAME_MANAGER),
      LocalDateTime.now(),
      LocalDateTime.now(),
      null
    );
    when(_userService.findAll(10)).thenReturn(List.of(_response));
    _mockMvc
      .perform(get(Routes.USERS).param(Queries.LIMIT, Mocks.Users.PAGINATION))
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(userId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_EMAIL).value(Mocks.Users.EMAIL_JANE))
      .andExpect(jsonPath(JsonPaths.INDEX_0_ROLE_ID).value(roleId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_ROLE_NAME).value(Mocks.Roles.ROLE_NAME_MANAGER));
  }

  @Test
  void create_ShouldDelegateToService() throws Exception {
    UserCreateRequest _request = new UserCreateRequest(
      Mocks.Users.EMAIL_JOHN,
      Mocks.Users.PASSWORD_JOHN,
      Mocks.Users.FIRST_NAME_JOHN,
      Mocks.Users.LAST_NAME_JOHN,
      Mocks.Users.PHONE_SECONDARY,
      7L,
      Boolean.TRUE
    );

    UserResponse _response = new UserResponse(
      101L,
      Mocks.Users.EMAIL_JOHN,
      Mocks.Users.FIRST_NAME_JOHN,
      Mocks.Users.LAST_NAME_JOHN,
      Mocks.Users.PHONE_SECONDARY,
      true,
      new RoleSummary(7L, Mocks.Roles.ROLE_NAME_STAFF),
      LocalDateTime.now(),
      LocalDateTime.now(),
      null
    );

    when(_userService.create(any(UserCreateRequest.class))).thenReturn(_response);

    _mockMvc
      .perform(
        post(Routes.USERS)
        .contentType(
          RequestValidators.requireNonNull(MediaType.APPLICATION_JSON, "Media Type")
        )
        .content(
          RequestValidators.requireNonNull(_objectMapper.writeValueAsString(_request), "Request")
        )
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath(JsonPaths.ID).value(101))
      .andExpect(jsonPath(JsonPaths.EMAIL).value(Mocks.Users.EMAIL_JOHN));

    ArgumentCaptor<UserCreateRequest> captor = ArgumentCaptor.forClass(UserCreateRequest.class);
    verify(_userService).create(captor.capture());

    UserCreateRequest captured = captor.getValue();
    assertThat(captured.email()).isEqualTo(Mocks.Users.EMAIL_JOHN);
    assertThat(captured.roleId()).isEqualTo(7L);
    assertThat(captured.active()).isTrue();
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    _mockMvc.perform(delete(Routes.USERS + Routes.APPEND_ID, 11L)).andExpect(status().isNoContent());
    verify(_userService).delete(eq(11L));
  }
}
