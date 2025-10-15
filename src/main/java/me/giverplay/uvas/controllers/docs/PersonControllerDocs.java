package me.giverplay.uvas.controllers.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import me.giverplay.uvas.data.dto.PersonDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PersonControllerDocs {

  @Operation(
    summary = "Finds all people",
    description = "Finds all people registered in the database",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
          )
        }),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  List<PersonDTO> findAll();

  @Operation(
    summary = "Finds a person",
    description = "Finds a specific person by your ID",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = PersonDTO.class))),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  PersonDTO findById(@PathVariable("id") Long id);

  @Operation(
    summary = "Creates a new person",
    description = "Creates a new person in the database",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Created",
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = PersonDTO.class))),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person);

  @Operation(
    summary = "Updates a person",
    description = "Update a specific person information",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = PersonDTO.class))),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  PersonDTO update(@RequestBody PersonDTO person);

  @Operation(
    summary = "Disables a person",
    description = "Disables a specific person by your ID",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = PersonDTO.class))),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  PersonDTO disable(@PathVariable("id") Long id);

  @Operation(
    summary = "Deletes a person",
    description = "Delete a specific person from the database by your ID",
    tags = {"People"},
    responses = {
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  ResponseEntity<?> delete(@PathVariable("id") Long id);
}
