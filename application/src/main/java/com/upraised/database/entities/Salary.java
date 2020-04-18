package com.upraised.database.entities;

import com.upraised.database.datatypes.Currency;
import com.upraised.service.customAnnotation.ValidSalary;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.hibernate.annotations.Type;

@Embeddable
@ValidSalary
public class Salary {

  Integer minSalary;

  Integer maxSalary;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "currency")
  @Type( type = "pgsql_enum" )
  Currency currency;

  public Salary() {}

  public Salary(Integer minSalary, Integer maxSalary, Currency currency) {
    this.minSalary = minSalary;
    this.maxSalary = maxSalary;
    this.currency = currency;
  }

  public Integer getMinSalary() {
    return minSalary;
  }

  public void setMinSalary(int minSalary) {
    this.minSalary = minSalary;
  }

  public Integer getMaxSalary() {
    return maxSalary;
  }

  public void setMaxSalary(int maxSalary) {
    this.maxSalary = maxSalary;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
