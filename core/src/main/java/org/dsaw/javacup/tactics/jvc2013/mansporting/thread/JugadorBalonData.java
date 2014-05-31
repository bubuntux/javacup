/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGJugadorDetalle;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;

/**
 * @author MaN
 */
public class JugadorBalonData implements Comparable<JugadorBalonData> {

  private final MSGJugadorDetalle jugador;
  private final int iteraciones;
  private final Punto3D punto;
  private final boolean fueraCampo;

  public JugadorBalonData(MSGJugadorDetalle jugador, boolean fueraCampo, int iteraciones,
                          Punto3D punto) {
    if (iteraciones < 0) {
      throw new IllegalArgumentException("iteraciones no puede ser <null>");
    }

    this.iteraciones = iteraciones;
    this.punto = punto;
    this.jugador = jugador;
    this.fueraCampo = fueraCampo;
  }

  public int getIteraciones() {
    return iteraciones;
  }

  public Punto3D getPunto() {
    return punto;
  }

  public boolean isFueraCampo() {
    return fueraCampo;
  }


  public MSGJugadorDetalle getJugador() {
    return jugador;
  }

  @Override
  public int compareTo(JugadorBalonData o) {
    return iteraciones - o.iteraciones;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final JugadorBalonData other = (JugadorBalonData) obj;
    if (this.jugador != other.jugador && (this.jugador == null || !this.jugador
        .equals(other.jugador))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + (this.jugador != null ? this.jugador.hashCode() : 0);
    return hash;
  }
}
