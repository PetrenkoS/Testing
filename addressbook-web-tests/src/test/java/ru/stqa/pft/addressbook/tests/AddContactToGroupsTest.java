package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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
    if (app.db().groups().size() == 0) {  //Проверяет, есть ли хоть одна группа, и создает в случае отсутствия
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
    }
    if (app.db().contacts().size() == 0) { //Проверяет, есть ли хоть один контакт, и создает в случае отсутствия
      app.goTo().gotoAddContactPage();
      app.contact().create(new ContactData().withFirstname("1").withLastname("2").withAddress("3").withHometelephone("34"), true);
    } else {
      ContactData contact = app.db().contacts().iterator().next();
     // if (contact.getGroups().size() == 0) {
     //   contact.inGroup(groups.iterator().next());
      }
    }

//Проверяет, что контакт не входит в группу

  //для обеспечения предусловий  теста мы должны получить список всех групп и сравнить его с списком групп
  // к которым присоединен контакт и при наличии "свободной" сразу выполнять тест или создавать группу
  // но если этот контакт входит во все группы -- необязательно создавать новый контакт или новую группу.
  // можно попробовать другой контакт там на самом деле нужно начинать с проверки контакта, ну и далее по списку,

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
  public void testContactToGroup(ContactData contact) { //Добавляет существующий контакт в группу

    //ContactData newContact = new ContactData().withFirstname("test_name").withLastname("test_surname").
         //   .inGroup(groups.iterator().next());
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.contact().addSelectedContactsToGroup(contact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
   // assertThat(after.size(), equalTo(before.size() + 1));
//    assertThat(after, equalTo(before.withAdded(
    // contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    verifyContactListInUI();
  }


  }


