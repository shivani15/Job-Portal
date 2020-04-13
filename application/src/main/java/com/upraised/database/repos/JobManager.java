package com.upraised.database.repos;

import com.upraised.database.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* This class manages the queries on the jobs table in database. */
@Repository
public interface JobManager extends JpaRepository<Job,Long> {}
