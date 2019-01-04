package ru.andrew.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.andrew.pft.addressbook.applicationManager.ApplicationManager;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
  Logger logger = LoggerFactory.getLogger(TestBase.class);

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m, Object[] p) {
    logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> (new GroupData().withId(g.getId()).withName(g.getGroupName())))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUi")) {
      Contacts contactsDb = app.db().contacts();
      Contacts contactsUi = app.contact().all();
      assertThat(contactsUi, equalTo(contactsDb.stream().map((c) -> (new ContactData()
              .withId(c.getId()).withName(c.getContactName())
              .withLastName(c.getLastContactName()).withAddress(c.getAddress()))).collect(Collectors.toSet())));
    }
  }
}

