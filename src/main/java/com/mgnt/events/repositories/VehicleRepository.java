package com.mgnt.events.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
  boolean existsByAssignedPersonnelIdAndDeletedAtIsNull(UUID personnelId);
}
