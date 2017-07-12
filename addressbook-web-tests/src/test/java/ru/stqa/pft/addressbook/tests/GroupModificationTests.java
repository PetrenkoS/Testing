package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Created by Admin on 12.07.2017.
 */
public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
 app.getNavigationHelper().gotoGroupPage();
 app.getContactHelper().selectContactOrGroup();
 app.getGroupHelper().initGroupModification();
 app.getGroupHelper().fillGroupForm(new GroupData("test3", "test4", "test5"));
 app.getGroupHelper().submitGroupModification();
 app.getGroupHelper().returntoGroupPage();

  }

}
