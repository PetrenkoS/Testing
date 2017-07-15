package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testsContactCreation() {
    int before = app.getContactHelper().getContactCount();
    app.getNavigationHelper().gotoAddContactPage();
    app.getContactHelper().fillContactForms(new ContactData("1", "2", "3", "34", "test1"), true);
    app.getContactHelper().submitAddContact();
    app.getNavigationHelper().gotoHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);
  }


}
