package org.dsaw.javacup.tactics.jvc2012.celta;


public class Utils {

  /**
   * Devuelve la distancia de un punto a a una recta
   *
   * @param point Array con X, Y
   * @param line  Array con X1, Y1, X1, Y1
   * @return Distancia a la recta
   */
  public static double getDistanceToLine(double[] point, double line[]) {
    double a, b, c;
    double x1 = line[0];
    double y1 = line[1];
    double x2 = line[2];
    double y2 = line[3];

    a = y2 - y1;
    b = (x2 - x1) * -1;
    c = (x1 * (y2 - y1)) - (y1 * (x2 - x1));

    return Math.abs(a * point[0] + b * point[1] + c) /
           (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
  }


}
