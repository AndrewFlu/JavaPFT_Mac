package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withName("ContactName").withLastName("ContactSurname")
              .withMobilePhone("+7(987) 777-12-55").withEmail1("email@yandex.com"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withName("ModificationName").withLastName("ModificationLastName")
            .withMobilePhone("+7 (917)719-11-01").withEmail1("modifactionemail@yandex.ru").withWorkPhone("")
            .withHomePhone("").withEmail2("").withEmail3("").withAddress("");

    app.goTo().homePage();
    app.contact().modify(contact);
    assertThat(app.contact().getContactCount(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}
