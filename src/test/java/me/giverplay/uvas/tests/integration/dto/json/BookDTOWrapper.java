package me.giverplay.uvas.tests.integration.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BookDTOWrapper implements Serializable {

  @JsonProperty("_embedded")
  private BookEmbeddedDTO embedded;

  public BookEmbeddedDTO getEmbedded() {
    return embedded;
  }

  public void setEmbedded(BookEmbeddedDTO embedded) {
    this.embedded = embedded;
  }
}
