package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withName("ContactName").withLastName("ContactSurname")
              .withMobilePhone("+7(987) 777-12-55").withEmail1("email@yandex.com"));
    }
  }

  @Test(enabled = true)
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.goTo().homePage();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertThat(app.contact().getContactCount(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}