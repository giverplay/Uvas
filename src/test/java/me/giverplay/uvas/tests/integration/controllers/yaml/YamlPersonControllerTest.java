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
import me.giverplay.uvas.tests.integration.dto.PersonDTO;
import me.giverplay.uvas.tests.integration.dto.xml.PersonPagedModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class YamlPersonControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static YamlObjectMapper yamlMapper;
  private static PersonDTO person;

  @BeforeAll
  static void setUp() {
    yamlMapper = new YamlObjectMapper();
    person = new PersonDTO();
  }

  @Test
  @Order(1)
  void create() {
    mockPerson();
    specification = new RequestSpecBuilder()
      .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GIVERPLAY)
      .setBasePath("/api/v1/person")
      .setPort(TestConfig.SERVER_PORT)
      .addFilter(new RequestLoggingFilter(LogDetail.ALL))
      .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
      .setContentType(MediaType.APPLICATION_YAML_VALUE)
      .build();

    person = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .body(person, yamlMapper)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(PersonDTO.class, yamlMapper);

    assertNotNull(person);
    assertTrue(person.getId() > 0);

    assertEquals(person.getFirstName(), "Maria");
    assertEquals(person.getLastName(), "Kane");
    assertEquals(person.getAddress(), "Calistoga, California");
    assertEquals(person.getGender(), "Female");
    assertTrue(person.isEnabled());
  }

  @Test
  @Order(2)
  void update() {
    person.setAddress("Calistoga, California - USA");

    person = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .body(person, yamlMapper)
      .when().put()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(PersonDTO.class, yamlMapper);

    assertNotNull(person);
    assertTrue(person.getId() > 0);

    assertEquals(person.getFirstName(), "Maria");
    assertEquals(person.getLastName(), "Kane");
    assertEquals(person.getAddress(), "Calistoga, California - USA");
    assertEquals(person.getGender(), "Female");
    assertTrue(person.isEnabled());
  }

  @Test
  @Order(3)
  void findById() {
    PersonDTO foundPerson = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .pathParam("id", person.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(PersonDTO.class, yamlMapper);

    assertNotNull(foundPerson);
    assertTrue(foundPerson.getId() > 0);

    assertEquals(foundPerson.getFirstName(), "Maria");
    assertEquals(foundPerson.getLastName(), "Kane");
    assertEquals(foundPerson.getAddress(), "Calistoga, California - USA");
    assertEquals(foundPerson.getGender(), "Female");
    assertTrue(foundPerson.isEnabled());
  }

  @Test
  @Order(4)
  void disable() {
    PersonDTO foundPerson = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .pathParam("id", person.getId())
      .when().patch("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(PersonDTO.class, yamlMapper);

    assertNotNull(foundPerson);
    assertTrue(foundPerson.getId() > 0);

    assertEquals(foundPerson.getFirstName(), "Maria");
    assertEquals(foundPerson.getLastName(), "Kane");
    assertEquals(foundPerson.getAddress(), "Calistoga, California - USA");
    assertEquals(foundPerson.getGender(), "Female");
    assertFalse(foundPerson.isEnabled());
  }

  @Test
  @Order(5)
  void delete() {
    given(specification)
      .pathParam("id", person.getId())
      .when().delete("{id}")
      .then().statusCode(204);
  }

  @Test
  @Order(6)
  void findAll() {
    PersonPagedModel wrapper = given()
      .config(RestAssuredConfig.config()
        .encoderConfig(EncoderConfig.encoderConfig()
          .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
      .spec(specification)
      .accept(MediaType.APPLICATION_YAML_VALUE)
      .queryParams("page", 3, "size", 10, "direction", "asc")
      .when().get()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_YAML_VALUE)
      .extract().body().as(PersonPagedModel.class, yamlMapper);

    List<PersonDTO> people = wrapper.getContent();

    PersonDTO personOne = people.getFirst();
    assertNotNull(personOne);
    assertEquals(personOne.getId(), 801);
    assertEquals(personOne.getFirstName(), "Alisun");
    assertEquals(personOne.getLastName(), "Allison");
    assertEquals(personOne.getAddress(), "Room 506");
    assertEquals(personOne.getGender(), "Female");
    assertTrue(personOne.isEnabled());

    PersonDTO personSeven = people.get(6);
    assertNotNull(personSeven);
    assertEquals(personSeven.getId(), 518);
    assertEquals(personSeven.getFirstName(), "Alma");
    assertEquals(personSeven.getLastName(), "Gettins");
    assertEquals(personSeven.getAddress(), "15th Floor");
    assertEquals(personSeven.getGender(), "Female");
    assertFalse(personSeven.isEnabled());
  }

  private void mockPerson() {
    person.setFirstName("Maria");
    person.setLastName("Kane");
    person.setAddress("Calistoga, California");
    person.setGender("Female");
    person.setEnabled(true);
  }
}