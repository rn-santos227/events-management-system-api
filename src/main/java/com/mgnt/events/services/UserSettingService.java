package com.mgnt.events.services;

import java.util.Objects;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.models.User;
import com.mgnt.events.models.UserSetting;
import com.mgnt.events.repositories.UserRepository;
import com.mgnt.events.repositories.UserSettingRepository;
import com.mgnt.events.requests.settings.UserSettingRequest;
import com.mgnt.events.responses.settings.UserSettingResponse;
import com.mgnt.events.util.RequestValidators;

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

  @Transactional
  public UserSettingResponse getByUserId(@NonNull UUID userId) {
    User user = getUser(userId);
    UserSetting userSetting =
      _userSettingRepository
        .findByUserId(userId)
        .orElseGet(() -> _userSettingRepository.save(
          RequestValidators.requireNonNull(defaultSettings(user), "User must not be null") )
        );
    return toResponse(userSetting);
  }
  
  @Transactional
  public UserSettingResponse update(@NonNull UUID userId, UserSettingRequest request) {
    User user = getUser(userId);
    UserSetting userSetting =
      _userSettingRepository
        .findByUserId(userId)
        .orElseGet(() -> new UserSetting(user, null, null, null, null, true));

    return toResponse(_userSettingRepository.save(userSetting));
  }

  private User getUser(@NonNull UUID id) {
    return Objects.requireNonNull(
      _userRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
    );
  }

  private UserSetting defaultSettings(User user) {
    return new UserSetting(
      user,
      Defaults.DEFAULT_THEME,
      Defaults.DEFAULT_DENSITY,
      Defaults.DEFAULT_FONT_SIZE,
      Defaults.DEFAULT_PAGE_SIZE,
      true
    );
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

