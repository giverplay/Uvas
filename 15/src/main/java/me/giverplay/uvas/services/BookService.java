package me.giverplay.uvas.services;

import me.giverplay.uvas.data.dto.BookDTO;
import me.giverplay.uvas.exception.exceptions.RequiredObjectIsNullException;
import me.giverplay.uvas.exception.exceptions.ResourceNotFoundException;
import me.giverplay.uvas.hateoas.BookHATEOAS;
import me.giverplay.uvas.mapper.ObjectMapper;
import me.giverplay.uvas.model.BookEntity;
import me.giverplay.uvas.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BookService {
  private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

  @Autowired
  private BookRepository repository;

  public List<BookDTO> findAll() {
    LOGGER.info("Finding all books");
    List<BookDTO> books = ObjectMapper.parseObjects(repository.findAll(), BookDTO.class);
    books.forEach(BookHATEOAS::addLinks);
    return books;
  }

  public BookDTO findById(long id) {
    LOGGER.info("Finding one book");
    BookDTO book = ObjectMapper.parseObject(findById0(id), BookDTO.class);
    BookHATEOAS.addLinks(book);
    return book;
  }

  private BookEntity findById0(long id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));
  }

  public BookDTO create(BookDTO book) {
    if (book == null) {
      throw new RequiredObjectIsNullException();
    }

    LOGGER.info("Creating one book");
    BookEntity entity = ObjectMapper.parseObject(book, BookEntity.class);
    BookDTO dto = ObjectMapper.parseObject(repository.save(entity), BookDTO.class);
    BookHATEOAS.addLinks(dto);
    return dto;
  }

  public BookDTO update(BookDTO book) {
    if (book == null) {
      throw new RequiredObjectIsNullException();
    }

    LOGGER.info("Updating one book");
    BookEntity entity = findById0(book.getId());

    entity.setTitle(book.getTitle());
    entity.setAuthor(book.getAuthor());
    entity.setLaunchDate(book.getLaunchDate());
    entity.setPrice(book.getPrice());

    BookDTO dto = ObjectMapper.parseObject(repository.save(entity), BookDTO.class);
    BookHATEOAS.addLinks(dto);
    return dto;
  }

  public void delete(long id) {
    LOGGER.info("Deleting one book");
    BookEntity entity = findById0(id);
    repository.delete(entity);
  }
}
