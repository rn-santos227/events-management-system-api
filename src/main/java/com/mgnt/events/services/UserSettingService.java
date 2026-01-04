package com.mgnt.events.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mgnt.events.models.User;
import com.mgnt.events.models.UserSetting;
import com.mgnt.events.repositories.UserRepository;
import com.mgnt.events.repositories.UserSettingRepository;
import com.mgnt.events.responses.settings.UserSettingResponse;

@Service
public class UserSettingService {
  private final UserRepository _userRepository;
  private final UserSettingRepository _userSettingRepository;
 
  public UserSettingService(
    UserRepository userRepository,
    UserSettingRepository userSettingRepository
  ) {
    this._userRepository = userRepository;
    this._userSettingRepository = userSettingRepository;
  }
  

  private UserSettingResponse toResponse(UserSetting userSetting) {
   UserSetting ensuredSettings = Objects.requireNonNull(userSetting, "User settings must not be null");
    User user = Objects.requireNonNull(ensuredSettings.getUser(), "User must not be null");

    return new UserSettingResponse(
      Objects.requireNonNull(user.getId(), "User identifier must not be null"),
      ensuredSettings.getTheme(),
      ensuredSettings.getDensity(),
      ensuredSettings.getFontSize(),
      ensuredSettings.getDefaultPageSize(),
      ensuredSettings.isRememberState(),
      ensuredSettings.getCreatedAt(),
      ensuredSettings.getUpdatedAt()
    );
  }
}

