package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver driver) {
    super(driver);
  }

  public void gotoHomePage() {
    if (isElementPresent(By.id("maintable"))){
      return;
    }
    click(By.xpath("//a[contains(text(), 'home')]"));
  }

  public void gotoGroupPage() {
    if (isElementPresent(By.name("new"))
            && isElementPresent(By.tagName("h1"))
            && driver.findElement(By.xpath("//body[1]/div[1]/div[4]/h1[1]")).getText().equals("Groups")) {
      return;
    }
    click(By.xpath("//a[contains(text(),'groups')]"));
  }
}
