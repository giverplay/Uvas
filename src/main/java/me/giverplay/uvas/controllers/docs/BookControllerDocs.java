package me.giverplay.uvas.controllers.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import me.giverplay.uvas.data.dto.BookDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookControllerDocs {

  @Operation(
    summary = "Finds all books",
    description = "Finds all books registered in the database",
    tags = {"Books"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
          )
        }),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  List<BookDTO> findAll();

  @Operation(
    summary = "Finds a book",
    description = "Finds a specific book by your ID",
    tags = {"Books"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = BookDTO.class))),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  BookDTO findById(@PathVariable("id") long id);

  @Operation(
    summary = "Creates a new book",
    description = "Creates a new book in the database",
    tags = {"Books"},
    responses = {
      @ApiResponse(
        description = "Created",
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = BookDTO.class))),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  ResponseEntity<BookDTO> create(@RequestBody BookDTO book);

  @Operation(
    summary = "Updates a book",
    description = "Update a specific book information",
    tags = {"Books"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = BookDTO.class))),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  BookDTO update(@RequestBody BookDTO book);

  @Operation(
    summary = "Deletes a book",
    description = "Delete a specific book from the database by your ID",
    tags = {"Books"},
    responses = {
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
  ResponseEntity<?> delete(@PathVariable("id") long id);
}
