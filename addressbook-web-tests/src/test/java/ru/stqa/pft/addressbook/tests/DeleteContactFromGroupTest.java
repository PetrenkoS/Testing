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
    if (app.db().groups().size() > 0) {

    } else {
    Assert.assertFalse(true, "There is no group");
  }
  }
 //Проверяет, есть ли в группах контакты

  @Test
  public void testDeleteContactFromGroup(ContactData contact) { //Удаляет контакт из группы
    Groups groups = app.db().groups();
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData wasteContact = before.iterator().next();
    app.goTo().homePage();
    app.contact().deleteSelectedContactsFromGroup(wasteContact);
    app.goTo().homePage();
    //Contacts after = app.db().contacts();
   //assertEquals(after.size(), before.size() - 1);
   //assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();


    //throws SQLException
    //Connection conn = null;
   // try {
   //   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
    //  Statement st = conn.createStatement();
    //  ResultSet rs = st.executeQuery("select id from address_in_groups");
    //  Groups groups = new Groups();
    //  while (rs.next()) {
   //          }
   //   rs.close();
   //   st.close();
    //  conn.close();
    //  System.out.println(groups);
   // } catch (SQLException ex) {
      // handle any errors
   //   System.out.println("SQLException: " + ex.getMessage());
   //   System.out.println("SQLState: " + ex.getSQLState());
   //   System.out.println("VendorError: " + ex.getErrorCode());
  //  }

   // Groups groups = app.db().groups();
    //app.goTo().homePage();
   // Contacts before = app.db().contacts();
    //ContactData deletedContact = before.iterator().next();
   // app.goTo().homePage();
   // app.contact().delete(deletedContact);
  //  app.goTo().homePage();
   // Contacts after = app.db().contacts();
//    assertEquals(after.size(), before.size() - 1);
//    assertThat(after, equalTo(before.without(deletedContact)));
   // verifyContactListInUI();



  }


}
