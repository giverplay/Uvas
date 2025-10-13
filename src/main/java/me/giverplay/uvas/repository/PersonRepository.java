package me.giverplay.uvas.repository;

import me.giverplay.uvas.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

  @Query("update person")
  void disablePerson(@Param("id") long id);
}
