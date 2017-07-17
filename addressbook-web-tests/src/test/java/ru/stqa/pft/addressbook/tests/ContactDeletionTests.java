package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    
    @Test(enabled = false)
    public void testsContactDeletion() {
      app.goTo().gotoHomePage();
      if (! app.getContactHelper().isThereAContact()) {
        app.getContactHelper().createContact(new ContactData("1", "2", "3", "34", "test1"), true);
      }
      app.goTo().gotoHomePage();
      List<ContactData> before = app.getContactHelper().getContactList();
      app.getContactHelper().selectContact(before.size() - 1);
      app.getContactHelper().deleteSelectedContacts();
      app.goTo().gotoHomePage();
      List<ContactData> after = app.getContactHelper().getContactList();
      Assert.assertEquals(after.size(), before.size() - 1);

before.remove(before.size() - 1);
  Assert.assertEquals(before, after);
}
    }



