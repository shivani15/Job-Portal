package com.upraised.service;

import com.upraised.database.entities.Company;
import com.upraised.database.entities.Job;
import com.upraised.database.entities.Recruiter;
import com.upraised.database.repos.CompanyManager;
import com.upraised.database.repos.JobManager;
import com.upraised.database.repos.RecruiterManager;
import com.upraised.exceptionUtils.HttpConflictException;
import com.upraised.exceptionUtils.HttpForbiddenException;
import com.upraised.exceptionUtils.HttpNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

  @Autowired
  CompanyManager companyManager;

  @Autowired
  JobManager jobManager;

  @Autowired
  RecruiterManager recruiterManager;

  @GetMapping("/company")
  public List<Company> getCompanies(@RequestParam(value="name", required=false) String companyName) {
    if(companyName == null)
      return companyManager.findAll();

    return companyManager.findByNameContainingIgnoreCase(companyName);
  }

  @PostMapping("/company")
  public Company createCompany(@Valid @RequestBody Company company) {
    try{
      return companyManager.save(company);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictException("Company with name " + company.getName()
          + " and website " + company.getWebsite() + " already exists");
    }
  }

  @PostMapping("/company/{companyId}/job")
  public Job createJob(@PathVariable Long companyId,
      @RequestHeader(value="Recruiter-Id") Long recruiterId,
      @Valid @RequestBody Job job) {

    Optional<Recruiter> opRecruiter = recruiterManager.findById(recruiterId);
    if (!opRecruiter.isPresent()) {
      throw new HttpNotFoundException("Recruiter not found with id " + recruiterId);
    }

    Optional<Company> opCompany = companyManager.findById(companyId);
    if (!opCompany.isPresent()) {
      throw new HttpNotFoundException("Company not found with id " + companyId);
    }

    if (!opCompany.get().getRecruiters().contains(opRecruiter.get())) {
      throw new HttpForbiddenException("Recruiter with id " + recruiterId + " not allowed to add jobs "
          + "for company with id " + companyId);
    }

    job.setCompany(opCompany.get());
    job.setRecruiter(opRecruiter.get());

    return jobManager.save(job);
  }
}
