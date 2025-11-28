package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.CATEGORIES)
@SQLDelete(sql = Queries.DELETE_CATEGORIES)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Category extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = Attributes.NAME, nullable = false, unique = true)
  private String name;

  @Column(name = Attributes.DESCRIPTION)
  private String description;

  public Category() {}

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }
}