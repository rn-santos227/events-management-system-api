package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.Accommodation;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.AccommodationRepository;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.requests.accommodations.AccommodationRequest;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
import com.mgnt.events.responses.files.StoredFileSummary;
import com.mgnt.events.util.RequestValidators;

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

  @Transactional(readOnly = true)
  public List<AccommodationResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
   if (sanitizedLimit == null) {
      return _accommodationRepository
        .findAll(DEFAULT_SORT)
        .stream()
        .map(this::toResponse)
        .toList();
    }

    return _accommodationRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  public AccommodationResponse findById(UUID id) {
    Accommodation accommodation = _accommodationRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));
    return toResponse(Objects.requireNonNull(accommodation));
  }

  @Transactional(rollbackFor = Throwable.class)
  public AccommodationResponse create(AccommodationRequest request) {
    Accommodation accommodation = new Accommodation(
      request.name(),
      request.address(),
      request.contactPerson(),
      request.contactNumber(),
      request.email(),
      request.type()
    );

    accommodation.setLatitude(request.latitude());
    accommodation.setLongitude(request.longitude());
    accommodation.setImage(resolveImage(request.imageId()));
    return toResponse(_accommodationRepository.save(accommodation));
  }

  @Transactional(rollbackFor = Throwable.class)
  public AccommodationResponse update(UUID id, AccommodationRequest request) {
    Accommodation accommodation = _accommodationRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));

    accommodation.setName(request.name());
    accommodation.setAddress(request.address());
    accommodation.setContactPerson(request.contactPerson());
    accommodation.setContactNumber(request.contactNumber());
    accommodation.setEmail(request.email());
    accommodation.setType(request.type());
    accommodation.setLatitude(request.latitude());
    accommodation.setLongitude(request.longitude());
    accommodation.setImage(resolveImage(request.imageId()));

    return toResponse(_accommodationRepository.save(accommodation));
  }

  @Transactional(rollbackFor = Throwable.class)
  public void delete(UUID id) {
    Accommodation accommodation = _accommodationRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));
      
    if (accommodation != null) {
      _accommodationRepository.delete(accommodation);
    }
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
