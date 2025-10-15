package me.giverplay.uvas.repository;

import me.giverplay.uvas.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

  @Modifying(clearAutomatically = true)
  @Query("UPDATE PersonEntity p SET p.enabled = false WHERE p.id =:id")
  void disablePerson(@Param("id") long id);
}
