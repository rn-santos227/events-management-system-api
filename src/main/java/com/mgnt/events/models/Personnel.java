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
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.PERSONNEL)
@SQLDelete(sql = Queries.DELETE_PERSONNEL)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Personnel extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = Attributes.NAME, nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String fullName;

  @Column(name = Attributes.CONTACT_NUMBER, nullable = false, length = Defaults.DEFAULT_PHONE_LENGTH)
  private String contactNumber;

  @Column(name = Attributes.FUNCTION, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String role;

  @Column(name = Attributes.EMAIL, length = Defaults.DEFAULT_EMAIL_LENGTH)
  private String email;

  public Personnel() {}

  public Personnel(String fullName, String contactNumber, String role, String email) {
    this.fullName = fullName;
    this.contactNumber = contactNumber;
    this.role = role;
    this.email = email;
  }
}