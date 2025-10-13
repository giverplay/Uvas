package me.giverplay.uvas.hateoas;

import me.giverplay.uvas.controllers.PersonController;
import me.giverplay.uvas.data.dto.PersonDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class PersonHATEOAS {
  private PersonHATEOAS() {
  }

  public static void addLinks(PersonDTO dto) {
    dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
    dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
    dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("CREATE"));
    dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
    dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
  }
}
