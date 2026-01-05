package com.mgnt.events.controllers;

import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.settings.UserSettingResponse;
import com.mgnt.events.services.UserSettingService;

@RestController
@RequestMapping(Routes.USER_SETTINGS)
public class UserSettingController {
  private final UserSettingService _userSettingService;

  public UserSettingController(UserSettingService userSettingService) {
    this._userSettingService = userSettingService;
  }

  @GetMapping
  public UserSettingResponse getByUserId(@PathVariable @NonNull UUID id) {
    return _userSettingService.getByUserId(id);
  }
}
