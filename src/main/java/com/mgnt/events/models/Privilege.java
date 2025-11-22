package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.PRIVILEGES)
@SQLDelete(sql = Queries.DELETE_PRIVILEGES)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
public class Privilege extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Setter
  @Column(unique = true, nullable = false)
  private String name;

  @Setter
  @Column(unique = true, nullable = false)
  private String action;

  @Setter
  @Column(nullable = false)
  private String resource;

  @ManyToMany(mappedBy = Tables.PRIVILEGES)
  private Set<Role> roles = new LinkedHashSet<>();

  public Privilege() {}
  public Privilege(String name, String action, String resource) {
    this.name = name;
    this.action = action;
    this.resource = resource;
  }

  @PreRemove
  @Override
  protected void onDelete() {
    if (roles != null && roles.stream().anyMatch(role -> role.getDeletedAt() == null)) {
      throw new IllegalStateException("Cannot delete privilege that is still assigned to active roles.");
    }
    super.onDelete();
  }
}
