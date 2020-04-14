package com.upraised.integerationtest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import com.upraised.Application;
import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.FundingStage;
import com.upraised.database.datatypes.SeniorityLevel;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {ApiIntegrationTest.Initializer.class})
public class ApiIntegrationTest {

  @ClassRule
  public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
      .withDatabaseName("job_portal")
      .withUsername("postgres")
      .withPassword("test");

  @Value("http://localhost:${local.server.port}")
  String baseUrl;

  private final String TEST_COMPANY_NAME = "Test Company";
  private final String TEST_COMPANY_WEBSITE = "http://test.com";
  private final String TEST_JOB_NAME = "Software Developer";

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testGetCompanies_returns_200_with_expected_companies() {
    when().
        get(baseUrl + "/company").
        then()
        .statusCode(HttpStatus.OK.value()).assertThat()
        .body("size()", greaterThan(1));
  }

  @Test
  public void testSearchCompaniesByName_returns_200_with_expected_companies() {
    when().
        get(baseUrl + "/company?name=druv")
        .then()
        .statusCode(HttpStatus.OK.value()).assertThat()
        .body("size()", equalTo(1));
  }

  @Test
  public void testSearchCompaniesByName_returns_200_with_no_companies() {
    when().
        get(baseUrl + "/company?name=shivani")
        .then()
        .statusCode(HttpStatus.OK.value()).assertThat()
        .body("size()", equalTo(0));
  }

  private Response createTestCompany(String companyName, String companyWebsite) {
    Map<String,String> requestBody = new HashMap<>();
    if(companyName != null) {
      requestBody.put("name", companyName);
    }
    requestBody.put("fundingStage", FundingStage.IPO.toString());
    if(companyWebsite != null) {
      requestBody.put("website", companyWebsite);
    }
    return given().contentType("application/json")
        .body(requestBody)
        .when()
        .post(baseUrl + "/company");
  }

  private Response createJob(String title, Long companyId, Integer recruiterId) {
    Map<String,String> requestBody = new HashMap<>();
    if(title != null) {
      requestBody.put("title", title);
    }
    requestBody.put("seniorityLevel", SeniorityLevel.ENTRY_LEVEL.toString());
    requestBody.put("country", Country.India.toString());
    requestBody.put("city", City.Mumbai.toString());
    RequestSpecification requestSpecification = given().contentType("application/json");
    if(recruiterId != null) {
      requestSpecification.header("Recruiter-Id", recruiterId.toString());
    }
    String url = baseUrl + "/company";
    if(companyId != null) {
      url += "/" + companyId.toString();
    }
    url += "/job";
    return requestSpecification.body(requestBody)
        .when()
        .post(url);
  }

  @Test
  public void testCreateCompany_returns_201_with_created_company() {

    Response response = createTestCompany(TEST_COMPANY_NAME, TEST_COMPANY_WEBSITE);
    response.then()
        .statusCode(HttpStatus.CREATED.value()).assertThat()
        .body("name", equalTo(TEST_COMPANY_NAME))
        .body("fundingStage", equalTo(FundingStage.IPO.toString()))
        .body("website", equalTo(TEST_COMPANY_WEBSITE));
  }

  @Test
  public void testCreateExistingCompany_returns_409() {
    String companyName = TEST_COMPANY_NAME + UUID.randomUUID();
    String website = TEST_COMPANY_WEBSITE + UUID.randomUUID();
    createTestCompany(companyName, website);
    Response response = createTestCompany(companyName, website);
    response.then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  public void testCreateCompanyWithMissingValues_returns_400() {
    Response response = createTestCompany(TEST_COMPANY_NAME+ UUID.randomUUID(), null);
    response.then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void testCreateJob_returns_201_with_created_job() {

    Response response = createJob(TEST_JOB_NAME, (long) 20, 2);
    response.then()
        .statusCode(HttpStatus.CREATED.value()).assertThat()
        .body("title", equalTo(TEST_JOB_NAME))
        .body("seniorityLevel", equalTo(SeniorityLevel.ENTRY_LEVEL.toString()))
        .body("city", equalTo(City.Mumbai.toString()));
  }

  @Test
  public void testCreateJobWithMissingTitle_returns_400() {

    Response response = createJob(null, (long) 20, 2);
    response.then()
        .statusCode(HttpStatus.BAD_REQUEST.value()).assertThat();
  }

  @Test
  public void testCreateJobWithMissingRecruiter_returns_400() {

    Response response = createJob(TEST_JOB_NAME, (long) 20, null);
    response.then()
        .statusCode(HttpStatus.BAD_REQUEST.value()).assertThat();
  }

  @Test
  public void testCreateJobWithMissingCompany_returns_404() {

    Response response = createJob(TEST_JOB_NAME, null, 2);
    response.then()
        .statusCode(HttpStatus.NOT_FOUND.value()).assertThat();
  }

  @Test
  public void testGetExistingJob_returns_200_with_expected_jobs() {
    when().
        get(baseUrl + "/job").
        then()
        .statusCode(HttpStatus.OK.value()).assertThat()
        .body("size()", greaterThan(1));
  }

  @Test
  public void testSearchJobsWithFilters_returns_200_with_expected_jobs() {
    when().
        get(baseUrl + "/job?company=Qualcomm&company=adda&country=India&seniority_level=SENIOR_LEVEL")
        .then()
        .statusCode(HttpStatus.OK.value()).assertThat()
        .body("size()", equalTo(1));
  }

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + postgres.getJdbcUrl(),
          "spring.datasource.username=" + postgres.getUsername(),
          "spring.datasource.password=" + postgres.getPassword(),
          "spring.batch.initialize-schema=always",
          "spring.datasource.initialization-mode=always"
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

}
