package me.giverplay.uvas.data.dto;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.giverplay.uvas.serializer.GenderSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "first_name", "last_name", "address", "gender"})
@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private long id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonSerialize(using = GenderSerializer.class)
  private String gender;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String phoneNumber;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String address;

  @JsonFormat(pattern = "dd/MM/yyyy")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Date birthday;

  private String password;

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

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof PersonDTO personDTO)) return false;
    return id == personDTO.id && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(gender, personDTO.gender) && Objects.equals(phoneNumber, personDTO.phoneNumber) && Objects.equals(address, personDTO.address) && Objects.equals(birthday, personDTO.birthday) && Objects.equals(password, personDTO.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, gender, phoneNumber, address, birthday, password);
  }
}
