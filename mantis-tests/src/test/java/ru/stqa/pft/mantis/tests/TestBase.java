package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;


/**
 * Created by Admin on 12.07.2017.
 */
public class TestBase {


  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)

  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    String issueStatus = app.soap().getIssueStatusById(issueId);
    if (issueStatus.equals("Closed") || issueStatus.equals("Resolved")) {
      return false;
    } else {
      return true;
    }

  }

  public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      System.out.println("It is useless, the bug is still not fixed.");
      throw new SkipException("Ignored because of issue " + issueId);
    }


  }


}
