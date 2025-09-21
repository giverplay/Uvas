package me.giverplay.uvas.services;

import me.giverplay.uvas.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger LOGGER = Logger.getLogger(PersonService.class.getName());
  private final AtomicLong IDS = new AtomicLong();

  private final AtomicInteger mockApt = new AtomicInteger();

  private final String[] mockNames = {
    "Ana", "Luísa", "Marina", "Anna", "Mariana", "Arielly", "Isabelle", "Emily",
    "Júlia", "Aline", "Sara", "Milena", "Eduarda", "Franciele", "Luana", "Emanuelly",
    "Viviane", "Isadora", "Luna", "Marcela", "Adriana", "Maria", "Luara", "Gabriele"
  };

  public List<Person> findAll() {
    LOGGER.info("Finding all people");

    List<Person> people = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      people.add(mockPerson(i));
    }

    return people;
  }

  public Person findById(String id) {
    LOGGER.info("Finding one person: " + id);
    return mockPerson((int) IDS.getAndIncrement());
  }

  public Person create(Person person) {
    LOGGER.info("Creating one person");
    person.setId(IDS.getAndIncrement());
    LOGGER.info("Created person %s %s #%d".formatted(person.getFirstName(), person.getLastName(), person.getId()));
    return person;
  }

  public Person update(Person person) {
    LOGGER.info("Updating person #" + person.getId());
    return person;
  }

  public Person delete(String id) {
    LOGGER.info("Deleting person #" + id);
    return mockPerson(Integer.parseInt(id));
  }

  private Person mockPerson(int id) {
    Random random = new Random(id);

    String firstName = mockNames[random.nextInt(mockNames.length)];
    String lastName;

    do {
      lastName = mockNames[random.nextInt(mockNames.length)];
    } while (lastName.equals(firstName));

    String address = "Uvalândia, Uva County - Rua Videia, Grape Hills - Grape Palace apt " + mockApt.incrementAndGet();

    return new Person(id, firstName, lastName, address, "Female");
  }
}
