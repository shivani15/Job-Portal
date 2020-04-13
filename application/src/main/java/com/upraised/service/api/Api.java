package com.upraised.service.api;

import com.upraised.database.entities.Company;
import com.upraised.database.entities.Job;
import com.upraised.database.entities.Recruiter;
import com.upraised.service.repos.CompanyManager;
import com.upraised.service.job.JobFilterBuilder;
import com.upraised.service.repos.JobManager;
import com.upraised.service.repos.RecruiterManager;
import com.upraised.exceptionUtils.HttpConflictException;
import com.upraised.exceptionUtils.HttpForbiddenException;
import com.upraised.exceptionUtils.HttpNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private CompanyManager companyManager;

  @Autowired
  private JobManager jobManager;

  @Autowired
  private RecruiterManager recruiterManager;

  /* Get api call to get all the companies or companies with passed company name
  @param companyName - take company name as query param "name" to be searched
  @return return the list of matching/all company details if any
  * */
  @GetMapping("/company")
  public List<Company> getCompanies(@RequestParam(value="name", required=false) String companyName) {
    if(companyName == null)
      return companyManager.findAll();

    return companyManager.findByNameContainingIgnoreCase(companyName);
  }

  /* Post api call to create a new company
  @param company - parses and creates a Company object from the request body and validate the input
  @return If successful, return 201(Created) with the newly created company else throw appropriate exception.
  * */
  @PostMapping("/company")
  public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
    try{
      return ResponseEntity.status(HttpStatus.CREATED).body(companyManager.save(company));
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictException("Company with name " + company.getName()
          + " and website " + company.getWebsite() + " already exists");
    }
  }

  /* Post api call to create a new job for the company with {companyId}
  For now since we don't have authentication info, taking Recruiter-Id as an extra header
  @param companyId - it a path param which has the company id for which the new job is to be created
  @param recruiterId - read from Recruiter-Id header, contains the recruiter id who is adding the job
  @param job - parses and creates a Job object from the request body and validate the input
  @return If successful, return 201(Created) with the newly created job else throw appropriate exception.
  * */
  @PostMapping("/company/{companyId}/job")
  public ResponseEntity<Job> createJob(@PathVariable Long companyId,
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

    Set<Recruiter> recruiters = opCompany.get().getRecruiters();
    if (recruiters == null || !recruiters.contains(opRecruiter.get())) {
      throw new HttpForbiddenException("Recruiter with id " + recruiterId + " not allowed to add jobs "
          + "for company with id " + companyId);
    }

    job.setCompany(opCompany.get());
    job.setRecruiter(opRecruiter.get());

    return ResponseEntity.status(HttpStatus.CREATED).body(jobManager.save(job));
  }

  /* Get api call to get all the jobs filter based on passed params
  @param companyName - take list of company names as query param "company" to be filtered
  @param countries - take list of countries as query param "country" to be filtered
  @param cities - take list of cities as query param "city" to be filtered
  @param senioritylevels - take list of senioritylevels as query param "seniority_level" to be filtered
  @return return the list of matching/all job details if any
  * */
  @GetMapping("/job")
  public List<Job> getJobs(@RequestParam(value="company", required=false) List<String> companyNames,
      @RequestParam(value="country", required=false) List<String> countries,
      @RequestParam(value="city", required=false) List<String> cities,
      @RequestParam(value="title", required=false) List<String> titles,
      @RequestParam(value="seniority_level", required=false) List<String> senioritylevels) {

    JobFilterBuilder builder = jobManager.createJobFilterBuilder()
        .addFilter(companyNames, "company")
        .addFilter(countries, "country")
        .addFilter(cities, "cities")
        .addFilter(titles, "title")
        .addFilter(senioritylevels, "seniority_level");
     return jobManager.filterJobs(builder);
  }
}
