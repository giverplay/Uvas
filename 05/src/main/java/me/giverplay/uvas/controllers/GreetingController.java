package me.giverplay.uvas.controllers;


import me.giverplay.uvas.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong ids = new AtomicLong();

  @RequestMapping("/greeting")
  public Greeting greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(ids.getAndIncrement(), String.format(TEMPLATE, name));
  }
}
