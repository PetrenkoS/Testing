package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Admin on 18.07.2017.
 */
public class ContactEmailsTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().gotoAddContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Angela").withLastname("Merkel").withAddress("3").withHometelephone("34").withMobilePhone("456").withWorkPhone("7625")
              .withEmail("gfujk@gvgk.com").withEmail2("gfujk@njgd.com").withEmail3("gfujk@gal.com"), true);
    }
  }

  @Test
  public void testContactEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next(); // контакт с главной страницы
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact); // контакт со страницы редактирования
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }



  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));

  }


}
