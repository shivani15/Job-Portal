package com.upraised.service.job;

import com.upraised.database.entities.Job;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

// Implementation of JobFilterManager interface
public class JobFilterManagerImpl implements JobFilterManager {

  @PersistenceContext
  private EntityManager entityManager;

  private CriteriaQuery<Job> query;
  private Root<Job> job;

  @Override
  public JobFilterBuilder createJobFilterBuilder() {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    query = builder.createQuery( Job.class );
    job = query.from( Job.class );
    JobFilterBuilder jobFilterBuilder = new JobFilterBuilder(builder, job);
    return jobFilterBuilder;
  }

  @Override
  public List<Job> filterJobs(JobFilterBuilder builder) {
    query.select( job );
    if(builder.getJobFilterPredicate() != null) {
      query.where(builder.getJobFilterPredicate());
    }

    return entityManager.createQuery(query)
        .getResultList();
  }
}
