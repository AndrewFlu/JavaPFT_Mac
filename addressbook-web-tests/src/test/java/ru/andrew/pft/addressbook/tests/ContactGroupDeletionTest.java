package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.util.Spliterators;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupDeletionTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() < 1){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("NameForGroupDeletion"));
    }
    Groups listGroups = app.db().groups();
    if (app.db().contacts().size() == 0){
      app.goTo().homePage();
      app.contact().create(new ContactData().withName("NameForGroupDeletion").withLastName("LastNameForGroupDeletion")
      .inGroup(listGroups.iterator().next()));
    }
  }

  @Test
  public void testContactGroupDeletion() {
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();

    while (contact.getGroups().size() == 0){
      contacts.remove(contact);
      contact = contacts.iterator().next();
    }
    GroupData group = contact.getGroups().iterator().next();
    Groups contactGroupsBefore = app.db().getContactGroups(contact);
    app.goTo().homePage();
    app.contact().deleteFromGroup(contact);
    Groups contactGroupsAfter = app.db().getContactGroups(contact);
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.without(group)));
  }
}
