package me.giverplay.uvas.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.giverplay.uvas.controllers.docs.BookControllerDocs;
import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(path = "/api/v1/book",
  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
)
@Tag(name = "Books API", description = "Endpoints for managing books")
public class BookController implements BookControllerDocs {

  @Autowired
  private BookService service;

  @GetMapping
  @Override
  public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "direction", defaultValue = "asc") String direction
  ) {
    Sort.Direction sortDirection = "desc".equals(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
    return ResponseEntity.ok(service.findAll(pageable));
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @GetMapping(path = "/{id}")
  public BookDTO findById(@PathVariable(name = "id") long id) {
    return service.findById(id);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto) {
    BookDTO book = service.create(dto);
    return new ResponseEntity<>(book, HttpStatus.CREATED);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
  public BookDTO update(@RequestBody BookDTO dto) {
    return service.update(dto);
  }

  // @CrossOrigin(origins = "http://localhost:8080")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
