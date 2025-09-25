package me.giverplay.uvas.me.giverplay.uvas.test.mocks;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.model.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {

  public PersonEntity mockEntity() {
    return mockEntity(0);
  }

  public PersonDTO mockDTO() {
    return mockDTO(0);
  }

  public List<PersonEntity> mockEntityList() {
    List<PersonEntity> people = new ArrayList<>();

    for (int i = 0; i < 14; i++) {
      people.add(mockEntity(i));
    }

    return people;
  }

  public List<PersonDTO> mockDTOList() {
    List<PersonDTO> people = new ArrayList<>();

    for (int i = 0; i < 14; i++) {
      people.add(mockDTO(i));
    }

    return people;
  }

  public PersonEntity mockEntity(Integer number) {
    PersonEntity person = new PersonEntity();
    person.setAddress("Address Test" + number);
    person.setFirstName("First Name Test" + number);
    person.setGender(((number % 2) == 0) ? "Male" : "Female");
    person.setId(number.longValue());
    person.setLastName("Last Name Test" + number);

    return person;
  }

  public PersonDTO mockDTO(Integer number) {
    PersonDTO person = new PersonDTO();
    person.setAddress("Address Test" + number);
    person.setFirstName("First Name Test" + number);
    person.setGender(((number % 2) == 0) ? "Male" : "Female");
    person.setId(number.longValue());
    person.setLastName("Last Name Test" + number);

    return person;
  }

}