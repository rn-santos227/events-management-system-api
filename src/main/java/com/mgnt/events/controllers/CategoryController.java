package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.CategoryService;

@RestController
@RequestMapping(Routes.CATEGORIES)
public class CategoryController {
  private final CategoryService _categoryService ;

  public CategoryController(CategoryService categoryService) {
    this._categoryService = categoryService;
  }
}
