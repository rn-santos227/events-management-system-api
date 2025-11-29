package com.mgnt.events.models;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Tables.ACCOMMODATIONS)
@SQLDelete(sql = Queries.DELETE_ACCOMMODATIONS)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Accomodation extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = Attributes.NAME, nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String name;

  @Column(name = Attributes.ADDRESS, nullable = false)
  private String address;

  @Column(name = Attributes.CONTACT_PERSON, nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String contactPerson;

}
