package me.giverplay.uvas;

import me.giverplay.uvas.exception.exceptions.UnsupportedMathOperationException;
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
    throws UnsupportedMathOperationException {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new UnsupportedMathOperationException("The sum terms must be numbers");
    }

    return parseDouble(numberOne) + parseDouble(numberTwo);
  }

  @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
                            @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new UnsupportedMathOperationException("The subtraction terms must be numbers");
    }

    return parseDouble(numberOne) - parseDouble(numberTwo);
  }

  @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
                               @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new UnsupportedMathOperationException("The multiplication factors must be numbers");
    }

    return parseDouble(numberOne) * parseDouble(numberTwo);
  }

  private double parseDouble(String value) {
    if (value == null) return 0.0D;

    value = value.replaceAll(",", ".");
    return Double.parseDouble(value);
  }

  private boolean isNaN(String value) {
    if (value == null) return true;

    value = value.replaceAll(",", ".");
    return !value.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
