package com.mgnt.events.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mgnt.events.constants.Profiles;

@Configuration
@Profile(Profiles.LOCAL)
@EnableWebSecurity
public class LocalSecurityConfig {
  private final UserDetailsService _userDetailsService;

  public LocalSecurityConfig(UserDetailsService userDetailsService) {
    this._userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  @SuppressWarnings("deprecation")
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(_userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
