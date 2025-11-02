package com.mgnt.events.responses.auth;

public record LoginResponse(String accessToken, String tokenType, long expiresAt) {}
