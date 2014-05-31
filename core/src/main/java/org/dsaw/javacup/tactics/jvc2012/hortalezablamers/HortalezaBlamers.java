package org.dsaw.javacup.tactics.jvc2012.hortalezablamers;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.despejes.DespejeSimulado;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos.MovimientoABalon;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos.MovimientoBascular;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos.MovimientoCoberturaLimitada;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos.MovimientoDesmarque;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.pases.PaseAlHuecoSimulado;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.remates.RemateAPuertaSimulado;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.saques.SaquesABalonParado;

import java.util.LinkedList;
import java.util.List;

public class HortalezaBlamers implements Tactic {

  // variables de detalle de jugadores y de disposicion de jugadores
  private TacticDetail detalle = new HortalezaBlamersDetalle();
  private Position alineacionSaca[] = Alineacion.alineacionSaca;
  private Position alineacionRecibe[] = Alineacion.alineacionRecibe;

  // variables
  private LinkedList<Command> comandos = new LinkedList<>();
  private int indicePorteroPropio;
  private int indicePorteroRival = -1;
  private MovimientoCoberturaLimitada movimientoCobertura;
  private MovimientoABalon movimientoABalon;

  /**
   * Constructor que inicializa el indice del portero propio, para tenerlo identificado
   */
  public HortalezaBlamers() {
    super();

    // obtenemos el indice del portero propio
    for (int i = 0; i < detalle.getPlayers().length; i++) {
      if (detalle.getPlayers()[i].isGoalKeeper()) {
        indicePorteroPropio = i;
      }
    }

    movimientoCobertura = new MovimientoCoberturaLimitada();
    movimientoABalon = new MovimientoABalon();
  }

  /**
   * Metodos para el framework
   */
  @Override
  public Position[] getStartPositions(GameSituations gs) {
    return alineacionSaca;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations gs) {
    return alineacionRecibe;
  }

  @Override
  public TacticDetail getDetail() {
    return detalle;
  }

  /**
   * Metodo de entrada para cada iteracion
   */
  @Override
  public List<Command> execute(GameSituations gs) {
    try {
      comandos.clear();

      // obtenemos el indice del portero rival solo la primera vez
      if (indicePorteroRival == -1) {
        for (int i = 0; i < gs.rivalPlayersDetail().length; i++) {
          if (gs.rivalPlayersDetail()[i].isGoalKeeper()) {
            indicePorteroRival = i;
            break;
          }
        }
      }

      // movimiento de los jugadores
      comandos
          .addAll(MovimientoBascular.movimientoBascular(gs.ballPosition(), indicePorteroPropio));
      comandos.addAll(MovimientoDesmarque.mover(gs));

      // movimientos para cubrir a los jugadores mas adelantados del equipo rival
      comandos.addAll(movimientoCobertura.fijarCoberturas(gs));

      // movimiento del jugador que recogera el balon
      comandos.addAll(movimientoABalon.correrHaciaBalon(gs));

      // pases y tiros
      comandos.addAll(rematarBalon(gs));

      return comandos;
    } catch (Exception e) {
      e.printStackTrace();
      comandos.clear();
      return comandos;
    }
  }

  /**
   * Comportamiento de los pases y tiros. Primero los remates a balon parado, y despues los remates
   * en juego, si no toca sacar
   */
  private List<Command> rematarBalon(GameSituations gs) {

    if (gs.isStarts()) {
      return SaquesABalonParado.sacarABalonParado(gs);
    } else {
      return rematarBalonEnJuego(gs);
    }
  }

  /**
   * Realiza el golpeo del balon cuando este esta en juego segun lo siguiente: Si es el portero
   * despeja al centro del campo con 45� para evitar a los delanteros Si no, si tiene un pase para
   * hacer, lo hace Si no, si tiene un tiro para hacer, lo hace Si no, despeja al campo contrario
   */
  private List<Command> rematarBalonEnJuego(GameSituations gs) {
    LinkedList<Command> comandos = new LinkedList<>();

    int rematan[] = gs.canKick();

    // comprobamos si el portero esta entre los que pueden rematar, para incluir esa accion siempre
    // el portero despeja con 45� para evitar a los delanteros
    for (int indiceRematador : rematan) {
      if (indiceRematador == indicePorteroPropio) {
        comandos.add(DespejeSimulado.despejar(gs, indiceRematador));
      }
    }

    if (rematan.length > 0 && rematan[0] != indicePorteroPropio) {
      // si es el jugador mas cercano a la porteria rival, corre hacia la porteria rival
      List<Command> comandosSolo = soloDelanteDelPortero(gs, rematan[0]);
      if (comandosSolo != null) {
        return comandosSolo;
      }

      List<Command> comandosPase = PaseAlHuecoSimulado.pasar(gs, rematan[0]);
      CommandHitBall comandoRemate = RemateAPuertaSimulado.rematarAPuerta(gs, rematan[0]);

      // si tiene tiro, tira
      if (comandoRemate != null) {
        comandos.add(comandoRemate);
        HBLogger.log("TIRO!!");
      }
      // si puede pasar, pasa
      else if (comandosPase != null) {
        comandos.addAll(comandosPase);
      }
      // si no, despeja
      else {
//				CommandHitBall chb = new CommandHitBall(rematan[0], new Position(0, Constants.ANCHO_AREA_GRANDE * 1.20), 1, Constants.ANGULO_VERTICAL_MAX);
//				comandos.add(chb);
        comandos.add(DespejeSimulado.despejar(gs, rematan[0]));
        HBLogger.log("DESPEJE!!");
      }
    }
    return comandos;
  }

  private List<Command> soloDelanteDelPortero(GameSituations gs, int indiceRematador) {
    if (gs.ballPosition().distance(Constants.centroArcoSup) < HBConstants.DISTANCIA_LIMITE_TIRO) {
      return null;
    }

    double distanciaMiJugador = gs.myPlayers()[indiceRematador].distance(Constants.centroArcoSup);

    for (int i = 0; i < 11; i++) {
      if (i == indicePorteroRival) {
        continue;
      }

      Position p = gs.rivalPlayers()[i];
      if (p.distance(Constants.centroArcoSup) < distanciaMiJugador) {
        return null;
      }
    }

    // en este punto ya se que mi jugador es el m�s cercano a la porteria rival
    LinkedList<Command> comandos = new LinkedList<>();
//		comandos.add(new CommandMoveTo(indiceRematador, Constants.centroArcoSup));
//		comandos.add(new CommandHitBall(indiceRematador));
    // parece que el autopase no funciona bien en el framework (o no se usarlo)
    // asi que lo hago a mano
    // TODO: mejorar calculando una potencia mejor
    double angulo = Math.toDegrees(gs.myPlayers()[indiceRematador].angle(Constants.centroArcoSup));
    comandos.add(new CommandHitBall(indiceRematador, angulo, 0.4, false));
    comandos.add(new CommandMoveTo(indiceRematador, Constants.centroArcoSup));
    return comandos;
  }
}
