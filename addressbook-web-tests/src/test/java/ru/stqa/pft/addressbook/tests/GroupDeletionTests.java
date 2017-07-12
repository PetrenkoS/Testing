package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testsGroupDeletion() {

    app.gotoGroupPage();
    app.selectContactOrGroup();
    app.initGroupDeletion();
    app.gotoGroupPage();
  }


}
