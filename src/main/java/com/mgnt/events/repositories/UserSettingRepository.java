package com.mgnt.events.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.models.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, UUID> {
  Optional<UserSetting> findByUserId(UUID userId);
}
