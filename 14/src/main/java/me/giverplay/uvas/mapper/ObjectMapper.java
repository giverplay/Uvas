package me.giverplay.uvas.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.List;

public class ObjectMapper {
  private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

  public static <O, D> D parseObject(O origin, Class<D> destination) {
    return MAPPER.map(origin, destination);
  }

  public static <O, D> List<D> parseObjects(List<O> origins, Class<D> destination) {
    return origins.stream().map(o -> ObjectMapper.parseObject(o, destination)).toList();
  }
}
