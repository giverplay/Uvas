package me.giverplay.uvas.data.dto;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PersonDTOV2 implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private long id;

  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  private Date birthday;

  public PersonDTOV2() {
  }

  public PersonDTOV2(long id, String firstName, String lastName, String address, String gender, Date birthday) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.gender = gender;
    this.birthday = birthday;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof PersonDTOV2 that)) return false;
    return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(gender, that.gender) && Objects.equals(birthday, that.birthday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, birthday);
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
