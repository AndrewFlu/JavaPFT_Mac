package ru.andrew.pft.addressbook.model;

import java.util.Objects;

public class ContactData {

  private int id;
  private final String contactName;
  private final String lastContactName;
  private final String mobilePhone;
  private final String email1;
  private String group;

  public ContactData(int id, String contactName, String lastContactName, String mobilePhone, String email1, String group) {
    this.id = id;
    this.contactName = contactName;
    this.lastContactName = lastContactName;
    this.mobilePhone = mobilePhone;
    this.email1 = email1;
    this.group = group;
  }

  public ContactData(String contactName, String lastContactName, String mobilePhone, String email1, String group) {
    this.id = Integer.MAX_VALUE;
    this.contactName = contactName;
    this.lastContactName = lastContactName;
    this.mobilePhone = mobilePhone;
    this.email1 = email1;
    this.group = group;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getContactName() {
    return contactName;
  }

  public String getLastContactName() {
    return lastContactName;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getEmail1() {
    return email1;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", contactName='" + contactName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(contactName, that.contactName) &&
            Objects.equals(lastContactName, that.lastContactName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contactName, lastContactName);
  }
}
