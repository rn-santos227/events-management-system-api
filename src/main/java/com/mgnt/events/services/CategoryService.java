package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.CategoryRepository;

@Service
public class CategoryService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final CategoryRepository _categoryRepository;

  public CategoryService(CategoryRepository categoryService) {
    this._categoryRepository = categoryService;
  }
}
