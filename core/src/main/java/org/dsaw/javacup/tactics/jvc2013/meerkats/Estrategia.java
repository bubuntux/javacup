package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementación de la estrategia.
 */
public class Estrategia implements Tactic {

  // ATRIBUTOS =================================================================================//

  /**
   * Detalle de la táctica implementada.
   */
  private TacticaDetalleImpl detalle = new TacticaDetalleImpl();

  /**
   * Controlador de los jugadores de manera individual.
   */
  ControladorJugador controladorjugador = new ControladorJugador();

  /**
   * Lista de comandos.
   */
  private LinkedList<Command> comandos = new LinkedList<>();

  /**
   * Situación del partido en la iteración actual.
   */
  private GameSituations sp;

  /**
   * Cache de la situación del partido en la iteración anterior.
   */
  private SituacionPartidoCache spItAnterior = new SituacionPartidoCache();

  /**
   * Cuarto de la cancha en el que se encuentra el balón.
   */
  private int cuartoCancha;

  /**
   * Flag que indica si el equipo está atacando o defendiendo.
   */
  private boolean atacando;

  /**
   * Lista de rivalPlayers marcados.
   */
  private LinkedList<Integer> jugadoresMarcados = new LinkedList<>();

  /**
   * Alineación ofensiva para el primer cuarto de cancha.
   */
  private Alineacion primerCuartoAtacando = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-10, -35.5),
      new Position(0.0, -41.1),
      new Position(10, -35.5),
      new Position(24.3, -31),
      new Position(-24.3, -31),
      new Position(-16.3, -12.8),
      new Position(16.3, -11.8),
      new Position(0.0, 16.4),
      new Position(0.0, 0.0),
      new Position(0.0, -17.3)
  });

  /**
   * Alineación ofensiva para el segundo cuarto de cancha.
   */
  private Alineacion segundoCuartoAtacando = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-14, -26.8),
      new Position(0.0, -35.4),
      new Position(14, -26.8),
      new Position(25.2, -13.1),
      new Position(-25.2, -13.1),
      new Position(-13.5, 0.7),
      new Position(13.5, 0.7),
      new Position(0.0, 17.3),
      new Position(0.0, 0.7),
      new Position(0.0, -15)
  });

  /**
   * Alineación ofensiva para el tercer cuarto de cancha.
   */
  private Alineacion tercerCuartoAtacando = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-13, -25),
      new Position(0.0, -36.6),
      new Position(13, -25),
      new Position(28, 18.9),
      new Position(-28, 18.9),
      new Position(-16.6, 5),
      new Position(16.6, 5),
      new Position(0.0, 34.2),
      new Position(0.0, 16),
      new Position(0.0, -9.3)
  });

  /**
   * Alineación ofensiva para el cuarto cuarto de cancha.
   */
  private Alineacion cuartoCuartoAtacando = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-14.4, -18.3),
      new Position(0.0, -25.7),
      new Position(14.4, -18.3),
      new Position(25.7, 28.4),
      new Position(-25.7, 28.4),
      new Position(-16.2, 18.8),
      new Position(16.2, 18.8),
      new Position(0.0, 39.4),
      new Position(0.0, 18.8),
      new Position(0.0, -4.8)
  });

  /**
   * Alineación defensiva para el cuarto cuarto de cancha.
   */
  private Alineacion cuartoCuartoDefendiendo = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-14.1, -22.4),
      new Position(0.0, -26.8),
      new Position(14.1, -22.4),
      new Position(23.2, 0.6),
      new Position(-23.2, 0.6),
      new Position(-14, 7.6),
      new Position(14, 7.6),
      new Position(0.0, 33.0),
      new Position(0.0, 21.6),
      new Position(0.0, -9.7)
  });

  /**
   * Alineación defensiva para el tercer cuarto de cancha.
   */
  private Alineacion tercerCuartoDefendiendo = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-12.8, -25),
      new Position(0.0, -28.5),
      new Position(12.8, -25),
      new Position(20.2, -9.4),
      new Position(-20.2, -9.4),
      new Position(-16.6, 5),
      new Position(16.6, 4),
      new Position(0.0, 19),
      new Position(0.0, 0.7),
      new Position(0.0, -14.7)
  });

  /**
   * Alineación defensiva para el segundo cuarto de cancha.
   */
  private Alineacion segundoCuartoDefendiendo = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-12, -35.7),
      new Position(0.0, -39.4),
      new Position(12, -35.7),
      new Position(21.2, -25.8),
      new Position(-21.2, -25.8),
      new Position(-12.3, -13.2),
      new Position(12.3, -13.2),
      new Position(0.0, 9.5),
      new Position(0.0, -3.3),
      new Position(0.0, -22.6)
  });

  /**
   * Alineación defensiva para el primer cuarto de cancha.
   */
  private Alineacion primerCuartoDefendiendo = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-10.5, -38.9),
      new Position(0.0, -41.1),
      new Position(10.5, -38.9),
      new Position(21.1, -32.4),
      new Position(-21.1, -32.4),
      new Position(-12.9, -15),
      new Position(12.9, -15),
      new Position(0.0, 9.5),
      new Position(0.0, -5.7),
      new Position(0.0, -25.7)
  });

  /**
   * Alineación para sacar de mitad de cancha después de recibir un gol.
   */
  private Alineacion iniciaSacando = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-13.9, -31.1),
      new Position(0.0, -36.1),
      new Position(13.9, -31.1),
      new Position(26.1, -24.5),
      new Position(-26.1, -24.5),
      new Position(-14, -11.3),
      new Position(14, -11.3),
      new Position(4.5, -7.8),
      new Position(0.0, -0.1),
      new Position(0.0, -18.8)
  });

  /**
   * Alineación para esperar el saque de mitad de cancha después de hacer un gol.
   */
  private Alineacion iniciaRecibiendo = new Alineacion(new Position[]{
      new Position(0.0, -50.4),
      new Position(-10.7, -35.8),
      new Position(0.0, -40),
      new Position(10.7, -35.8),
      new Position(24.8, -28.3),
      new Position(-24.8, -28.3),
      new Position(-16.9, -15.2),
      new Position(16.9, -15.2),
      new Position(6.5, -6.6),
      new Position(-6.5, -6.6),
      new Position(0.0, -22.6)
  });

  // MÉTODOS ===================================================================================//

  @Override
  public TacticDetail getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return iniciaSacando.getPosiciones();
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return iniciaRecibiendo.getPosiciones();
  }

  @Override
  public List<Command> execute(GameSituations sp) {
    this.sp = sp;

    // Limpia la lista de comandos
    comandos.clear();

    // Asignaciones
    cuartoCancha = Utilitarios.obtenerCuartoCancha(sp.ballPosition());
    atacando = Utilitarios.isAtacando(sp);
    controladorjugador
        .update(sp, spItAnterior, comandos, cuartoCancha, jugadoresMarcados, atacando);

    // ACCIONES DE EQUIPO
    alinearEquipo();
    if (atacando) {
      atacar();
    } else {
      defender();
    }

    // ACCIONES INDIVIDUALES
    controladorjugador.comportamientoProyectados();
    controladorjugador.comportamientoPoseedoresBalon();
    controladorjugador.comportamientoReceptor();
    controladorjugador.comportamientoArquero();
    if (sp.isStarts()) {
      comandos = new LinkedList<>(comandos.subList(0, Utilitarios.NUMERO_JUGADORES));
      controladorjugador.update(comandos);
      controladorjugador.comportamientoSacador(Utilitarios.obtenerJugadorSaque(sp.ballPosition()));
    }

    comandos = controladorjugador.getComandos();

    spItAnterior.update(sp);

    return comandos;
  }

  // Acciones de corte grupal (por equipo) =====================================================//

  /**
   * Forma al equipo de acuerdo a la situación del partido.
   */
  private void alinearEquipo() {
    if (atacando) {
      if (cuartoCancha == Utilitarios.PRIMER_CUARTO_CAMPO) {
        comandos = primerCuartoAtacando.alinearEquipo(sp, comandos, atacando);
      } else if (cuartoCancha == Utilitarios.SEGUNDO_CUARTO_CAMPO) {
        comandos = segundoCuartoAtacando.alinearEquipo(sp, comandos, atacando);
      } else if (cuartoCancha == Utilitarios.TERCER_CUARTO_CAMPO) {
        comandos = tercerCuartoAtacando.alinearEquipo(sp, comandos, atacando);
      } else {
        comandos = cuartoCuartoAtacando.alinearEquipo(sp, comandos, atacando);
      }
    } else {
      if (cuartoCancha == Utilitarios.PRIMER_CUARTO_CAMPO) {
        comandos = primerCuartoDefendiendo.alinearEquipo(sp, comandos, atacando);
      } else if (cuartoCancha == Utilitarios.SEGUNDO_CUARTO_CAMPO) {
        comandos = segundoCuartoDefendiendo.alinearEquipo(sp, comandos, atacando);
      } else if (cuartoCancha == Utilitarios.TERCER_CUARTO_CAMPO) {
        comandos = tercerCuartoDefendiendo.alinearEquipo(sp, comandos, atacando);
      } else {
        comandos = cuartoCuartoDefendiendo.alinearEquipo(sp, comandos, atacando);
      }
    }
  }

  private void atacar() {
    if (!sp.isRivalStarts() && !sp.isStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde
        //se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        //Recorre la lista de jugadores que pueden recuperar
        for (int i = 1; i < recuperadores.length; i++) {
          //Ordena a los jugadores recuperadores que se ubique
          //en la posicion de recuperacion
          if (recuperadores[i] != 0) {
            comandos.add(new CommandMoveTo(recuperadores[i],
                                           new Position(posRecuperacion[0], posRecuperacion[1])));
          }
        }
      }
    }
  }

  private void defender() {
    jugadoresMarcados.clear();
    if (cuartoCancha == Utilitarios.PRIMER_CUARTO_CAMPO) {
      controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_LIBERO);
      if (sp.ballPosition().getX() >= 0) {
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_CENTRAL_D);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_CENTRAL_I);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_LATERAL_D);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_LATERAL_I);
      } else {
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_CENTRAL_I);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_CENTRAL_D);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_LATERAL_I);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_LATERAL_D);
      }
      controladorjugador.comportamientoMarcarPase(Alineacion.INDICE_CONTENCION);
    } else if (cuartoCancha == Utilitarios.SEGUNDO_CUARTO_CAMPO) {
      if (sp.ballPosition().getX() >= 0) {
        controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_VOLANTE_D);
        controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_VOLANTE_I);
      } else {
        controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_VOLANTE_I);
        controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_VOLANTE_D);
      }
      controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_CONTENCION);
    } else if (cuartoCancha == Utilitarios.TERCER_CUARTO_CAMPO) {
      controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_DELANTERO);
      controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_ARMADOR);
      if (sp.ballPosition().getX() >= 0) {
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_VOLANTE_D);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_VOLANTE_I);
      } else {
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_VOLANTE_I);
        controladorjugador.comportamientoMarcarHombre(Alineacion.INDICE_VOLANTE_D);
      }
    } else {
      controladorjugador.comportamientoDesplazarseHaciaBalon(Alineacion.INDICE_DELANTERO);
      controladorjugador.comportamientoMarcarPase(Alineacion.INDICE_ARMADOR);
    }

    if (!sp.isRivalStarts() && !sp.isStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde
        //se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        //Recorre la lista de jugadores que pueden recuperar
        for (int i = 1; i < recuperadores.length; i++) {
          //Ordena a los jugadores recuperadores que se ubique
          //en la posicion de recuperacion
          if (recuperadores[i] != 0) {
            comandos.add(new CommandMoveTo(recuperadores[i],
                                           new Position(posRecuperacion[0], posRecuperacion[1])));
          }
        }
      }
    }
  }

}
