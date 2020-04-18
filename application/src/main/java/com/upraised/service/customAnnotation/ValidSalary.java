package com.upraised.service.customAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = SalaryValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalary {

  String message() default "Salary values are not valid, one of the field can't be null!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}