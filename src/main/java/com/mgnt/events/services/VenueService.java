package com.mgnt.events.services;

import static com.mgnt.events.constants.Cache.VENUE_BY_ID;
import static com.mgnt.events.constants.Cache.KEY_ALL;
import static com.mgnt.events.constants.Cache.KEY_ID;
import static com.mgnt.events.constants.Cache.VENUES;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.models.Venue;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.repositories.VenueRepository;
import com.mgnt.events.requests.venues.VenueRequest;
import com.mgnt.events.responses.files.StoredFileSummary;
import com.mgnt.events.responses.venues.VenueResponse;
import com.mgnt.events.util.RequestValidators;

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

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = VENUES, key = KEY_ALL)
  public List<VenueResponse> findAll(@Nullable Integer limit, @Nullable Integer page) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    Integer sanitizedPage =
      sanitizedLimit == null ? null : RequestValidators.requireNonNegativeOrNull(page, Queries.PAGE);
    if (sanitizedLimit == null) {
      return _venueRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    int resolvedPage = sanitizedPage != null ? sanitizedPage.intValue() : 0;

    return _venueRepository
      .findAll(PageRequest.of(resolvedPage, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = VENUE_BY_ID, key = KEY_ID)
  public VenueResponse findById(UUID id) {
    Venue venue = _venueRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));
    return toResponse(Objects.requireNonNull(venue));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VENUES, VENUE_BY_ID }, allEntries = true)
  public VenueResponse create(VenueRequest request) {
    Venue venue = new Venue(request.name(), request.address(), request.contactPerson(), request.type());
    venue.setContactNumber(request.contactNumber());
    venue.setEmail(request.email());
    venue.setLatitude(request.latitude());
    venue.setLongitude(request.longitude());
    venue.setImage(resolveImage(request.imageId()));
  
    return toResponse(_venueRepository.save(venue));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VENUES, VENUE_BY_ID }, allEntries = true)
  public VenueResponse update(UUID id, VenueRequest request) {
    Venue venue = _venueRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

    venue.setName(request.name());
    venue.setAddress(request.address());
    venue.setContactPerson(request.contactPerson());
    venue.setContactNumber(request.contactNumber());
    venue.setEmail(request.email());
    venue.setType(request.type());
    venue.setLatitude(request.latitude());
    venue.setLongitude(request.longitude());
    venue.setImage(resolveImage(request.imageId()));

    return toResponse(_venueRepository.save(venue));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VENUES, VENUE_BY_ID }, allEntries = true)
  public void delete(UUID id) {
    Venue venue = _venueRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

    if(venue != null) {
      _venueRepository.delete(venue);
    }
  }

  private VenueResponse toResponse(Venue venue) {
    Venue ensuredVenue = Objects.requireNonNull(venue, "Venue must not be null");
    return new VenueResponse(
      Objects.requireNonNull(ensuredVenue.getId(), "Venue identifier must not be null"),
      ensuredVenue.getName(),
      ensuredVenue.getAddress(),
      ensuredVenue.getContactPerson(),
      ensuredVenue.getContactNumber(),
      ensuredVenue.getEmail(),
      ensuredVenue.getType(),
      ensuredVenue.getLatitude(),
      ensuredVenue.getLongitude(),
      toStoredFileSummary(ensuredVenue.getImage()),
      ensuredVenue.getCreatedAt(),
      ensuredVenue.getUpdatedAt()
    );
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

  private StoredFile resolveImage(UUID imageId) {
    if (imageId == null) {
      return null;
    }

    return _storedFileRepository
      .findById(imageId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image not found"));
  }
}
