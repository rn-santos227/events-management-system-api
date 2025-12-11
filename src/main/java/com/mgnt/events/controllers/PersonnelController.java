package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
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
}
