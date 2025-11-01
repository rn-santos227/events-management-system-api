package com.mgnt.events.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.LoginRequest;
import com.mgnt.events.security.auth.LoginResponse;
import com.mgnt.events.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Routes.AUTH)
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping(Routes.LOGIN)
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }
}
