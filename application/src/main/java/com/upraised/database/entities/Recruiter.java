package com.upraised.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* This class in an entity is mapped to recruiters table in postgres database. */
@Entity
@Table(name = "recruiters")
@JsonInclude(Include.NON_NULL)
public class Recruiter extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recruiter_generator")
  @SequenceGenerator(name="recruiter_generator", sequenceName = "recruiters_id_seq", allocationSize=1)
  private Long id;

  @Column(nullable = false, unique = true)
  private String emailId;

  @Column(nullable = false)
  private String name;

  /* Single recruiter can manage jobs for one or more companies. */
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "recruiters")
  @JsonIgnore
  private Set<Company> companies;

  public Recruiter() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Map<String, Object>> getCompany() {
    if (companies ==null || companies.isEmpty()) return null;

    Set<Map<String, Object>> companyInfo = new HashSet<>();
    for (Company company: companies) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", company.getId());
      map.put("name", company.getName());
      companyInfo.add(map);
    }
    return companyInfo;
  }

  public void setCompanies(Set<Company> companies) {
    this.companies = companies;
  }
}
