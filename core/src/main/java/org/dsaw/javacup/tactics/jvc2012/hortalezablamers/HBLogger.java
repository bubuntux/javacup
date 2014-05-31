package org.dsaw.javacup.tactics.jvc2012.hortalezablamers;

public class HBLogger {

  protected static final boolean log = false;

  public static void log(String message) {
    if (log) {
      System.out.println(message);
    }
  }
}
