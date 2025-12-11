package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.PersonnelService;

@RestController
@RequestMapping(Routes.PERSONNEL)
public class PersonnelController {
 private final PersonnelService _personnelService;

  public PersonnelController(PersonnelService personnelService) {
    this._personnelService = personnelService;
  }
}
