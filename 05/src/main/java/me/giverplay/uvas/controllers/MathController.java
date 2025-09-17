package me.giverplay.uvas.controllers;

import me.giverplay.uvas.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

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

  @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double division(@PathVariable(value = "numberOne") String numberOne,
                               @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new UnsupportedMathOperationException("The multiplication dividend and divisor must be numbers");
    }

    double divisor = parseDouble(numberTwo);

    if(divisor == 0) {
      throw new UnsupportedMathOperationException("The divisor cannot be zero");
    }

    return parseDouble(numberOne) / divisor;
  }

  @RequestMapping(value = "/sqrt/{number}", method = RequestMethod.GET)
  public double sqrt(@PathVariable(value = "number") String number) throws UnsupportedMathOperationException {
    if(isNaN(number)) {
      throw new UnsupportedMathOperationException("The number cannot bem null");
    }

    double value = parseDouble(number);

    if(value < 0) {
      throw new UnsupportedMathOperationException("Cannot extract square root from negative numbers");
    }

    return Math.sqrt(parseDouble(number));
  }

  @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double mean(@PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    if (isNaN(numberOne) || isNaN(numberTwo)) {
      throw new UnsupportedMathOperationException("The mean values must be numbers");
    }

    return (parseDouble(numberOne) + parseDouble(numberTwo)) / 2.0D;
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
