/**
 *
 */
package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.enums.CUADRANTE_CANCHA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorTiros {

  private static ControladorTiros instance;

  private CommandHitBall tiro;

  static {
    instance = new ControladorTiros();
  }

  private ControladorTiros() {

  }

  public static ControladorTiros getInstance() {
    return instance;
  }

  public Command getTiro(final int jugador, final GameSituations situacionPartido) {
    // TODO : Implementar esta validacion
    // if (ControladorUtils.getInstance().esPosiblePasarBalon(rivalPlayers, jugadorConBalon, jugadorDestino)) {

    Position destinoBola = null;
    List<Integer> todosLosJugadoresAPase = new ArrayList<>();
    List<Integer> jugadoresMediosTracerosPase = new ArrayList<>();
    List<Integer> jugadoresMediosDelanterosPase = new ArrayList<>();
    List<Integer> jugadoresDelanterosPase = new ArrayList<>();
    Random random = new Random();
    double fuerzaRemateDelanteros = 1;
    double ejeXDestino = ControladorUtils.getInstance().generaNumeroDobleAleatorioEntre(0, 20);
    double fuerzaRemateDefensas = 1;
    boolean tiraLadoDerecho = ControladorUtils.getInstance().generaBoleanoAleatorioEntre();

    switch (CUADRANTE_CANCHA.valueOf(
        ControladorEstadoPartido.getInstance().getJugadoresConCuadrante().get(jugador))) {
      case CUADRANTE_1_SUP:
      case CUADRANTE_4_SUP:
        this.getTiroDefensas(jugador, situacionPartido, destinoBola,
                             todosLosJugadoresAPase, jugadoresMediosTracerosPase,
                             jugadoresMediosDelanterosPase, jugadoresDelanterosPase, random,
                             fuerzaRemateDelanteros, ejeXDestino, fuerzaRemateDefensas,
                             tiraLadoDerecho);
        break;

      case CUADRANTE_2_SUP:
      case CUADRANTE_3_SUP:
        this.getTiroMediosTraseros(jugador, situacionPartido, destinoBola,
                                   todosLosJugadoresAPase,
                                   jugadoresMediosDelanterosPase, jugadoresDelanterosPase,
                                   random, fuerzaRemateDelanteros, ejeXDestino,
                                   fuerzaRemateDefensas,
                                   tiraLadoDerecho);
        break;

      case CUADRANTE_2_INF:
      case CUADRANTE_3_INF:
        this.getTiroMediosDelanteros(jugador, situacionPartido, destinoBola,
                                     todosLosJugadoresAPase,
                                     jugadoresMediosDelanterosPase, jugadoresDelanterosPase,
                                     random, fuerzaRemateDelanteros, ejeXDestino,
                                     fuerzaRemateDefensas,
                                     tiraLadoDerecho);
        break;

      case CUADRANTE_1_INF:
      case CUADRANTE_4_INF:
        this.getTiroDelanteros(jugador, situacionPartido, destinoBola,
                               todosLosJugadoresAPase,
                               jugadoresMediosDelanterosPase, jugadoresDelanterosPase,
                               random, fuerzaRemateDelanteros, ejeXDestino, fuerzaRemateDefensas,
                               tiraLadoDerecho);
        break;
    }

    return this.tiro;
  }


  private void getTiroDefensas(final int jugador,
                               final GameSituations situacionPartido, Position destinoBola,
                               List<Integer> todosLosJugadoresAPase,
                               List<Integer> jugadoresMediosTracerosPase,
                               List<Integer> jugadoresMediosDelanterosPase,
                               List<Integer> jugadoresDelanterosPase, Random random,
                               double fuerzaRemateDelanteros, double ejeXDestino,
                               double fuerzaRemateDefensas, boolean tiraLadoDerecho) {
    List<Integer> jugadoresPorCuadranteTmp;
    // Obtener los jugadores probables a darles pase
    for (CUADRANTE_CANCHA cuadranteCancha : CUADRANTE_CANCHA.values()) {
      if (CUADRANTE_CANCHA.CUADRANTE_2_SUP.equals(cuadranteCancha)
          || CUADRANTE_CANCHA.CUADRANTE_3_SUP.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresMediosTracerosPase.addAll(jugadoresPorCuadranteTmp);
        }
      } else if (CUADRANTE_CANCHA.CUADRANTE_2_INF.equals(cuadranteCancha)
                 || CUADRANTE_CANCHA.CUADRANTE_3_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresMediosDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      } else if (CUADRANTE_CANCHA.CUADRANTE_1_INF.equals(cuadranteCancha)
                 || CUADRANTE_CANCHA.CUADRANTE_4_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      }
    }

    // Analizar el pase
    if (todosLosJugadoresAPase.size() > 0) {

      boolean existeJugadorParaRematar = Boolean.FALSE;
      int jugadorIntentaReciber = 0;

      // Verifica si existe algun jugador para dar un pase
      while (!todosLosJugadoresAPase.isEmpty()) {
        int jugadorAleatorio = random.nextInt(todosLosJugadoresAPase.size());
        jugadorIntentaReciber = todosLosJugadoresAPase.get(jugadorAleatorio);
        if (ControladorEstadoPartido.getInstance().getJugadoresPuedenRematar()
            .contains(jugadorIntentaReciber)) {
          existeJugadorParaRematar = Boolean.TRUE;
          break;
        }
        todosLosJugadoresAPase.remove(jugadorAleatorio);
      }

      if (existeJugadorParaRematar) {
        if (jugadoresMediosTracerosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros, random.nextInt(40));
        } else if (jugadoresMediosDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros, random.nextInt(42));
        } else if (jugadoresDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro = new CommandHitBall(jugador, destinoBola, 1, 40);
        } else {
          destinoBola = Constants.centroArcoSup;
          if (tiraLadoDerecho) {
            destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
          } else {
            destinoBola.setPosition(ejeXDestino, destinoBola.getY());
          }
          this.tiro = new CommandHitBall(jugador, destinoBola, 1, 45);
        }
      } else {
        destinoBola = Constants.centroArcoSup;
        if (tiraLadoDerecho) {
          destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
        } else {
          destinoBola.setPosition(ejeXDestino, destinoBola.getY());
        }
        this.tiro = new CommandHitBall(jugador, destinoBola, 1, 40);
      }

    } else {
      destinoBola = Constants.centroArcoSup;
      if (tiraLadoDerecho) {
        destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
      } else {
        destinoBola.setPosition(ejeXDestino, destinoBola.getY());
      }
      this.tiro = new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, 40);
    }
  }


  private void getTiroMediosTraseros(final int jugador,
                                     final GameSituations situacionPartido, Position destinoBola,
                                     List<Integer> todosLosJugadoresAPase,
                                     List<Integer> jugadoresMediosDelanterosPase,
                                     List<Integer> jugadoresDelanterosPase, Random random,
                                     double fuerzaRemateDelanteros, double ejeXDestino,
                                     double fuerzaRemateDefensas, boolean tiraLadoDerecho) {
    List<Integer> jugadoresPorCuadranteTmp;
    // Obtener los jugadores probables a darles pase
    for (CUADRANTE_CANCHA cuadranteCancha : CUADRANTE_CANCHA.values()) {
      if (CUADRANTE_CANCHA.CUADRANTE_2_INF.equals(cuadranteCancha)
          || CUADRANTE_CANCHA.CUADRANTE_3_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresMediosDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      } else if (CUADRANTE_CANCHA.CUADRANTE_1_INF.equals(cuadranteCancha)
                 || CUADRANTE_CANCHA.CUADRANTE_4_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      }
    }

    // Analizar el pase
    if (todosLosJugadoresAPase.size() > 0) {

      boolean existeJugadorParaRematar = Boolean.FALSE;
      int jugadorIntentaReciber = 0;

      // Verifica si existe algun jugador para dar un pase
      while (!todosLosJugadoresAPase.isEmpty()) {
        int jugadorAleatorio = random.nextInt(todosLosJugadoresAPase.size());
        jugadorIntentaReciber = todosLosJugadoresAPase.get(jugadorAleatorio);
        if (ControladorEstadoPartido.getInstance().getJugadoresPuedenRematar()
            .contains(jugadorIntentaReciber)) {
          existeJugadorParaRematar = Boolean.TRUE;
          break;
        }
        todosLosJugadoresAPase.remove(jugadorAleatorio);
      }

      if (existeJugadorParaRematar) {
        if (jugadoresMediosDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas,
                                 ControladorUtils.getInstance()
                                     .generaNumeroDobleAleatorioEntre(15, 40));
        } else if (jugadoresDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                 ControladorUtils.getInstance()
                                     .generaNumeroDobleAleatorioEntre(15, 40));
        } else {
          destinoBola = Constants.centroArcoSup;
          if (tiraLadoDerecho) {
            destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
          } else {
            destinoBola.setPosition(ejeXDestino, destinoBola.getY());
          }
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                 ControladorUtils.getInstance()
                                     .generaNumeroDobleAleatorioEntre(15, 40));
        }
      } else {
        destinoBola = Constants.centroArcoSup;
        if (tiraLadoDerecho) {
          destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
        } else {
          destinoBola.setPosition(ejeXDestino, destinoBola.getY());
        }
        this.tiro =
            new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                               ControladorUtils.getInstance()
                                   .generaNumeroDobleAleatorioEntre(15, 40));
      }

    } else {
      destinoBola = Constants.centroArcoSup;
      if (tiraLadoDerecho) {
        destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
      } else {
        destinoBola.setPosition(ejeXDestino, destinoBola.getY());
      }
      this.tiro =
          new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas,
                             ControladorUtils.getInstance()
                                 .generaNumeroDobleAleatorioEntre(15, 40));
    }
  }


  private void getTiroMediosDelanteros(final int jugador,
                                       final GameSituations situacionPartido, Position destinoBola,
                                       List<Integer> todosLosJugadoresAPase,
                                       List<Integer> jugadoresMediosDelanterosPase,
                                       List<Integer> jugadoresDelanterosPase, Random random,
                                       double fuerzaRemateDelanteros, double ejeXDestino,
                                       double fuerzaRemateDefensas, boolean tiraLadoDerecho) {
    List<Integer> jugadoresPorCuadranteTmp;
    // Obtener los jugadores probables a darles pase
    for (CUADRANTE_CANCHA cuadranteCancha : CUADRANTE_CANCHA.values()) {
      if (CUADRANTE_CANCHA.CUADRANTE_2_INF.equals(cuadranteCancha)
          || CUADRANTE_CANCHA.CUADRANTE_3_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          for (Integer jugadorMedioDelantero : jugadoresPorCuadranteTmp) {
            if (jugador != jugadorMedioDelantero) {
              jugadoresMediosDelanterosPase.add(jugadorMedioDelantero);
            }
          }
        }
      } else if (CUADRANTE_CANCHA.CUADRANTE_1_INF.equals(cuadranteCancha)
                 || CUADRANTE_CANCHA.CUADRANTE_4_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      }
    }

    // Analizar el pase
    if (todosLosJugadoresAPase.size() > 0) {

      boolean existeJugadorParaRematar = Boolean.FALSE;
      int jugadorIntentaReciber = 0;

      // Verifica si existe algun jugador para dar un pase
      while (!todosLosJugadoresAPase.isEmpty()) {
        int jugadorAleatorio = random.nextInt(todosLosJugadoresAPase.size());
        jugadorIntentaReciber = todosLosJugadoresAPase.get(jugadorAleatorio);
        if (ControladorEstadoPartido.getInstance().getJugadoresPuedenRematar()
            .contains(jugadorIntentaReciber)) {
          existeJugadorParaRematar = Boolean.TRUE;
          break;
        }
        todosLosJugadoresAPase.remove(jugadorAleatorio);
      }

      if (existeJugadorParaRematar) {
        if (jugadoresMediosDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(20));
        } else if (jugadoresDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          destinoBola.setPosition(destinoBola.getX(), destinoBola.getY() + 10);
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(20));
        } else {
          destinoBola = Constants.centroArcoSup;
          if (tiraLadoDerecho) {
            destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
          } else {
            destinoBola.setPosition(ejeXDestino, destinoBola.getY());
          }
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(35));
        }
      } else {
        destinoBola = Constants.centroArcoSup;
        if (tiraLadoDerecho) {
          destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
        } else {
          destinoBola.setPosition(ejeXDestino, destinoBola.getY());
        }
        this.tiro =
            new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(37));
      }

    } else {
      destinoBola = Constants.centroArcoSup;
      if (tiraLadoDerecho) {
        destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
      } else {
        destinoBola.setPosition(ejeXDestino, destinoBola.getY());
      }
      this.tiro =
          new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(57));
    }
  }


  private void getTiroDelanteros(final int jugador,
                                 final GameSituations situacionPartido, Position destinoBola,
                                 List<Integer> todosLosJugadoresAPase,
                                 List<Integer> jugadoresMediosDelanterosPase,
                                 List<Integer> jugadoresDelanterosPase, Random random,
                                 double fuerzaRemateDelanteros, double ejeXDestino,
                                 double fuerzaRemateDefensas, boolean tiraLadoDerecho) {
    List<Integer> jugadoresPorCuadranteTmp;
    // Obtener los jugadores probables a darles pase
    for (CUADRANTE_CANCHA cuadranteCancha : CUADRANTE_CANCHA.values()) {
      if (CUADRANTE_CANCHA.CUADRANTE_2_INF.equals(cuadranteCancha)
          || CUADRANTE_CANCHA.CUADRANTE_3_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          jugadoresMediosDelanterosPase.addAll(jugadoresPorCuadranteTmp);
        }
      } else if (CUADRANTE_CANCHA.CUADRANTE_1_INF.equals(cuadranteCancha)
                 || CUADRANTE_CANCHA.CUADRANTE_4_INF.equals(cuadranteCancha)) {
        jugadoresPorCuadranteTmp =
            ControladorEstadoPartido.getInstance().getJugadoresPorCuadrantes()
                .get(cuadranteCancha.name());
        if (jugadoresPorCuadranteTmp != null) {
          todosLosJugadoresAPase.addAll(jugadoresPorCuadranteTmp);
          for (Integer jugadorMedioDelantero : jugadoresPorCuadranteTmp) {
            if (jugador != jugadorMedioDelantero) {
              jugadoresMediosDelanterosPase.add(jugadorMedioDelantero);
            }
          }
        }
      }
    }

    // Analizar el pase
    if (todosLosJugadoresAPase.size() > 0) {

      boolean existeJugadorParaRematar = Boolean.FALSE;
      int jugadorIntentaReciber = 0;

      // Verifica si existe algun jugador para dar un pase
      while (!todosLosJugadoresAPase.isEmpty()) {
        int jugadorAleatorio = random.nextInt(todosLosJugadoresAPase.size());
        jugadorIntentaReciber = todosLosJugadoresAPase.get(jugadorAleatorio);
        if (ControladorEstadoPartido.getInstance().getJugadoresPuedenRematar()
            .contains(jugadorIntentaReciber)) {
          existeJugadorParaRematar = Boolean.TRUE;
          break;
        }
        todosLosJugadoresAPase.remove(jugadorAleatorio);
      }

      if (existeJugadorParaRematar) {
        if (jugadoresMediosDelanterosPase.contains(jugadorIntentaReciber)) {
          destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas, random.nextInt(60));
        } else {

          // estoy marcado, da pase
          if (ControladorEstadoPartido.getInstance().getJugadoresMarcados().containsKey(jugador) &&
              ControladorEstadoPartido.getInstance().getJugadoresMarcados().get(jugador).size()
              > 0) {
            // si estoy en el area grande, tira
            if (ControladorEstadoPartido.getInstance().isEstaBalonEnElAreaGrandeInf()) {
              destinoBola = Constants.centroArcoSup;
              ejeXDestino =
                  ControladorUtils.getInstance()
                      .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

              int anguloTiro = 13;
//							double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//									new Recta(new Posicion(x, y), new Posicion(x, y)));

              if (tiraLadoDerecho) {
                destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
              } else {
                destinoBola.setPosition(ejeXDestino, destinoBola.getY());
              }
              this.tiro =
                  new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                     random.nextInt(anguloTiro));
            } else { // da pase
              destinoBola = situacionPartido.myPlayers()[jugadorIntentaReciber];
              this.tiro =
                  new CommandHitBall(jugador, destinoBola, fuerzaRemateDefensas,
                                     random.nextInt(60));
            }
            // si estas entre el area grande y chica, tira
          } else {
            // Si estoy dentro del area media, tira
            if (situacionPartido.myPlayers()[jugador].getY() >= 34) {
              destinoBola = Constants.centroArcoSup;
              ejeXDestino =
                  ControladorUtils.getInstance()
                      .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

              int anguloTiro = 13;
//							double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//									new Recta(new Posicion(x, y), new Posicion(x, y)));

              if (tiraLadoDerecho) {
                destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
              } else {
                destinoBola.setPosition(ejeXDestino, destinoBola.getY());
              }
              this.tiro =
                  new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                     random.nextInt(anguloTiro));
            } else { //camina
              this.tiro = new CommandHitBall(jugador);
            }
          }


        }
      } else {

        // estoy marcado, da pase
        if (ControladorEstadoPartido.getInstance().getJugadoresMarcados().containsKey(jugador) &&
            ControladorEstadoPartido.getInstance().getJugadoresMarcados().get(jugador).size() > 0) {
          // si estoy en el area grande, tira
          if (ControladorEstadoPartido.getInstance().isEstaBalonEnElAreaGrandeInf()) {
            destinoBola = Constants.centroArcoSup;
            ejeXDestino =
                ControladorUtils.getInstance()
                    .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

            int anguloTiro = 13;
//						double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//								new Recta(new Posicion(x, y), new Posicion(x, y)));

            if (tiraLadoDerecho) {
              destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
            } else {
              destinoBola.setPosition(ejeXDestino, destinoBola.getY());
            }
            this.tiro =
                new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                   random.nextInt(anguloTiro));
          }

          // si estas entre el area grande y chica, tira
        } else {
          // Si estoy dentro del area media, tira
          if (situacionPartido.myPlayers()[jugador].getY() >= 34) {
            destinoBola = Constants.centroArcoSup;
            ejeXDestino =
                ControladorUtils.getInstance()
                    .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

            int anguloTiro = 13;
//						double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//								new Recta(new Posicion(x, y), new Posicion(x, y)));

            if (tiraLadoDerecho) {
              destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
            } else {
              destinoBola.setPosition(ejeXDestino, destinoBola.getY());
            }
            this.tiro =
                new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                   random.nextInt(anguloTiro));
          } else { //camina
            this.tiro = new CommandHitBall(jugador);
          }
        }


      }

    } else {

      // estoy marcado, da pase
      if (ControladorEstadoPartido.getInstance().getJugadoresMarcados().containsKey(jugador) &&
          ControladorEstadoPartido.getInstance().getJugadoresMarcados().get(jugador).size() > 0) {
        // si estoy en el area grande, tira
        if (ControladorEstadoPartido.getInstance().isEstaBalonEnElAreaGrandeInf()) {
          destinoBola = Constants.centroArcoSup;
          ejeXDestino =
              ControladorUtils.getInstance()
                  .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

          int anguloTiro = 13;
//					double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//							new Recta(new Posicion(x, y), new Posicion(x, y)));

          if (tiraLadoDerecho) {
            destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
          } else {
            destinoBola.setPosition(ejeXDestino, destinoBola.getY());
          }
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                 random.nextInt(anguloTiro));
        }
        // si estas entre el area grande y chica, tira
      } else {
        // Si estoy dentro del area media, tira
        if (situacionPartido.myPlayers()[jugador].getY() >= 34) {
          destinoBola = Constants.centroArcoSup;
          ejeXDestino =
              ControladorUtils.getInstance()
                  .generaNumeroDobleAleatorioEntre(0, ((Constants.LARGO_ARCO / 2) - 0.5));

          int anguloTiro = 13;
//					double anguloTiro  = ControladorUtils.getInstance().getAnguloDe2Rectas(new Recta(new Posicion(x, y), new Posicion(x, y)), 
//							new Recta(new Posicion(x, y), new Posicion(x, y)));

          if (tiraLadoDerecho) {
            destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
          } else {
            destinoBola.setPosition(ejeXDestino, destinoBola.getY());
          }
          this.tiro =
              new CommandHitBall(jugador, destinoBola, fuerzaRemateDelanteros,
                                 random.nextInt(anguloTiro));
        } else { //camina
          this.tiro = new CommandHitBall(jugador);
        }
      }


    }
  }


}
