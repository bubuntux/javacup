/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import org.dsaw.javacup.model.util.Position;

import java.util.Random;

/**
 * @author Usuario
 */
public class Region {

  private int id;
  private double arriba;
  private double izquierda;
  private double derecha;
  private double abajo;
  private double ancho;
  private double alto;
  private Position centro;
  private Random random;

  public Region(double arriba, double izquierda, double derecha, double abajo) {
    this.arriba = arriba;
    this.izquierda = izquierda;
    this.derecha = derecha;
    this.abajo = abajo;

    centro = new Position((izquierda + derecha) * 0.5,
                          (arriba + abajo) * 0.5);
    ancho = Math.abs(derecha - izquierda);
    alto = Math.abs(arriba - abajo);

    random = new Random(System.currentTimeMillis());
  }

  public Region(int id, double arriba, double izquierda, double derecha,
                double abajo) {
    this(arriba, izquierda, derecha, abajo);
    this.id = id;
  }

  public Region(double ancho, double alto, Position centro) {
    this.ancho = ancho;
    this.alto = alto;
    this.centro = centro;

    arriba = centro.getY() + alto / 2.0;
    izquierda = centro.getX() - ancho / 2.0;
    derecha = centro.getX() + ancho / 2.0;
    abajo = centro.getY() - alto / 2.0;

    random = new Random(System.currentTimeMillis());
  }

  public boolean isDentro(Position posicion, Tipo tipo) {
    switch (tipo) {
      case NORMAL:
        return (posicion.getX() > izquierda && posicion.getX() < derecha
                && posicion.getY() < arriba && posicion.getY() > abajo);
      case MITAD:
        double margenX = ancho * 0.25;
        double margenY = alto * 0.25;
        return posicion.getX() > (izquierda + margenX)
               && posicion.getX() < (derecha - margenX)
               && posicion.getY() < (arriba - margenY)
               && posicion.getY() > (abajo + margenY);
      default:
        return false;
    }
  }

  public Position getPosicionAleatoria() {
    return new Position(
        (derecha - izquierda) * random.nextDouble() + izquierda,
        (arriba - abajo) * random.nextDouble() + abajo);
  }

  public int getId() {
    return id;
  }

  public double getAbajo() {
    return abajo;
  }

  public double getArriba() {
    return arriba;
  }

  public double getDerecha() {
    return derecha;
  }

  public double getIzquierda() {
    return izquierda;
  }

  public double getAncho() {
    return ancho;
  }

  public double getAlto() {
    return alto;
  }

  public Position getCentro() {
    return centro;
  }

  public enum Tipo {

    MITAD,
    NORMAL,
  }
}
