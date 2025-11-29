package com.mgnt.events.models;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;
import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Enumerated(EnumType.STRING)
  @Column(name = Attributes.TYPE, nullable = false, length = Defaults.DEFAULT_MID_STRING_LENGTH)
  private VehicleType type;

  @Enumerated(EnumType.STRING)
  @Column(name = Attributes.STATUS, nullable = false, length = Defaults.DEFAULT_MID_STRING_LENGTH)
  private VehicleStatus status = VehicleStatus.AVAILABLE;
}
