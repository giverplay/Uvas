package me.giverplay.uvas.tests.integration.controllers.yaml;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.giverplay.uvas.tests.config.TestConfig;
import me.giverplay.uvas.tests.integration.AbstractIntegrationTest;
import me.giverplay.uvas.tests.integration.dto.BookDTO;
import me.giverplay.uvas.tests.integration.dto.xml.BookPagedModel;
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
class YamlBookControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static YamlObjectMapper yamlMapper;
  private static BookDTO book;

  @BeforeAll
  static void setUp() {
    yamlMapper = new YamlObjectMapper();
    book = new BookDTO();
  }

  @Test
  @Order(1)
  void create() {
    mockBook();
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GIVERPLAY)
      .setBasePath("/api/v1/book")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_YAML_VALUE)
      .build();

    book = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .body(book, yamlMapper)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(BookDTO.class, yamlMapper);

    assertNotNull(book);
    assertTrue(book.getId() > 0);

    assertEquals(book.getTitle(), "The Fire");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  @Test
  @Order(2)
  void update() {
    book.setTitle("The Fire: Origins");

    book = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .body(book, yamlMapper)
      .when().put()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(BookDTO.class, yamlMapper);

    assertNotNull(book);
    assertTrue(book.getId() > 0);

    assertEquals(book.getTitle(), "The Fire: Origins");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  @Test
  @Order(3)
  void findById() {
    BookDTO foundBook = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .pathParam("id", book.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(BookDTO.class, yamlMapper);

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
  void findAll() {
    BookPagedModel wrapper = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .queryParams("page", 5, "size", 10, "direction", "asc")
      .when().get()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(BookPagedModel.class, yamlMapper);

    List<BookDTO> books = wrapper.getContent();

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