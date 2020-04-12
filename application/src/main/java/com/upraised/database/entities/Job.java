package com.upraised.database.entities;


import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.JobStatus;
import com.upraised.database.datatypes.SeniorityLevel;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "jobs")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
@TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)
public class Job extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "seniority_level", nullable = false)
  @Type( type = "pgsql_enum" )
  private SeniorityLevel seniorityLevel;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "country", nullable = false)
  @Type( type = "pgsql_enum" )
  private Country country;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "city", nullable = false)
  @Type( type = "pgsql_enum" )
  private City city;

  @Column(columnDefinition = "text")
  private String description;

  @Type(type = "list-array")
  @Column(columnDefinition = "varchar[]")
  private List<String> skills;

  private String websiteLink;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private final Date postingDate = new Date();

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "job_status", nullable = false)
  @Type( type = "pgsql_enum" )
  private JobStatus status = JobStatus.OPEN;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recruiter", nullable = false)
  private Recruiter recruiter;

  @ManyToOne
  @JoinColumn(name = "company", nullable = false)
  private Company company;

  public Job() {
  }

  public Job(String title, SeniorityLevel seniorityLevel, Country country, City city) {
    this.title = title;
    this.seniorityLevel = seniorityLevel;
    this.country = country;
    this.city = city;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public SeniorityLevel getSeniorityLevel() {
    return seniorityLevel;
  }

  public void setSeniorityLevel(SeniorityLevel seniorityLevel) {
    this.seniorityLevel = seniorityLevel;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getSkills() {
    return skills;
  }

  public void setSkills(List<String> skills) {
    this.skills = skills;
  }

  public String getWebsiteLink() {
    return websiteLink;
  }

  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }

  public Date getPostingDate() {
    return postingDate;
  }

  public JobStatus getStatus() {
    return status;
  }

  public void setStatus(JobStatus status) {
    this.status = status;
  }

  public Map<String, Object> getRecruiter() {
    Map<String, Object> map = new HashMap<>();
    map.put("id",recruiter.getId());
    map.put("name", recruiter.getName());
    map.put("email", recruiter.getEmailId());
    return map;
  }

  public void setRecruiter(Recruiter recruiter) {
    this.recruiter = recruiter;
  }

  public Map<String, Object> getCompany() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", company.getId());
    map.put("name", company.getName());
    return map;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
