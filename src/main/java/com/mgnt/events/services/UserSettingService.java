package com.mgnt.events.services;

import org.springframework.stereotype.Service;

import com.mgnt.events.repositories.UserRepository;
import com.mgnt.events.repositories.UserSettingRepository;

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
}
