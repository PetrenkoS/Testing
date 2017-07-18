package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.junit.MatcherAssert;
import org.junit.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Admin on 18.07.2017.
 */
public class ContactPhoneTests extends TestBase{

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next(); // контакт с главной страницы
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact); // контакт со страницы редактирования
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
      }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHometelephone(), contact.getMobilePhone(), contact.getWorkPhone())
    .stream().filter((s) -> ! s.equals("")).map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));

       }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
