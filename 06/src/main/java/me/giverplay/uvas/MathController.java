package me.giverplay.uvas;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
  private final AtomicLong ids = new AtomicLong();

  @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double sum(@PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
    throws Exception {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new Exception();
    }

    return parseDouble(numberOne) + parseDouble(numberTwo);
  }

  private double parseDouble(String value) {
    if(value == null) return 0.0D;

    value = value.replaceAll(",", ".");
    return Double.parseDouble(value);
  }

  private boolean isNaN(String value) {
    if(value == null) return true;

    value = value.replaceAll(",", ".");
    return !value.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
