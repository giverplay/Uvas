package me.giverplay.uvas.services;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.data.dto.PersonDTOV2;
import me.giverplay.uvas.exception.ResourceNotFoundException;
import me.giverplay.uvas.mapper.ObjectMapper;
import me.giverplay.uvas.mapper.PersonMapper;
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

  @Autowired
  private PersonMapper personMapper;

  public List<PersonDTO> findAll() {
    LOGGER.info("Finding all people");
    return ObjectMapper.parseObjects(repository.findAll(), PersonDTO.class);
  }

  public PersonDTO findById(Long id) {
    LOGGER.info("Finding one person: " + id);
    PersonEntity entity = findById0(id);
    return ObjectMapper.parseObject(entity, PersonDTO.class);
  }

  private PersonEntity findById0(Long id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));
  }

  public PersonDTO create(PersonDTO person) {
    LOGGER.info("Creating one person");
    PersonEntity entity = repository.save(ObjectMapper.parseObject(person, PersonEntity.class));
    return ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
  }

  public PersonDTOV2 createV2(PersonDTOV2 person) {
    LOGGER.info("Creating one person V2");
    PersonEntity entity = repository.save(personMapper.convertDTOToEntity(person));
    return personMapper.convertEntityToDTO(repository.save(entity));
  }

  public PersonDTO update(PersonDTO person) {
    LOGGER.info("Updating person #" + person.getId());

    PersonEntity entity = findById0(person.getId());
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
  }

  public PersonDTOV2 updateV2(PersonDTOV2 person) {
    LOGGER.info("Updating person V2 #" + person.getId());

    PersonEntity entity = findById0(person.getId());
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());
    // entity.setBirthday(person.getBirthday())

    return personMapper.convertEntityToDTO(repository.save(entity));
  }

  public void delete(Long id) {
    LOGGER.info("Deleting person #" + id);

    PersonEntity entity = findById0(id);
    repository.delete(entity);
  }
}
