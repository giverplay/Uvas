package me.giverplay.uvas.tests.repository;

import me.giverplay.uvas.model.PersonEntity;
import me.giverplay.uvas.repository.PersonRepository;
import me.giverplay.uvas.tests.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest extends AbstractIntegrationTest {

  private static PersonEntity person;

  @Autowired
  private PersonRepository repository;

  @BeforeAll
  static void setUp() {
    person = new PersonEntity();
  }

  @Order(1)
  @Test
  void findPeopleByName() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));
    person = repository.findPeopleByName("lua", pageable).getContent().getFirst();

    assertNotNull(person);

    assertEquals(person.getId(), 1);
    assertEquals(person.getFirstName(), "Luana");
    assertEquals(person.getLastName(), "Silva");
    assertEquals(person.getGender(), "Female");
    assertEquals(person.getAddress(), "Rua dos Grapes");
    assertTrue(person.isEnabled());
  }

  @Order(2)
  @Test
  void disablePerson() {
    repository.disablePerson(person.getId());

    Optional<PersonEntity> result = repository.findById(person.getId());

    assertTrue(result.isPresent());

    person = result.get();

    assertEquals(person.getId(), 1);
    assertEquals(person.getFirstName(), "Luana");
    assertEquals(person.getLastName(), "Silva");
    assertEquals(person.getGender(), "Female");
    assertEquals(person.getAddress(), "Rua dos Grapes");
    assertFalse(person.isEnabled());
  }
}