package me.giverplay.uvas.hateoas;

import me.giverplay.uvas.controllers.BookController;
import me.giverplay.uvas.data.dto.BookDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class BookHATEOAS {
  private BookHATEOAS() {
  }

  public static void addLinks(BookDTO dto) {
    dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
    dto.add(linkTo(methodOn(BookController.class).findAll(0, 10, "asc")).withRel("findAll").withType("GET"));
    dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("CREATE"));
    dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
    dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
  }
}
