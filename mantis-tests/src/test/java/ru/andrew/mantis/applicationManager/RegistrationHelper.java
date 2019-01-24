package ru.andrew.mantis.applicationManager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String user, String email) {
    driver.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.id("username"), user);
    type(By.id("email-field"), email);
    click(By.cssSelector("input[value='Зарегистрироваться'"));
  }

  public void finish(String confirmationLinc, String password) {
    driver.get(confirmationLinc);

  }
}
