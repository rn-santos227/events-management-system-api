package com.mgnt.events.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.enums.TokenType;
import com.mgnt.events.models.User;
import com.mgnt.events.models.UserToken;
import com.mgnt.events.repositories.UserTokenRepository;
import com.mgnt.events.requests.auth.LoginRequest;
import com.mgnt.events.responses.auth.LoginResponse;
import com.mgnt.events.util.RequestValidators;

@Service
public class AuthenticationService {
  private final AuthenticationManager _authenticationManager;
  private final JwtService _jwtService;
  private final UserTokenRepository _tokenRepository;

  public AuthenticationService(
    AuthenticationManager authenticationManager,
    JwtService jwtService,
    UserTokenRepository tokenRepository
  ) {
    this._authenticationManager = authenticationManager;
    this._jwtService = jwtService;
    this._tokenRepository = tokenRepository;
  }

  @Transactional(rollbackFor = Throwable.class)
  public LoginResponse authenticate(LoginRequest request) {
    Authentication authentication = authenticateUser(request.getEmail(), request.getPassword());
    User user = (User) authentication.getPrincipal();

    revokeActiveTokens(user);

    String token = _jwtService.generateToken(user);
    Instant expiration = _jwtService.extractExpiration(token);

    saveUserToken(user, token, expiration);

    long expiresAt = expiration != null ? expiration.toEpochMilli() : 0L;
    return new LoginResponse(token, "Bearer", expiresAt);
  }

  @Transactional(rollbackFor = Throwable.class)
  public void logout(String authorizationHeader) {
    String _header = RequestValidators.requireNonNull(authorizationHeader, HttpHeaders.AUTHORIZATION);
    if (!_header.startsWith("Bearer ")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization header must contain a Bearer token");
    }

    String _token = _header.substring(Defaults.DEFAULT_JWT_SUBSTRING);
    if (RequestValidators.isBlank(_token)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization token must not be blank");
    }

   UserToken _userToken = _tokenRepository
      .findByToken(_token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token"));
  }

  private Authentication authenticateUser(String email, String password) {
    return _authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(email, password)
    );
  }

  private void revokeActiveTokens(User user) {
    List<UserToken> validTokens = _tokenRepository.findAllByUserAndExpiredFalseAndRevokedFalse(user);
    if (validTokens.isEmpty()) {
      return;
    }

    validTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    _tokenRepository.saveAll(validTokens);
  }

  private void saveUserToken(User user, String token, Instant expiration) {
    UserToken _userToken = new UserToken();
    _userToken.setUser(user);
    _userToken.setToken(token);
    _userToken.setExpired(false);
    _userToken.setRevoked(false);
    _userToken.setTokenType(TokenType.BEARER);
    if (expiration != null) {
      _userToken.setExpiresAt(LocalDateTime.ofInstant(expiration, ZoneOffset.UTC));
    }
    _tokenRepository.save(_userToken);
  }
}
