package me.giverplay.uvas.repository;

import me.giverplay.uvas.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
