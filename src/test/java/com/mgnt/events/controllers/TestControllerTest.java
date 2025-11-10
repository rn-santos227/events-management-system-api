package com.mgnt.events.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import com.mgnt.events.util.RequestValidators;

@ExtendWith(MockitoExtension.class)
public class TestControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @InjectMocks
  private TestController _testController;

  @BeforeEach()
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_testController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void test_shouldReturnMessage() throws Exception {
    _mockMvc
      .perform(get(Routes.TEST))
      .andExpect(status().isOk())
      .andExpect(content().contentType(
        RequestValidators.requireNonNull(MediaType.APPLICATION_JSON, "Media Tye")
      ))
      .andExpect(jsonPath(JsonPaths.MESSAGE).value(Mocks.Messages.TEST));
  }
}
