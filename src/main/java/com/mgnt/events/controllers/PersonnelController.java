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
import com.mgnt.events.requests.personnel.PersonnelRequest;
import com.mgnt.events.responses.personnel.PersonnelResponse;
import com.mgnt.events.services.PersonnelService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.PERSONNEL)
public class PersonnelController {
  private final PersonnelService _personnelService;

  public PersonnelController(PersonnelService personnelService) {
    this._personnelService = personnelService;
  }

  @GetMapping
  public List<PersonnelResponse> findAll(
    @RequestParam(name = Queries.LIMIT, required = true) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _personnelService.findAll(sanitizedLimit);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PersonnelResponse create(@Valid @RequestBody PersonnelRequest request) {
    return _personnelService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public PersonnelResponse update(@PathVariable @NonNull UUID id, @Valid @RequestBody PersonnelRequest request) {
    return _personnelService.update(id, request);
  }
}
