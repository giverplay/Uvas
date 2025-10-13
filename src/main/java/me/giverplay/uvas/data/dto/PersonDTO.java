package me.giverplay.uvas.data.dto;


import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
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

  public PersonDTO(long id, String firstName, String lastName, String address, String gender, boolean enabled) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.gender = gender;
    this.enabled = enabled;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    PersonDTO dto = (PersonDTO) object;
    return id == dto.id && enabled == dto.enabled && Objects.equals(firstName, dto.firstName) && Objects.equals(lastName, dto.lastName) && Objects.equals(address, dto.address) && Objects.equals(gender, dto.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, firstName, lastName, address, gender, enabled);
  }
}
