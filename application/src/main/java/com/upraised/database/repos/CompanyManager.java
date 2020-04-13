package com.upraised.database.repos;

import com.upraised.database.entities.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* This class manages the queries on the companies table in database. */
@Repository
public interface CompanyManager extends JpaRepository<Company,Long> {

  List<Company> findByNameContainingIgnoreCase(String name);
}
