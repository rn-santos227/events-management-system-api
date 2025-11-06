package com.mgnt.events.controllers;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.privileges.PrivilegeRequest;
import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.services.PrivilegeService;

@RestController
@RequestMapping(Routes.PRIVILEGES)
public class PrivilegeController {
  private final PrivilegeService _privilegeService;

  public PrivilegeController(PrivilegeService privilegeService) {
    this._privilegeService = privilegeService;
  }

  @GetMapping
  public List<PrivilegeResponse> findAll(@RequestParam(name = Queries.LIMIT, required = false) Integer limit) {
    return _privilegeService.findAll(limit);
  }

  @GetMapping(Routes.APPEND_ID)
  public PrivilegeResponse findById(@PathVariable @NonNull Long id) {
    return _privilegeService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PrivilegeResponse create(@Valid @RequestBody PrivilegeRequest request) {
    return _privilegeService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public PrivilegeResponse update(
    @PathVariable @NonNull Long id,
    @Valid @RequestBody PrivilegeRequest request
  ) {
    return _privilegeService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull Long id) {
    _privilegeService.delete(id);
  }
}
