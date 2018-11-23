package ru.andrew.pft.addressbook.model;

import java.util.Objects;

public class ContactData {

  private int id = Integer.MAX_VALUE;
  private String contactName;
  private String lastContactName;
  private String mobilePhone;
  private String email1;
  private String group;

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withName(String contactName) {
    this.contactName = contactName;
    return this;
  }

  public ContactData withLastName(String lastContactName) {
    this.lastContactName = lastContactName;
    return this;
  }

  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public ContactData withEmail1(String email1) {
    this.email1 = email1;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
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
    return id == that.id &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(lastContactName, that.lastContactName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, contactName, lastContactName);
  }
}
