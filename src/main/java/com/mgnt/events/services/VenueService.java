package com.mgnt.events.services;

import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.repositories.VenueRepository;
import com.mgnt.events.responses.files.StoredFileSummary;

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

  private StoredFileSummary toStoredFileSummary(StoredFile image) {
    if (image == null) {
      return null;
    }

    StoredFile ensuredFile = Objects.requireNonNull(image, "Stored file must not be null");
    return new StoredFileSummary(
      Objects.requireNonNull(ensuredFile.getId(), "Stored file identifier must not be null"),
      ensuredFile.getFileName(),
      ensuredFile.getUrl()
    );
  }
}
