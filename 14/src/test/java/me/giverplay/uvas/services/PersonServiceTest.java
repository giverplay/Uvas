package me.giverplay.uvas.services;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.mapper.MockPerson;
import me.giverplay.uvas.model.PersonEntity;
import me.giverplay.uvas.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
  void findAll() {
  }

  @Test
  void findById() {
    PersonEntity person = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(person));
    PersonDTO result = service.findById(1L);

    assertNotNull(result);
    assertNotNull(result.getLinks());

    assertEquals(1L, result.getId());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", person.getLastName());
    assertEquals("Address Test1", person.getAddress());
    assertEquals("Female", person.getGender());

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("self") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("findAll") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("create") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("CREATE")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("update") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("PUT")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("delete") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("DELETE")
    ));
  }

  @Test
  void create() {
    PersonEntity person = input.mockEntity(1L);
    PersonDTO dto = input.mockDTO(1);

    when(repository.save(person)).thenReturn(person);
    PersonDTO result = service.create(dto);

    assertNotNull(result);
    assertNotNull(result.getLinks());

    assertEquals(1L, result.getId());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", person.getLastName());
    assertEquals("Address Test1", person.getAddress());
    assertEquals("Female", person.getGender());

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("self") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("findAll") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("create") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("CREATE")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("update") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("PUT")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("delete") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("DELETE")
    ));
  }

  @Test
  void update() {
    PersonEntity person = input.mockEntity(1L);
    PersonDTO dto = input.mockDTO(1);

    when(repository.findById(1L)).thenReturn(Optional.of(person));
    when(repository.save(person)).thenReturn(person);
    PersonDTO result = service.update(dto);

    assertNotNull(result);
    assertNotNull(result.getLinks());

    assertEquals(1L, result.getId());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", person.getLastName());
    assertEquals("Address Test1", person.getAddress());
    assertEquals("Female", person.getGender());

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("self") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("findAll") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("GET")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("create") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("CREATE")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("update") &&
        link.getHref().endsWith("/person") &&
        link.getType().equals("PUT")
    ));

    assertTrue(result.getLinks().stream().anyMatch(link ->
      link.getRel().value().equals("delete") &&
        link.getHref().endsWith("/person/1") &&
        link.getType().equals("DELETE")
    ));
  }

  @Test
  void delete() {
    PersonEntity person = input.mockEntity(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(person));
    service.delete(1L);

    verify(repository, times(1)).findById(anyLong());
    verify(repository, times(1)).delete(any(PersonEntity.class));
    verifyNoInteractions(repository);
  }
}