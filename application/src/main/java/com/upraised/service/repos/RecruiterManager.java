package com.upraised.service.repos;

import com.upraised.database.entities.Recruiter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/* This class manages the queries on the recruiters table in database. */
public interface RecruiterManager extends JpaRepository<Recruiter,Long> {
  Optional<Recruiter> findById(Long recruiterID);
}
