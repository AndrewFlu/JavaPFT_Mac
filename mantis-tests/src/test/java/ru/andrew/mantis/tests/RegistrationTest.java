package ru.andrew.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.andrew.mantis.model.MailMessage;

import java.util.List;

public class RegistrationTest extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() {
    String user = "user";
    String email = "useremail@localhost.localdomain";
    app.registration().start(user, email);
    List<MailMessage> messages = app.mail().wailForMail(2, 10000);
    findConfirmationLinc(messages, email);

  }

  private String findConfirmationLinc(List<MailMessage> messages, String email) {
    MailMessage message = messages.stream().filter((m) -> m.to.equals(email)).findFirst().get();

  }

  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
