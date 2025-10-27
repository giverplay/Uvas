package me.giverplay.uvas.tests.integration.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.giverplay.uvas.tests.integration.dto.PersonDTO;

import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDTO implements Serializable {

  @JsonProperty("people")
  private List<PersonDTO> people;

  public List<PersonDTO> getPeople() {
    return people;
  }

  public void setPeople(List<PersonDTO> people) {
    this.people = people;
  }
}
