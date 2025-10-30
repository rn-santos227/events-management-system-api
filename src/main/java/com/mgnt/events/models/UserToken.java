package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private boolean revoke;

  @Column(nullable = false)
  private boolean expired;

  @Column(name = Attributes.EXPIRES_AT)
  private LocalDateTime expiresAt;
}
