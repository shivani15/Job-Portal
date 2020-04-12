package com.upraised.database.datatypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Country extends BaseEnum {

  public Country() {
    super();
  }
}
