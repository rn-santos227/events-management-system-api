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
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.Set;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.ROLES)
@SQLDelete(sql = Queries.DELETE_TIMESTAMP)
@SQLRestriction(Queries.DELETE_RESTRICTION)
public class Role extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = Tables.ROLES_PRIVILEGES,
    joinColumns = @JoinColumn(name = Attributes.ROLE_ID),
    inverseJoinColumns = @JoinColumn(name = Attributes.PRIVILEGE_ID)
  )
  private Set<Privilege> privileges;

  @OneToMany(mappedBy = Attributes.ROLE)
  private Set<User> users;

  public Role() {}
  public Role(String name) { this.name = name; }

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Set<Privilege> getPrivileges() { return privileges; }
  public void setPrivileges(Set<Privilege> privileges) { this.privileges = privileges; }
  public Set<User> getUsers() { return users; }
  public void setUsers(Set<User> users) { this.users = users; }

  @PreRemove
  @Override
  protected void onDelete() {
    if (users != null && users.stream().anyMatch(user -> user.getDeletedAt() == null)) {
      throw new IllegalStateException("Cannot delete role that is still assigned to active users.");
    }
    super.onDelete();
  }
}
