package com.mgnt.events.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {}
