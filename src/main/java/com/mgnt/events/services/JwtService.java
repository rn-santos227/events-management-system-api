package com.mgnt.events.services;

import com.auth0.jwt.algorithms.Algorithm;

public class JwtService {
  private final Algorithm algorithm;
  private final long expirationMillis;
}
