package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver driver) {
    super(driver);
  }

  public void gotoHomePage() {
    click(By.linkText("home"));
  }

  public void gotoGroupPage() {
    click(By.linkText("groups"));
  }
}
