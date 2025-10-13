package me.giverplay.uvas.tests.integration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");

    private static void startContainers() {
      Startables.deepStart(Stream.of(mysql)).join();
    }

    private static Map<String, Object> createConnectionConfiguration() {
      return Map.of(
        "spring.datasource.url", mysql.getJdbcUrl(),
        "spring.datasource.username", mysql.getUsername(),
        "spring.datasource.password", mysql.getPassword()
      );
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
      startContainers();

      ConfigurableEnvironment environment = context.getEnvironment();
      MapPropertySource testContainer = new MapPropertySource("testcontainers", createConnectionConfiguration());
      environment.getPropertySources().addFirst(testContainer);
    }
  }
}
