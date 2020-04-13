package com.upraised.service.api;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.FundingStage;
import com.upraised.database.datatypes.SeniorityLevel;
import com.upraised.database.entities.Company;
import com.upraised.database.entities.Job;
import com.upraised.database.entities.Recruiter;
import com.upraised.exceptionUtils.HttpConflictException;
import com.upraised.exceptionUtils.HttpForbiddenException;
import com.upraised.exceptionUtils.HttpNotFoundException;
import com.upraised.service.job.JobFilterBuilder;
import com.upraised.service.repos.CompanyManager;
import com.upraised.service.repos.JobManager;
import com.upraised.service.repos.RecruiterManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiTest {

  @InjectMocks
  private Api testApiInterface;

  @Mock
  private CompanyManager mockCompanyManager;

  @Mock
  private JobManager mockJobManager;

  @Mock
  private RecruiterManager mockRecruiterManager;

  private List<Company> testCompaniesList;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    createTestCompanies();
  }

  private void createTestCompanies() {
    testCompaniesList = new ArrayList<>();
    testCompaniesList.add(createCompanyWith((long) 1, "TestCompany1",
        FundingStage.SEED_ROUND, "TestWebLink1"));
    testCompaniesList.add(createCompanyWith((long) 2, "TestCompany2",
        FundingStage.SERIES_A, "TestWebLink2"));
    testCompaniesList.add(createCompanyWith((long) 3, "TestNewCompany1",
        FundingStage.MnA, "TestWebLink3"));
    testCompaniesList.add(createCompanyWith((long) 4, "TestNewCompany2",
        FundingStage.IPO, "TestWebLink4"));
    testCompaniesList.add(createCompanyWith((long) 5, "TestNEWCompany3",
        FundingStage.SEED_ROUND, "TestWebLink5"));
  }

  @Test
  public void testGetAllCompanies() {

    when(mockCompanyManager.findAll()).thenReturn(testCompaniesList);

    List<Company> companiesList = testApiInterface.getCompanies(null);
    Assert.assertEquals(testCompaniesList, companiesList);
  }

  @Test
  public void testGetCompaniesWithName() {

    when(mockCompanyManager.findByNameContainingIgnoreCase("new")).thenReturn(testCompaniesList.subList(2,4));

    List<Company> companiesList = testApiInterface.getCompanies("new");
    Assert.assertEquals(testCompaniesList.subList(2,4), companiesList);
  }

  @Test
  public void testCreateCompany() {

    when(mockCompanyManager.save(any())).thenReturn(testCompaniesList.get(0));

    ResponseEntity<Company> response = testApiInterface.createCompany(testCompaniesList.get(0));
    Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assert.assertEquals(testCompaniesList.get(0), response.getBody());
  }

  @Test(expected= HttpConflictException.class)
  public void testCreateCompanyConflict() {

    when(mockCompanyManager.save(any())).thenThrow(DataIntegrityViolationException.class);

    ResponseEntity<Company> reponse = testApiInterface.createCompany(testCompaniesList.get(0));
  }

  @Test
  public void testCreateJob() {

    Set<Company> companies = new HashSet<Company>();
    companies.add(testCompaniesList.get(0));
    companies.add(testCompaniesList.get(1));
    Company company = testCompaniesList.get(0);
    Recruiter recruiter = createTestRecruiterForCompany(companies);
    Job job = createTestJobForCompanyByRecruiter(company, recruiter);

    when(mockRecruiterManager.findById(any())).thenReturn((Optional<Recruiter>)Optional.of(recruiter));
    when(mockCompanyManager.findById(any())).thenReturn((Optional<Company>)Optional.of(company));
    when(mockJobManager.save(any())).thenReturn(job);

    ResponseEntity<Job> response = testApiInterface.createJob(company.getId(), recruiter.getId(), createTestJob());
    Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assert.assertEquals(job, response.getBody());
  }

  @Test(expected = HttpNotFoundException.class)
  public void testCreateJobWithNonExistingRecruiter() {

    Set<Company> companies = new HashSet<Company>();
    companies.add(testCompaniesList.get(0));
    companies.add(testCompaniesList.get(1));
    Company company = testCompaniesList.get(0);
    Recruiter recruiter = createTestRecruiterForCompany(companies);

    when(mockRecruiterManager.findById(any())).thenReturn(Optional.empty());

    ResponseEntity<Job> response = testApiInterface.createJob(company.getId(), recruiter.getId(), createTestJob());
  }

  @Test(expected = HttpNotFoundException.class)
  public void testCreateJobWithNonExistingCompany() {

    Set<Company> companies = new HashSet<Company>();
    companies.add(testCompaniesList.get(0));
    companies.add(testCompaniesList.get(1));
    Company company = testCompaniesList.get(0);
    Recruiter recruiter = createTestRecruiterForCompany(companies);

    when(mockRecruiterManager.findById(any())).thenReturn((Optional<Recruiter>)Optional.of(recruiter));
    when(mockCompanyManager.findById(any())).thenReturn(Optional.empty());

    ResponseEntity<Job> response = testApiInterface.createJob(company.getId(), recruiter.getId(), createTestJob());
  }

  @Test(expected = HttpForbiddenException.class)
  public void testCreateJobWithInvalidRecruiter() {

    Set<Company> companies = new HashSet<Company>();
    companies.add(testCompaniesList.get(1));
    Company company = testCompaniesList.get(0);
    Recruiter recruiter = createTestRecruiterForCompany(companies);

    when(mockRecruiterManager.findById(any())).thenReturn((Optional<Recruiter>)Optional.of(recruiter));
    when(mockCompanyManager.findById(any())).thenReturn((Optional<Company>)Optional.of(company));

    ResponseEntity<Job> response = testApiInterface.createJob(company.getId(), recruiter.getId(), createTestJob());
  }

  @Test
  public void testGetJobs() {

    Job job = createTestJob();
    List<Job> jobList = new ArrayList<>();
    jobList.add(job);
    JobFilterBuilder mockJobFilterBuilder = mock(JobFilterBuilder.class);
    when(mockJobManager.createJobFilterBuilder()).thenReturn(mockJobFilterBuilder);
    when(mockJobFilterBuilder.addFilter(any(),any())).thenReturn(mockJobFilterBuilder);
    when(mockJobManager.filterJobs(any())).thenReturn(jobList);

    Assert.assertEquals(jobList, testApiInterface.getJobs(
        null, null, null, null, null));
  }

  private Company createCompanyWith(Long id, String name, FundingStage fundingStage, String website) {
    Company company = new Company();
    company.setId(id);
    company.setName(name);
    company.setFundingStage(fundingStage);
    company.setWebsite(website);
    return company;
  }

  private Recruiter createTestRecruiterForCompany(Set<Company> companies) {
    Recruiter recruiter = new Recruiter();
    recruiter.setId((long) 1);
    recruiter.setName("TestRecruiter");
    recruiter.setEmailId("test@test.com");
    recruiter.setCompanies(companies);

    Set<Recruiter> recruiters = new HashSet<Recruiter>();
    recruiters.add(recruiter);
    Iterator<Company> itr = companies.iterator();
    while(itr.hasNext()){
      itr.next().setRecruiters(recruiters);
    }
    return recruiter;
  }

  private Job createTestJob() {
    Job job = new Job();
    job.setId((long) 1);
    job.setTitle("TestJob");
    job.setCountry(Country.India);
    job.setCity(City.Mumbai);
    job.setSeniorityLevel(SeniorityLevel.ENTRY_LEVEL);
    return job;
  }

  private Job createTestJobForCompanyByRecruiter(Company company, Recruiter recruiter) {
    Job job = createTestJob();

    job.setCompany(company);
    job.setRecruiter(recruiter);
    return job;
  }
}