package com.mgnt.events.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
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
import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.auth.LoginRequest;
import com.mgnt.events.responses.auth.LoginResponse;
import com.mgnt.events.services.AuthenticationService;
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private AuthenticationService _authenticationService;

  @InjectMocks
  private AuthenticationController _authenticationController;

  @BeforeAll
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_authenticationController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void login_ShouldReturnAccessToken() throws Exception {
    LoginResponse response = new LoginResponse(Mocks.Auth.TOKEN_VALUE, Mocks.Auth.TOKEN_TYPE, 1_720_000_000L);
    when(_authenticationService.authenticate(any(LoginRequest.class))).thenReturn(response);

    LoginRequest request = new LoginRequest();
    request.setEmail(Mocks.Auth.EMAIL);
    request.setPassword(Mocks.Auth.PASSWORD);

    _mockMvc.perform(
      post(Routes.AUTH_LOGIN)
        .contentType(
          RequestValidators.requireNonNull(MediaType.APPLICATION_JSON, "Media Type")
        )
        .content(
          RequestValidators.requireNonNull(_objectMapper.writeValueAsString(request), "Request")
        )
    )
    .andExpect(status().isOk())
    .andExpect(jsonPath(JsonPaths.ACCESS_TOKEN).value(Mocks.Auth.TOKEN_VALUE))
    .andExpect(jsonPath(JsonPaths.TOKEN_TYPE).value(Mocks.Auth.TOKEN_TYPE))
    .andExpect(jsonPath(JsonPaths.EXPIRES_AT).value(1_720_000_000L));

    ArgumentCaptor<LoginRequest> requestCaptor = ArgumentCaptor.forClass(LoginRequest.class);
    verify(_authenticationService).authenticate(requestCaptor.capture());

    LoginRequest captured = requestCaptor.getValue();
    assertThat(captured.getEmail()).isEqualTo(Mocks.Auth.EMAIL);
    assertThat(captured.getPassword()).isEqualTo(Mocks.Auth.PASSWORD);
  }
}
