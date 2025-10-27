package me.giverplay.uvas.tests.integration.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import me.giverplay.uvas.tests.config.TestConfig;
import me.giverplay.uvas.tests.integration.AbstractIntegrationTest;
import me.giverplay.uvas.tests.integration.dto.BookDTO;
import me.giverplay.uvas.tests.integration.dto.json.BookDTOWrapper;
import me.giverplay.uvas.tests.unit.mock.MockBook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonBookControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;
  private static BookDTO book;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    book = new BookDTO();
  }

  @Test
  @Order(1)
  void create() throws JsonProcessingException {
    mockBook();
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GIVERPLAY)
      .setBasePath("/api/v1/book")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(book)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    book = objectMapper.readValue(content, BookDTO.class);

    assertNotNull(book);
    assertTrue(book.getId() > 0);

    assertEquals(book.getTitle(), "The Fire");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  @Test
  @Order(2)
  void update() throws JsonProcessingException {
    book.setTitle("The Fire: Origins");

    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(book)
      .when().put()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    book = objectMapper.readValue(content, BookDTO.class);

    assertNotNull(book);
    assertTrue(book.getId() > 0);

    assertEquals(book.getTitle(), "The Fire: Origins");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  @Test
  @Order(3)
  void findById() throws JsonProcessingException {
    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", book.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    BookDTO foundBook = objectMapper.readValue(content, BookDTO.class);
    assertNotNull(foundBook);
    assertTrue(foundBook.getId() > 0);

    assertEquals(book.getTitle(), "The Fire: Origins");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  @Test
  @Order(4)
  void delete() {
    given(specification)
      .pathParam("id", book.getId())
      .when().delete("{id}")
      .then().statusCode(204);
  }

  @Test
  @Order(5)
  void findAll() throws JsonProcessingException {
    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParams("page", 5, "size", 10, "direction", "asc")
      .when().get()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    BookDTOWrapper wrapper = objectMapper.readValue(content, BookDTOWrapper.class);
    List<BookDTO> books = wrapper.getEmbedded().getBooks();

    BookDTO bookOne = books.getFirst();
    assertNotNull(bookOne);
    assertEquals(bookOne.getId(), 751);
    assertEquals(bookOne.getTitle(), "Annie");
    assertEquals(bookOne.getAuthor(), "Florinda Elan");
    assertEquals(bookOne.getLaunchDate(), Date.from(OffsetDateTime.parse("1977-11-10T03:00:00.000+00:00").toInstant()));
    assertEquals(bookOne.getPrice(), 29.99);

    BookDTO bookSix = books.get(5);
    assertNotNull(bookSix);
    assertEquals(bookSix.getId(), 892);
    assertEquals(bookSix.getTitle(), "Argo");
    assertEquals(bookSix.getAuthor(), "Anastassia Franzke");
    assertEquals(bookSix.getLaunchDate(), Date.from(OffsetDateTime.parse("1967-04-07T03:00:00.000+00:00").toInstant()));
    assertEquals(bookSix.getPrice(), 8.99);
  }

  private void mockBook() {
    book.setTitle("The Fire");
    book.setAuthor("Fireman");
    book.setPrice(15.0);
    book.setLaunchDate(new Date(MockBook.BOOKS_TIMESTAMP));
  }
}