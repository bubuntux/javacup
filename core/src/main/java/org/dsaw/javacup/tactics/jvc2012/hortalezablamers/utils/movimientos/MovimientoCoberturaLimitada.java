package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.HBLogger;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class MovimientoCoberturaLimitada {

  protected HashMap<Integer, Integer> coberturas;
  protected boolean coberturasFijadas;
  protected HashMap<Integer, Double> sumatorioDistancias;
  protected final int ITERACIONES_PARA_COMPROBAR_COBERTURAS = 200;
  protected int iteracionesParaComprobarCoberturas;

  public MovimientoCoberturaLimitada() {
    coberturas = new HashMap<>();
    coberturasFijadas = false;
    sumatorioDistancias = new HashMap<>();
    iteracionesParaComprobarCoberturas = 0;
  }

  public List<Command> fijarCoberturas(GameSituations gs) {

    LinkedList<Command> comandos = new LinkedList<>();

    // obtengo los 3 rivales mas cercanos a la porteria
    Vector<IndiceYDistancia> distanciasRivalesAPorteria = new Vector<>();
    for (int i = 0; i < 11; i++) {
      distanciasRivalesAPorteria
          .add(new IndiceYDistancia(i, gs.rivalPlayers()[i].distance(Constants.centroArcoInf)));
    }

    Collections.sort(distanciasRivalesAPorteria);
    int indiceDelanteroCentroRival = distanciasRivalesAPorteria.get(0).getIndice();
    // cuando alcanzan un tercio de mi campo de juego ligo mis defensas a sus delanteros 1 a 1
    if (!coberturasFijadas && gs.rivalPlayers()[indiceDelanteroCentroRival].getY() < (
        -Constants.LARGO_CAMPO_JUEGO / 6)) {
      for (int i = 0; i < 4; i++) {
        coberturas.put(i + 1, distanciasRivalesAPorteria.get(i).getIndice());
      }

      coberturasFijadas = true;
      HBLogger.log("Coberturas fijadas.");
    }

    if (coberturasFijadas) {
      // muevo mis n jugadores mas retrasados para cubrir a esos n
      for (int i = 0; i < 4; i++) {
        Position posicionRival = gs.rivalPlayers()[coberturas.get(i + 1)];
        Position
            posicionCobertura =
            posicionRival.moveAngle(posicionRival.angle(Constants.centroArcoInf), 1);
        comandos.add(new CommandMoveTo(i + 1, posicionCobertura));
      }

      // acumulamos las distancias a mi porteria, hasta las iteraciones marcadas
      for (int i = 0; i < 11; i++) {
        Double sumatorioJugador = sumatorioDistancias.get(i);
        if (sumatorioJugador == null) {
          sumatorioDistancias.put(i, 0.);
          sumatorioJugador = 0.;
        }
        sumatorioDistancias
            .put(i, sumatorioJugador + gs.rivalPlayers()[i].distance(Constants.centroArcoInf));
      }
      iteracionesParaComprobarCoberturas++;
      if (iteracionesParaComprobarCoberturas % ITERACIONES_PARA_COMPROBAR_COBERTURAS == 0) {
        // obtengo los 3 rivales mas cercanos a la porteria
        Vector<IndiceYDistancia> distanciasMediasRivalesAPorteria = new Vector<>();
        for (int i = 0; i < 11; i++) {
          distanciasMediasRivalesAPorteria.add(new IndiceYDistancia(i, sumatorioDistancias.get(i)));
        }

        // cubro a los n jugadores con medias mas cercanas a mi porteria
        Collections.sort(distanciasMediasRivalesAPorteria);
        Vector<Integer> jugadoresPropiosUsados = new Vector<>();
        for (int i = 0; i < 4; i++) {
          Integer indiceCubierto = distanciasMediasRivalesAPorteria.get(i).getIndice();
          // compruebo que jugador mio esta mas cerca del rival
          Vector<IndiceYDistancia> distanciasRivalAPropios = new Vector<>();
          for (int j = 1; j < 11; j++) {
            if (!jugadoresPropiosUsados.contains(j)) {
              distanciasRivalAPropios.add(new IndiceYDistancia(j, gs.rivalPlayers()[indiceCubierto]
                  .distance(gs.myPlayers()[j])));
            }
          }

          // lo pongo a cubrir al rival
          Collections.sort(distanciasRivalAPropios);
          Integer indiceCubridor = distanciasRivalAPropios.firstElement().getIndice();
          coberturas.put(indiceCubridor, indiceCubierto);
          jugadoresPropiosUsados.add(indiceCubridor);
        }

        HBLogger.log("Coberturas comprobadas.");
      }
    }

    return comandos;
  }

  public HashMap<Integer, Integer> getCoberturas() {
    return coberturas;
  }

  public boolean isCoberturasFijadas() {
    return coberturasFijadas;
  }

}
