package com.mgnt.events.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.Personnel;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, UUID> {}
