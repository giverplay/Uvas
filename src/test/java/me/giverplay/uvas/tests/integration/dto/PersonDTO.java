package me.giverplay.uvas.tests.integration.dto;


import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PersonDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private long id;

  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  public PersonDTO() {
  }

  public PersonDTO(long id, String firstName, String lastName, String address, String gender) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.gender = gender;
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
    if (object == null || getClass() != object.getClass()) return false;
    PersonDTO personDTO = (PersonDTO) object;
    return id == personDTO.id && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(gender, personDTO.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender);
  }
}
