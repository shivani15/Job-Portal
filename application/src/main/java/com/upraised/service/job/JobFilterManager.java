package com.upraised.service.job;

import com.upraised.database.entities.Job;
import java.util.List;

// Basic interface to provide utilities to apply filters to jobs and get the results
public interface JobFilterManager {

  public JobFilterBuilder createJobFilterBuilder();

  public List<Job> filterJobs(JobFilterBuilder builder);
}
