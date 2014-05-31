/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import org.dsaw.javacup.tactics.jvc2013.mansporting.util.MathUtils;

/**
 * @author MaN
 */
public class Segmento {

  /**
   * Recta a la que hace referencia.
   */
  private final Recta recta;
  /**
   * Punto A
   */
  private final Punto puntoA;
  /**
   * Punto B
   */
  private final Punto puntoB;

  /**
   * Segmento.
   *
   * @param puntoA punto A.
   * @param puntoB punto B
   */
  public Segmento(Punto puntoA, Punto puntoB) {
    if (puntoA.equals(puntoB)) {
      this.recta = null;
    } else {
      this.recta = new Recta(puntoA, puntoB);
    }
    this.puntoA = puntoA;
    this.puntoB = puntoB;
  }

  /**
   * Indica si es un punto de la recta
   *
   * @param punto punto a comprobar.
   * @return si es un punto de la recta.
   */
  public boolean isPoint(Punto punto) {
    if (recta != null && !recta.isPoint(punto)) {
      return false;
    }

    double minY = MathUtils.round(Math.min(puntoA.getY(), puntoB.getY()), 3);
    double maxY = MathUtils.round(Math.max(puntoA.getY(), puntoB.getY()), 3);
    double minX = MathUtils.round(Math.min(puntoA.getX(), puntoB.getX()), 3);
    double maxX = MathUtils.round(Math.max(puntoA.getX(), puntoB.getX()), 3);

    double x = MathUtils.round(punto.getX(), 3);
    double y = MathUtils.round(punto.getY(), 3);

    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  /**
   * Devuelve la coordenada Y.
   *
   * @param x coordenada x.
   * @return coordenada y.
   */
  public Double getY(double x) {
    if (recta != null) {
      double minY = MathUtils.round(Math.min(puntoA.getY(), puntoB.getY()), 3);
      double maxY = MathUtils.round(Math.max(puntoA.getY(), puntoB.getY()), 3);
      double y = MathUtils.round(recta.getY(x), 3);
      return y >= minY && y <= maxY ? y : null;
    } else {
      return puntoA.getX() == x ? puntoA.getY() : null;
    }
  }

  /**
   * Devuelve la coordenada x.
   *
   * @param y coordenada y.
   * @return coordenada x.
   */
  public Double getX(double y) {
    if (recta != null) {
      double minX = MathUtils.round(Math.min(puntoA.getX(), puntoB.getX()), 3);
      double maxX = MathUtils.round(Math.max(puntoA.getX(), puntoB.getX()), 3);
      double x = MathUtils.round(recta.getX(y), 3);
      return x >= minX && x <= maxX ? x : null;
    } else {
      return puntoA.getY() == y ? puntoA.getX() : null;
    }
  }

  public Punto getPuntoA() {
    return puntoA;
  }

  public Punto getPuntoB() {
    return puntoB;
  }

  public Recta getRecta() {
    return recta;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Segmento other = (Segmento) obj;
    if (this.puntoA != other.puntoA && (this.puntoA == null || !this.puntoA.equals(other.puntoA))) {
      return false;
    }
    if (this.puntoB != other.puntoB && (this.puntoB == null || !this.puntoB.equals(other.puntoB))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + (this.puntoA != null ? this.puntoA.hashCode() : 0);
    hash = 47 * hash + (this.puntoB != null ? this.puntoB.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return String.format("Segmento -> puntoA:%s, puntoB:%s", puntoA, puntoB);
  }
}
