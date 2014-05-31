package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Common {

  /**
   * Calcula una direccion entre dos posiciones
   */
  public static double calcularDireccion(Position posicionReferencia, Position posicionCalculo) {
    return Math.atan2(
        posicionCalculo.getY() - posicionReferencia.getY(),
        posicionCalculo.getX() - posicionReferencia.getX());
  }

  /**
   * Calcula un angulo entre dos direcciones
   */
  public static double calcularAngulo(double d1, double d2) {

    double angulo = Math.max(d1, d2) - Math.min(d1, d2);
    if (angulo > Math.PI) {
      angulo = (2 * Math.PI) - angulo;
    }

    return angulo;
  }

  /**
   * Redondea al valor mas cercano del divisor
   */
  public static double redondeaMultiplo(double valor, double divisor) {
    return Math.round(valor / divisor) * divisor;
  }

  /**
   * Devuelve true cuando la posicion esta dentro del area propia
   */
  public static boolean dentroAreaPropia(Position p) {
    return (p.getX() >= -Constants.LARGO_AREA_GRANDE / 2
            && p.getX() <= Constants.LARGO_AREA_GRANDE / 2 &&
            p.getY() >= -Constants.LARGO_CAMPO_JUEGO / 2
            && p.getY() <= (-Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_GRANDE);
  }

  /**
   * Devuelve true cuando la posicion esta dentro del area rival
   */
  public static boolean dentroAreaRival(Position p) {
    return (p.getX() >= -Constants.LARGO_AREA_GRANDE / 2
            && p.getX() <= Constants.LARGO_AREA_GRANDE / 2 &&
            p.getY() <= Constants.LARGO_CAMPO_JUEGO / 2
            && p.getY() >= (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE);
  }

}
