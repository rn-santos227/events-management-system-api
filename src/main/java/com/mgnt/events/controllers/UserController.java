package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.UserService;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {
  private final UserService _userService;

  public UserController(UserService userService) {
    this._userService = userService;
  }
}
