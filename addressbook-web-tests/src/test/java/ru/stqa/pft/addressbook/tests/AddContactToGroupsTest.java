package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Admin on 25.07.2017.
 */
public class AddContactToGroupsTest extends TestBase {
  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }


  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test45")
              .withHeader("header45")
              .withFooter("footer45"));
    }
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
  }


  @Test(dataProvider = "validContactsFromJson")
  public void testAddContactToGroups(ContactData contact) { //Добавляет новый контакт в группу
    Groups groups = app.db().groups();
    ContactData newContact = new ContactData().withFirstname("test_name").withLastname("test_surname")
            .inGroup(groups.iterator().next());
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.goTo().gotoAddContactPage();
    app.contact().create(newContact, true);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size() + 1));
//    assertThat(after, equalTo(before.withAdded(
    // contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    verifyContactListInUI();


  }

  @Test(dataProvider = "validContactsFromJson")
  public void testSelectedContactToGroup() { //Добавляет существующий контакт в группу
    int contactId = 0;
    boolean completed = false;
    Groups beforeAddGroups = null;
    Groups beforeWithAddedGroups = null;
    Groups exitedGroups = app.db().groups();
    Contacts contacts = app.db().contacts();

    for (ContactData editedContact : contacts) {
      beforeAddGroups = editedContact.getGroups();
      GroupData newGroup = app.contact().getGroupToAdd(exitedGroups, editedContact);
      if (newGroup != null) {
        app.contact().addContact(editedContact, newGroup);
        contactId = editedContact.getId();
        beforeWithAddedGroups = beforeAddGroups.withAdded(newGroup);
        completed = true;
        break;
      }
    }

    if (!completed) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test11").withHeader("test22")
              .withFooter("test33"));
      Groups extendedGroups = app.db().groups();
      GroupData lastAddedGroup = extendedGroups.stream().max((g1, g2) -> Integer.compare(g1.getId(), g2.getId())).get();
      ContactData contact = contacts.iterator().next();
      contactId = contact.getId();
      app.goTo().homePage();
      app.contact().addContact(contact, lastAddedGroup);
      app.goTo().homePage();
      beforeWithAddedGroups = beforeAddGroups.withAdded(lastAddedGroup);
    }
    Groups groupAfter = app.db().contactById(contactId).getGroups();
    assertThat(groupAfter, equalTo(beforeWithAddedGroups));
  }


}



