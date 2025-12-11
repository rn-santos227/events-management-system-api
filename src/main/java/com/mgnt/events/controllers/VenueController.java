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
import com.mgnt.events.requests.venues.VenueRequest;
import com.mgnt.events.responses.venues.VenueResponse;
import com.mgnt.events.services.VenueService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.VENUES)
public class VenueController {
  private final VenueService _venueService;

  public VenueController(VenueService venueService) {
    this._venueService = venueService;
  }

  @GetMapping
  public List<VenueResponse> findAll(
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _venueService.findAll(sanitizedLimit);
  }

  @GetMapping(Routes.APPEND_ID)
  public VenueResponse findById(@PathVariable @NonNull UUID id) {
    return _venueService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VenueResponse create(@Valid @RequestBody VenueRequest request) {
    return _venueService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public VenueResponse update(@PathVariable @NonNull UUID id, @Valid @RequestBody VenueRequest request) {
    return _venueService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _venueService.delete(id);
  }
}
