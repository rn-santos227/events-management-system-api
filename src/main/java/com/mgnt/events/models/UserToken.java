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
import lombok.Getter;
import lombok.Setter;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Tables;
import com.mgnt.events.types.TokenType;

@Entity
@Table(name = Tables.USER_TOKENS)
@Getter
public class UserToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(nullable = false, unique = false, length = Defaults.DEFAULT_TOKEN_LENGTH)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = Attributes.TOKEN_TYPE, nullable = false)
  @Setter
  private TokenType tokenType = TokenType.BEARER;

  @Setter
  @Column(nullable = false)
  private boolean revoked;

  @Setter
  @Column(nullable = false)
  private boolean expired;

  @Setter
  @Column(name = Attributes.EXPIRES_AT)
  private LocalDateTime expiresAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = Attributes.USER_ID, nullable = false)
  @Setter
  private User user;
}
