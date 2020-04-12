package com.upraised.database.entities;

import com.sun.tools.javac.util.List;
import com.upraised.database.datatypes.Category;
import com.upraised.database.datatypes.City;
import com.upraised.database.datatypes.Country;
import com.upraised.database.datatypes.FundingStage;
import com.upraised.database.datatypes.JobStatus;
import com.upraised.database.datatypes.SeniorityLevel;
import com.upraised.utils.Constants;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "jobs")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class Job extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "seniority_level", nullable = false)
  private SeniorityLevel seniorityLevel;

  @ManyToOne
  @JoinColumn(name = "county", nullable = false)
  private Country country;

  @ManyToOne
  @JoinColumn(name = "city", nullable = false)
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

  @ManyToOne
  @JoinColumn(name = "status", nullable = false)
  private JobStatus status = new JobStatus(Constants.OPEN);

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recruiter", nullable = false)
  private Recruiter recruiter;

  @ManyToOne
  @JoinColumn(name = "company", nullable = false)
  private Company company;

  public Job() {
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
}
