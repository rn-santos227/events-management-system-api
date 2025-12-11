package com.mgnt.events.controllers;

import jakarta.validation.Valid;
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
import com.mgnt.events.requests.accommodations.AccommodationRequest;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
import com.mgnt.events.services.AccommodationService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.ACCOMMODATIONS)
public class AccommodationController {
  private final AccommodationService _accommodationService;

  public AccommodationController(AccommodationService accommodationService) {
    this._accommodationService = accommodationService;
  }

  @GetMapping
  public List<AccommodationResponse> findAll(
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _accommodationService.findAll(sanitizedLimit);
  }

  @GetMapping(Routes.APPEND_ID)
  public AccommodationResponse findById(@PathVariable @NonNull UUID id) {
    return _accommodationService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccommodationResponse create(@Valid @RequestBody AccommodationRequest request) {
    return _accommodationService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public AccommodationResponse update(@PathVariable @NonNull UUID id, @Valid @RequestBody AccommodationRequest request) {
    return _accommodationService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _accommodationService.delete(id);
  }
}
