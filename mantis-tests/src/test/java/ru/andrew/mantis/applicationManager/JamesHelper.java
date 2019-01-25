package ru.andrew.mantis.applicationManager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.andrew.mantis.model.MailMessage;

import javax.mail.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.List;

public class JamesHelper {

  private ApplicationManager app;
  private TelnetClient telnet;
  private Session mailSession;
  private String mailserver;
  private InputStream inputStream;
  private PrintStream outputStream;

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

  public List<MailMessage> waitForMail(String user, String password, int timeout) {
  }
}
