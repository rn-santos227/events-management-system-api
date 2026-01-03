package com.mgnt.events.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.mgnt.events.enums.DensityOption;

@Converter(autoApply = false)
public class DensityOptionConverter implements AttributeConverter<DensityOption, String> {
  @Override
  public String convertToDatabaseColumn(DensityOption attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public DensityOption convertToEntityAttribute(String dbData) {
    return dbData == null ? null : DensityOption.valueOf(dbData);
  }
}
