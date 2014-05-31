/**
 *
 */
package org.dsaw.javacup.tactics.jvc2012.team2012;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;

/**
 * Historial del balon en el partido
 *
 * @author willBender
 */
public class HistorialBalon {

  private ArrayList<PosicionBalon> posicionesBalon = new ArrayList<>();

  public void addPosicionBalonIteracionActual(GameSituations sp) {
    PosicionBalon pb = new PosicionBalon(sp.ballPosition(), sp.iteration(), sp.ballAltitude());
    posicionesBalon.add(pb);
  }

  /**
   * @return the posicionesBalon
   */
  public ArrayList<PosicionBalon> getPosicionesBalon() {
    return posicionesBalon;
  }

  public Boolean aMiArco() {
    if (numeroIteracionesBalon() > 1) {
      Position pFinal = getPosicionesBalon().get(numeroIteracionesBalon() - 1).getPosicion();
      Position pInicial = getPosicionesBalon().get(numeroIteracionesBalon() - 2).getPosicion();
      if (pFinal.getY() < pInicial.getY() || pFinal.getY() == pInicial.getY()) {
        return true;
      }
    }
    return false;
  }

  public Boolean aSuArco() {
    if (numeroIteracionesBalon() > 1) {
      Position pFinal = getPosicionesBalon().get(numeroIteracionesBalon() - 1).getPosicion();
      Position pInicial = getPosicionesBalon().get(numeroIteracionesBalon() - 2).getPosicion();
      if (pFinal.getY() > pInicial.getY()) {
        return true;
      }
    }
    return false;
  }

  public int numeroIteracionesBalon() {
    return getPosicionesBalon().size();
  }

  public ArrayList<PosicionBalon> predecirBalon(GameSituations sp, int numIteraciones) {
    ArrayList<PosicionBalon> result = new ArrayList<>();
    int itActual = sp.iteration();
    for (int i = 1; i <= numIteraciones; i++) {
      double[] posBalon = sp.getTrajectory(itActual + i);
      Position p = new Position(posBalon[0], posBalon[1]);
      PosicionBalon pb = new PosicionBalon(p, itActual + i, posBalon[2]);
      result.add(pb);
    }
    return result;
  }

}