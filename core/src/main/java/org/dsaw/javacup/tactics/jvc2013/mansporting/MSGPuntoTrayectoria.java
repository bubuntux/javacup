/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;

/**
 * @author MaN
 */
public interface MSGPuntoTrayectoria {

  Punto3D getPunto();

  public static class Punto3DTrayectoria implements MSGPuntoTrayectoria {

    private final Punto3D punto;

    public Punto3DTrayectoria(Punto3D punto) {
      this.punto = punto;
    }

    @Override
    public Punto3D getPunto() {
      return punto;
    }
  }

  public static class Punto3DErrorTrayectoria extends Punto3DTrayectoria {

    private final Punto3D puntoMax;
    private final Punto3D puntoMin;

    public Punto3DErrorTrayectoria(Punto3D punto, Punto3D puntoMax, Punto3D puntoMin) {
      super(punto);
      this.puntoMax = puntoMax;
      this.puntoMin = puntoMin;
    }

    public Punto3D getPuntoMax() {
      return puntoMax;
    }

    public Punto3D getPuntoMin() {
      return puntoMin;
    }
  }
}
