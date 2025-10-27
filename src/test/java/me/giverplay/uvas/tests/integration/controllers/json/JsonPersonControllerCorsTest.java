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
import me.giverplay.uvas.tests.integration.dto.PersonDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonPersonControllerCorsTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;
  private static PersonDTO person;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    person = new PersonDTO();
  }

  @Test
  @Order(1)
  void create() throws JsonProcessingException {
    mockPerson();
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GIVERPLAY)
      .setBasePath("/api/v1/person")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .body(person)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    person = objectMapper.readValue(content, PersonDTO.class);
    validatePerson(person);
  }

  @Test
  @Order(2)
  void createWithWrongOrigin() {
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MOONLAR)
      .setBasePath("/api/v1/person")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .body(person)
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
      .setBasePath("/api/v1/person")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .pathParam("id", person.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    PersonDTO foundPerson = objectMapper.readValue(content, PersonDTO.class);
    validatePerson(foundPerson);
  }

  @Test
  @Order(4)
  void findByIdWithWrongOrigin() {
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MOONLAR)
      .setBasePath("/api/v1/person")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_JSON_VALUE)
      .build();

    String content = given(specification)
      .pathParam("id", person.getId())
      .when().get("{id}")
      .then().statusCode(403)
      .extract().body().asString();

    assertEquals(content, "Invalid CORS request");
  }

  private void validatePerson(PersonDTO person) {
    assertNotNull(person);
    assertTrue(person.getId() > 0);

    assertNotNull(person.getFirstName());
    assertNotNull(person.getLastName());
    assertNotNull(person.getAddress());
    assertNotNull(person.getGender());

    assertEquals(person.getFirstName(), "Lara");
    assertEquals(person.getLastName(), "Croft");
    assertEquals(person.getAddress(), "Somewhere - UK");
    assertEquals(person.getGender(), "Female");
    assertTrue(person.isEnabled());
  }

  private void mockPerson() {
    person.setFirstName("Lara");
    person.setLastName("Croft");
    person.setAddress("Somewhere - UK");
    person.setGender("Female");
    person.setEnabled(true);
  }
}