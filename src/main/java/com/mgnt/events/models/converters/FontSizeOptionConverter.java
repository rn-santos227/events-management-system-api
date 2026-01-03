package com.mgnt.events.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.mgnt.events.enums.FontSizeOption;

@Converter(autoApply = false)
public class FontSizeOptionConverter implements AttributeConverter<FontSizeOption, String> {
  @Override
  public String convertToDatabaseColumn(FontSizeOption attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public FontSizeOption convertToEntityAttribute(String dbData) {
    return dbData == null ? null : FontSizeOption.valueOf(dbData);
  }
}
