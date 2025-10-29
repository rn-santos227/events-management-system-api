package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = Tables.USERS)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(
    nullable = false,
    unique = true,
    length = Defaults.DEFAULT_EMAIL_LENGTH
  )
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(
    name = Attributes.FIRST_NAME,
    nullable = false,
    length = Defaults.DEFAULT_MID_STRING_LENGTH
  )
  private String firstName;

  @Column(
    name = Attributes.LAST_NAME,
    nullable = false,
    length = Defaults.DEFAULT_MID_STRING_LENGTH
  )
  private String lastName;

  @ManyToOne
  @JoinColumn(name = Attributes.ROLE_ID, nullable = false)
  private Role role;

  @Column(nullable = false)
  private boolean active = true;

  @Column(
    name = Attributes.CREATED_AT,
    nullable = false,
    updatable = false
  )
  private LocalDateTime createdAt = LocalDateTime.now();

  public User() {}
  public User(String email, String password, String firstName, String lastName, Role role) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.active = true;
    this.createdAt = LocalDateTime.now();
  }
}
