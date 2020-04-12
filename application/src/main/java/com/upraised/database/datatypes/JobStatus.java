package com.upraised.database.datatypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class JobStatus extends BaseEnum {

  public JobStatus() {
    super();
  }

  public JobStatus(String value) {
    super(value);
  }
}
