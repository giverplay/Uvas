package me.giverplay.uvas.tests.unit.services;

import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.exception.exceptions.RequiredObjectIsNullException;
import me.giverplay.uvas.tests.unit.mock.MockBook;
import me.giverplay.uvas.model.BookEntity;
import me.giverplay.uvas.repository.BookRepository;
import me.giverplay.uvas.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  private MockBook input;

  @InjectMocks
  private BookService service;

  @Mock
  private BookRepository repository;

  @BeforeEach
  void setUp() {
    input = new MockBook();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Disabled("REASON: WIP")
  void findAll() {
    List<BookEntity> list = input.mockEntityList();
    when(repository.findAll()).thenReturn(list);
    List<BookDTO> books = new ArrayList<>(); // service.findAll();

    assertNotNull(books);
    assertEquals(list.size(), books.size());

    validateBook(books.getFirst(), 0L);
    validateBook(books.get(3), 3L);
    validateBook(books.get(5), 5L);
  }

  @Test
  void findById() {
    BookEntity book = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(book));
    BookDTO result = service.findById(1L);
    validateBook(result, 1L);
  }

  @Test
  void create() {
    BookEntity book = input.mockEntity(1L);
    BookDTO dto = input.mockDTO(1);
    when(repository.save(book)).thenReturn(book);
    BookDTO result = service.create(dto);
    validateBook(result, 1L);
  }

  @Test
  void testCreateWithBook() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

    String expect = "Cannot persist a null object";
    assertEquals(expect, exception.getMessage());
  }

  @Test
  void testUpdateBook() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));

    String expect = "Cannot persist a null object";
    assertEquals(expect, exception.getMessage());
  }

  @Test
  void update() {
    BookEntity book = input.mockEntity(1L);
    BookDTO dto = input.mockDTO(1);

    when(repository.findById(1L)).thenReturn(Optional.of(book));
    when(repository.save(book)).thenReturn(book);
    BookDTO result = service.update(dto);
    validateBook(result, 1L);
  }

  @Test
  void delete() {
    BookEntity book = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(book));
    service.delete(1L);

    verify(repository, times(1)).findById(anyLong());
    verify(repository, times(1)).delete(any(BookEntity.class));
    verifyNoMoreInteractions(repository);
  }

  void validateBook(BookDTO dto, long id) {
    assertNotNull(dto);
    assertNotNull(dto.getLinks());

    assertEquals(id, dto.getId());
    assertEquals("Book " + id, dto.getTitle());
    assertEquals("Author " + id, dto.getAuthor());
    assertEquals(id * 10, dto.getPrice());
    assertEquals(new Date(MockBook.BOOKS_TIMESTAMP), dto.getLaunchDate());

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("self") &&
        link.getHref().endsWith("/api/v1/book/" + id) &&
        link.getType().equals("GET")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("findAll") &&
        link.getHref().contains("/api/v1/book") &&
        link.getType().equals("GET")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("create") &&
        link.getHref().endsWith("/api/v1/book") &&
        link.getType().equals("CREATE")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("update") &&
        link.getHref().endsWith("/api/v1/book") &&
        link.getType().equals("PUT")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("delete") &&
        link.getHref().endsWith("/api/v1/book/" + id) &&
        link.getType().equals("DELETE")
    ));
  }
}