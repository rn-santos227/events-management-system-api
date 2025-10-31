package com.mgnt.events.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
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

  public String extractUsername(String token) {
    return extractClaim(token, verifier -> verifier.getSubject());
  }

  private <T> T extractClaim(String token, Function<com.auth0.jwt.interfaces.DecodedJWT, T> resolver) {
    try {
      com.auth0.jwt.interfaces.DecodedJWT decodedJWT = getVerifier().verify(token);
      return resolver.apply(decodedJWT);
    } catch (JWTVerificationException ex) {
      return null;
    }
  }
  private JWTVerifier getVerifier() {
    return JWT.require(algorithm).build();
  }
}
