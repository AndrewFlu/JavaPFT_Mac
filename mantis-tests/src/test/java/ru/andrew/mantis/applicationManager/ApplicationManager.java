package ru.andrew.mantis.applicationManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private final Properties properties;
  private WebDriver driver;
  private String projectPath = System.getProperty("user.dir");
  public String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (driver != null) {
      driver.quit();
    }
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public HttpHelper newSession() {
    return new HttpHelper(this);
  }

  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public JamesHelper james(){
    if (jamesHelper == null){
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public WebDriver getDriver() {
    if (driver == null) {
      if (browser.equals("firefox")) {
        System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/firefox/geckodriver");
        driver = new FirefoxDriver();
      } else if (browser.equals("chrome")) {
        System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chrome/chromedriver");
        driver = new ChromeDriver();
      } else if (browser.equals("safari")) {
        driver = new SafariDriver();
      } else if (browser.equals("iexplorer")) {
        System.setProperty("webdriver.ie.driver", projectPath + "/drivers/ie/IEDriverServer.exe");
        driver = new InternetExplorerDriver();
      }
      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      driver.get(properties.getProperty("web.baseUrl"));
    }
    return driver;
  }
}
