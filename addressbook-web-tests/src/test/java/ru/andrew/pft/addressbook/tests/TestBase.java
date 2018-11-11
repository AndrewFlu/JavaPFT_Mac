package ru.andrew.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.andrew.pft.addressbook.applicationManager.ApplicationManager;

public class TestBase {

  protected final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    app.init();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }
}
