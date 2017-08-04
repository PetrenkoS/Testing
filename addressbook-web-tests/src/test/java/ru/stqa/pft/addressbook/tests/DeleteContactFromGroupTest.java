package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Admin on 25.07.2017.
 */
public class DeleteContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() { //Проверяет, есть ли хоть одна группа
    app.goTo().groupPage();
    if(app.db().groups().size() == 0) {

      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
    Groups groups = app.db().groups();

    app.goTo().homePage();
    if(app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("firstname3").withLastname("lastname3").inGroup(groups.iterator().next()), true);
       }
    if (app.db().contactsAreInGroup().size() == 0) {
      app.goTo().homePage();
      Groups group = app.db().groups();
      ContactData modifiedContact = app.db().contactsWithoutGroup().iterator().next();
      GroupData addedGroup = group.iterator().next();
      app.contact().selectAddContact(modifiedContact.getId());
      app.contact().addContactToGroup(addedGroup.getId());
    }
  }

  @Test
  public void testDeleteContactFromGroup() { //Удаляет контакт из группы
    app.goTo().homePage();
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    app.goTo().homePage();
    for (GroupData group : groups) {
      app.contact().selectDeletedGroupFromList(group);
    }
    Contacts contacts = app.db().contacts();
    ContactData requiredContact = app.db().contactById(contacts.iterator().next().getId());
    app.goTo().homePage();
    app.contact().deleteContactFromGroup(requiredContact);
    Contacts after = app.db().contacts();
    ContactData contactFromDb = app.db().contactById(requiredContact.getId());
    assertThat(after, equalTo(before.without(requiredContact).withAdded(contactFromDb)));
  }
}