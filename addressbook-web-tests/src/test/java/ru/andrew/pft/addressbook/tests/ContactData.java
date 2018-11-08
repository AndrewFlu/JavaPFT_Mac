package ru.andrew.pft.addressbook.tests;

public class ContactData {
  private final String contactName;
  private final String lastContactName;
  private final String mobilePhone;
  private final String email1;

  public ContactData(String contactName, String lastContactName, String mobilePhone, String email1) {
    this.contactName = contactName;
    this.lastContactName = lastContactName;
    this.mobilePhone = mobilePhone;
    this.email1 = email1;
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
}
