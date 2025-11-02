package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.responses.privileges.PrivilegeResponse;

@Service
public class PrivilegeService {
  private static final Sort DEFA_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this.privilegeRepository = privilegeRepository;
  }


  private PrivilegeResponse toResponse(Privilege privilege) {

  }
}
