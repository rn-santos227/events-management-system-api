package com.mgnt.events.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.mgnt.events.repositories.UserTokenRepository;

@Service
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserTokenRepository tokenRepository;

  public AuthenticationService(
    AuthenticationManager authenticationManager,
    JwtService jwtService,
    UserTokenRepository tokenRepository
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.tokenRepository = tokenRepository;
  }
}
