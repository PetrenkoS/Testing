package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 28.07.2017.
 */
public class PasswordChangeTest extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }


  @Test
  public void testPasswordChange() throws IOException, MessagingException {
    app.passwordChangeHelper().login("administrator", "root");
    app.passwordChangeHelper().gotoManageUsers();
    app.passwordChangeHelper().selectUser();
    String user = app.db().getUsername();
    app.passwordChangeHelper().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String email = user+"@localhost.localdomain";
    String confirmationLink = findConfirmationLink(mailMessages, email);
    String newPassword = "0000";
    app.passwordChangeHelper().changePassword(confirmationLink, newPassword);
    HttpSession session = app.newSession();
    Assert.assertTrue(app.newSession().login(user, newPassword));

  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
