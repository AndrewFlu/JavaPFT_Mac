package ru.andrew.pft.addressbook.applicationManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.util.List;

public class DbHelper {
  private SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Contacts contacts (){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> contacts = session.createQuery("from ContactData where deprecated = 0000-00-00").list();
    session.getTransaction().commit();
    session.close();
    return new Contacts(contacts);
  }

  public Groups groups (){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> groups = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();
    return new Groups(groups);
  }

  public Groups getContactGroups(ContactData contact) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List <ContactData> contacts = session.createQuery("from ContactData where id = " + contact.getId()).list();
    session.getTransaction().commit();
    session.close();
    return contacts.iterator().next().getGroups();
  }
}