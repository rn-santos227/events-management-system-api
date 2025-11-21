package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.AUDIT_LOGS)
@Getter
@Setter
public class AuditLog extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = Attributes.USER_ID)
  private User user;

  @Column(nullable = false, length = Defaults.DEFAULT_MAX_STRING_LENGTH)
  private String action;

  @Column(nullable = false, length = Defaults.DEFAULT_METHOD_LENGTH)
  private String method;

  @Column(nullable = false)
  private String path;

  @Column(name = Attributes.STATUS_CODE, nullable = false)
  private int statusCode;

  @Column(name = Attributes.IP_ADDRESS, length = Defaults.DEFAULT_IP_LENGTH)
  private String ipAddress;

  @Column(name = Attributes.MESSAGE, columnDefinition = Queries.TEXT)
  private String message;
}
