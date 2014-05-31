/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.util.List;

/**
 * @author MaN
 */
public abstract class MSGTrayectoria<E extends MSGPuntoTrayectoria> {

  public abstract E getPosicion(int interacion);

  public abstract int getIteracionMax();

  public static SituacionPartidoTrayectoria getInstance(GameSituations sp) {
    return new SituacionPartidoTrayectoria(sp);
  }

  public static DisparoTrayectoria getInstance(TrayectoriaPunto candidato,
                                               MSGJugadorDetalle jugadorPasador,
                                               Punto3D balonEnPase, Punto3D puntoDestino) {
    return new DisparoTrayectoria(candidato, jugadorPasador, balonEnPase, puntoDestino);
  }

  public static class SituacionPartidoTrayectoria
      extends MSGTrayectoria<MSGPuntoTrayectoria.Punto3DTrayectoria> {

    private final GameSituations sp;

    public SituacionPartidoTrayectoria(GameSituations sp) {
      this.sp = sp;
    }

    @Override
    public MSGPuntoTrayectoria.Punto3DTrayectoria getPosicion(int interacion) {
      return new MSGPuntoTrayectoria.Punto3DTrayectoria(new Punto3D(sp.getTrajectory(interacion)));
    }

    @Override
    public int getIteracionMax() {
      return Integer.MAX_VALUE;
    }
  }

  public static class DisparoTrayectoria
      extends MSGTrayectoria<MSGPuntoTrayectoria.Punto3DErrorTrayectoria> {

    private final TrayectoriaPunto candidato;
    private final Punto3D balon;
    private final double anguloHorizontal;
    private final double anguloHorizontalMaximo;
    private final double anguloHorizontalMinimo;

    public DisparoTrayectoria(TrayectoriaPunto candidato, MSGJugadorDetalle jugadorPasador,
                              Punto3D balonEnPase, Punto3D puntoDestino) {
      this.candidato = candidato;
      this.balon = balonEnPase;
      this.anguloHorizontal = TrigonometriaUtils
          .getAngulo(balon.getPuntoXY(), puntoDestino.getPuntoXY());
      double e = Constants.getErrorAngular(jugadorPasador.getEstadisticas().getPrecision());
      this.anguloHorizontalMaximo = this.anguloHorizontal + (e / 2) * Math.PI;
      this.anguloHorizontalMinimo = this.anguloHorizontal - (e / 2) * Math.PI;
    }

    @Override
    public MSGPuntoTrayectoria.Punto3DErrorTrayectoria getPosicion(int interacion) {
      return new MSGPuntoTrayectoria.Punto3DErrorTrayectoria(
          getPosicion(interacion, this.anguloHorizontal),
          getPosicion(interacion, this.anguloHorizontalMinimo),
          getPosicion(interacion, this.anguloHorizontalMaximo));
    }

    private Punto3D getPosicion(int iteracion, double anguloHorizontal) {
      List<TrayectoriaPunto> desplazamientos = candidato.getTrayectoria().getPuntos();
      if (iteracion >= desplazamientos.size()) {
        iteracion = desplazamientos.size() - 1;
      }

      TrayectoriaPunto desp = desplazamientos.get(iteracion);

      double dx = desp.getDesplazamientoH() * Math.cos(anguloHorizontal);
      double dy = desp.getDesplazamientoH() * Math.sin(anguloHorizontal);

      return new Punto3D(
          balon.getX() + dx,
          balon.getY() + dy,
          balon.getZ() + desp.getDesplazamientoV());
    }

    @Override
    public int getIteracionMax() {
      return candidato.getTrayectoria().getPuntos().size() - 1;
    }
  }
}
