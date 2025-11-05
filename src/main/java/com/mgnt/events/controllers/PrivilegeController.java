package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.PrivilegeService;

@RestController
@RequestMapping(Routes.PRIVILEGES)
public class PrivilegeController {
  private final PrivilegeService _privilegeService;

  public PrivilegeController(PrivilegeService privilegeService) {
    this._privilegeService = privilegeService;
  }
}
