package com.mgnt.events.services;

import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Category;
import com.mgnt.events.repositories.CategoryRepository;
import com.mgnt.events.responses.categories.CategoryResponse;

@Service
public class CategoryService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final CategoryRepository _categoryRepository;

  public CategoryService(CategoryRepository categoryService) {
    this._categoryRepository = categoryService;
  }
  
  private CategoryResponse toRespose(Category category) {
    Category ensuredCategory = Objects.requireNonNull(category, "Category must not be null");
    return new CategoryResponse(
      Objects.requireNonNull(ensuredCategory.getId(), "Category identifier must not be null"),
      ensuredCategory.getName(),
      ensuredCategory.getDescription(),
      ensuredCategory.getCreatedAt(),
      ensuredCategory.getUpdatedAt()
    );
  }
}
