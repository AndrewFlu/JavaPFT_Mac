package ru.andrew.pft.rest;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id &&
            Objects.equals(title, issue.title) &&
            Objects.equals(description, issue.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description);
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            '}';
  }
}
