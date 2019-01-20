package ru.andrew.mantis.applicationManager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
  private final ApplicationManager app;
  private FTPClient ftpClient;

  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftpClient = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException {
    ftpClient.connect(app.getProperty("ftp.host"));
    ftpClient.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftpClient.deleteFile(backup);
    ftpClient.rename(target, backup);
    ftpClient.enterLocalPassiveMode();
    ftpClient.storeFile(target, new FileInputStream("src/test/resources/config_defaults_inc.php"));
    ftpClient.disconnect();
  }

  public void restore(String backup, String target) throws IOException {
    ftpClient.connect(app.getProperty("ftp.host"));
    ftpClient.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftpClient.deleteFile(target);
    ftpClient.rename(backup, target);
    ftpClient.disconnect();
  }
}
