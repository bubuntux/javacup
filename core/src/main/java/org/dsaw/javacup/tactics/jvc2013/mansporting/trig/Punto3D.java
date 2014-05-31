/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import java.io.Serializable;

/**
 * @author MaN
 */
public class Punto3D implements Serializable {

  private final double x;
  private final double y;
  private final double z;
  private final Punto puntoxy;
  private final Punto puntoxz;
  private final Punto puntozy;

  public Punto3D(double[] valores) {
    if (valores == null) {
      this.x = 0;
      this.y = 0;
      this.z = 0;
    } else {
      this.x = valores[0];
      this.y = valores[1];
      this.z = valores[2];
    }

    this.puntoxy = new Punto(x, y);
    this.puntoxz = new Punto(x, z);
    this.puntozy = new Punto(z, y);
  }

  public Punto3D(double x, double y, double z) {
    this(new double[]{x, y, z});
  }

  public double getZ() {
    return z;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }

  public Punto getPuntoXY() {
    return puntoxy;
  }

  public Punto getPuntoXZ() {
    return puntoxz;
  }

  public Punto getPuntoZY() {
    return puntozy;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Punto3D other = (Punto3D) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash =
        11 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x)
                                                              >>> 32));
    hash =
        11 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y)
                                                              >>> 32));
    hash =
        11 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z)
                                                              >>> 32));
    return hash;
  }

  @Override
  public String toString() {
    return String.format("Punto3D -> x:%s, y:%s, z:%s", x, y, z);
  }
}
