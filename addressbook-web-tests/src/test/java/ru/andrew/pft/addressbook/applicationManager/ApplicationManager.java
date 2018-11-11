package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private WebDriver driver;
  private ContactHelper contactHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;

  private String projectPath = System.getProperty("user.dir");
  public String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
  }

  public void init() {

    if (browser == "firefox"){
      System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/firefox/geckodriver");
      driver = new FirefoxDriver();
    } else if (browser == "chrome"){
      System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chrome/chromedriver");
      driver = new ChromeDriver();
    } else if (browser == "safari"){
      driver = new SafariDriver();
    } else if (browser == "iexplore"){
      driver = new InternetExplorerDriver();
    }

    groupHelper = new GroupHelper(driver);
    contactHelper = new ContactHelper(driver);
    sessionHelper = new SessionHelper(driver);
    navigationHelper = new NavigationHelper(driver);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get("http://localhost/addressbook/index.php");
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    driver.quit();
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }
}
