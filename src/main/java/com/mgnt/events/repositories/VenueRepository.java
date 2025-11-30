package com.mgnt.events.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, UUID> {}
