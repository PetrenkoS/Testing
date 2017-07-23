package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

/**
 * Created by Admin on 12.07.2017.
 */
public class ContactHelper extends HelperBase {


  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContactForms(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHometelephone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    //attach(By.name("photo"), contactData.getPhoto());
    //if(creation) {
             // new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        // } else {
     // Assert.assertFalse(isElementPresent(By.name("new_group")));
    }


  private String getLastName(WebElement element) {
    return element.findElement(By.xpath("./td[2]")).getText();
  }

  private String getFirstName(WebElement element) {
    return element.findElement(By.xpath("./td[3]")).getText();
  }

  private String getEmails(WebElement element) { return element.findElement(By.xpath("./td[5]")).getText(); }

  private String getAllPhones(WebElement element) { return element.findElement(By.xpath("./td[6]")).getText(); }


  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModification(contact.getId());
    fillContactForms(contact, false);
    submitContactModification();
  }

  public void submitAddContact() {
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }


  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void initContactModification(int id) {
    //wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img")).click();
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    click(By.cssSelector("a[href = 'edit.php?id=" + id + "']"));
  }

  public void create(ContactData contact, boolean creation) {
    fillContactForms(contact, creation);
    submitAddContact();

  }


    public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();

  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

   // public Contacts all() {
  //    Contacts contacts = new Contacts();
  //  List<WebElement> elements = wd.findElements(By.xpath("//tr[contains(@name,\"entry\")]"));
   // for (WebElement element : elements) {
  //    String firstname = getFirstName(element);
  //    String lastname = getLastName(element);
  //    int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
   //   contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
  //  }
  //  return contacts;
 // }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withAddress(address)
             .withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId())
            .withFirstname(firstname).withLastname(lastname)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHometelephone(home).withMobilePhone(mobile).withWorkPhone(work);

  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }
}
