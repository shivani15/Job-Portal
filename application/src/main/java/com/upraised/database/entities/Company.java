package com.upraised.database.entities;

import com.upraised.database.datatypes.Category;
import com.upraised.database.datatypes.FundingStage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

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

  @ManyToOne
  @JoinColumn(name = "funding_stage", nullable = false)
  private FundingStage fundingStage;

  @ManyToOne
  @JoinColumn(name = "category")
  private Category category;

  @NotBlank
  @Column(nullable = false)
  private String website;

  private String numEmployees;

  @Column(columnDefinition = "text")
  private String visionStatement;

  public Company() {}

  public Company(String name, String fundingStage, String website) {
    this.name = name;
    this.fundingStage = new FundingStage(fundingStage);
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

  public String getFundingStage() {
    return fundingStage.getValue();
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
}
