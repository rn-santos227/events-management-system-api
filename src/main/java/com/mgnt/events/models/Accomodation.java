package com.mgnt.events.models;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Tables.ACCOMMODATIONS)
@SQLDelete(sql = Queries.DELETE_ACCOMMODATIONS)
@SQLRestriction(Queries.DELETE_RESTRICTION)
@Getter
@Setter
public class Accomodation extends AuditableEntity {
  
}
