package com.upraised.database.datatypes;

public enum SeniorityLevel {

  INTERNSHIP("Internship"),
  ENTRY("Entry Level"),
  MID_SENIOR("Mid Senior Level"),
  SENIOR("Senior Level"),
  DIRECTOR("Director"),
  EXECUTIVE("Executive");

  private final String value;

  SeniorityLevel(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}