package com.upraised.service;

import com.upraised.database.entities.Company;
import com.upraised.database.repos.CompanyManager;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyApi {

  @Autowired
  private CompanyManager companyManager;

  @GetMapping("/companies")
  public List<Company> getAllCompanies() {
    return companyManager.findAll();
  }

  @PostMapping("/companies")
  public Company createCompany(@Valid @RequestBody Company company) {
    return companyManager.save(company);
  }

  @GetMapping("/companies/search")
  public List<Company> searchCompanies(String name) {
    return companyManager.findByNameContainingIgnoreCase(name);
  }
}
