package ru.andrew.pft.addressbook.model;

import java.util.Objects;

public class GroupData {
  private final String id;

  private final String groupName;
  private final String groupHeader;
  private final String groupFooter;

  public GroupData(String groupName, String groupHeader, String groupFooter) {
    this.id = null;
    this.groupName = groupName;
    this.groupHeader = groupHeader;
    this.groupFooter = groupFooter;
  }

  public GroupData(String id, String groupName, String groupHeader, String groupFooter) {
    this.id = id;
    this.groupName = groupName;
    this.groupHeader = groupHeader;
    this.groupFooter = groupFooter;
  }

  public String getId() {
    return id;
  }

  public String getGroupName() {
    return groupName;
  }

  public String getGroupHeader() {
    return groupHeader;
  }

  public String getGroupFooter() {
    return groupFooter;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id='" + id + '\'' +
            ", groupName='" + groupName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return Objects.equals(id, groupData.id) &&
            Objects.equals(groupName, groupData.groupName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, groupName);
  }
}
