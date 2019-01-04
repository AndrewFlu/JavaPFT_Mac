package ru.andrew.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Contacts extends ForwardingSet <ContactData> {

  private Set <ContactData> forwardingContactSet;

  public Contacts(Contacts contacts) {
    this.forwardingContactSet = new HashSet<>(contacts.delegate());
  }

  public Contacts() {
    this.forwardingContactSet = new HashSet<>();
  }

  public Contacts(List<ContactData> contacts) {
    this.forwardingContactSet = new HashSet<>(contacts);
  }

  public Contacts(Set<ContactData> contacts) {
    this.forwardingContactSet = new HashSet<>(contacts);
  }


  @Override
  protected Set delegate() {
    return forwardingContactSet;
  }


  public Contacts withAdded(ContactData contact) {
    Contacts contacts = new Contacts(this);
    contacts.add(contact);
    return contacts;
  }

  public Contacts without(ContactData contact) {
    Contacts contacts = new Contacts(this);
    contacts.remove(contact);
    return contacts;
  }
}
