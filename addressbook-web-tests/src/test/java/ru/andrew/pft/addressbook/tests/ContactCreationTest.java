package ru.andrew.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

  @DataProvider(name = "validContactDataFromXml")
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = "";
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
    }
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
  }

  @DataProvider(name = "validContactDataFromJson")
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    Gson gson = new Gson();
    String json = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      List<ContactData> contacts = (List<ContactData>) gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactDataFromJson")
  public void testContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().getContactCount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact
            .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test
  public void testContactCreationWithPhoto() {
    ContactData contact = new ContactData()
            .withName("ContactName1").withLastName("LastContactName").withMobilePhone("+79012050505")
            .withEmail1("email@gmal.com").withPhoto(new File("src/test/resources/IMG_0268.JPG"));
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().getContactCount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    Assert.assertTrue(app.contact().hasPhoto(contact));
  }
}
