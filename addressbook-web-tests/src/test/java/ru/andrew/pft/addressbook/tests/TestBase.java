package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.andrew.pft.addressbook.applicationManager.ApplicationManager;

public class TestBase {

  protected final ApplicationManager app = new ApplicationManager();

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    app.init();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }
}
