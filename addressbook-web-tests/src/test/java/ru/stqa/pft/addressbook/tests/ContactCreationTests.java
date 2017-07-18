package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testsContactCreation() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().gotoAddContactPage();
    ContactData contact = new ContactData()
            .withFirstname("Vladimir").withLastname("Putin").withAddress("Kremlin").withHometelephone("111");
    app.contact().create(new ContactData()
            .withFirstname("Putin").withLastname("Vladimir").withAddress("Kremlin").withHometelephone("111").withGroup("test1"), true); // Эта строчка не работает почему-то, если поставить "contact, true"
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

}
