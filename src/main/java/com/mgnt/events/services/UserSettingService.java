package com.mgnt.events.services;

import org.springframework.stereotype.Service;

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
    
  }
}

