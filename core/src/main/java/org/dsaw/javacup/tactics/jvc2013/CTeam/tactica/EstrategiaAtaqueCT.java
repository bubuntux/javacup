package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Disparo;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.DistanciaMinima;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Triangulation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.dsaw.javacup.model.util.Constants.centroArcoInf;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.PROPIO;

public class EstrategiaAtaqueCT implements IEstrategiaCT {

  private final GameSituations sp;

  private final ICTeam2011 tactica;

  private final Triangulation voronoiRivales;

  protected List<IJugadorCT> recuperacionActual;

  public EstrategiaAtaqueCT(GameSituations sp, ICTeam2011 tactica, Triangulation voronoiTodos,
                            Triangulation voronoiRivales, Triangulation voronoiMios) {
    this.sp = sp;
    this.tactica = tactica;
    this.voronoiRivales = voronoiRivales;
  }

  @Override
  public boolean ejecutarIteracion() {
    double[] posB = sp.getTrajectory(tactica.iteracionesRecuperacion());
    PosicionCT balon = new PosicionCT(posB[0], posB[1]);
    double aBalon = sp.ballPosition().angle(balon.getPos());

    int maxRecuperacion = 3;
    recuperacionActual = new ArrayList<>();
    boolean controlBalon = false;
    double p[] = new double[]{0.0, -0.25, 0.25};
    int idx = 0;
    List<IJugadorCT> aRecuperar = new ArrayList<>();
    for (IJugadorCT jug : tactica.previoJugadoresRecuperacion()) {
      if (tactica.jugadoresRecuperacion().contains(jug)) {
        aRecuperar.add(jug);
        if (aRecuperar.size() >= maxRecuperacion) {
          break;
        }
      }
    }

    for (IJugadorCT jug : tactica.jugadoresRecuperacion()) {
      if (jug.getIndice() == 0) {
        continue;
      }
      if (tactica.recuperando().size() > tactica.rivalesRecuperacion().size()) {
        break;
      }
      aRecuperar.add(jug);

      if (aRecuperar.size() >= maxRecuperacion) {
        break;
      }
    }

    for (IJugadorCT jug : aRecuperar) {
      controlBalon = controlBalon || jug.isControlBalon();
      tactica.recuperando().add(jug);
      jug.setSiguiente(balon.moverAngulo(aBalon, p[idx++ % p.length]));
      recuperacionActual.add(jug);
    }

    int[] excluir = new int[recuperacionActual.size() + 3];
    for (int i = 0; i < recuperacionActual.size(); i++) {
      excluir[i + 3] = recuperacionActual.get(i).getIndice();
    }
    excluir[0] = 0;
    excluir[1] = 1;
    excluir[2] = 2;

    // obtener Posiciones Posibles Pase
    int jugadoresRestantes = 8 - recuperacionActual.size();
    Map<IJugadorCT, PosicionCT> posPases = new LinkedHashMap<>();

    PosicionCT[] centros = voronoiRivales.centros().toArray(new PosicionCT[0]);
    int[] cercanos = balon.indicesMasCercanos(centros);
    int maxDifY = balon.getY() < 10 ? -5 : 0;
    for (int i = cercanos.length - 1; i >= 0; i--) {
      idx = cercanos[i];
      PosicionCT centro = centros[idx];
      Position c = centro.getPos();

      if (c.getY() < balon.getY() - maxDifY) {
        continue;
      }

      int indiceMioCercano = c.nearestIndex(sp.myPlayers(), excluir);
      double dMio = c.distance(sp.myPlayers()[indiceMioCercano]);
      int indiceRivalCercano = c.nearestIndex(sp.rivalPlayers());
      double dRival = c.distance(sp.rivalPlayers()[indiceRivalCercano]);

      if (dMio < dRival - Constants.VELOCIDAD_MAX) {
        IJugadorCT jug = tactica.getJugadores(PROPIO).get(indiceMioCercano);
        posPases.put(jug, centro);
        int[] exNew = new int[excluir.length + 1];
        System.arraycopy(excluir, 0, exNew, 1, excluir.length);
        exNew[0] = indiceMioCercano;

        jug.setSiguiente(centro);

        jugadoresRestantes--;
      }

      if (jugadoresRestantes <= 0) {
        break;
      }
    }
    List<PosicionCT> posPasesFinal = new ArrayList<>(posPases.values());
    for (IJugadorCT j : posPases.keySet()) {
      posPasesFinal.add(j.getActual());
    }

    if (controlBalon) {
      PosicionCT ultimo = null;
      for (PosicionCT pos : posPasesFinal) {
        Disparo d = null;
        int[] itRemate = sp.iterationsToKick();
        for (IJugadorCT jug : recuperacionActual) {
          d = jug.paseLibre(pos, itRemate[jug.getIndice()]);
          if (d == null) {
            break;
          } else {
            jug.disparar(pos, d);
          }
        }
        if (d != null) {
          ultimo = null;
          break;
        }
        ultimo = pos;
      }

      if (ultimo != null) {
        for (IJugadorCT jug : recuperacionActual) {
          jug.disparar(ultimo, new Disparo(45, 0.75));
        }
      }
    }

    // mantener un par de defensas
    List<PosicionCT> aDefender = new ArrayList<>();
    List<IJugadorCT> defensas = new ArrayList<>();
    int[] rivCercanos = centroArcoInf.nearestIndexes(sp.rivalPlayers());
    for (int i = 0; i < 2; i++) {
      Position r = sp.rivalPlayers()[rivCercanos[i]];
      double a = r.angle(centroArcoInf);
      IJugadorCT jugador = tactica.getJugadores(PROPIO).get(i + 1);
      if (!recuperacionActual.contains(jugador)) {
        aDefender.add((new PosicionCT(r).moverAngulo(a, 0.5)));
        defensas.add(jugador);
      }
    }

    DistanciaMinima dm = new DistanciaMinima(defensas, aDefender);
    defensas = dm.getOrdenados();
    for (int i = 0; i < defensas.size(); i++) {
      defensas.get(i).setSiguiente(aDefender.get(i));
    }

    return true;
  }

  protected PosicionCT posicionVoronoi(PosicionCT pos) {
    Map<PosicionCT, List<PosicionCT>> voronoi = voronoiRivales.voronoi();
    PosicionCT[] keys = voronoi.keySet().toArray(new PosicionCT[0]);
    int idx = pos.indiceMasCercano(keys);
    return keys[idx];
  }

}
