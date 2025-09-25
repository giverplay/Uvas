package me.giverplay.uvas.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {
  @Override
  public void serialize(String gender, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(gender.equals("Male") ? "M" : "F");
  }
}
