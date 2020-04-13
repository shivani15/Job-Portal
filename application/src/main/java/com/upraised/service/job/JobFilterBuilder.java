package com.upraised.service.job;

import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.SeniorityLevel;
import com.upraised.database.entities.Job;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

// Basic validator interface for applying filter.
interface IValidator {
  Predicate validate(Root<Job> job,String value);
}

// Builder class to create Predicate for filtering the jobs based on passed params
public class JobFilterBuilder {

  private CriteriaBuilder builder;
  private Root<Job> job;
  private Predicate jobFilter = null;

  JobFilterBuilder(CriteriaBuilder builder, Root<Job> job){
    this.builder = builder;
    this.job = job;
  }

  public Predicate getJobFilterPredicate() {
    return jobFilter;
  }

  /* Add a new filter to the query based on the passed field and values. */
  public JobFilterBuilder addFilter(List<String> values, String field) {
    if(values != null && field != null) {
      switch (field) {
        case "company": {
          // apply company filters if any and get filtered query
          IValidator companyValidator = (obj, value) -> {
            String likeValue = "%" + value + "%";
            return builder.like(obj.get("company").get("name"), likeValue);
          };
          jobFilter = getUpdatedPredicate(values, companyValidator, job, jobFilter);
          break;
        }
        case "country": {
          // apply country filters if any and get filtered query
          IValidator countryValidator = (obj, value) -> {
            return builder.equal(obj.get("country"), Country.valueOf(value));
          };
          jobFilter = getUpdatedPredicate(values, countryValidator, job, jobFilter);
          break;
        }
        case "cities": {
          // apply cities filters if any and get filtered query
          IValidator citiesValidator = (obj, value) -> {
            return builder.equal(obj.get("city"), City.valueOf(value));
          };
          jobFilter = getUpdatedPredicate(values, citiesValidator, job, jobFilter);
          break;
        }
        case "title": {
          // apply titles filters if any and get filtered query
          IValidator titlesValidator = (obj, value) -> {
            String likeValue = "%" + value + "%";
            return builder.like(obj.get("title"), likeValue);
          };
          jobFilter = getUpdatedPredicate(values, titlesValidator, job, jobFilter);
        }
        case "seniority_level": {
          // apply levels filters if any and get filtered query
          IValidator levelsValidator = (obj, value) -> {
            return builder.equal(obj.get("seniorityLevel"), SeniorityLevel.valueOf(value));
          };
          jobFilter = getUpdatedPredicate(values, levelsValidator, job, jobFilter);
        }
      }
    }
    return this;
  }

  /*
  Common utility to iterate the values list and create calculatedPredicate by taking 'OR' of predicate for all values.
  Then update the queryPredicate by taking  'AND' with the calculatedPredicate.
  * */
  private Predicate getUpdatedPredicate(List<String> values, IValidator validator,
      Root<Job> job, Predicate queryPredicate) {

    Predicate calculatedPredicate = null;
    if(values != null) {
      for (String value : values) {
        Predicate predicate = validator.validate(job,value);
        if(calculatedPredicate == null) {
          calculatedPredicate = predicate;
        } else {
          calculatedPredicate = builder.or(calculatedPredicate, predicate);
        }
      }
    }

    if(calculatedPredicate != null) {
      if(queryPredicate == null) {
        queryPredicate = calculatedPredicate;
      } else {
        queryPredicate = builder.and(queryPredicate, calculatedPredicate);
      }
    }
    return queryPredicate;
  }

}
