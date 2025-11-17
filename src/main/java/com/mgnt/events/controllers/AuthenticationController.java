package com.mgnt.events.controllers;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.auth.LoginRequest;
import com.mgnt.events.responses.auth.LoginResponse;
import com.mgnt.events.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Routes.AUTH)
public class AuthenticationController {
  private final AuthenticationService _authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this._authenticationService = authenticationService;
  }

  @PostMapping(Routes.LOGIN)
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(_authenticationService.authenticate(request));
  }

  @PostMapping(Routes.LOGOUT)
  public ResponseEntity<Map<String, String>> logout(
    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
  ) {
    
  }
}
 
