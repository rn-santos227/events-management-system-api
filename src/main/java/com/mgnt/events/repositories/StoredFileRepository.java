package com.mgnt.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgnt.events.models.StoredFile;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {}
