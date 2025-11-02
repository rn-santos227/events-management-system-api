package com.mgnt.events.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.constants.Tables;
import com.mgnt.events.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByNameIgnoreCase(String name);

  @EntityGraph(attributePaths = Tables.PRIVILEGES)
  Optional<Role> findWithPrivilegesById(Long id);
}
