package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    
    @Test
    public void testsContactDeletion() {
      app.getNavigationHelper().gotoHomePage();
      app.getContactHelper().selectContacts();
      app.getContactHelper().deleteSelectedContacts();
      app.getNavigationHelper().gotoHomePage();
    }


}
