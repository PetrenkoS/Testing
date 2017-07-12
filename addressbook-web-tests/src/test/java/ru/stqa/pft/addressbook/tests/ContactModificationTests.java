package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Admin on 12.07.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.gotoHomePage();
    app.selectContactOrGroup();
    app.initContactModification();
    app.fillContactForms(new ContactData("gkv", "gbhvzl", "bxlvz", "gsi"));
    app.submitContactModification();
    app.gotoHomePage();
  }

}
