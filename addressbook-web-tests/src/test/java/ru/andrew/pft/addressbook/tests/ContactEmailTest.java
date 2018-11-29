package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withName("TestName").withLastName("TestLastName")
              .withEmail1("testmail+1@mail.ru").withEmail2("testmail+2@mail.ru").withEmail3("testmail+3@mail.ru"));
    }
  }

  @Test
  public void testContactEmail() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmails(), equalTo(mergedEmails(contactDataFromEditForm)));
  }

  private String mergedEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3()).stream()
            .filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }

}
