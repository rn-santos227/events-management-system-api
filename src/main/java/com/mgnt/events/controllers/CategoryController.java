package com.mgnt.events.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.categories.CategoryRequest;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.services.CategoryService;
import com.mgnt.events.util.RequestValidators;

import jakarta.validation.Valid;

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

  @GetMapping(Routes.APPEND_ID)
  public CategoryResponse findById(@PathVariable @NonNull UUID id) {
    return _categoryService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
    return _categoryService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public CategoryResponse update(@PathVariable @NonNull UUID id, @Valid @RequestBody CategoryRequest request) {
    return _categoryService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _categoryService.delete(id);
  }
}
