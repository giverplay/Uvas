package me.giverplay.uvas.tests.integration.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.giverplay.uvas.tests.integration.dto.BookDTO;

import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDTO implements Serializable {

  @JsonProperty("books")
  private List<BookDTO> books;

  public List<BookDTO> getBooks() {
    return books;
  }

  public void setBooks(List<BookDTO> books) {
    this.books = books;
  }
}
