package com.mgnt.events.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.util.Set;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Joins;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.ROLES)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = Joins.ROLES_PRIVELEGES,
    joinColumns = @JoinColumn(name = Attributes.ROLE_ID),
    inverseJoinColumns = @JoinColumn(name = Attributes.PRIVILEGE_ID)
  )
  private Set<Privilege> privileges;

  @ManyToMany(mappedBy = Tables.ROLES)
  private Set<User> users;

  public Role() {}
  public Role(String name) { this.name = name; }

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Set<Privilege> getPrivileges() { return privileges; }
  public void setPrivileges(Set<Privilege> privileges) { this.privileges = privileges; }
}
