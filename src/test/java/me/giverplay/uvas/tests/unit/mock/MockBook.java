package me.giverplay.uvas.tests.unit.mock;

import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.model.BookEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
  public static final long BOOKS_TIMESTAMP = 1735700400000L;

  public BookEntity mockEntity() {
    return mockEntity(0);
  }

  public BookDTO mockDTO() {
    return mockDTO(0);
  }

  public List<BookEntity> mockEntityList() {
    List<BookEntity> books = new ArrayList<>();

    for (int i = 0; i < 14; i++) {
      books.add(mockEntity(i));
    }

    return books;
  }

  public List<BookDTO> mockDTOList() {
    List<BookDTO> books = new ArrayList<>();

    for (int i = 0; i < 14; i++) {
      books.add(mockDTO(i));
    }

    return books;
  }

  public BookEntity mockEntity(long id) {
    BookEntity book = new BookEntity();
    book.setId(id);
    book.setTitle("Book " + id);
    book.setAuthor("Author " + id);
    book.setPrice(10 * id);
    book.setLaunchDate(new Date(BOOKS_TIMESTAMP));

    return book;
  }

  public BookDTO mockDTO(long id) {
    BookDTO book = new BookDTO();
    book.setId(id);
    book.setTitle("Book " + id);
    book.setAuthor("Author " + id);
    book.setPrice(10 * id);
    book.setLaunchDate(new Date(BOOKS_TIMESTAMP));

    return book;
  }
}