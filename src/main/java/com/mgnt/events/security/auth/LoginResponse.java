package com.mgnt.events.security.auth;

public record LoginResponse(String accessToken, String tokenType, long expiresAt) {}
