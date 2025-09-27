package me.giverplay.uvas.controllers;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/person",
  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
)
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping
  public List<PersonDTO> findAll() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}")
  public PersonDTO findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
    PersonDTO dto = service.create(person);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  public PersonDTO update(@RequestBody PersonDTO person) {
    return service.update(person);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
