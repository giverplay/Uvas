package me.giverplay.uvas.utils;

public final class MathUtils {
  private MathUtils() { }

  public static double parseDouble(String value) {
    if (value == null) return 0.0D;

    value = value.replaceAll(",", ".");
    return Double.parseDouble(value);
  }

  public static boolean isNumber(String value) {
    if (value == null) return true;

    value = value.replaceAll(",", ".");
    return value.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
