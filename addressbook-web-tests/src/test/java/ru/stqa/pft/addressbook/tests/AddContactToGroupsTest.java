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
public class AddContactToGroupsTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    Groups groups = app.db().groups();

    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("firstname1").withLastname("lastname1"), true);
    } else {
      ContactData contact = app.db().contacts().iterator().next();
      if (contact.getGroups().size() == 0) {
        contact.inGroup(groups.iterator().next());
      }
    }
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test45")
              .withHeader("header45")
              .withFooter("footer45"));
    }
    if (app.db().contactsWithoutGroup().size() == 0) {
      app.contact().create(new ContactData().withFirstname("firstname2").withLastname("lastname2"), true);
    }

  }


 

  @Test
  public void testAddContactToGroup() {
    Contacts before = app.db().contacts();
    app.goTo().homePage();
    Groups group = app.db().groups();
    ContactData modifiedContact = app.db().contactsWithoutGroup().iterator().next();
    GroupData addedGroup = group.iterator().next();
    app.contact().selectAddContact(modifiedContact.getId());
    app.contact().addContactToGroup(addedGroup.getId());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(modifiedContact.inGroup(addedGroup))));
  }
}



