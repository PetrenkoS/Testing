package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    
    @Test
    public void testsContactDeletion() {
      app.gotoHomePage();
      app.selectContacts();
      app.deleteSelectedContacts();
      app.gotoHomePage();
    }


}
