package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.USERS)
@SQLDelete(sql = Queries.DELETE_TIMESTAMP)
@SQLRestriction(Queries.DELETE_RESTRICTION)
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

  @Column(name = Attributes.CREATED_AT, nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = Attributes.UPDATED_AT, nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = Attributes.DELETED_AT)
  private LocalDateTime deletedAt;

  public User() {}
  public User(String email, String password, String firstName, String lastName, Role role) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }

  public String getFullName() {
    return String.format("%s %s", 
      firstName != null ? firstName.trim() : "", 
      lastName != null ? lastName.trim() : ""
    ).trim();
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public LocalDateTime getDeletedAt() { return deletedAt; }
  public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @PreRemove
  protected void onDelete() {
    deletedAt = LocalDateTime.now();
  }
}
