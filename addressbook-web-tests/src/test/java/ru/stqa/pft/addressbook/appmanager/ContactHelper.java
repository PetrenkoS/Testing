package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 12.07.2017.
 */
public class ContactHelper extends HelperBase {


  public ContactHelper(WebDriver wd) {
    super(wd);
  }
  private SessionFactory sessionFactory;

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
    attach(By.name("photo"), contactData.getPhoto());
    if (creation) { // Добавление контакта в какую-нибудь группу
      if (contactData.getGroups().size() > 0) { //Если список групп больше 0
        Assert.assertTrue(contactData.getGroups().size() == 1); //При условии, что список групп равен 1
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  private String getLastName(WebElement element) {
    return element.findElement(By.xpath("./td[2]")).getText();
  }

  private String getFirstName(WebElement element) {
    return element.findElement(By.xpath("./td[3]")).getText();
  }

  private String getEmails(WebElement element) {
    return element.findElement(By.xpath("./td[5]")).getText();
  }

  private String getAllPhones(WebElement element) {
    return element.findElement(By.xpath("./td[6]")).getText();
  }


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
    // click(By.cssSelector(String.format("a[href='edit.php?id=%s']", id)));
    click(By.cssSelector("a[href = 'edit.php?id=" + id + "']"));
  }

  public void create(ContactData contact, boolean creation) {
    fillContactForms(contact, creation);
    submitAddContact();

  }

  public void deleteSelectedContactsFromGroup(ContactData contact) {
    if (!wd.findElement(By.xpath("//form[@id='right']/select//option[3]")).isSelected()) { //выбираем наверху справа группу
      click(By.xpath("//form[@id='right']/select//option[3]"));
    }
    selectContactById(contact.getId());
    click(By.name("remove"));
    wd.switchTo().alert().accept();
  }

  public void addSelectedContactsToGroup(ContactData contact, Set<GroupData> allGroups) {
   click(By.cssSelector("input[value='"+ contact.getId() + "']"));
  // if (!wd.findElement(By.xpath("//div[@class='right']//select[normalize-space(.)='test1 test2 test3']//option[3]")).isSelected()) {
  //    wd.findElement(By.xpath("//div[@class='right']//select[normalize-space(.)='test1 test2 test3']//option[3]")).click();
  //  }
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(getGroupsAll().iterator().next().getName());
    wd.findElement(By.name("add")).click();
      }
   // wd.findElement(By.xpath("//select[@name='group']//option[@value='" + "" + "']")).click();



  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();

  }

 //выбор группы из списка для добавления в нее контакта
  public void selectSituatedGroupFromList(GroupData group) {
    new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
    click(By.xpath("//div[@id='content']/form[2]/div[4]/input"));
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

  public  List<GroupData> getGroupsAll(){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    GroupData groups = new GroupData();
    List<GroupData> result = session.createQuery( "from GroupData").getResultList();
    session.getTransaction().commit();
    session.close();
    return result;
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

  public GroupData getGroupToAdd(Groups groups, ContactData contact) {
    Groups beforeAssignmentGroups = contact.getGroups();
    for(GroupData group : groups) {
      if(!beforeAssignmentGroups.contains(group)) {
        return group;
      }
    }
    return null;
  }

  public void addContact(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    addSelectedContactToGroup(group);
    //contactCache = null;
      }

  private void addSelectedContactToGroup(GroupData group) {
    new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
    click(By.xpath("//div[@id='content']/form[2]/div[4]/input"));
  }
}


