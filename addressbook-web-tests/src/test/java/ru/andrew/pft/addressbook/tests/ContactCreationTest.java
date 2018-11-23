package ru.andrew.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() {
    ContactData contactData = new ContactData()
            .withName("ContactName1").withLastName("LastContactName").withMobilePhone("+79012050505")
            .withEmail1("email@gmal.com").withGroup("Group1");

    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    app.contact().create(contactData);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contactData);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }
}
