package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    gotoHomePage();
    initContactCreation();
    fillContactForm(new ContactData("ContactName", "LastContactName", "+79012050505", "email@gmal.com"));
    submitContactCreation();
    returnToHomePage();
  }
}
