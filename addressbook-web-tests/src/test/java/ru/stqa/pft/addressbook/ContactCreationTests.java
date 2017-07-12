package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testsContactCreation() {

        gotoAddContactPage();
        fillContactForms(new ContactData("1", "2", "3", "34"));
        submitAddContact();
        gotoHomePage();
    }


}
