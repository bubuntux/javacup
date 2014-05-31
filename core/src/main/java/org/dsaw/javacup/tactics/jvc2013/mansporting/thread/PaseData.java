/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;

/**
 * @author MaN
 */
public class PaseData {

  private final Punto3D puntoDestino;
  private final TrayectoriaPunto trayectoriaPunto;
  private final JugadoresBalonData jugadoresMasCercanos;

  public PaseData(Punto3D puntoDestino, TrayectoriaPunto trayectoriaPunto,
                  JugadoresBalonData jugadoresBalonData) {
    this.puntoDestino = puntoDestino;
    this.trayectoriaPunto = trayectoriaPunto;
    this.jugadoresMasCercanos = jugadoresBalonData;
  }

  public Punto3D getPuntoDestino() {
    return puntoDestino;
  }

  public TrayectoriaPunto getTrayectoriaPunto() {
    return trayectoriaPunto;
  }

  public JugadoresBalonData getJugadoresMasCercanos() {
    return jugadoresMasCercanos;
  }
}
