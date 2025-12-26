package com.mgnt.events.controllers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.events.services.CategoryService;

public class CategoryControllerTest {
  private MockMvc _mockMvc;
  private ObjectMapper _objectMapper;

  @Mock
  private CategoryService _categoryService;

  @InjectMocks
  private CategoryController _categoryController;

}
