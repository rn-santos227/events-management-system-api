package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Personnel;
import com.mgnt.events.repositories.PersonnelRepository;
import com.mgnt.events.responses.personnel.PersonnelResponse;

@Service
public class PersonnelService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PersonnelRepository _personnelRepository;

  public PersonnelService(PersonnelRepository personnelRepository) {
    this._personnelRepository = personnelRepository;
  }

  @Transactional(readOnly = true)
  public List<PersonnelResponse> findAll(@Nullable Integer limit) {

  }

  private PersonnelResponse toRespose(Personnel personnel) {
    Personnel ensuredPersonnel = Objects.requireNonNull(personnel, "Personnel must not be null");
    return new PersonnelResponse(
      Objects.requireNonNull(ensuredPersonnel.getId(), "Personnel identifier must not be null"),
      ensuredPersonnel.getFullName(),
      ensuredPersonnel.getContactNumber(),
      ensuredPersonnel.getEmail(),
      ensuredPersonnel.getRole(),
      ensuredPersonnel.getCreatedAt(),
      ensuredPersonnel.getUpdatedAt()
    );
  }
}
