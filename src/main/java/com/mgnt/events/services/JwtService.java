package com.mgnt.events.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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

  public Instant extractExpiration(String token) {
    return extractClaim(token, verifier -> verifier.getExpiresAt().toInstant());
  }

  public String generateToken(UserDetails userDetails) {
    Instant now = Instant.now();
    Instant expiration = now.plus(expirationMillis, ChronoUnit.MILLIS);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    if (username == null || !username.equals(userDetails.getUsername())) {
      return false;
    }

    try {
      getVerifier().verify(token);
      return true;
    } catch (JWTVerificationException ex) {
      return false;
    }
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

  public Optional<Long> getExpirationMillis() {
    return expirationMillis <= 0 ? Optional.empty() : Optional.of(expirationMillis);
  }
}
