package com.upraised.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Table(name = "founders")
public class Founder extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recruiter_generator")
  @SequenceGenerator(name="recruiter_generator", sequenceName = "recruiters_id_seq", allocationSize=1)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "text")
  private String description;

  /* Single founder can be associated with multiple companies. */
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "founders")
  @JsonIgnore
  private Set<Company> companies;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Company> getCompanies() {
    return companies;
  }

  public Set<Map<String, Object>> getCompany() {
    if (companies.isEmpty()) return null;

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
