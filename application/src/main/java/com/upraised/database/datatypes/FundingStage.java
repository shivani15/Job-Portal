package com.upraised.database.datatypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class FundingStage extends BaseEnum {

  public FundingStage() {
    super();
  }

  public FundingStage(String value) {
    super(value);
  }
}
