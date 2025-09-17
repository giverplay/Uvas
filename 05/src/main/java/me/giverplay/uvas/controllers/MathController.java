package me.giverplay.uvas.controllers;

import me.giverplay.uvas.exception.UnsupportedMathOperationException;
import me.giverplay.uvas.utils.MathUtils;
import me.giverplay.uvas.utils.MathValidate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/math")
public class MathController {

  @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double sum(@PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    MathValidate.areNumbers(numberOne, numberTwo, "The sum terms must be numbers");
    return MathUtils.parseDouble(numberOne) + MathUtils.parseDouble(numberTwo);
  }

  @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
                            @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    MathValidate.areNumbers(numberOne, numberTwo, "The subtraction terms must be numbers");
    return MathUtils.parseDouble(numberOne) - MathUtils.parseDouble(numberTwo);
  }

  @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
                               @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    MathValidate.areNumbers(numberOne, numberTwo, "The multiplication terms must be numbers");
    return MathUtils.parseDouble(numberOne) * MathUtils.parseDouble(numberTwo);
  }

  @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double division(@PathVariable(value = "numberOne") String numberOne,
                         @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    MathValidate.areNumbers(numberOne, numberTwo, "The division terms must be numbers");
    double divisor = MathUtils.parseDouble(numberTwo);

    MathValidate.notZero(divisor, "The divisor cannot be zero");
    return MathUtils.parseDouble(numberOne) / divisor;
  }

  @RequestMapping(value = "/sqrt/{number}", method = RequestMethod.GET)
  public double sqrt(@PathVariable(value = "number") String number) throws UnsupportedMathOperationException {
    MathValidate.isNumber(number, "The number cannot bem null");
    double value = MathUtils.parseDouble(number);

    MathValidate.isPositive(value, "Cannot extract square root from negative numbers");
    return Math.sqrt(MathUtils.parseDouble(number));
  }

  @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
  public Double mean(@PathVariable(value = "numberOne") String numberOne,
                     @PathVariable(value = "numberTwo") String numberTwo)
    throws UnsupportedMathOperationException {

    MathValidate.areNumbers(numberOne, numberTwo, "The mean terms must be numbers");
    return (MathUtils.parseDouble(numberOne) + MathUtils.parseDouble(numberTwo)) / 2.0D;
  }
}
