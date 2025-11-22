package com.mgnt.events.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
  List<AuditLog> findAllByUserId(UUID userId, Sort sort);
  Page<AuditLog> findAllByUserId(UUID userId, Pageable pageable);
}
