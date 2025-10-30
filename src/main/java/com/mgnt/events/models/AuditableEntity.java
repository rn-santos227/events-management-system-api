package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import com.mgnt.events.constants.Attributes;

@MappedSuperclass
public abstract class AuditableEntity {
  @Column(name = Attributes.CREATED_AT, nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = Attributes.UPDATED_AT, nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = Attributes.DELETED_AT)
  private LocalDateTime deletedAt;
}
