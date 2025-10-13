package me.giverplay.uvas.tests.integration.swagger;

import me.giverplay.uvas.tests.config.TestConfig;
import me.giverplay.uvas.tests.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTests extends AbstractIntegrationTest {

  @Test
  void shouldDisplaySwaggerUIPage() {
    String content = given()
      .basePath("/swagger-ui/index.html").port(TestConfig.SERVER_PORT)
      .when().get()
      .then().statusCode(200)
      .extract().body().asString();

    assertTrue(content.contains("Swagger UI"));
  }
}
