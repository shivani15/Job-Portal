package com.upraised.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upraised.database.datatypes.Category;
import com.upraised.database.datatypes.FundingStage;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "companies",
    uniqueConstraints={
    @UniqueConstraint(columnNames = {"name", "website"})
})
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "funding_stage", nullable = false)
  @Type( type = "pgsql_enum" )
  private FundingStage fundingStage;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "category", nullable = false)
  @Type( type = "pgsql_enum" )
  private Category category;

  @NotBlank
  @Column(nullable = false)
  private String website;

  private String numEmployees;

  @Column(columnDefinition = "text")
  private String visionStatement;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "company_recruiter",
      joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "recruiter_id", referencedColumnName = "id"))
  @JsonIgnore
  private Set<Recruiter> recruiters;

  public Company() {}

  public Company(String name, FundingStage fundingStage, String website) {
    this.name = name;
    this.fundingStage = fundingStage;
    this.website = website;
  }

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

  public Set<Recruiter> getRecruiters() {
    return recruiters;
  }

  public void setRecruiters(Set<Recruiter> recruiters) {
    this.recruiters = recruiters;
  }
}
