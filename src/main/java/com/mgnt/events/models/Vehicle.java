package com.mgnt.events.models;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Tables.VEHICLES)
@SQLDelete(sql = Queries.DELETE_VEHICLES)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Vehicle extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
}
