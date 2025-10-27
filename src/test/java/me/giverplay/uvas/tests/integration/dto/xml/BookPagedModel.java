package me.giverplay.uvas.tests.integration.dto.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import me.giverplay.uvas.tests.integration.dto.BookDTO;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class BookPagedModel implements Serializable {
  @XmlElement(name = "content")
  private List<BookDTO> content;

  public List<BookDTO> getContent() {
    return content;
  }

  public void setContent(List<BookDTO> content) {
    this.content = content;
  }
}
