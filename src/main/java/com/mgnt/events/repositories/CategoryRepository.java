package com.mgnt.events.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.events.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Optional<Category> findByNameIgnoreCase(String name);
}