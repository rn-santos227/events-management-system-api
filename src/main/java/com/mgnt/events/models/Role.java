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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = Tables.ROLES)
@SQLDelete(sql = Queries.DELETE_TIMESTAMP)
@SQLRestriction(Queries.DELETE_RESTRICTION)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = Tables.ROLES_PRIVELEGES,
    joinColumns = @JoinColumn(name = Attributes.ROLE_ID),
    inverseJoinColumns = @JoinColumn(name = Attributes.PRIVILEGE_ID)
  )
  private Set<Privilege> privileges;

  @OneToMany(mappedBy = Tables.ROLES)
  private Set<User> users;

  @Column(name = Attributes.CREATED_AT, nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = Attributes.UPDATED_AT, nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = Attributes.DELETED_AT)
  private LocalDateTime deletedAt;

  public Role() {}
  public Role(String name) { this.name = name; }

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Set<Privilege> getPrivileges() { return privileges; }
  public void setPrivileges(Set<Privilege> privileges) { this.privileges = privileges; }
  public Set<User> getUsers() { return users; }
  public void setUsers(Set<User> users) { this.users = users; }
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
    if (users != null && users.stream().anyMatch(user -> user.getDeletedAt() == null)) {
      throw new IllegalStateException("Cannot delete role that is still assigned to active users.");
    }
    deletedAt = LocalDateTime.now();
  }
}
