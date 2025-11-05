package com.mgnt.events.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.repositories.UserTokenRepository;
import com.mgnt.events.services.JwtService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService _jwtService;
  private final UserDetailsService _userDetailsService;
  private final UserTokenRepository _tokenRepository;

  public JwtAuthenticationFilter(
    JwtService jwtService,
    UserDetailsService userDetailsService,
    UserTokenRepository tokenRepository
  ) {
    this._jwtService = jwtService;
    this._userDetailsService = userDetailsService;
    this._tokenRepository = tokenRepository;
  }

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authHeader.substring(Defaults.DEFAULT_JWT_SUBSTRING);
    final String userEmail = _jwtService.extractUsername(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = _userDetailsService.loadUserByUsername(userEmail);
      boolean isTokenValid = _tokenRepository
        .findByToken(jwt)
        .map(token -> !token.isExpired() && !token.isRevoked())
        .orElse(false);

      
      if (_jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}