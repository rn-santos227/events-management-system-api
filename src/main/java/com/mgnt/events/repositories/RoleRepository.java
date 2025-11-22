package com.mgnt.events.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.constants.Tables;
import com.mgnt.events.models.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByNameIgnoreCase(String name);

  @EntityGraph(attributePaths = Tables.PRIVILEGES)
  Optional<Role> findWithPrivilegesById(UUID id);
}
