package com.mgnt.events.controllers;

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
import com.mgnt.events.requests.users.UserCreateRequest;
import com.mgnt.events.requests.users.UserUpdateRequest;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.services.UserService;
import com.mgnt.events.util.RequestValidators;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {
  private final UserService _userService;

  public UserController(UserService userService) {
    this._userService = userService;
  }

  @GetMapping
  public List<UserResponse> findAll(@RequestParam(name = Queries.LIMIT, required = false) Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _userService.findAll(sanitizedLimit);
  }

  @GetMapping(Routes.APPEND_ID)
  public UserResponse findById(@PathVariable @NonNull UUID id) {
    return _userService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@Valid @RequestBody UserCreateRequest request) {
    return _userService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public UserResponse update(
    @PathVariable @NonNull UUID id,
    @Valid @RequestBody UserUpdateRequest request
  ) {
    return _userService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _userService.delete(id);
  }
}
