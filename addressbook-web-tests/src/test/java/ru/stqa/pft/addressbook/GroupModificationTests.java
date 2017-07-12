package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created by Admin on 12.07.2017.
 */
public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
 gotoGroupPage();
 selectContactOrGroup();
 initGroupModification();
 fillGroupForm(new GroupData("test3", "test4", "test5"));
 submitGroupModification();
 returntoGroupPage();

  }

}
