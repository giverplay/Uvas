package me.giverplay.uvas.mapper;

import me.giverplay.uvas.data.dto.PersonDTOV2;
import me.giverplay.uvas.model.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
  public PersonDTOV2 convertEntityToDTO(PersonEntity entity) {
    PersonDTOV2 dto = new PersonDTOV2();

    dto.setId(entity.getId());
    dto.setFirstName(entity.getFirstName());
    dto.setLastName(entity.getLastName());
    dto.setAddress(entity.getAddress());
    dto.setGender(entity.getGender());
    dto.setBirthday(new Date());

    return dto;
  }

  public PersonEntity convertDTOToEntity(PersonDTOV2 dto) {
    PersonEntity entity = new PersonEntity();

    entity.setId(dto.getId());
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setAddress(dto.getAddress());
    entity.setGender(dto.getGender());
    // entity.setBirthday(dto.getBirthday());

    return entity;
  }
}
