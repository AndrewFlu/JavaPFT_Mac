package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("ContactName", "ContactSurname",
              "+7(987) 777-12-55", "email@yandex.com", null));
      app.getContactHelper().selectContact();
      app.getContactHelper().initContactDeletion();
      app.getContactHelper().submitContactDeletion();
      app.getNavigationHelper().gotoHomePage();
    }
  }
}