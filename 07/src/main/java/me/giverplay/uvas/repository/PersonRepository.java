package me.giverplay.uvas.repository;

import me.giverplay.uvas.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
