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
import com.mgnt.events.enums.DensityOption;
import com.mgnt.events.enums.FontSizeOption;
import com.mgnt.events.enums.ThemeOption;
import com.mgnt.events.models.converters.DensityOptionConverter;
import com.mgnt.events.models.converters.FontSizeOptionConverter;
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

  @Column(name = Attributes.DENSITY, nullable = false)
  @Convert(converter = DensityOptionConverter.class)
  private DensityOption density;

  @Column(name = Attributes.FONT_SIZE, nullable = false)
  @Convert(converter = FontSizeOptionConverter.class)
  private FontSizeOption fontSize;

  @Column(name = Attributes.DEFAULT_PAGE_SIZE, nullable = false)
  private Integer defaultPageSize;

  @Column(name = Attributes.REMEMBER_STATE, nullable = false)
  private boolean rememberState;
}
