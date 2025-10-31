package com.mgnt.events.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;

public class JwtService {
  private final Algorithm algorithm;
  private final long expirationMillis;

  public JwtService(
    @Value("${app.security.jwt.secret}") String secret,
    @Value("${app.security.jwt.expiration:3600000}") long expirationMillis
  ) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.expirationMillis = expirationMillis;
  }

  private JWTVerifier getVerifier() {
    return JWT.require(algorithm).build();
  }
}
