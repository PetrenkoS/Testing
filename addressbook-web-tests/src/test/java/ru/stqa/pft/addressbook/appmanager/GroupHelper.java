package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

/**
 * Created by Admin on 12.07.2017.
 */
public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returntoGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void initGroupDeletion() {
    click(By.xpath("//div[@id='content']/form/input[5]"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    groupCache = null;
    returntoGroupPage();
  }

    public void delete(GroupData group) {
    selectGroupById(group.getId());
    initGroupDeletion();
    groupCache = null;
    returntoGroupPage();
  }


  public void selectGroupById(int id) {

    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    groupCache = null;
    returntoGroupPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();

  }

  private Groups groupCache = null;

  public Groups allGroups() {
    Groups contacts = new Groups();
    List <WebElement> elements = wd.findElements(By.name("add"));
    for (WebElement element : elements)
    {
      List<WebElement> cells = element.findElements(By.name("to_group"));
      int id = Integer.parseInt(element.findElement(By.tagName("option value")).getAttribute("value"));
      contacts.add(new GroupData().withId(id));
    }
    return contacts;
  }


  public Groups all() {
    if (groupCache != null) {
      return new Groups(groupCache);
    }

    groupCache = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for(WebElement element : elements) {
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groupCache.add(new GroupData().withId(id).withName(name));
    }
    return new Groups(groupCache);
  }


}
