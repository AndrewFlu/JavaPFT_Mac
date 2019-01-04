package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withName("Name").withAddress("Main Address \n Countries"));
    }
  }

  @Test
  public void testContactAddress() {
    ContactData contact = app.db().contacts().iterator().next();
    app.goTo().homePage();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(cleared(contact.getAddress()), equalTo(cleared(contactDataFromEditForm.getAddress())));
  }

  private String cleared(String address) {
    String[] split = address.split("\\n");
    return Arrays.asList(split).stream().map(String::trim).collect(Collectors.joining("\n"));
  }
}
