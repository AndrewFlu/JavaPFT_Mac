package ru.andrew.mantis.applicationManager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.andrew.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

  private ApplicationManager app;
  private TelnetClient telnet;
  private Session mailSession;
  private String mailserver;
  private InputStream inputStream;
  private PrintStream outputStream;
  private Store store;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient();
    mailSession = Session.getDefaultInstance(System.getProperties());
  }

  public void createUser(String user, String password) {
    initTelnetSession();
    write("adduser " + user + " " + password);
    readUntil("User " + user + " deleted");
    closeTelnetSession();
  }

  public void clearAllMessagesInMailBox(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for (Message message : inbox. getMessages()){
      message.setFlag(Flags.Flag.DELETED, true);
    }
    closeFolder(inbox);
  }

  private void closeTelnetSession() {
    write("quit");
  }

  private void initTelnetSession() {
    mailserver = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailserver, port);
      inputStream = telnet.getInputStream();
      outputStream = new PrintStream(telnet.getOutputStream());
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // непонятно почему не выполняется логин с первой попытки
    readUntil("Login id:");
    write("");
    readUntil("Password:");
    write("");

    // вторая попытка будет успешной
    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    // считываем приветственное сообщение
    readUntil("Welcome " + login + ". HELP for a list of commands");
  }

  private void write(String value) {
    try {
      outputStream.println(value);
      outputStream.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) inputStream.read();
      while (true) {
        System.out.print(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) inputStream.read();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<MailMessage> waitForMail(String user, String password, int timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout){
      List<MailMessage> allMail = getAllMail(user, password);
      if (allMail.size()>0){
        return allMail;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  private List<MailMessage> getAllMail(String user, String password) throws MessagingException {
    Folder inbox = openInbox(user, password);
    List <MailMessage> mails = Arrays.asList(inbox.getMessages()).stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
    closeFolder(inbox);
    return  mails;
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }

  private Folder openInbox(String user, String password) throws MessagingException {
    store = mailSession.getStore("POP3");
    store.connect(mailserver, user, password);
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  private static MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
