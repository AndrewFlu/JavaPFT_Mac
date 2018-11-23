package ru.andrew.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTest extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() {
    ContactData contact = new ContactData()
            .withName("ContactName1").withLastName("LastContactName").withMobilePhone("+79012050505")
            .withEmail1("email@gmal.com").withGroup("Group1");

    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(after, before);
  }
}
