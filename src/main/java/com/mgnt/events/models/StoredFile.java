package com.mgnt.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Defaults;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.STORED_FILES)
@SQLDelete(sql = Queries.DELETE_STORED_FILES)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class StoredFile extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = Attributes.FILE_NAME, nullable = false, length = Defaults.DEFAULT_FILE_NAME_LENGTH)
  private String fileName;

  @Column(
    name = Attributes.STORAGE_KEY,
    nullable = false,
    unique = true,
    length = Defaults.DEFAULT_STORAGE_KEY_LENGTH
  )
  private String storageKey;

  @Column(name = Attributes.BUCKET, nullable = false, length = Defaults.DEFAULT_BUCKET_LENGTH)
  private String bucket;

  @Column(name = Attributes.CONTENT_TYPE, length = Defaults.DEFAULT_CONTENT_TYPE_LENGTH)
  private String contentType;

  @Column(name = Attributes.FILE_SIZE, nullable = false)
  private long size;

  @Column(name = Attributes.NOTES, columnDefinition = Queries.TEXT)
  private String notes;

  @Column(name = Attributes.URL, length = Defaults.DEFAULT_URL_LENGTH)
  private String url;
}
