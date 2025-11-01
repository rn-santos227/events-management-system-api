package com.mgnt.events.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.repositories.UserTokenRepository;
import com.mgnt.events.requests.LoginRequest;
import com.mgnt.events.security.auth.LoginResponse;

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

  @Transactional
  public LoginResponse authenticate(LoginRequest request) {
    
  }
}
