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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mgnt.events.constants.JsonPaths;
import com.mgnt.events.constants.Mocks;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.services.CategoryService;
import com.mgnt.events.util.RequestValidators;

public class CategoryControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private CategoryService _categoryService;

  @InjectMocks
  private CategoryController _categoryController;

  @BeforeEach
  void setUp() {
    _objectMapper = new ObjectMapper();
    _objectMapper.registerModule(new JavaTimeModule());

    _mockMvc = MockMvcBuilders
      .standaloneSetup(_categoryController)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(
        RequestValidators.requireNonNull(_objectMapper, "Object Mapper")
      ))
      .build();
  }

  @Test
  void findAll_ShouldReturnCategoryResponses() throws Exception {
    UUID categoryId = UUID.randomUUID();
    List<CategoryResponse> responses = List.of(
      new CategoryResponse(
        categoryId,
        Mocks.Categories.NAME,
        Mocks.Categories.DESCRIPTION,
        LocalDateTime.now(),
        LocalDateTime.now()
      )
    );

    when(_categoryService.findAll(5, 0)).thenReturn(responses);
    _mockMvc
      .perform(
        get(Routes.CATEGORIES)
          .param(Queries.LIMIT, Mocks.Categories.PAGINATION_LIMIT)
          .param(Queries.PAGE, Mocks.Categories.PAGINATION_PAGE)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath(JsonPaths.INDEX_0_ID).value(categoryId.toString()))
      .andExpect(jsonPath(JsonPaths.INDEX_0_NAME).value(Mocks.Categories.NAME));
  }
}
