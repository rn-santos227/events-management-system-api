package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;

@Service
public class PrivilegeService {
  private static final Sort DEFA_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
}
