package ru.andrew.mantis.model;

public class IssueStatus {

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public IssueStatus withId(int id) {
    this.id = id;
    return this;
  }

  public IssueStatus withName(String name) {
    this.name = name;
    return this;
  }
}
