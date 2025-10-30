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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  @PrePersist
  private void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  private void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @PreRemove
  private void preRemove() {
    onDelete();
  }

  protected void onDelete() {
    deletedAt = LocalDateTime.now();
  }
}
