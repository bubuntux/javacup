package org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades;

import org.dsaw.javacup.model.util.Position;

/**
 * Clase que representa la zona de acci�n de un jugador.
 *
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public class Zona {

  private double xIzq;
  private double xDer;
  private double ySup;
  private double yInf;

  public Zona(double xIzq, double xDer, double ySup, double yInf) {
    this.xIzq = xIzq;
    this.xDer = xDer;
    this.ySup = ySup;
    this.yInf = yInf;
  }


  // Devuelve true si una posici�n se encuentra dentro de la zona
  public boolean enMiZona(Position pos) {

    return (xIzq <= pos.getX() && xDer >= pos.getX() &&
            ySup >= pos.getY() && yInf <= pos.getY());
  }

  public double getXDer() {
    return xDer;
  }

  public void setXDer(double xDer) {
    this.xDer = xDer;
  }

  public double getXIzq() {
    return xIzq;
  }

  public void setXIzq(double xIzq) {
    this.xIzq = xIzq;
  }

  public double getYInf() {
    return yInf;
  }

  public void setYInf(double yInf) {
    this.yInf = yInf;
  }

  public double getYSup() {
    return ySup;
  }

  public void setYSup(double ySup) {
    this.ySup = ySup;
  }

}
