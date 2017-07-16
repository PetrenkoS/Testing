package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id;
  private final String firstname;
  private final String lastname;
  private final String address;

  public void setId(int id) {
    this.id = id;
  }

  private final String hometelephone;
  private String group;

  public ContactData(String firstname, String lastname, String address, String hometelephone, String group) {
    this.id = 0;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.hometelephone = hometelephone;
    this.group = group;
  }

  public ContactData(int id, String firstname, String lastname, String address, String hometelephone, String group) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.hometelephone = hometelephone;
    this.group = group;
  }

  public int getId() {  return id;  }

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

  public String getGroup() {     return group;   }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }
}
