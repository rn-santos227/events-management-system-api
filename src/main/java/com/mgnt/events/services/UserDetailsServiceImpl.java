package com.mgnt.events.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mgnt.events.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository _userRepository;
  
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this._userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return _userRepository
      .findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
