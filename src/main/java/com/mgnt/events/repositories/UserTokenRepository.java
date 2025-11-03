package com.mgnt.events.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.models.User;
import com.mgnt.events.models.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
  List<UserToken> findAllByUserAndExpiredFalseAndRevokedFalse(User user);
  Optional<UserToken> findByToken(String token);
}
