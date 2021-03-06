package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private final Properties properties;
  private WebDriver driver;
  private ContactHelper contactHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private DbHelper dbHelper;

  private String projectPath = System.getProperty("user.dir");
  public String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    dbHelper = new DbHelper();

    if ("".equals(properties.getProperty("selenium.server"))){
      if (browser.equals("firefox")) {
        System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/firefox/geckodriver");
        driver = new FirefoxDriver();
      } else if (browser.equals("chrome")) {
        System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chrome/chromedriver");
        driver = new ChromeDriver();
      } else if (browser.equals("safari")) {
        driver = new SafariDriver();
      } else if (browser.equals("iexplore")) {
        System.setProperty("webdriver.ie.driver", projectPath + "/drivers/ie/IEDriverServer.exe");
        driver = new InternetExplorerDriver();
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }

    groupHelper = new GroupHelper(driver);
    contactHelper = new ContactHelper(driver);
    sessionHelper = new SessionHelper(driver);
    navigationHelper = new NavigationHelper(driver);
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    driver.get(properties.getProperty("web.baseUrl"));
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public void stop() {
    driver.quit();
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public DbHelper db(){
    return dbHelper;
  }

  public byte[] takeScreenshot(){
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }
}
