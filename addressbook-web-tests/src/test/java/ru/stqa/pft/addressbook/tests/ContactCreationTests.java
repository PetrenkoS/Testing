package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testsContactCreation() {

    app.gotoAddContactPage();
    app.fillContactForms(new ContactData("1", "2", "3", "34"));
    app.submitAddContact();
    app.gotoHomePage();
  }


}
