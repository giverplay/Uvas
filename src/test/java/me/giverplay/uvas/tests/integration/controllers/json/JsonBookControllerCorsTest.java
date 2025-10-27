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
import me.giverplay.uvas.tests.unit.mock.MockBook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonBookControllerCorsTest extends AbstractIntegrationTest {

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
      .body(book)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    book = objectMapper.readValue(content, BookDTO.class);
    validateBook(book);
  }

  @Test
  @Order(2)
  void createWithWrongOrigin() {
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MOONLAR)
      .setBasePath("/api/v1/book")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .body(book)
      .when().post()
      .then().statusCode(403)
      .extract().body().asString();

    assertEquals(content, "Invalid CORS request");
  }

  @Test
  @Order(3)
  void findById() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
      .setBasePath("/api/v1/book")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .pathParam("id", book.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    BookDTO foundBook = objectMapper.readValue(content, BookDTO.class);
    validateBook(foundBook);
  }

  @Test
  @Order(4)
  void findByIdWithWrongOrigin() {
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MOONLAR)
      .setBasePath("/api/v1/book")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .pathParam("id", book.getId())
      .when().get("{id}")
      .then().statusCode(403)
      .extract().body().asString();

    assertEquals(content, "Invalid CORS request");
  }

  private void validateBook(BookDTO book) {
    assertNotNull(book);
    assertTrue(book.getId() > 0);

    assertNotNull(book.getTitle());
    assertNotNull(book.getAuthor());
    assertNotNull(book.getLaunchDate());
    assertTrue(book.getPrice() > 0);

    assertEquals(book.getTitle(), "The Fire");
    assertEquals(book.getAuthor(), "Fireman");
    assertEquals(book.getLaunchDate(), new Date(MockBook.BOOKS_TIMESTAMP));
    assertEquals(book.getPrice(), 15.0);
  }

  private void mockBook() {
    book.setTitle("The Fire");
    book.setAuthor("Fireman");
    book.setPrice(15.0);
    book.setLaunchDate(new Date(MockBook.BOOKS_TIMESTAMP));
  }
}
