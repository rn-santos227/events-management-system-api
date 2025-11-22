package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.USERS)
@SQLDelete(sql = Queries.DELETE_USERS)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
public class User extends AuditableEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Setter
  @Column(
    nullable = false,
    unique = true,
    length = Defaults.DEFAULT_EMAIL_LENGTH
  )
  private String email;

  @Setter
  @Column(nullable = false)
  private String password;

  @Setter
  @Column(
    name = Attributes.FIRST_NAME,
    nullable = false,
    length = Defaults.DEFAULT_MID_STRING_LENGTH
  )
  private String firstName;

  @Setter
  @Column(
    name = Attributes.LAST_NAME,
    nullable = false,
    length = Defaults.DEFAULT_MID_STRING_LENGTH
  )
  private String lastName;

  @Setter
  @Column(
    name = Attributes.CONTACT_NUMBER,
    nullable = false,
    length = Defaults.DEFAULT_PHONE_LENGTH
  )
  private String contactNumber;

  @ManyToOne
  @JoinColumn(name = Attributes.ROLE_ID, nullable = false)
  @Setter
  private Role role;

  @Setter
  @Column(nullable = false)
  private boolean active = true;

  public User() {}
  public User(
    String email,
    String password,
    String firstName,
    String lastName,
    String contactNumber,
    Role role
  ) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.contactNumber = contactNumber;
    this.role = role;
  }

  public String getFullName() {
    return String.format("%s %s",
      firstName != null ? firstName.trim() : "",
      lastName != null ? lastName.trim() : ""
    ).trim();
  }

  @Override
  public String getUsername() { return email; }

  @Override
  public boolean isAccountNonExpired() { return active; }

  @Override
  public boolean isAccountNonLocked() { return active; }

  @Override
  public boolean isCredentialsNonExpired() { return active; }

  @Override
  public boolean isEnabled() { return active; }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role == null) {
      return Collections.emptySet();
    }

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    if (role.getName() != null && !role.getName().isBlank()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }

    if (role.getPrivileges() != null && !role.getPrivileges().isEmpty()) {
      authorities.addAll(
        role
          .getPrivileges()
          .stream()
          .filter(privilege -> privilege.getAction() != null)
          .map(privilege -> new SimpleGrantedAuthority(privilege.getAction()))
          .collect(Collectors.toSet())
      );
    }
    return authorities;
  }
}
