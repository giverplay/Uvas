package me.giverplay.uvas.tests.integration.dto.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import me.giverplay.uvas.tests.integration.dto.PersonDTO;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PersonPagedModel implements Serializable {
  @XmlElement(name = "content")
  public List<PersonDTO> content;

  public List<PersonDTO> getContent() {
    return content;
  }

  public void setContent(List<PersonDTO> content) {
    this.content = content;
  }
}
