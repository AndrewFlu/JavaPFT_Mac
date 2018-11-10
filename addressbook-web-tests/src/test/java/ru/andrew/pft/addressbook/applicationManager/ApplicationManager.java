package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private WebDriver driver;
  private GroupHelper groupHelper;

  public void init() {
    String projectPath = System.getProperty("user.dir");
    System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/Firexox/geckodriver");

    driver = new FirefoxDriver();
    groupHelper = new GroupHelper(driver);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    driver.get("http://localhost/addressbook/index.php");
    login("admin", "secret");
  }

  public void stop() {
    driver.quit();
  }

  public void login(String username, String password) {
    driver.findElement(By.name("user")).click();
    driver.findElement(By.name("user")).clear();
    driver.findElement(By.name("user")).sendKeys(username);
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys(password);
    driver.findElement(By.id("LoginForm")).submit();
  }

  public void gotoHomePage() {
    driver.findElement(By.linkText("home")).click();
  }

  public void returnToHomePage() {
    driver.findElement(By.linkText("home page")).click();
  }

  public void gotoGroupPage() {
    driver.findElement(By.linkText("groups")).click();
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

  public boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }
}
