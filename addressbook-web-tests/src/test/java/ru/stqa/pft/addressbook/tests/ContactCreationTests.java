package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testsContactCreation() {

    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoAddContactPage();
    ContactData contact = new ContactData("Vladimir", "Putin", "Kremlin", "111", "test1");
    app.getContactHelper().fillContactForms(contact, true);
    app.getContactHelper().submitAddContact();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);


int max = 0;
for (ContactData g : after) {
  if (g.getId() > max) {
    max = g.getId();
  }
}
contact.setId(max);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }


}
