package ru.andrew.pft.addressbook.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.andrew.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerators {

  @Parameter(names = "-c", description = "Count of contacts to generate")
  private int count;

  @Parameter(names = "-f", description = "File to save generated data")
  private String file;

  @Parameter(names = {"-d", "--format"}, description = "Format")
  private String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerators generator = new ContactDataGenerators();
    try {
      JCommander.newBuilder().addObject(generator).build().parse(args);
    } catch (ParameterException ex) {
      ex.usage();
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    System.out.println(new File(".").getAbsolutePath());
    if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format: " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    String xml = xStream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }


  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withName(String.format("GeneratedName_%s", i))
              .withLastName(String.format("GeneratedLastName_%s", i))
              .withEmail1(String.format("email+%s@yandex.ru", i))
              .withMobilePhone(String.format("+7937938015%s", i))
              .withAddress(String.format("Russia, Yoshkar-Ola, Annikova st, 25/%s", i))
              .withEmail2("")
              .withEmail3("")
              .withHomePhone("")
              .withWorkPhone(""));
    }
    return contacts;
  }
}
