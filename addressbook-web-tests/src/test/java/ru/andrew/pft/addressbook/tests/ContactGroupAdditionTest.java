package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupAdditionTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("GroupNameForContactAdditionName"));
    }
   }

  @Test
  public void testContactAddToGroup() {
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();
    Groups groupList = app.db().groups();
    GroupData group = groupList.iterator().next();

    while (contact.getGroups().size() > 1){
      contacts.remove(contact);
      contact = contacts.iterator().next();
    }
    if (contact.getGroups().size() > 0){
      while (contact.getGroups().iterator().next().getId() == group.getId()) {
        groupList.remove(group);
        group = groupList.iterator().next();
      }
    }

    Groups contactGroupsBefore = contact.getGroups();
    app.goTo().homePage();
    app.contact().addToGroup(contact, group);
    Groups contactGroupsAfter = app.db().getContactGroups(contact);
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withAdded(group)));
  }
}
