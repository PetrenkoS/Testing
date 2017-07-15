package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    
    @Test
    public void testsContactDeletion() {
      app.getNavigationHelper().gotoHomePage();
      if (! app.getContactHelper().isThereAContact()) {
        app.getContactHelper().createContact(new ContactData("1", "2", "3", "34", "test1"), true);
      }
      app.getNavigationHelper().gotoHomePage();
      int before = app.getContactHelper().getContactCount();
      app.getContactHelper().selectContact(before - 1);
      app.getContactHelper().deleteSelectedContacts();
      app.getNavigationHelper().gotoHomePage();
      int after = app.getContactHelper().getContactCount();
      Assert.assertEquals(after, before - 1);
    }


}
