package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.PrivilegeRepository;

@Service
public class PrivilegeService {
  private static final Sort DEFA_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this.privilegeRepository = privilegeRepository;
  }  
}
