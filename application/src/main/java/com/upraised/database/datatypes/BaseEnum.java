package com.upraised.database.datatypes;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEnum {

  @Id
  String value;

  BaseEnum() {}

  BaseEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
