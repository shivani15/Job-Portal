package com.upraised.database.datatypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SeniorityLevel extends BaseEnum {

  public SeniorityLevel() {
    super();
  }
}
