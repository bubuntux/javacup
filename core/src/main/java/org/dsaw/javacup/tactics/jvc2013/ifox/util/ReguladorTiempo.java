/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import java.util.Random;

/**
 * @author Usuario
 */
public class ReguladorTiempo {

  private static final double VARIACION = 10.0;
  private double tiempoActualizacion;
  private double tiempoSigActualizacion;
  private Random random;

  public ReguladorTiempo(double actualizacionesPorSegundo) {
    random = new Random(System.currentTimeMillis());
    tiempoSigActualizacion = System.currentTimeMillis()
                             + 1000 * random.nextDouble();

    if (actualizacionesPorSegundo > 0) {
      tiempoActualizacion = 1000.0 / actualizacionesPorSegundo;
    } else if (actualizacionesPorSegundo < 1E-12
               && actualizacionesPorSegundo > -1E-12) {
      tiempoActualizacion = 0.0;
    } else if (actualizacionesPorSegundo < 0) {
      tiempoActualizacion = -1.0;
    }
  }

  public boolean isListo() {
    if (tiempoActualizacion < 1E-12 && tiempoActualizacion > -1E-12) {
      return true;
    }
    if (tiempoActualizacion < 0) {
      return false;
    }

    double tiempoActual = System.currentTimeMillis();
    if (tiempoActual >= tiempoSigActualizacion) {
      tiempoSigActualizacion = tiempoActual + tiempoActualizacion
                               + VARIACION * (random.nextDouble() * 2.0 - 1.0);
      return true;
    }

    return false;
  }

}
