package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.Set;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = Tables.PRIVILEGES)
@SQLDelete(sql = Queries.DELETE_TIMESTAMP)
@SQLRestriction(Queries.DELETE_RESTRICTION)
public class Privilege extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String action;

  @Column(unique = true, nullable = false)
  private String table;

  @ManyToMany(mappedBy = Tables.PRIVILEGES)
  private Set<Role> roles;

  public Privilege() {}
  public Privilege(String name, String action, String table) {
    this.name = name;
    this.action = action;
    this.table = table;
  }

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getAction() { return action; }
  public void setAction(String action) { this.action = action; }
  public String getTable() { return table; }
  public void setTable(String table) { this.table = table; }

  @PreRemove
  @Override
  protected void onDelete() {
    if (roles != null && roles.stream().anyMatch(role -> role.getDeletedAt() == null)) {
      throw new IllegalStateException("Cannot delete privilege that is still assigned to active roles.");
    }
    super.onDelete();
  }
}
