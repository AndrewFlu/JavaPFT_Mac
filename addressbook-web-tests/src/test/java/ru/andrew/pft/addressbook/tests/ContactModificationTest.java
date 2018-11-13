package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData(
            "ModificationName", "ModificationLastName",
            "+7(000) 111-11-11", "modifactionemail@yandex.ru", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }

}
