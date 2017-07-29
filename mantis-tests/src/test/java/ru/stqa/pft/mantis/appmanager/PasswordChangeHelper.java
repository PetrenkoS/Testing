package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 28.07.2017.
 */
public class PasswordChangeHelper extends HelperBase{


  public PasswordChangeHelper(ApplicationManager app) {
    super(app);
    wd = app.getDriver();
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Login']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }


  public void gotoManageUsers() {
    click(By.xpath("//a[text() = 'Manage']"));
    click(By.xpath("//a[contains(.,'Manage Users')]"));
  }

  public void selectUser() {
    click(By.linkText("user1501244209595"));
    // click(By.name("user1501246092707"));
    //click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
    //click(By.xpath(String.format("//a[contains(., 'user_id=%s']", id)));
      }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

  public void changePassword(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }



}
