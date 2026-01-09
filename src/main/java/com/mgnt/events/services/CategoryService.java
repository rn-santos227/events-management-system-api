package com.mgnt.events.services;

import static com.mgnt.events.constants.Cache.CATEGORIES;
import static com.mgnt.events.constants.Cache.CATEGORY_BY_ID;
import static com.mgnt.events.constants.Cache.KEY;
import static com.mgnt.events.constants.Cache.KEY_ID;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.Category;
import com.mgnt.events.repositories.CategoryRepository;
import com.mgnt.events.requests.categories.CategoryRequest;
import com.mgnt.events.requests.categories.CategoryUpdateRequest;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.util.RequestValidators;

@Service
public class CategoryService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final CategoryRepository _categoryRepository;

  public CategoryService(CategoryRepository categoryService) {
    this._categoryRepository = categoryService;
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = CATEGORIES, key = KEY)
  public List<CategoryResponse> findAll(@Nullable Integer limit, @Nullable Integer page) {  
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    Integer sanitizedPage =
      sanitizedLimit == null ? null : RequestValidators.requireNonNegativeOrNull(page, Queries.PAGE);

    if (sanitizedLimit == null) {
      return _categoryRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    int resolvedPage = sanitizedPage != null ? sanitizedPage.intValue() : 0;
    return _categoryRepository
     .findAll(PageRequest.of(resolvedPage, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = CATEGORY_BY_ID, key = KEY_ID)
  public CategoryResponse findById(UUID id) {
    Category category = _categoryRepository
      .findById(RequestValidators.requireNonNull(id, "ID must no be null"))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    return toResponse(Objects.requireNonNull(category));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { CATEGORIES, CATEGORY_BY_ID }, allEntries = true)
  public CategoryResponse create(CategoryRequest request) {
    validateNameUniqueness(request.name(), null);
    Category category = new Category(request.name(), request.description());
    return toResponse(_categoryRepository.save(category));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { CATEGORIES, CATEGORY_BY_ID }, allEntries = true)
  public CategoryResponse update(UUID id, CategoryRequest request) {
    Category category = _categoryRepository
      .findById(RequestValidators.requireNonNull(id, "ID must no be null"))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    validateNameUniqueness(request.name(), id);
    category.setName(request.name());
    category.setDescription(request.description());

    return toResponse(_categoryRepository.save(category));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { CATEGORIES, CATEGORY_BY_ID }, allEntries = true)
  public CategoryResponse updatePartial(UUID id, CategoryUpdateRequest request) {
    Category category = _categoryRepository
      .findById(RequestValidators.requireNonNull(id, "ID must no be null"))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

      if (request.name() != null) {
      validateNameUniqueness(request.name(), id);
      category.setName(request.name());
    }

    if (request.description() != null) {
      category.setDescription(request.description());
    }

    return toResponse(_categoryRepository.save(
      RequestValidators.requireNonNull(category, "Category must no be null")
    ));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { CATEGORIES, CATEGORY_BY_ID }, allEntries = true)
  public void delete(UUID id) {
    Category category = _categoryRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    if (category != null) {
      _categoryRepository.delete(category);
    }
  }

  private void validateNameUniqueness(String name, UUID excludeId) {
    _categoryRepository
      .findByNameIgnoreCase(name)
      .filter(existing -> excludeId == null || !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
      });
  }
  
  private CategoryResponse toResponse(Category category) {
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
