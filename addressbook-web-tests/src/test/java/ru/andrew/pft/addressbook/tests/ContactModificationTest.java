package ru.andrew.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().list().size() == 0){
      app.contact().create(new ContactData("ContactName", "ContactSurname",
              "+7(987) 777-12-55", "email@yandex.com", null));
    }
  }

  @Test (enabled = true)
  public void testContactModification(){
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    int id = before.get(index).getId();
    ContactData contact = new ContactData(
            id,
            "ModificationName", "ModificationLastName",
            "+7(000) 111-11-11", "modifactionemail@yandex.ru", null);

    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }

}
