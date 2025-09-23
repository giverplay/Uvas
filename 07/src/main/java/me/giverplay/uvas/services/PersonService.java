package me.giverplay.uvas.services;

import me.giverplay.uvas.exception.ResourceNotFoundException;
import me.giverplay.uvas.model.Person;
import me.giverplay.uvas.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

  @Autowired
  private PersonRepository repository;

  public List<Person> findAll() {
    LOGGER.info("Finding all people");
    return repository.findAll();
  }

  public Person findById(Long id) {
    LOGGER.info("Finding one person: " + id);
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));
  }

  public Person create(Person person) {
    LOGGER.info("Creating one person");
    return repository.save(person);
  }

  public Person update(Person person) {
    LOGGER.info("Updating person #" + person.getId());

    Person entity = findById(person.getId());
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return repository.save(entity);
  }

  public void delete(Long id) {
    LOGGER.info("Deleting person #" + id);

    Person entity = findById(id);
    repository.delete(entity);
  }
}
