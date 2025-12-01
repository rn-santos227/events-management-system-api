package com.mgnt.events.models;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;
import com.mgnt.events.enums.AccommodationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Tables.ACCOMMODATIONS)
@SQLDelete(sql = Queries.DELETE_ACCOMMODATIONS)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Accommodation extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = Attributes.NAME, nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String name;

  @Column(name = Attributes.ADDRESS, nullable = false)
  private String address;

  @Column(name = Attributes.CONTACT_PERSON, nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String contactPerson;

  @Column(name = Attributes.CONTACT_NUMBER, length = Defaults.DEFAULT_PHONE_LENGTH)
  private String contactNumber;

  @Column(name = Attributes.EMAIL, length = Defaults.DEFAULT_EMAIL_LENGTH)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = Attributes.TYPE, nullable = false, length = Defaults.DEFAULT_MID_STRING_LENGTH)
  private AccommodationType type;

  @Column(name = Attributes.LATITUDE, precision = 9, scale = 6)
  private BigDecimal latitude;

  @Column(name = Attributes.LONGITUDE, precision = 9, scale = 6)
  private BigDecimal longitude;

  @ManyToOne
  @JoinColumn(name = Attributes.IMAGE_ID)
  private StoredFile image;

  public Accommodation() {}
  public Accommodation(
    String name,
    String address,
    String contactPerson,
    String contactNumber,
    String email,
    AccommodationType type)
  {
    this.name = name;
    this.address = address;
    this.contactPerson = contactPerson;
    this.contactNumber = contactNumber;
    this.email = email;
    this.type = type;
  }
}
