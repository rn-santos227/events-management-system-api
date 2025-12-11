package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.services.CategoryService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.CATEGORIES)
public class CategoryController {
  private final CategoryService _categoryService ;

  public CategoryController(CategoryService categoryService) {
    this._categoryService = categoryService;
  }

  @GetMapping
  public List<CategoryResponse> findAll(
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _categoryService.findAll(sanitizedLimit);
  }
}
