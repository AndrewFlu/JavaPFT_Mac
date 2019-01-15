package ru.andrew.mantis.applicationManager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {

  private final ApplicationManager app;
  private final WebDriver driver;

  public RegistrationHelper(ApplicationManager app) {
    this.app = app;
    this.driver = app.getDriver();
  }

  public void start(String user, String email) {
    driver.get(app.getProperty("web.baseUrl") + "/signup_page.php");
  }
}
