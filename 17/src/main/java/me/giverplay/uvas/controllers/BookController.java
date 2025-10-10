package me.giverplay.uvas.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.giverplay.uvas.controllers.docs.BookControllerDocs;
import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.services.BookService;
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
@RequestMapping(path = "/api/v1/book",
  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@Tag(name = "Books API", description = "Endpoints for managing books")
public class BookController implements BookControllerDocs {

  @Autowired
  private BookService service;

  @GetMapping
  public List<BookDTO> findAll() {
    return service.findAll();
  }

  @GetMapping(path = "/{id}")
  public BookDTO findById(@PathVariable(name = "id") long id) {
    return service.findById(id);
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto) {
    BookDTO book = service.create(dto);
    return new ResponseEntity<>(book, HttpStatus.CREATED);
  }

  @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
  public BookDTO update(@RequestBody BookDTO dto) {
    return service.update(dto);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
