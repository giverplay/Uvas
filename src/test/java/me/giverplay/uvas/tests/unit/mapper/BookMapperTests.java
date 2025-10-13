package me.giverplay.uvas.tests.unit.mapper;

import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.mapper.ObjectMapper;
import me.giverplay.uvas.tests.unit.mock.MockBook;
import me.giverplay.uvas.model.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookMapperTests {
  private MockBook inputObject;

  @BeforeEach
  public void setUp() {
    inputObject = new MockBook();
  }

  @Test
  public void parseEntityToDTOTest() {
    BookDTO output = ObjectMapper.parseObject(inputObject.mockEntity(), BookDTO.class);
    assertBook(output, 0L);
  }

  @Test
  public void parseEntityListToDTOListTest() {
    List<BookDTO> outputList = ObjectMapper.parseObjects(inputObject.mockEntityList(), BookDTO.class);

    assertBook(outputList.get(0), 0);
    assertBook(outputList.get(2), 2);
    assertBook(outputList.get(4), 4);
    assertBook(outputList.get(7), 7);
    assertBook(outputList.get(12), 12);
  }

  @Test
  public void parseDTOToEntityTest() {
    BookEntity output = ObjectMapper.parseObject(inputObject.mockDTO(), BookEntity.class);
    assertBook(output, 0L);
  }

  @Test
  public void parserDTOListToEntityListTest() {
    List<BookEntity> outputList = ObjectMapper.parseObjects(inputObject.mockDTOList(), BookEntity.class);

    assertBook(outputList.get(0), 0L);
    assertBook(outputList.get(1), 1L);
    assertBook(outputList.get(3), 3L);
    assertBook(outputList.get(9), 9L);
  }

  private void assertBook(BookDTO output, long id) {
    assertEquals(id, output.getId());
    assertEquals("Book " + id, output.getTitle());
    assertEquals("Author " + id, output.getAuthor());
    assertEquals(id * 10, output.getPrice());
    assertEquals(new Date(MockBook.BOOKS_TIMESTAMP), output.getLaunchDate());
  }

  private void assertBook(BookEntity output, long id) {
    assertEquals(id, output.getId());
    assertEquals("Book " + id, output.getTitle());
    assertEquals("Author " + id, output.getAuthor());
    assertEquals(id * 10, output.getPrice());
    assertEquals(new Date(MockBook.BOOKS_TIMESTAMP), output.getLaunchDate());
  }
}