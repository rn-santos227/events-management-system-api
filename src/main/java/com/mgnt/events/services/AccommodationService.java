package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.AccommodationRepository;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.responses.files.StoredFileSummary;

@Service
public class AccommodationService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);

  private final AccommodationRepository _accommodationRepository;
  private final StoredFileRepository _storedFileRepository;

  public AccommodationService(
    AccommodationRepository accommodationRepository,
    StoredFileRepository storedFileRepository
  ) {
    this._accommodationRepository = accommodationRepository;
    this._storedFileRepository = storedFileRepository;
  }

  private StoredFileSummary toStoredFileSummary(StoredFile image) {
    if (image == null) {
      return null;
    }

  }
}
