package com.upraised.database.repos;

import com.upraised.database.entities.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyManager extends JpaRepository<Company,Long> {

  List<Company> findByNameContainingIgnoreCase(String name);
}
