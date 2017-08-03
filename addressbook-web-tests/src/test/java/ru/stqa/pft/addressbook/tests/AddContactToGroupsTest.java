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

  }


  @Test
  public void testAddSelectedContactToGroup() { //Добавляет выбранный контакт в группу
    int contactId = 0;
    boolean finished = false;
    Groups beforeAddGroups = null;
    Groups beforeWithAddedGroups = null;
    Groups exitedGroups = app.db().groups();
    Contacts contacts = app.db().contacts();

    for (ContactData selectContact : contacts) {
      beforeAddGroups = selectContact.getGroups();
      GroupData newGroup = app.contact().getGroupToAdd(exitedGroups, selectContact);
      if (newGroup != null) {
        app.contact().addContact(selectContact, newGroup);
        contactId = selectContact.getId();
        beforeWithAddedGroups = beforeAddGroups.withAdded(newGroup);
        finished = true;
        break;
      }
    }

    if (!finished) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test_name").withHeader("test_header").withFooter("test_footer"));
      Groups newGroups = app.db().groups();
      GroupData recentGroup = newGroups.stream().max((g1, g2) -> Integer.compare(g1.getId(), g2.getId())).get();
      ContactData contact = contacts.iterator().next();
      contactId = contact.getId();
      app.goTo().homePage();
      app.contact().addContact(contact, recentGroup);
      app.goTo().homePage();
      beforeWithAddedGroups = beforeAddGroups.withAdded(recentGroup);
    }
    Groups groupAfter = app.db().contactById(contactId).getGroups();
    assertThat(groupAfter, equalTo(beforeWithAddedGroups));
  }


}



