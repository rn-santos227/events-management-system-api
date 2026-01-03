package com.mgnt.events.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.mgnt.events.enums.ThemeOption;

@Converter(autoApply = false)
public class ThemeOptionConverter implements AttributeConverter<ThemeOption, String> {
  @Override
  public String convertToDatabaseColumn(ThemeOption attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public ThemeOption convertToEntityAttribute(String dbData) {
    return dbData == null ? null : ThemeOption.valueOf(dbData);
  }
}
