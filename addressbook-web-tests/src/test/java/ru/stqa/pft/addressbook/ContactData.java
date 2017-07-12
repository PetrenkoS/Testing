package ru.stqa.pft.addressbook;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String address;
  private final String hometelephone;

  public ContactData(String firstname, String lastname, String address, String hometelephone) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.hometelephone = hometelephone;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getHometelephone() {
    return hometelephone;
  }
}
