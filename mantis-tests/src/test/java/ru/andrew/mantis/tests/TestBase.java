package ru.andrew.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.andrew.mantis.applicationManager.ApplicationManager;
import ru.andrew.mantis.model.IssueStatus;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
//    app.ftp().upload(new File("src/test/resources/config_defaults_inc.php"), "config_defaults_inc.php", "config_defaults_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
//    app.ftp().restore("config_defaults_inc.php.bak", "config_defaults_inc.php");
    app.stop();
  }

  public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    IssueStatus issueStatus = app.soap().getIssueStatus(issueId);
    if (issueStatus.getId() == 80 || issueStatus.getId() == 90) {
      return false;
    } else {
      return true;
    }
  }

  public void skipTestIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Test ignored because of issue with id:" + issueId + " didn't fix");
    }
  }
}

