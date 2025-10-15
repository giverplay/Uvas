package me.giverplay.uvas.tests.integration.dto;


import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class PersonDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private long id;

  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  private boolean enabled;

  public PersonDTO() {
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
    return id == personDTO.id && enabled == personDTO.enabled && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(gender, personDTO.gender);
  }

  @Override
  public String toString() {
    return "Person { %d, %s %s, %s, %s, %s }".formatted(id, firstName, lastName, gender, address, enabled ? "Enabled" : "Disabled");
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, enabled);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
