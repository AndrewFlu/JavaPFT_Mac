package ru.andrew.mantis.tests;

import org.testng.annotations.Test;
import ru.andrew.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTest extends TestBase {

  //  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user_email%s@localhost.localdomain", now);
    app.james().createUser(user, password);

    app.registration().start(user, email);
//    List<MailMessage> messages = app.mail().wailForMail(2, 20000); // получение почты на встроенный сервер Wiser
    List<MailMessage> messages = app.james().waitForMail(user, password, 50000);
    String confirmationLinc = findConfirmationLinc(messages, email);
    app.registration().finish(confirmationLinc, password);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLinc(List<MailMessage> messages, String email) {
    MailMessage message = messages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }

  //  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
