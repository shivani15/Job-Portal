package com.upraised.service.repos;

import com.upraised.database.entities.Job;
import com.upraised.service.job.JobFilterManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* This class manages the queries on the jobs table in database.
* It also extends JobFilterManager which manages the job filtering and return results. */
@Repository
public interface JobManager extends JpaRepository<Job,Long>, JobFilterManager {}
