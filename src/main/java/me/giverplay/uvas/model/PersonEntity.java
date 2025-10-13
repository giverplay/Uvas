package me.giverplay.uvas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "person")
public class PersonEntity implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name", nullable = false, length = 80)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 80)
  private String lastName;

  @Column(nullable = false, length = 100)
  private String address;

  @Column(nullable = false, length = 6)
  private String gender;

  @Column(nullable = false)
  private boolean enabled;

  public PersonEntity() {
  }

  public PersonEntity(long id, String firstName, String lastName, String address, String gender, boolean enabled) {
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
    if (object == null || getClass() != object.getClass()) return false;
    PersonEntity entity = (PersonEntity) object;
    return id == entity.id && enabled == entity.enabled && Objects.equals(firstName, entity.firstName) && Objects.equals(lastName, entity.lastName) && Objects.equals(address, entity.address) && Objects.equals(gender, entity.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, enabled);
  }
}
