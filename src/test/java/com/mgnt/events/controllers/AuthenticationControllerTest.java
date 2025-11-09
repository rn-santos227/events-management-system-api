package com.mgnt.events.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
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
  void setup() {
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
    LoginResponse response = new LoginResponse("token-value", "Bearer", 1_720_000_000L);
    when(_authenticationService.authenticate(any(LoginRequest.class))).thenReturn(response);
  }
}
