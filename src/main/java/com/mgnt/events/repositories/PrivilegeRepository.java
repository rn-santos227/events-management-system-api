package com.mgnt.events.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.models.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
  Optional<Privilege> findByNameIgnoreCase(String name);
  Optional<Privilege> findByActionIgnoreCase(String action);
}
