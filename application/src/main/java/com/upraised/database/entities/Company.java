package com.upraised.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.upraised.database.datatypes.Category;
import com.upraised.database.datatypes.FundingStage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;

/* This class in an entity is mapped to companies table in postgres database. */
@Entity
@Table(name = "companies",
    uniqueConstraints={
    @UniqueConstraint(columnNames = {"name", "website"})
})
@JsonInclude(Include.NON_NULL)
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
  @SequenceGenerator(name="company_generator", sequenceName = "companies_id_seq", allocationSize=1)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "funding_stage", nullable = false)
  @Type( type = "pgsql_enum" )
  private FundingStage fundingStage;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "category")
  @Type( type = "pgsql_enum" )
  private Category category;

  @NotBlank
  @Column(nullable = false)
  private String website;

  private String numEmployees;

  @Column(columnDefinition = "text")
  private String visionStatement;

  /* Single company can have multiple recruiters. */
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "company_recruiter",
      joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "recruiter_id", referencedColumnName = "id"))
  private Set<Recruiter> recruiters;

  /* Single company can have founders recruiters. */
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "company_founder",
      joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "founder_id", referencedColumnName = "id"))
  private Set<Founder> founders;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FundingStage getFundingStage() {
    return fundingStage;
  }

  public void setFundingStage(FundingStage fundingStage) {
    this.fundingStage = fundingStage;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getNumEmployees() {
    return numEmployees;
  }

  public void setNumEmployees(String numEmployees) {
    this.numEmployees = numEmployees;
  }

  public String getVisionStatement() {
    return visionStatement;
  }

  public void setVisionStatement(String visionStatement) {
    this.visionStatement = visionStatement;
  }

  @JsonIgnore
  public Set<Recruiter> getRecruitersList() {
    return recruiters;
  }

  public Set<Map<String, Object>> getRecruiters() {
    if (recruiters.isEmpty()) return null;

    Set<Map<String, Object>> recruiterInfo = new HashSet<>();
    for (Recruiter recruiter: recruiters) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", recruiter.getId());
      map.put("name", recruiter.getName());
      map.put("email", recruiter.getEmailId());
      recruiterInfo.add(map);
    }
    return recruiterInfo;
  }

  public void setRecruiters(Set<Recruiter> recruiters) {
    this.recruiters = recruiters;
  }

  public Set<Map<String, Object>> getFounders() {
    if (founders.isEmpty()) return null;

    Set<Map<String, Object>> founderInfo = new HashSet<>();
    for (Founder founder: founders) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", founder.getId());
      map.put("name", founder.getName());
      map.put("description", founder.getDescription());
      founderInfo.add(map);
    }
    return founderInfo;
  }

  public void setFounders(Set<Founder> founders) {
    this.founders = founders;
  }
}
