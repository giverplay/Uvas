package me.giverplay.uvas.controllers;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.data.dto.PersonDTOV2;
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
@RequestMapping("/person")
public class PersonController {
  @Autowired
  private PersonService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonDTO> findAll() {
    return service.findAll();
  }

  @GetMapping(value = "/v2", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonDTO> findAllV2() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDTO findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @GetMapping(value = "/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDTO findByIdV2(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
    PersonDTO dto = service.create(person);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PersonDTOV2> createV2(@RequestBody PersonDTOV2 person) {
    PersonDTOV2 dto = service.createV2(person);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDTO update(@RequestBody PersonDTO person) {
    return service.update(person);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDTOV2 updateV2(@RequestBody PersonDTOV2 person) {
    return service.updateV2(person);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteV2(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
