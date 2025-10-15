package me.giverplay.uvas.services;

import jakarta.transaction.Transactional;
import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.exception.exceptions.RequiredObjectIsNullException;
import me.giverplay.uvas.exception.exceptions.ResourceNotFoundException;
import me.giverplay.uvas.hateoas.PersonHATEOAS;
import me.giverplay.uvas.mapper.ObjectMapper;
import me.giverplay.uvas.model.PersonEntity;
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

  public List<PersonDTO> findAll() {
    LOGGER.info("Finding all people");
    List<PersonDTO> people = ObjectMapper.parseObjects(repository.findAll(), PersonDTO.class);
    people.forEach(PersonHATEOAS::addLinks);
    return people;
  }

  public PersonDTO findById(Long id) {
    LOGGER.info("Finding one person: " + id);
    PersonEntity entity = findById0(id);
    PersonDTO dto = ObjectMapper.parseObject(entity, PersonDTO.class);
    PersonHATEOAS.addLinks(dto);
    return dto;
  }

  private PersonEntity findById0(Long id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));
  }

  public PersonDTO create(PersonDTO person) {
    if (person == null) {
      throw new RequiredObjectIsNullException();
    }

    LOGGER.info("Creating one person");
    PersonEntity entity = ObjectMapper.parseObject(person, PersonEntity.class);
    PersonDTO dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
    PersonHATEOAS.addLinks(dto);
    return dto;
  }

  public PersonDTO update(PersonDTO person) {
    if (person == null) {
      throw new RequiredObjectIsNullException();
    }

    LOGGER.info("Updating person #" + person.getId());

    PersonEntity entity = findById0(person.getId());
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    PersonDTO dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
    PersonHATEOAS.addLinks(dto);
    return dto;
  }

  @Transactional
  public PersonDTO disable(long id) {
    LOGGER.info("Disabling person #" + id);

    findById0(id);
    repository.disablePerson(id);

    PersonDTO dto = ObjectMapper.parseObject(findById0(id), PersonDTO.class);
    PersonHATEOAS.addLinks(dto);
    return dto;
  }

  public void delete(Long id) {
    LOGGER.info("Deleting person #" + id);

    PersonEntity entity = findById0(id);
    repository.delete(entity);
  }
}
