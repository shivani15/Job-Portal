package com.upraised.service.customAnnotation;

import com.upraised.database.entities.Salary;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SalaryValidator implements ConstraintValidator<ValidSalary, Salary> {

  public void initialize(SalaryValidator constraintAnnotation) {
  }

  @Override
  public boolean isValid(Salary salary, ConstraintValidatorContext constraintValidatorContext) {
    return (salary.getCurrency() == null && salary.getMaxSalary() == null && salary.getMinSalary() == null)
        || (salary.getCurrency() != null && salary.getMaxSalary() != null && salary.getMinSalary() != null);
  }
}
