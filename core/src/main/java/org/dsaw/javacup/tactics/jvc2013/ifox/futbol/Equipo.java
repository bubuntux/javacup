/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.futbol;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Mensaje;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Mensajero;
import org.dsaw.javacup.tactics.jvc2013.ifox.javacup.tactica.Leti;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Parametros;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.PosicionAyudaCalculador;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Transformacion;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Vector2d;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Usuario
 */
public class Equipo {

  private GameSituations situacion;
  private List<Command> comandos;
  private Map<Alineacion.Tipo, Alineacion> alineaciones;
  private Alineacion alineacion;
  private Jugador[] jugadores;
  private Jugador jugadorControlador;
  private Jugador jugadorAyudante;
  private Jugador mejorJugadorDeAyuda;
  private Jugador jugadorAAyudar;
  private PosicionAyudaCalculador posicionCalculador;
  private Random random;

  public Equipo() {
    comandos = new LinkedList<>();
    alineaciones = new EnumMap<>(Alineacion.Tipo.class);

    Alineacion saque = new Alineacion(Alineacion.Tipo.SAQUE, Leti.alineacion1);
    Alineacion ataqueNormal = new Alineacion(Alineacion.Tipo.ATAQUE_NORMAL, Leti.alineacion2);
    Alineacion ataqueFuerte = new Alineacion(Alineacion.Tipo.ATAQUE_FUERTE, Leti.alineacion3);
    Alineacion recepcion = new Alineacion(Alineacion.Tipo.RECEPCION, Leti.alineacion4);
    Alineacion defensaNormal = new Alineacion(Alineacion.Tipo.DEFENSA_NORMAL, Leti.alineacion5);
    Alineacion defensaFuerte = new Alineacion(Alineacion.Tipo.DEFENSA_FUERTE, Leti.alineacion6);

    alineaciones.put(Alineacion.Tipo.SAQUE, saque);
    alineaciones.put(Alineacion.Tipo.RECEPCION, recepcion);
    alineaciones.put(Alineacion.Tipo.ATAQUE_FUERTE, ataqueFuerte);
    alineaciones.put(Alineacion.Tipo.ATAQUE_NORMAL, ataqueNormal);
    alineaciones.put(Alineacion.Tipo.DEFENSA_FUERTE, defensaFuerte);
    alineaciones.put(Alineacion.Tipo.DEFENSA_NORMAL, defensaNormal);

    jugadores = new Jugador[11];
    for (int i = 0; i < jugadores.length; i++) {
      if (i == 0) {
        jugadores[i] = new Jugador(this, i, Jugador.Rol.PORTERO);
      } else if (i >= 1 && i <= 4) {
        jugadores[i] = new Jugador(this, i, Jugador.Rol.DEFENSOR);
      } else if (i == 5 || i == 6 || i == 8) {
        jugadores[i] = new Jugador(this, i, Jugador.Rol.MEDIO);
      } else {
        jugadores[i] = new Jugador(this, i, Jugador.Rol.ATACANTE);
      }
    }

    posicionCalculador = new PosicionAyudaCalculador(this,
                                                     Parametros.Equipo.NUM_POSICIONES_HORIZONTALES,
                                                     Parametros.Equipo.NUM_POSICIONES_VERTICALES);
    random = new Random(System.currentTimeMillis());
  }

  public void actualizar() {
    comandos.clear();
    seleccionarAlineacion();
    calcularMejorPosicionDeAyuda();

    for (int i = 0; i < jugadores.length; i++) {
      jugadores[i].actualizar();
    }
  }

  public void seleccionarAlineacion() {
    if (situacion.isRivalStarts()) {
      alineacion = alineaciones.get(Alineacion.Tipo.DEFENSA_NORMAL);
    } else {
      alineacion = alineaciones.get(Alineacion.Tipo.DEFENSA_NORMAL);
    }

    if ((situacion.rivalGoals() - situacion.myGoals()) == 1) {
      alineacion = alineaciones.get(Alineacion.Tipo.ATAQUE_FUERTE);
    } else if (situacion.rivalGoals() - situacion.myGoals() > 1) {
      alineacion = alineaciones.get(Alineacion.Tipo.DEFENSA_FUERTE);
    }

    if (situacion.rivalGoals() - situacion.myGoals() > 2) {
      alineacion = alineaciones.get(Alineacion.Tipo.ATAQUE_FUERTE);
    }
  }

  public boolean isEnControl() {
    return situacion.canKick().length > 0
           && situacion.rivalCanKick().length == 0;
  }

  public void recuperarBalon() {
    int[] recuperadores = situacion.getRecoveryBall();
    if (recuperadores.length > 1) {
      double[] posicionRecuperacion = situacion.getTrajectory(recuperadores[0]);
      for (int i = 1; i < recuperadores.length; i++) {
        Position objetivo = new Position(posicionRecuperacion[0], posicionRecuperacion[1]);
        jugadores[recuperadores[i]].irAObjetivo(objetivo);
      }
    }
  }

  public Position puedeRematar(Jugador rematador, double fuerza, double distancia) {
    int intentos = Parametros.Equipo.INTENTOS_MARCAR_GOL;
    while (intentos-- > 0) {
      Vector2d objetivo = new Vector2d(Constants.centroArcoSup);
      if (rematador.getPosicion().norm(objetivo.toPosicion())
          > distancia) {
        return null;
      }

      int minX = (int) (Constants.posteIzqArcoSup.getX()
                        + Constants.RADIO_BALON);
      int maxX = (int) (Constants.posteDerArcoSup.getX()
                        - Constants.RADIO_BALON);
      objetivo.x = (maxX - minX) * random.nextDouble() + minX;

      int tiempo = Util.iteracionesPase(rematador, objetivo.toPosicion());
      if (tiempo > 0) {
        //if (isPaseSeguroDeTodoOponente(rematador, null,
        //        objetivo.toPosicion())) {
        return objetivo.toPosicion();
        //}
      }
    }
    return null;
  }

  public void calcularMejorPosicionDeAyuda() {
    if (jugadorAAyudar != null) {
      Position posicionAyuda = posicionCalculador.calcular(jugadorAAyudar).toPosicion();
      int id = posicionAyuda.nearestIndex(situacion.myPlayers());
      mejorJugadorDeAyuda = jugadores[id];
    }
  }

  public Jugador jugadorMasCercano(Position posicion) {
    int id = posicion.nearestIndex(situacion.myPlayers());
    return jugadores[id];
  }

  public Position buscarPase(Jugador pasador, double distanciaPaseMinima) {
    Position mejorResultado = null;
    double masCercanoAGol = Double.MAX_VALUE;
    for (Jugador jugador : jugadores) {
      if (!jugador.equals(pasador)
          && pasador.getPosicion().norm(jugador.getPosicion())
             > distanciaPaseMinima * distanciaPaseMinima) {
        Position resultado = calcularMejorPaseAReceptor(pasador, jugador);
        if (resultado != null) {
          double distanciaAGol = Math.abs(
              Constants.centroArcoSup.getY()
              - resultado.getY());
          if (distanciaAGol < masCercanoAGol) {
            masCercanoAGol = distanciaAGol;
            mejorResultado = resultado;
          }
        }
      }
    }

    return mejorResultado;
  }

  public Position calcularMejorPaseAReceptor(Jugador pasador, Jugador receptor) {
    return receptor.getPosicion();
//        Posicion objetivo = null;
//
//        double iteraciones = Util.iteracionesPase(pasador, receptor.getPosicion());
//        if (iteraciones < 0) return null;
//
//        double rangoIntercepcion = receptor.getMaxVelocidad() * iteraciones;
//        double reduccion = 0.00002;
//
//        rangoIntercepcion *= reduccion;
//        Posicion[] tangentes = Geometria.getPuntosTangenciales(receptor
//                .getPosicion(), rangoIntercepcion, pasador.getPosicion());
//        if (tangentes != null) {
//            Posicion tangenteA = tangentes[0];
//            Posicion tangenteB = tangentes[1];
//            Posicion[] pases = {tangenteA, receptor.getPosicion(), tangenteB};
//
//            double menorDistancia = Double.MAX_VALUE;
//            for (Posicion pase : pases) {
//                double distancia = Math.abs(Constants.centroArcoSup.getY()
//                        - pase.getY());
//                if (distancia < menorDistancia
//                        && pase.isDentroCampoJuego(0)
//                        && isPaseSeguroDeTodoOponente(pasador, receptor, pase)) {
//                    menorDistancia = distancia;
//                    objetivo = pase;
//                }
//            }
//        }
//        return objetivo;
  }

  public boolean isPaseSeguroDeTodoOponente(Jugador pasador, Jugador receptor,
                                            Position posicion) {
    if (pasador == null || receptor == null) {
      return false;
    }
    for (int i = 0; i < 11; i++) {
      if (!isPaseSeguroDeOponente(pasador, receptor, posicion, i)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPaseSeguroDeOponente(Jugador pasador, Jugador receptor,
                                        Position posicion, int oponente) {
    Vector2d desde = new Vector2d(pasador.getPosicion());
    Vector2d hasta = new Vector2d(posicion);

    Vector2d haciaReceptor = hasta.resta(desde);
    Vector2d haciaReceptorNormal = haciaReceptor.normal();

    Vector2d posicionOponente = new Vector2d(situacion.rivalPlayers()[oponente]);
    Vector2d posicionLocalOponente = Transformacion.puntoAEspacioLocal(
        posicionOponente, haciaReceptorNormal,
        haciaReceptorNormal.perpendicular(), desde);

    if (posicionLocalOponente.x <= 0.4) {
      return true;
    }

    if (desde.distanciaCuadrada(hasta)
        < desde.distanciaCuadrada(posicionOponente)) {
      if (receptor != null) {
        Vector2d posicionReceptor = new Vector2d(receptor.getPosicion());
        if (hasta.distanciaCuadrada(posicionOponente)
            > hasta.distanciaCuadrada(posicionReceptor)) {
          return true;
        }
      } else {
        return true;
      }
    }

    int iteraciones = Util.iteracionesPase(pasador, posicion);
    double velocidadOponente = Constants.getVelocidad(situacion
                                                          .getRivalPlayerSpeed(oponente));
    double alcanceOponente = velocidadOponente * iteraciones
                             + Constants.RADIO_BALON;

    if (Math.abs(posicionLocalOponente.y) < alcanceOponente) {
      return false;
    }

    return true;
  }

  public void pedirPase(Jugador solicitante) {
    if (solicitante == null) {
      return;
    }

    if (random.nextDouble() > 0.1) {
      return;
    }

    if (isPaseSeguroDeTodoOponente(jugadorControlador, solicitante,
                                   solicitante.getPosicion())) {
      Mensajero.getInstancia().entregarMensaje(Mensajero.INMEDIATO,
                                               solicitante, jugadorControlador,
                                               Mensaje.PASAR_BALON, solicitante);
    }
  }

  public void comunicarPaseAReceptorMasCercano(Jugador pasador,
                                               Position posicionPase) {
    int id = posicionPase.nearestIndex(situacion.myPlayers());
    Jugador receptor = jugadores[id];
    Mensajero.getInstancia().entregarMensaje(Mensajero.INMEDIATO,
                                             pasador, receptor, Mensaje.RECIBIR_BALON,
                                             posicionPase);
  }

  public Alineacion getAlineacion() {
    return alineacion;
  }

  public List<Command> getComandos() {
    return comandos;
  }

  public Jugador getJugadorAAyudar() {
    return jugadorAAyudar;
  }

  public void setJugadorAAyudar(Jugador jugadorAAyudar) {
    this.jugadorAAyudar = jugadorAAyudar;
  }

  public Jugador getJugadorAyudante() {
    return jugadorAyudante;
  }

  public void setJugadorAyudante(Jugador jugadorAyudante) {
    this.jugadorAyudante = jugadorAyudante;
  }

  public Jugador getJugadorControlador() {
    return jugadorControlador;
  }

  public void setJugadorControlador(Jugador jugadorControlador) {
    this.jugadorControlador = jugadorControlador;
  }

  public Position getMejorPosicionDeAyuda() {
    return posicionCalculador.getMejorPosicion(jugadorAAyudar).toPosicion();
  }

  public Jugador getMejorJugadorDeAyuda() {
    return mejorJugadorDeAyuda;
  }

  public void setMejorJugadorDeAyuda(Jugador mejorJugadorDeAyuda) {
    this.mejorJugadorDeAyuda = mejorJugadorDeAyuda;
  }

  public GameSituations getSituacion() {
    return situacion;
  }

  public void setSituacion(GameSituations situacion) {
    this.situacion = situacion;
  }
}
