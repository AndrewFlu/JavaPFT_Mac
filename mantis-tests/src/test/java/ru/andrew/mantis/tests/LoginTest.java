package ru.andrew.mantis.tests;

import org.testng.annotations.Test;
import ru.andrew.mantis.applicationManager.HttpHelper;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTest extends TestBase {

  @Test
  public void testLogin() throws IOException {
    HttpHelper session = app.newSession();
    assertTrue(session.login("administrator", "root"));
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
