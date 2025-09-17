package me.giverplay.uvas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong ids = new AtomicLong();

  @RequestMapping("/greeting")
  public Greetings greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greetings(ids.getAndIncrement(), String.format(TEMPLATE, name));
  }
}
