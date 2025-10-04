package me.giverplay.uvas.mapper;

import me.giverplay.uvas.data.dto.PersonDTO;
import me.giverplay.uvas.model.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTests {
  private MockPerson inputObject;

  @BeforeEach
  public void setUp() {
    inputObject = new MockPerson();
  }

  @Test
  public void parseEntityToDTOTest() {
    PersonDTO output = ObjectMapper.parseObject(inputObject.mockEntity(), PersonDTO.class);
    assertEquals(Long.valueOf(0L), output.getId());
    assertEquals("First Name Test0", output.getFirstName());
    assertEquals("Last Name Test0", output.getLastName());
    assertEquals("Address Test0", output.getAddress());
    assertEquals("Male", output.getGender());
  }

  @Test
  public void parseEntityListToDTOListTest() {
    List<PersonDTO> outputList = ObjectMapper.parseObjects(inputObject.mockEntityList(), PersonDTO.class);
    PersonDTO outputZero = outputList.getFirst();

    assertEquals(Long.valueOf(0L), outputZero.getId());
    assertEquals("First Name Test0", outputZero.getFirstName());
    assertEquals("Last Name Test0", outputZero.getLastName());
    assertEquals("Address Test0", outputZero.getAddress());
    assertEquals("Male", outputZero.getGender());

    PersonDTO outputSeven = outputList.get(7);

    assertEquals(Long.valueOf(7L), outputSeven.getId());
    assertEquals("First Name Test7", outputSeven.getFirstName());
    assertEquals("Last Name Test7", outputSeven.getLastName());
    assertEquals("Address Test7", outputSeven.getAddress());
    assertEquals("Female", outputSeven.getGender());

    PersonDTO outputTwelve = outputList.get(12);

    assertEquals(Long.valueOf(12L), outputTwelve.getId());
    assertEquals("First Name Test12", outputTwelve.getFirstName());
    assertEquals("Last Name Test12", outputTwelve.getLastName());
    assertEquals("Address Test12", outputTwelve.getAddress());
    assertEquals("Male", outputTwelve.getGender());
  }

  @Test
  public void parseDTOToEntityTest() {
    PersonEntity output = ObjectMapper.parseObject(inputObject.mockDTO(), PersonEntity.class);
    assertEquals(Long.valueOf(0L), output.getId());
    assertEquals("First Name Test0", output.getFirstName());
    assertEquals("Last Name Test0", output.getLastName());
    assertEquals("Address Test0", output.getAddress());
    assertEquals("Male", output.getGender());
  }

  @Test
  public void parserDTOListToEntityListTest() {
    List<PersonEntity> outputList = ObjectMapper.parseObjects(inputObject.mockDTOList(), PersonEntity.class);
    PersonEntity outputZero = outputList.getFirst();

    assertEquals(Long.valueOf(0L), outputZero.getId());
    assertEquals("First Name Test0", outputZero.getFirstName());
    assertEquals("Last Name Test0", outputZero.getLastName());
    assertEquals("Address Test0", outputZero.getAddress());
    assertEquals("Male", outputZero.getGender());

    PersonEntity outputSeven = outputList.get(7);

    assertEquals(Long.valueOf(7L), outputSeven.getId());
    assertEquals("First Name Test7", outputSeven.getFirstName());
    assertEquals("Last Name Test7", outputSeven.getLastName());
    assertEquals("Address Test7", outputSeven.getAddress());
    assertEquals("Female", outputSeven.getGender());

    PersonEntity outputTwelve = outputList.get(12);

    assertEquals(Long.valueOf(12L), outputTwelve.getId());
    assertEquals("First Name Test12", outputTwelve.getFirstName());
    assertEquals("Last Name Test12", outputTwelve.getLastName());
    assertEquals("Address Test12", outputTwelve.getAddress());
    assertEquals("Male", outputTwelve.getGender());
  }
}