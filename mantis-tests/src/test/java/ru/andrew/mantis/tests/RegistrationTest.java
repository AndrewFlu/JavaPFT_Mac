package ru.andrew.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTest extends TestBase {

  @Test
  public void testRegistration(){
    app.registration().start("user", "useremail@localhost.localdomain");
  }
}
