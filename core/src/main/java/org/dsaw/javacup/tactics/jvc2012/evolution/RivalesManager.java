package org.dsaw.javacup.tactics.jvc2012.evolution;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

public class RivalesManager {

  public static Rival[] obtenerRivales(Position[] posicionRivales) {

    Rival[] rivales = new Rival[posicionRivales.length];

    for (int i = 0; i < posicionRivales.length; i++) {
      rivales[i] = new Rival(i);
      rivales[i].setPosicion(posicionRivales[i]);
    }
    return rivales;
  }

  public static void actualizarRivales(Rival[] rivales, GameSituations sp) {
    Position[] posicionRivales = sp.rivalPlayers();
    for (int i = 0; i < posicionRivales.length; i++) {
      rivales[i].setPosicion(posicionRivales[i]);
      rivales[i].setTieneElBalon(tengoBalon(i, sp.canKick()));
    }
  }

  private static boolean tengoBalon(int i, int[] jugadoresConBalon) {

    for (int j = 0; j < jugadoresConBalon.length; j++) {
      if (i == jugadoresConBalon[j]) {
        return true;
      }
    }
    return false;

  }
}
