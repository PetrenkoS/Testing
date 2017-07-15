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
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
 app.getContactHelper().selectContactOrGroup();
 app.getGroupHelper().initGroupModification();
 app.getGroupHelper().fillGroupForm(new GroupData("test1", "test4", "test5"));
 app.getGroupHelper().submitGroupModification();
 app.getGroupHelper().returntoGroupPage();

  }

}
