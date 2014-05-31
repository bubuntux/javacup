/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import org.dsaw.javacup.model.util.Position;

/**
 * @author Usuario
 */
public class Vector2d implements Cloneable {

  public static final Vector2d CERO = new Vector2d(0.0, 0.0);
  public double x;
  public double y;

  public Vector2d() {
  }

  public Vector2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2d(Vector2d vector2d) {
    this.x = vector2d.x;
    this.y = vector2d.y;
  }

  public Vector2d(Position posicion) {
    this.x = posicion.getX();
    this.y = posicion.getY();
  }

  public void cero() {
    x = 0.0;
    y = 0.0;
  }

  public boolean isCero() {
    return (this.x * this.x + this.y * this.y) < Double.MIN_VALUE;
  }

  public double magnitud() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  public double magnitudCuadrada() {
    return this.x * this.x + this.y * this.y;
  }

  public Vector2d normal() {
    Vector2d otro = new Vector2d(this);
    double magnitud = otro.magnitud();
    if (magnitud > 0.0) {
      double unoSobreMagnitud = 1 / magnitud;
      otro.x *= unoSobreMagnitud;
      otro.y *= unoSobreMagnitud;
    }
    return otro;
  }

  public double punto(Vector2d vector2d) {
    return this.x * vector2d.x + this.y * vector2d.y;
  }

  public int signo(Vector2d vector2d) {
    return (this.y * vector2d.x > this.x * vector2d.y) ? -1 : 1;
  }

  public Vector2d perpendicular() {
    return new Vector2d(-this.y, this.x);
  }

  public Vector2d truncado(double magnitud) {
    if (this.magnitud() > magnitud) {
      return this.normal().productoEscalar(magnitud);
    }
    return this;
  }

  public double distancia(Vector2d vector2d) {
    double dx = this.x - vector2d.x;
    double dy = this.y - vector2d.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  public double distanciaCuadrada(Vector2d vector2d) {
    double dx = this.x - vector2d.x;
    double dy = this.y - vector2d.y;
    return dx * dx + dy * dy;
  }

  public Vector2d inverso() {
    return new Vector2d(-x, -y);
  }

  public Vector2d suma(Vector2d vector2d) {
    return new Vector2d(this.x + vector2d.x, this.y + vector2d.y);
  }

  public Vector2d resta(Vector2d vector2d) {
    return new Vector2d(this.x - vector2d.x, this.y - vector2d.y);
  }

  public Vector2d productoEscalar(double constante) {
    return new Vector2d(this.x * constante, this.y * constante);
  }

  public Vector2d divisionEscalar(double constante) {
    double unoSobreConstante = 1 / constante;
    return new Vector2d(this.x * unoSobreConstante,
                        this.y * unoSobreConstante);
  }

  public Vector2d reflejo(Vector2d normal) {
    return this.suma(normal.inverso().productoEscalar(2.0 * this.punto(normal)));
  }

  public Vector2d rodear(int maxX, int maxY) {
    Vector2d vector2d = new Vector2d(this);

    if (this.x > maxX) {
      vector2d.x = 0.0;
    } else if (this.x < 0) {
      vector2d.x = maxX;
    }

    if (this.y > maxY) {
      vector2d.y = 0.0;
    } else if (this.y < 0) {
      vector2d.y = maxY;
    }

    return vector2d;
  }

  public Position toPosicion() {
    return new Position(x, y);
  }

  @Override
  public String toString() {
    return "Vector2d{" + "x=" + x + "y=" + y + '}';
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 83 * hash + (int) (Double.doubleToLongBits(this.x)
                              ^ (Double.doubleToLongBits(this.x) >>> 32));
    hash = 83 * hash + (int) (Double.doubleToLongBits(this.y)
                              ^ (Double.doubleToLongBits(this.y) >>> 32));
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Vector2d)) {
      return false;
    }
    Vector2d otro = (Vector2d) obj;
    if (Math.abs(this.x - otro.x) < 1E-12
        && Math.abs(this.y - otro.y) < 1E-12) {
      return true;
    }
    return false;
  }
}
