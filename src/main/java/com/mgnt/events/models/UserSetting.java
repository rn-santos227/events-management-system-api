package com.mgnt.events.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Tables;

@Entity
@Table(name = Tables.USER_SETTINGS)
@SQLDelete(sql = Queries.DELETE_USER_SETTINGS)
@Getter
@Setter
public class UserSetting {
  
}
