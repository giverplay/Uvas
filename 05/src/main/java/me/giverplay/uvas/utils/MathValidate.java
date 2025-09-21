package me.giverplay.uvas.utils;

import me.giverplay.uvas.exception.UnsupportedMathOperationException;

public final class MathValidate {
  private MathValidate() {
  }

  public static void areNumbers(String numberOne, String numberTwo, String message) {
    if (!MathUtils.isNumber(numberOne) || !MathUtils.isNumber(numberTwo)) {
      throw new UnsupportedMathOperationException(message);
    }
  }

  public static void isNumber(String value, String message) {
    if (!MathUtils.isNumber(value)) {
      throw new UnsupportedMathOperationException(message);
    }
  }

  public static void notZero(double number, String message) {
    if (number == 0) {
      throw new UnsupportedMathOperationException(message);
    }
  }

  public static void isPositive(double number, String message) {
    if (number < 0) {
      throw new UnsupportedMathOperationException(message);
    }
  }
}
