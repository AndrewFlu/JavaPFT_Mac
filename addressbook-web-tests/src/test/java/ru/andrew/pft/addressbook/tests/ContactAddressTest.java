package ru.andrew.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0){
      app.contact().create(new ContactData().withName("Name").withAddress("Main Address \n Countries"));
    }
  }

  @Test
  public void testContactAddress(){
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(cleared(contact.getAddress()), equalTo(cleared(contactDataFromEditForm.getAddress())));
  }

  private String cleared(String address) {
    return address.replaceAll("\\s", "");
  }
}
