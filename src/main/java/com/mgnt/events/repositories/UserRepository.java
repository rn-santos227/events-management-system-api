package com.mgnt.events.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
