package com.upraised.database.datatypes;

public enum FundingStage {

  IPO("IPO"),
  MnA("M&A"),
  SEED_ROUND("Seed Round"),
  SERIESA("SeriesA"),
  SERIESB("SeriesB"),
  SERIESC("SeriesC"),
  SERIESD("SeriesD"),
  SERIESDPLUS("SeriesD+"),
  UNFUNDED("Unfunded"),
  UNKNOWN("Unknown");

  private final String value;

  FundingStage(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}