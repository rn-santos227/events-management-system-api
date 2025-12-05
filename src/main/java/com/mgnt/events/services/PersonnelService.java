package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.PersonnelRepository;

@Service
public class PersonnelService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PersonnelRepository _personnelRepository;

  public PersonnelService(PersonnelRepository personnelRepository) {
    this._personnelRepository = personnelRepository;
  }
}
