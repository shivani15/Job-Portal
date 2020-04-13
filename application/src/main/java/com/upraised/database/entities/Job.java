package com.upraised.database.entities;


import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.JobStatus;
import com.upraised.database.datatypes.SeniorityLevel;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/* This class in an entity is mapped to jobs table in postgres database. */
@Entity
@Table(name = "jobs")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class Job extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_generator")
  @SequenceGenerator(name="job_generator", sequenceName = "jobs_id_seq", allocationSize=1)
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

  /* This is an auto-generated field with the current date value.*/
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private final Date postingDate = new Date();

  /* Default state for any new opening is OPEN */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "job_status", nullable = false)
  @Type( type = "pgsql_enum" )
  private JobStatus status = JobStatus.OPEN;

  /* One job is posted/managed by one recruiter, but a single recruiter can have multiple jobs.*/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recruiter", nullable = false)
  private Recruiter recruiter;

  /* One job is linked to only one company, but a single company can have multiple jobs.*/
  @ManyToOne
  @JoinColumn(name = "company", nullable = false)
  private Company company;

  public Job() {}

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
