package com.mgnt.events.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.AUDIT_LOGS)
@Getter
@Setter
public class AuditLog {
    
}
