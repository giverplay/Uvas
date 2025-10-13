package me.giverplay.uvas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("People and Books API")
        .version("v1")
        .description("API DESCANSAnte do Carregar Molas")
        .termsOfService("https://giverplay.me")
        .license(new License()
          .name("MIT")
          .url("https://giverplay.me")));
  }
}
