package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import com.mgnt.events.constants.Attributes;

public abstract class AuditableEntity {
    
}
