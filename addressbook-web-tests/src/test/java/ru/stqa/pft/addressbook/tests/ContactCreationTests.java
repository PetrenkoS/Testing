package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstname("firstname1").withLastname("lastname1").withAddress("address1").withHometelephone("hometelephone1")});
    list.add(new Object[] {new ContactData().withFirstname("firstname2").withLastname("lastname2").withAddress("address2").withHometelephone("hometelephone2")});
    list.add(new Object[] {new ContactData().withFirstname("firstname3").withLastname("lastname3").withAddress("address3").withHometelephone("hometelephone3")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testsContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().gotoAddContactPage();
    app.contact().create(contact, true);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));


  }

  }
