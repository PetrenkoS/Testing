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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Admin on 25.07.2017.
 */
public class DeleteContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() { //Проверяет, есть ли хоть одна группа
    if(app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1")
              .withHeader("test2")
              .withFooter("test3"));
    }
    Groups groups = app.db().groups();

    app.goTo().homePage();
    if(app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("A")
              .withLastname("B")
              .inGroup(groups.iterator().next()), true);
    } else {
      ContactData contact= app.db().contacts().iterator().next();
      if(contact.getGroups().size() == 0) {
        contact.inGroup(groups.iterator().next());
      }
    }
  }

  @Test
  public void testDeleteContactFromGroup() { //Удаляет контакт из группы
    ContactData contact = app.db().contacts().iterator().next();
    Groups before = contact.getGroups();
    GroupData deleteGroup = before.iterator().next();
    app.goTo().homePage();
    app.contact().deleteFromGroup(contact, deleteGroup);
    app.goTo().homePage();
    app.contact().selectGroupById("");
    app.goTo().homePage();
    Groups after = app.db().contactById(contact.getId()).getGroups();
    assertThat(after, equalTo(before.without(deleteGroup)));
  }
}