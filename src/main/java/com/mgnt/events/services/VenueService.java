package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.repositories.VenueRepository;

@Service
public class VenueService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);

  private final VenueRepository _venueRepository;
  private final StoredFileRepository _storedFileRepository;

  public VenueService(VenueRepository venueRepository, StoredFileRepository storedFileRepository) {
    this._venueRepository = venueRepository;
    this._storedFileRepository = storedFileRepository;
  }
}
