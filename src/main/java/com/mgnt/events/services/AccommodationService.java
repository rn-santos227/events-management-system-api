package com.mgnt.events.services;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.lang.NonNull;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Accommodation;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.AccommodationRepository;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
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

    StoredFile ensuredFile = Objects.requireNonNull(image, "Stored file must not be null");
    return new StoredFileSummary(
      Objects.requireNonNull(ensuredFile.getId(), "Stored file identifier must not be null"),
      ensuredFile.getFileName(),
      ensuredFile.getUrl()
    );
  }

  private AccommodationResponse toResponse(Accommodation accommodation) {
    Accommodation ensuredAccommodation = Objects.requireNonNull(accommodation, "Accommodation must not be null");
    return new AccommodationResponse(
      Objects.requireNonNull(ensuredAccommodation.getId(), "Accommodation identifier must not be null"),
      ensuredAccommodation.getName(),
      ensuredAccommodation.getAddress(),
      ensuredAccommodation.getContactPerson(),
      ensuredAccommodation.getContactNumber(),
      ensuredAccommodation.getEmail(),
      ensuredAccommodation.getType(),
      ensuredAccommodation.getLatitude(),
      ensuredAccommodation.getLongitude(),
      toStoredFileSummary(ensuredAccommodation.getImage()),
      ensuredAccommodation.getCreatedAt(),
      ensuredAccommodation.getUpdatedAt()
    );
  }

  private StoredFile resolveImage(UUID imageId) {
    if (imageId == null) {
      return null;
    }

    return _storedFileRepository
      .findById(imageId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image not found"));
  }
}
