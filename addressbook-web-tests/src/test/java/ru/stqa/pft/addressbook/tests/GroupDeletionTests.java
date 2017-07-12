package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testsGroupDeletion() {

    app.getNavigationHelper().gotoGroupPage();
    app.getContactHelper().selectContactOrGroup();
    app.getGroupHelper().initGroupDeletion();
    app.getNavigationHelper().gotoGroupPage();
  }


}
