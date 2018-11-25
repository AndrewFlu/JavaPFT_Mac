package ru.andrew.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTest extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() {
    ContactData contact = new ContactData()
            .withName("ContactName1").withLastName("LastContactName").withMobilePhone("+79012050505")
            .withEmail1("email@gmal.com").withGroup("Group1");

    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size() + 1);
    assertThat(after, CoreMatchers.equalTo(before.withAdded(contact
            .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
