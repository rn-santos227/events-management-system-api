package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Tables;
import com.mgnt.events.types.TokenType;

@Entity
@Table(name = Tables.USER_TOKENS)
public class UserToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = false, length = Defaults.DEFAULT_TOKEN_LENGTH)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = Attributes.TOKEN_TYPE, nullable = false)
  private TokenType tokenType = TokenType.BEARER;

  @Column(nullable = false)
  private boolean revoked;

  @Column(nullable = false)
  private boolean expired;

  @Column(name = Attributes.EXPIRES_AT)
  private LocalDateTime expiresAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = Attributes.USER_ID, nullable = false)
  private User user;


  public Long getId() { return id; }
  public String getToken() { return token; }
  public void setToken(String token) { this.token = token; }
  public TokenType getTokenType() { return tokenType; }
  public void setTokenType(TokenType tokenType) { this.tokenType = tokenType; }
  public boolean isRevoked() { return revoked; }
  public void setRevoked(boolean revoked) { this.revoked = revoked; }
  public boolean isExpired() { return expired; }
  public void setExpired(boolean expired) { this.expired = expired; }
  public LocalDateTime getExpiresAt() { return expiresAt; }
  public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
}
