package com.mgnt.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.mgnt.events.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
