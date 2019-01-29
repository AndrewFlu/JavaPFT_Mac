package ru.andrew.pft.rest;

public class Issue {

  private int id;
  private String title;
  private String description;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public Issue withTitle(String title) {
    this.title = title;
    return this;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }
}
