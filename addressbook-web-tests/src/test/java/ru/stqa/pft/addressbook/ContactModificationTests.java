package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created by Admin on 12.07.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    gotoHomePage();
    selectContactOrGroup();
    initContactModification();
    fillContactForms(new ContactData("gkv", "gbhvzl", "bxlvz", "gsi"));
    submitContactModification();
    gotoHomePage();
  }

}
