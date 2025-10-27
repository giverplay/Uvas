package me.giverplay.uvas.tests.unit.services;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.exception.exceptions.RequiredObjectIsNullException;
import me.giverplay.uvas.model.PersonEntity;
import me.giverplay.uvas.repository.PersonRepository;
import me.giverplay.uvas.services.PersonService;
import me.giverplay.uvas.tests.unit.mock.MockPerson;
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
class PersonServiceTest {

  private MockPerson input;

  @InjectMocks
  private PersonService service;

  @Mock
  private PersonRepository repository;

  @BeforeEach
  void setUp() {
    input = new MockPerson();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Disabled("REASON: WIP")
  void findAll() {
    List<PersonEntity> list = input.mockEntityList();
    when(repository.findAll()).thenReturn(list);
    List<PersonDTO> people = new ArrayList<>(); //service.findAll(null);

    assertNotNull(people);
    assertEquals(list.size(), people.size());

    validatePerson(people.getFirst(), 0L);
    validatePerson(people.get(3), 3L);
    validatePerson(people.get(5), 5L);
  }

  @Test
  void findById() {
    PersonEntity person = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(person));
    PersonDTO result = service.findById(1L);
    validatePerson(result, 1L);
  }

  @Test
  void create() {
    PersonEntity person = input.mockEntity(1L);
    PersonDTO dto = input.mockDTO(1);
    when(repository.save(person)).thenReturn(person);
    PersonDTO result = service.create(dto);
    validatePerson(result, 1L);
  }

  @Test
  void testCreateWithPerson() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

    String expect = "Cannot persist a null object";
    assertEquals(expect, exception.getMessage());
  }

  @Test
  void testUpdateWithPerson() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));

    String expect = "Cannot persist a null object";
    assertEquals(expect, exception.getMessage());
  }

  @Test
  void update() {
    PersonEntity person = input.mockEntity(1L);
    PersonDTO dto = input.mockDTO(1);

    when(repository.findById(1L)).thenReturn(Optional.of(person));
    when(repository.save(person)).thenReturn(person);
    PersonDTO result = service.update(dto);
    validatePerson(result, 1L);
  }

  @Test
  void delete() {
    PersonEntity person = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(person));
    service.delete(1L);

    verify(repository, times(1)).findById(anyLong());
    verify(repository, times(1)).delete(any(PersonEntity.class));
    verifyNoMoreInteractions(repository);
  }

  void validatePerson(PersonDTO dto, long id) {
    assertNotNull(dto);
    assertNotNull(dto.getLinks());

    String expectedGender = id % 2 == 0 ? "Male" : "Female";

    assertEquals(id, dto.getId());
    assertEquals("First Name Test" + id, dto.getFirstName());
    assertEquals("Last Name Test" + id, dto.getLastName());
    assertEquals("Address Test" + id, dto.getAddress());
    assertEquals(expectedGender, dto.getGender());

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("self") &&
        link.getHref().endsWith("/api/v1/person/" + id) &&
        link.getType().equals("GET")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("findAll") &&
        link.getHref().endsWith("/api/v1/person") &&
        link.getType().equals("GET")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("create") &&
        link.getHref().endsWith("/api/v1/person") &&
        link.getType().equals("CREATE")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("update") &&
        link.getHref().endsWith("/api/v1/person") &&
        link.getType().equals("PUT")
    ));

    assertTrue(dto.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("delete") &&
        link.getHref().endsWith("/api/v1/person/" + id) &&
        link.getType().equals("DELETE")
    ));
  }
}