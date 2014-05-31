package org.dsaw.javacup.tactics.jvc2013.CTeam.util;

import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.PosicionCT;

import java.util.ArrayList;
import java.util.List;

public class DistanciaMinima {

  private double mejorDistancia = 1000000.0;

  private List<Integer> mejorOrden;

  private List<IJugadorCT> ordenados;

  private List<PosicionCT> posiciones;

  public DistanciaMinima(List<IJugadorCT> jugadores, List<PosicionCT> posiciones) {
    List<PosicionCT> posJugadores = new ArrayList<>();
    for (IJugadorCT jug : jugadores) {
      posJugadores.add(jug.getActual());
    }

    this.posiciones = posiciones;

    List<Integer> orden = new ArrayList<>();
    analizarOrden(orden, posJugadores);

    ordenados = new ArrayList<>();
    if (mejorOrden != null) {
      for (int idx : mejorOrden) {
        ordenados.add(jugadores.get(idx));
      }
    }
  }

  public List<IJugadorCT> getOrdenados() {
    return ordenados;
  }

  private void analizarOrden(List<Integer> orden, List<PosicionCT> posJugador) {
    if (orden.size() == posJugador.size()) {
      double dist = 0.0;
      int pos = 0;
      for (int idx : orden) {
        PosicionCT p1 = posiciones.get(pos++);
        PosicionCT p2 = posJugador.get(idx);
        dist += p1.distancia(p2);
      }
      if (dist <= mejorDistancia) {
        mejorOrden = new ArrayList<>(orden);
        mejorDistancia = dist;
      }
      return;
    }

    for (int i = 0; i < posJugador.size(); i++) {
      if (!orden.contains(i)) {
        orden.add(i);
        analizarOrden(orden, posJugador);
        orden.remove((Integer) i);
      }
    }
  }
}
