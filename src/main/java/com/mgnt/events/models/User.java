package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.USERS)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = Defaults.DEFAULT_EMAIL_LENGTH)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = Defaults.DEFAULT_MID_STRING_LENGTH)
  private String firstName;

  @Column(nullable = false, length = Defaults.DEFAULT_MID_STRING_LENGTH)
  private String lastName;

  @Column(nullable = false)
  private boolean active = true;

  public User() {}
}
