package com.mgnt.events.services;

import org.springframework.stereotype.Service;

import com.mgnt.events.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl {
  private final UserRepository userRepository;
  
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
