package com.upraised.database.datatypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class City extends BaseEnum {

  public City() {
    super();
  }
}
