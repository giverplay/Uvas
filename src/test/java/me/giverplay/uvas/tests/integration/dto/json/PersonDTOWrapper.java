package me.giverplay.uvas.tests.integration.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PersonDTOWrapper implements Serializable {

  @JsonProperty("_embedded")
  private PersonEmbeddedDTO embedded;

  public PersonEmbeddedDTO getEmbedded() {
    return embedded;
  }

  public void setEmbedded(PersonEmbeddedDTO embedded) {
    this.embedded = embedded;
  }
}
