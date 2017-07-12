package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 12.07.2017.
 */
public class TestBase {


  FirefoxDriver wd;



  @AfterMethod
  public void tearDown() {
      wd.quit();
  }

  public static boolean isAlertPresent(FirefoxDriver wd) {
      try {
          wd.switchTo().alert();
          return true;
      } catch (NoAlertPresentException e) {
          return false;
      }
  }

    @BeforeMethod
  public void setUp() throws Exception {
      wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
      wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      wd.get("http://localhost/addressbook/");
      login("admin", "secret");
  }

  private void login(String username, String password) {
      wd.findElement(By.name("user")).click();
      wd.findElement(By.name("user")).clear();
      wd.findElement(By.name("user")).sendKeys(username);
      wd.findElement(By.id("LoginForm")).click();
      wd.findElement(By.name("pass")).click();
      wd.findElement(By.name("pass")).clear();
      wd.findElement(By.name("pass")).sendKeys(password);
      wd.findElement(By.xpath("//form[@id='LoginForm']/input[3]")).click();
  }


  protected void returntoGroupPage() {
      wd.findElement(By.linkText("group page")).click();
  }


  protected void submitGroupCreation() {
      wd.findElement(By.name("submit")).click();
  }

    protected void fillGroupForm(GroupData groupData) {
      wd.findElement(By.name("group_name")).click();
      wd.findElement(By.name("group_name")).clear();
      wd.findElement(By.name("group_name")).sendKeys(groupData.getName());
      wd.findElement(By.name("group_header")).click();
      wd.findElement(By.name("group_header")).clear();
      wd.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
      wd.findElement(By.name("group_footer")).click();
      wd.findElement(By.name("group_footer")).clear();
      wd.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
  }

  protected void initGroupCreation() {
      wd.findElement(By.name("new")).click();
  }


  protected void gotoGroupPage() {
      wd.findElement(By.linkText("groups")).click();
  }


  protected void fillContactForms(ContactData contactData) {
      wd.findElement(By.name("firstname")).click();
      wd.findElement(By.name("firstname")).clear();
      wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstname());
      wd.findElement(By.name("lastname")).click();
      wd.findElement(By.name("lastname")).clear();
      wd.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
      wd.findElement(By.name("address")).click();
      wd.findElement(By.name("address")).clear();
      wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
      wd.findElement(By.name("home")).click();
      wd.findElement(By.name("home")).clear();
      wd.findElement(By.name("home")).sendKeys(contactData.getHometelephone());
      wd.findElement(By.name("address2")).click();
  }

  protected void submitAddContact() {
      wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  protected void gotoHomePage() {
      wd.findElement(By.linkText("home")).click();
  }

  protected void gotoAddContactPage() {
      wd.findElement(By.linkText("add new")).click();
  }

  protected void initGroupDeletion() {
    wd.findElement(By.xpath("//div[@id='content']/form/input[5]")).click();
  }

  protected void selectContactOrGroup() {
    wd.findElement(By.name("selected[]")).click();
  }

  protected void deleteSelectedContacts() {
    wd.findElement(By.xpath("//div[@id='content']/form[2]/div[2]/input")).click();
    wd.switchTo().alert().accept();
  }

  protected void selectContacts() {
    selectContactOrGroup();
  }
}
