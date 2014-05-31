package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <br> Utilidades. <br> <br>Copyright Fi2net S.A. 10-ene-2011
 */
public final class TrigonometriaUtils {

  /**
   * Log.
   */
  private static final Logger LOG = Logger.getLogger(TrigonometriaUtils.class.getName());

  /**
   * Constructor.
   */
  private TrigonometriaUtils() {
  }

  /**
   * Calcula el ángulo del vector.
   *
   * @param punto punto con el final del vector.
   * @return águno del vector.
   */
  public static double getAnguloVector(Punto punto) {
    double radianes = Math.atan2(punto.getY(), punto.getX());
    if (punto.getX() < 0) {
      return radianes += Math.PI;
    }
    if (radianes < 0) {
      return radianes += 2 * Math.PI;
    }
    return radianes;
  }

  /**
   * Devuelve el pu nto asociado al angulo.
   *
   * @param angulo angulo.
   * @param modulo modulo
   * @return punto.
   */
  public static Punto getPunto(double angulo, double modulo) {
    return new Punto(modulo * Math.cos(angulo), modulo * Math.sin(angulo));
  }

  /**
   * Calcula el módulo de un vector.
   *
   * @param punto punto del vector.
   * @return modulo del vector.
   */
  public static double getModuloVector(Punto punto) {
    return Math.sqrt(Math.pow(punto.getX(), 2) + Math.pow(punto.getY(), 2));
  }

  /**
   * Calcula el módulo de un vector.
   *
   * @param punto punto del vector.
   * @return modulo del vector.
   */
  public static double getModuloVector(Punto3D punto) {
    return Math
        .sqrt(Math.pow(punto.getX(), 2) + Math.pow(punto.getY(), 2) + Math.pow(punto.getZ(), 2));
  }

  /**
   * Calcula la distancia entre dos puntos.
   *
   * @param puntoA punto A.
   * @param puntoB punto B.
   * @return distancia.
   */
  public static double getDistancia(Punto puntoA, Punto puntoB) {
    return Math.sqrt(
        Math.pow(puntoB.getX() - puntoA.getX(), 2) + Math.pow(puntoB.getY() - puntoA.getY(), 2));
  }

  /**
   * Calcula la distancia entre dos puntos.
   *
   * @param puntoA punto A.
   * @param puntoB punto B.
   * @return distancia.
   */
  public static double getDistancia(Punto3D puntoA, Punto3D puntoB) {
    return Math.sqrt(
        Math.pow(puntoB.getX() - puntoA.getX(), 2) + Math.pow(puntoB.getY() - puntoA.getY(), 2)
        + Math.pow(puntoB.getZ() - puntoA.getZ(), 2));
  }

  /**
   * Traslada un punto según el pivote.
   *
   * @param punto  punto a traslada.
   * @param pivote pivote.
   * @return punto trasladado.
   */
  public static Punto trasladaAlPivote(Punto punto, Punto pivote) {
    return new Punto(punto.getX() + pivote.getX(), punto.getY() + pivote.getY());
  }

  /**
   * Comprueba si el punto a esta contenido en el circulo b.
   *
   * @param a punto.
   * @param b circulo.
   * @return indica si el punto es contenido.
   */
  public static boolean getContenido(Punto a, Circulo b) {
    return getDistancia(a, b.getCentro()) <= b.getRadio();
  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto getInterseccion(Recta a, Recta b) {

    if (a == null | b == null) {
      throw new IllegalArgumentException("ERROR son null");
    }

    // y = mx + b
    // m1x + b1 = m2x + b2
    // x (m1 - m2) = b2 - b1
    // x = (b2 - b1) / (m1 - m2)
    if (a.getM() == b.getM()) {
      return null;
    }

    if (a.getM() == Double.POSITIVE_INFINITY) {
      return new Punto(a.getB(), b.getY(a.getB()));
    } else if (b.getM() == Double.POSITIVE_INFINITY) {
      return new Punto(b.getB(), a.getY(b.getB()));
    }

    double x = (b.getB() - a.getB()) / (a.getM() - b.getM());
    double y = a.getY(x);
    return new Punto(x, y);
  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto getInterseccion(Segmento a, Recta b) {
    Punto p = getInterseccion(a.getRecta(), b);
    return p != null && a.isPoint(p) ? p : null;
  }

  public static boolean isContenido(Punto p, Rectangulo r) {
    if (p.getX() < r.getCentro().getX() - r.getAncho() / 2
        || p.getX() > r.getCentro().getX() + r.getAncho() / 2) {
      return false;
    }
    if (p.getY() < r.getCentro().getY() - r.getAlto() / 2
        || p.getY() > r.getCentro().getY() + r.getAlto() / 2) {
      return false;
    }
    return true;
  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto getInterseccion(Segmento a, Segmento b) {
    Punto p = getInterseccion(a.getRecta(), b.getRecta());
    return p != null && a.isPoint(p) && b.isPoint(p) ? p : null;
  }

  public static Punto[] getInterseccion(Segmento a, Rectangulo b) {
    Punto
        punto1 =
        new Punto(b.getCentro().getX() - b.getAncho() / 2, b.getCentro().getY() + b.getAlto() / 2);
    Punto
        punto2 =
        new Punto(b.getCentro().getX() + b.getAncho() / 2, b.getCentro().getY() + b.getAlto() / 2);
    Punto
        punto3 =
        new Punto(b.getCentro().getX() + b.getAncho() / 2, b.getCentro().getY() - b.getAlto() / 2);
    Punto
        punto4 =
        new Punto(b.getCentro().getX() - b.getAncho() / 2, b.getCentro().getY() - b.getAlto() / 2);

    List<Punto> puntos = new LinkedList<>();
    Punto i = getInterseccion(a, new Segmento(punto1, punto2));
    if (i != null) {
      puntos.add(i);
    }
    i = getInterseccion(a, new Segmento(punto2, punto3));
    if (i != null) {
      puntos.add(i);
    }
    i = getInterseccion(a, new Segmento(punto3, punto4));
    if (i != null) {
      puntos.add(i);
    }
    i = getInterseccion(a, new Segmento(punto4, punto1));
    if (i != null) {
      puntos.add(i);
    }
    return puntos.toArray(new Punto[puntos.size()]);

  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto[] getInterseccion(Recta a, Circulo b) {
    double[] xValues = getSolutionEcuationReal(
        Math.pow(a.getM(), 2) + 1,
        2 * a.getM() * a.getB() - 2 * b.getCentro().getY() * a.getM() - 2 * b.getCentro().getX(),
        Math.pow(b.getCentro().getX(), 2) + Math.pow(a.getB(), 2) - 2 * b.getCentro().getY() * a
            .getB() + Math.pow(b.getCentro().getY(), 2) - Math.pow(b.getRadio(), 2));

    if (xValues.length == 0) {
      return new Punto[0];
    }

    Set<Punto> puntos = new HashSet<>();
    for (double x : xValues) {
      for (double y : b.getY(x)) {
        Punto p = new Punto(x, y);
        if (a.isPoint(p)) {
          puntos.add(p);
        }
      }
    }
    return puntos.toArray(new Punto[puntos.size()]);
  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto[] getInterseccion(Segmento a, Circulo b) {
    List<Punto> puntos = new LinkedList<>();
    for (Punto p : getInterseccion(a.getRecta(), b)) {
      if (a.isPoint(p)) {
        puntos.add(p);
      }
    }
    return puntos.toArray(new Punto[puntos.size()]);
  }

  /**
   * Calcula la interseccion.
   *
   * @param a recta a
   * @param b recta b
   * @return interseccion.
   */
  public static Punto[] getInterseccion(Circulo a, Circulo b) {
    double d = -2 * a.getCentro().getX();
    double e = -2 * a.getCentro().getY();
    double
        f =
        Math.pow(a.getCentro().getX(), 2) + Math.pow(a.getCentro().getY(), 2) - Math
            .pow(a.getRadio(), 2);

    double g = -2 * b.getCentro().getX();
    double h = -2 * b.getCentro().getY();
    double
        i =
        Math.pow(b.getCentro().getX(), 2) + Math.pow(b.getCentro().getY(), 2) - Math
            .pow(b.getRadio(), 2);

    double m = (g - d) / (e - h);
    double n = (i - f) / (e - h);

    double[] xValues = getSolutionEcuationReal(
        Math.pow(m, 2) + 1,
        2 * m * n + d + e * m,
        Math.pow(n, 2) + e * n + f);

    if (xValues.length == 0) {
      return new Punto[0];
    }

    Set<Punto> puntos = new HashSet<>();
    for (double x : xValues) {
      for (double y : b.getY(x)) {
        Punto p = new Punto(x, y);
        if (a.isPoint(p)) {
          puntos.add(p);
        }
      }
    }
    return puntos.toArray(new Punto[puntos.size()]);
  }

  /**
   * Devuelve las soluciones reales.
   *
   * @param a valor de a.
   * @param b valor de b.
   * @param c valor de c.
   * @return solución en números reales.
   */
  public static double[] getSolutionEcuationReal(double a, double b, double c) {
    double[] result;
    if (4 * a * c > Math.pow(b, 2)) {
      result = new double[0];
    } else {
      result = new double[2];
      result[0] = ((-b) + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      result[1] = ((-b) - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    }
    return result;
  }

  /**
   * Calcula la recta perpendicular de una recta que pasa por un punto
   *
   * @param recta recta.
   * @param punto punto
   * @return perpendicular.
   */
  public static Recta getPerpendicular(Recta recta, Punto punto) {
    double anguloRecta = Math.atan(recta.getM());
    anguloRecta += Math.PI / 2;
    double m = Math.tan(anguloRecta);
    return new Recta(m, punto.getY() - m * punto.getX());
  }

  /**
   * Calcula el punto emdio.
   *
   * @param a primer punto.
   * @param b segundo punto.
   * @return punto medio.
   */
  public static Punto getPuntoMedio(Punto a, Punto b) {
    double minY = Math.min(a.getY(), b.getY());
    double maxY = Math.max(a.getY(), b.getY());
    double minX = Math.min(a.getX(), b.getX());
    double maxX = Math.max(a.getX(), b.getX());
    return new Punto(
        (minX + maxX) / 2, (minY + maxY) / 2);
  }

  /**
   * Calcula la mediatriz de un segmento
   *
   * @param segmento segmento.
   * @return mediatriz.
   */
  public static Recta getMediatriz(Segmento segmento) {
    Punto punto = getPuntoMedio(segmento.getPuntoA(), segmento.getPuntoB());
    return getPerpendicular(segmento.getRecta(), punto);
  }

  /**
   * Calcula las bisectrices del angulo que forman las dos rectas.
   *
   * @param rectaA primera recta.
   * @param rectaB segunda recta.
   * @return bisectrices
   */
  public static Recta[] getBisectrices(Recta rectaA, Recta rectaB) {
    Punto interseccion = getInterseccion(rectaA, rectaB);
    if (interseccion == null) {
      return new Recta[0];
    }

    Circulo circulo = new Circulo(interseccion, 100);
    Punto[] interseccionesA = getInterseccion(rectaA, circulo);
    Punto[] interseccionesB = getInterseccion(rectaB, circulo);

    Set<Recta> rectas = new HashSet<>();
    for (Punto pA : interseccionesA) {
      for (Punto pB : interseccionesB) {
        Segmento segmento = new Segmento(pA, pB);
        rectas.add(getMediatriz(segmento));
      }
    }
    return rectas.toArray(new Recta[rectas.size()]);
  }

  public static double radianToSexagesimal(double radian) {
    return radian * 360 / (2 * Math.PI);
  }

  public static double getAngulo(Punto puntoA, Punto puntoB) {
    double x = puntoB.getX() - puntoA.getX();
    double y = puntoB.getY() - puntoA.getY();
    return Math.atan2(y, x);
  }
}
