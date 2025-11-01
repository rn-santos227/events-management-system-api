package com.mgnt.events.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.models.User;
import com.mgnt.events.models.UserToken;
import com.mgnt.events.repositories.UserTokenRepository;
import com.mgnt.events.requests.LoginRequest;
import com.mgnt.events.security.auth.LoginResponse;
import com.mgnt.events.services.JwtService;
import com.mgnt.events.types.TokenType;

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
    Authentication authentication = authenticateUser(request.getEmail(), request.getPassword());
    User user = (User) authentication.getPrincipal();

    revokeActiveTokens(user);
  }

  private Authentication authenticateUser(String email, String password) {
    return authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(email, password)
    );
  }

  private void revokeActiveTokens(User user) {
    List<UserToken> validTokens = tokenRepository.findAllByUserAndExpiredFalseAndRevokedFalse(user);
    if (validTokens.isEmpty()) {
      return;
    }

    validTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validTokens);
  }

  private void saveUserToken(User user, String token, Instant expiration) {
    UserToken userToken = new UserToken();
    userToken.setUser(user);
    userToken.setToken(token);
    userToken.setExpired(false);
    userToken.setRevoked(false);
    userToken.setTokenType(TokenType.BEARER);
    if (expiration != null) {
      userToken.setExpiresAt(LocalDateTime.ofInstant(expiration, ZoneOffset.UTC));
    }
    tokenRepository.save(userToken);
  }
}
