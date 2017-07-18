package ru.stqa.pft.addressbook.tests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Admin on 18.07.2017.
 */
public class ContactAddressTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Angela").withLastname("Merkel").withAddress("3").withHometelephone("34")
              .withMobilePhone("456").withWorkPhone("7625").withEmail("gfujk@gvgk.com").withGroup("test1"), true);
    }
  }
  @Test
  public void testContactAddress() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next(); //загрузка множества контактов
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact); //получение информации о контакте
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

}
