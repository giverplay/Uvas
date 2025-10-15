package me.giverplay.uvas.tests.integration.controllers.yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.springframework.http.MediaType;

public class YamlObjectMapper implements ObjectMapper {
  private final YAMLMapper mapper;
  private final TypeFactory types;

  public YamlObjectMapper() {
    this.mapper = new YAMLMapper();
    this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    this.types = TypeFactory.defaultInstance();
  }

  @Override
  public Object deserialize(ObjectMapperDeserializationContext context) {
    checkContentType(context.getContentType());

    try {
      return mapper.readValue(context.getDataToDeserialize().asString(), types.constructType(context.getType()));
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Invalid YAML data", e);
    }
  }

  @Override
  public Object serialize(ObjectMapperSerializationContext context) {
    checkContentType(context.getContentType());

    try {
      return mapper.writeValueAsString(context.getObjectToSerialize());
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot serialize object to YAML. Is it valid?", e);
    }
  }

  private void checkContentType(String type) {
    if (!MediaType.APPLICATION_YAML_VALUE.equals(type)) {
      throw new IllegalArgumentException("The content is not application/yaml");
    }
  }
}
