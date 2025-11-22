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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.ROLES)
@SQLDelete(sql = Queries.DELETE_ROLES)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
public class Role extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Setter
  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = Tables.ROLES_PRIVILEGES,
    joinColumns = @JoinColumn(name = Attributes.ROLE_ID),
    inverseJoinColumns = @JoinColumn(name = Attributes.PRIVILEGE_ID)
  )

  @Setter
  private Set<Privilege> privileges = new LinkedHashSet<>();

  @OneToMany(mappedBy = Attributes.ROLE)
  @Setter
  private Set<User> users = new LinkedHashSet<>();

  public Role() {}
  public Role(String name) { this.name = name; }

  @PreRemove
  @Override
  protected void onDelete() {
    if (users != null && users.stream().anyMatch(user -> user.getDeletedAt() == null)) {
      throw new IllegalStateException("Cannot delete role that is still assigned to active users.");
    }
    super.onDelete();
  }
}
