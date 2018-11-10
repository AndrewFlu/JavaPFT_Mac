package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.andrew.pft.addressbook.model.ContactData;

public class ContactHelper {
  private WebDriver driver;

  public ContactHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void returnToHomePage() {
    driver.findElement(By.linkText("home page")).click();
  }

  public void submitContactCreation() {
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Notes:'])[1]/following::input[1]")).click();
  }

  public void fillContactForm(ContactData contactData) {
    driver.findElement(By.name("firstname")).click();
    driver.findElement(By.name("firstname")).clear();
    driver.findElement(By.name("firstname")).sendKeys(contactData.getContactName());
    driver.findElement(By.name("lastname")).click();
    driver.findElement(By.name("lastname")).clear();
    driver.findElement(By.name("lastname")).sendKeys(contactData.getLastContactName());
    driver.findElement(By.name("mobile")).click();
    driver.findElement(By.name("mobile")).clear();
    driver.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys(contactData.getEmail1());
  }

  public void initContactCreation() {
    driver.findElement(By.linkText("add new")).click();
  }
}
