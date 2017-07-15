package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Admin on 12.07.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("1", "2", "3", "34", "test1"), true);
    }
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContactOrGroup();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForms(new ContactData("gkv", "gbhvzl", "bxlvz", "gsi", null), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().gotoHomePage();


  }

}
