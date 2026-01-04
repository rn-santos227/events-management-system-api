package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;
import com.mgnt.events.enums.ThemeOption;
import com.mgnt.events.models.converters.ThemeOptionConverter;

@Entity
@Table(name = Tables.USER_SETTINGS)
@SQLDelete(sql = Queries.DELETE_USER_SETTINGS)
@Getter
@Setter
public class UserSetting {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = Attributes.USER_ID, nullable = false, unique = true)
  private User user;

  @Column(name = Attributes.THEME, nullable = false)
  @Convert(converter = ThemeOptionConverter.class)
  private ThemeOption theme;
}
