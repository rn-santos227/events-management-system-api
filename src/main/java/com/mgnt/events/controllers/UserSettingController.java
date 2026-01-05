package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.UserSettingService;

@RestController
@RequestMapping(Routes.USER_SETTINGS)
public class UserSettingController {
  private final UserSettingService _userSettingService;

  public UserSettingController(UserSettingService userSettingService) {
    this._userSettingService = userSettingService;
  }

}
