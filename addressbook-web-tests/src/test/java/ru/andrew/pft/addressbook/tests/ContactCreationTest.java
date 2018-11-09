package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.gotoHomePage();
    app.initContactCreation();
    app.fillContactForm(new ContactData("ContactName", "LastContactName", "+79012050505", "email@gmal.com"));
    app.submitContactCreation();
    app.returnToHomePage();
  }
}
