package ru.andrew.mantis.applicationManager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.andrew.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {

  private final Wiser wiser;
  private final ApplicationManager app;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser();
  }

  public List<MailMessage> wailForMail (int count, long timeout){
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() <= now + timeout){
      if (wiser.getMessages().size() >= count){
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");

  }

  private MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage message = m.getMimeMessage();
      return new MailMessage(message.getAllRecipients()[0].toString(), (String) message.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void start(){
    wiser.start();
  }

  public void stop(){
    wiser.stop();
  }
}
