package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.services.UserService;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {
  private final UserService _userService;

  public UserController(UserService userService) {
    this._userService = userService;
  }

  @GetMapping
  public List<UserResponse> findAll() {
    return _userService.findAll();
  }
}
