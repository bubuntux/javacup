package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGConstants;

/**
 * <br> Clase que representa una recta y=mx+b. <br> <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class Recta {

  /**
   * Tangente de la pendiente.
   */
  private final double m;
  /**
   * Desplazamiento.
   */
  private final double b;

  /**
   * Constructor.
   *
   * @param puntoA punto inicial de la recta.
   * @param puntoB punto final de la recta.
   */
  public Recta(Punto puntoA, Punto puntoB) {
    if (puntoA.equals(puntoB)) {
      throw new IllegalArgumentException("Es necesario indicar dos puntos diferentes");
    }
    if (puntoB.getX() == puntoA.getX()) {
      this.m = Double.POSITIVE_INFINITY;
      this.b = puntoB.getX();
    } else if (puntoB.getY() == puntoA.getY()) {
      this.m = 0;
      this.b = puntoB.getY();
    } else {
      this.m = (puntoB.getY() - puntoA.getY()) / (puntoB.getX() - puntoA.getX());
      this.b =
          (puntoA.getY() * puntoB.getX() - puntoB.getY() * puntoA.getX()) / (puntoB.getX() - puntoA
              .getX());
    }
  }

  /**
   * Constructor.
   *
   * @param m valor de M.
   * @param b valor de B.
   */
  public Recta(double m, double b) {
    this.m = m;
    this.b = b;
  }

  /**
   * Devuelve la B de la recta.
   *
   * @return B de la recta.
   */
  public double getB() {
    return b;
  }

  /**
   * Devuelve la M de la recta.
   *
   * @return M de la recta.
   */
  public double getM() {
    return m;
  }

  /**
   * Devuelve la coordenada x.
   *
   * @param y coordenada y.
   * @return coordenada x.
   */
  public double getX(double y) {
    // y = mx + b
    // x = (y - b) / m
    if (m == Double.POSITIVE_INFINITY) {
      return b;
    } else if (m == Double.MIN_VALUE) {
      throw new RuntimeException("No se puede calcular la X para una recta con pendiente infinita");
    } else {
      return (y - b) / m;
    }

  }

  /**
   * Devuelve la coordenada Y.
   *
   * @param x coordenada x.
   * @return coordenada y.
   */
  public double getY(double x) {
    // y = mx + b
    if (m == Double.MIN_VALUE) {
      return b;
    } else if (m == Double.POSITIVE_INFINITY) {
      throw new RuntimeException("No se puede calcular la Y para una recta con pendiente 0");
    } else {
      return x * m + b;
    }
  }

  /**
   * Indica si es un punto de la recta
   *
   * @param punto punto a comprobar.
   * @return si es un punto de la recta.
   */
  public boolean isPoint(Punto punto) {
    double distancia;
    if (m == Double.POSITIVE_INFINITY) {
      distancia = this.b - punto.getX();
    } else {
      distancia = TrigonometriaUtils.getDistancia(
          punto,
          new Punto(punto.getX(), getY(punto.getX())));
    }
    return distancia <= MSGConstants.PRECISION_COMPARAR_DISTANCIAS;
  }

  /**
   * Returns a string representation of the object. In general, the <code>toString</code> method
   * returns a string that "textually represents" this object. The result should be a concise but
   * informative representation that is easy for a person to read. It is recommended that all
   * subclasses override this method. <p/> The <code>toString</code> method for class
   * <code>Object</code> returns a string consisting of the name of the class of which the object is
   * an instance, the at-sign character `<code>@</code>', and the unsigned hexadecimal
   * representation of the hash code of the object. In other words, this method returns a string
   * equal to the value of: <blockquote>
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre></blockquote>
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return String.format("Recta -> m:%s, b:%s", m, b);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Recta other = (Recta) obj;
    if (this.m != other.m) {
      return false;
    }
    if (this.b != other.b) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash =
        19 * hash + (int) (Double.doubleToLongBits(this.m) ^ (Double.doubleToLongBits(this.m)
                                                              >>> 32));
    hash =
        19 * hash + (int) (Double.doubleToLongBits(this.b) ^ (Double.doubleToLongBits(this.b)
                                                              >>> 32));
    return hash;
  }
}
