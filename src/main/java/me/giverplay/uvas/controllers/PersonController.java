package me.giverplay.uvas.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.giverplay.uvas.controllers.docs.PersonControllerDocs;
import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/v1/person",
  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
)
@Tag(name = "People API", description = "Endpoints for managing people")
public class PersonController implements PersonControllerDocs {

  @Autowired
  private PersonService service;

  @GetMapping
  @Override
  public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "direction", defaultValue = "asc") String direction
  ) {
    Sort.Direction sortDirection = "desc".equals(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
    return ResponseEntity.ok(service.findAll(pageable));
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @GetMapping(value = "/{id}")
  @Override
  public PersonDTO findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  @Override
  public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
    PersonDTO dto = service.create(person);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  @Override
  public PersonDTO update(@RequestBody PersonDTO person) {
    return service.update(person);
  }

  @PatchMapping(value = "/{id}")
  @Override
  public PersonDTO disable(@PathVariable("id") Long id) {
    return service.disable(id);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @DeleteMapping(value = "/{id}")
  @Override
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
