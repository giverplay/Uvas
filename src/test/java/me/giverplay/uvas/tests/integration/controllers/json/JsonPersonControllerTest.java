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
import me.giverplay.uvas.tests.integration.dto.json.PersonDTOWrapper;
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
class JsonPersonControllerTest extends AbstractIntegrationTest {

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
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(person)
      .when().post()
      .then().statusCode(201)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    person = objectMapper.readValue(content, PersonDTO.class);

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
  void update() throws JsonProcessingException {
    person.setAddress("Calistoga, California - USA");

    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(person)
      .when().put()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    person = objectMapper.readValue(content, PersonDTO.class);

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
  void findById() throws JsonProcessingException {
    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", person.getId())
      .when().get("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    PersonDTO foundPerson = objectMapper.readValue(content, PersonDTO.class);
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
  void disable() throws JsonProcessingException {
    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", person.getId())
      .when().patch("{id}")
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    PersonDTO foundPerson = objectMapper.readValue(content, PersonDTO.class);
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
  void findAll() throws JsonProcessingException {
    String content = given(specification)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParams("page", 3, "size", 10, "direction", "asc")
      .when().get()
      .then().statusCode(200)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract().body().asString();

    PersonDTOWrapper wrapper = objectMapper.readValue(content, PersonDTOWrapper.class);
    List<PersonDTO> people = wrapper.getEmbedded().getPeople();

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