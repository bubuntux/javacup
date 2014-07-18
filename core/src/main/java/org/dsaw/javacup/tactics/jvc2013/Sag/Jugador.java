/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.Sag;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pdsanchez
 */
public abstract class Jugador {

  public static final int TIPO_PORTERO = 1;
  public static final int TIPO_DEFENSA = 2;
  public static final int TIPO_CENTRAL = 3;
  public static final int TIPO_DELANTERO = 4;
  public static final int TIPO_RIVAL = 5;

  private int idx;
  private int tipo;

  private boolean recuperador = false;
  private boolean interceptor = false;
  private boolean rematador = false;

  private Position posReferencia;
  private Position posActual;
  private Position posDestino;
  private Position posBalon;

  private double velocidad;
  private double precision;
  private double remate;

  //    private Jugador jAlQueMarco;
  private Jugador marca;

  private Jugador[] companerosCercanos;
  private Jugador[] rivalesCercanos;

  protected Jugador[] jugadores;
  protected Jugador[] rivales;

  protected GameSituations sp;

  public Jugador(int idx, Player detalle, Position posRef) {
    this(idx, detalle);
    this.posReferencia = posRef;
    this.posDestino = posRef;
  }

  public Jugador(int idx, Player detalle) {
    this.idx = idx;

    this.velocidad = Constants.getVelocidad(detalle.getSpeed());
    this.precision = Constants.getErrorAngular(detalle.getPrecision());
    this.remate = Constants.getVelocidadRemate(detalle.getPower());

    this.companerosCercanos = new Jugador[10];
    this.rivalesCercanos = new Jugador[11];
  }

  public int getIndice() {
    return idx;
  }

  public boolean isPortero() {
    return (tipo == TIPO_PORTERO);
  }

  public boolean isDefensa() {
    return (tipo == TIPO_DEFENSA);
  }

  public boolean isCentral() {
    return (tipo == TIPO_CENTRAL);
  }

  public boolean isDelantero() {
    return (tipo == TIPO_DELANTERO);
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  public boolean isRecuperador() {
    return recuperador;
  }

  public void setRecuperador(boolean recuperador) {
    this.recuperador = recuperador;
  }

  public Jugador getRecuperador() {
    for (Jugador j : jugadores) {
      if (j.isRecuperador()) {
        return j;
      }
    }
    return null;
  }

  public boolean isInterceptor() {
    return interceptor;
  }

  public void setInterceptor(boolean interceptor) {
    this.interceptor = interceptor;
  }

  public boolean isRematador() {
    return rematador;
  }

  public void setRematador(boolean rematador) {
    this.rematador = rematador;
  }

  public Jugador getRematador() {
    for (Jugador j : jugadores) {
      if (j.isRematador()) {
        return j;
      }
    }
    return null;
  }

  public Position getPosicionReferencia() {
    return posReferencia;
  }

  public void setPosicionReferencia(Position posRef) {
    this.posReferencia = posRef;
  }

  public Position getPosicionActual() {
    return posActual;
  }

  public void setPosicionActual(Position posActual) {
    this.posActual = posActual;
  }

  public Position getPosicionBalon() {
    return posBalon;
  }

  public Position getPosicionDestino() {
    return posDestino;
  }

  public void setPosicionDestino(Position posDestino) {
    this.posDestino = posDestino.setInsideGameField();
  }

  public void setPosicionDestino(double x, double y) {
    //this.posDestino.setPosicion(x, y);
    this.posDestino = new Position(x, y).setInsideGameField();
  }

  /**
   * @return la velocidad
   */
  public double getVelocidad() {
    return velocidad;
  }

  /**
   * @return la precision
   */
  public double getPrecision() {
    return precision;
  }

  /**
   * @return la potencia de remate
   */
  public double getRemate() {
    return remate;
  }

  public int getIteracionesAPosDestino() {
    //return (int) ( (posActual.distancia(posDestino) - Constants.DISTANCIA_CONTROL_BALON ) / velocidad);
    return (int) (posActual.distance(posDestino) / velocidad);
  }

  public boolean estaBalonEnCampoRival() {
    return (posBalon.getY() >= 0);
  }

  public boolean estaJugadorEnCampoRival() {
    return (posActual.getY() >= 0);
  }

//    public void setJugadorAlQueMarca(Jugador j) {
//        jAlQueMarco = j;
//    }
//
//    public Jugador getJugadorAlQueMarca() {
//        return jAlQueMarco;
//    }
//
//    public boolean estaMarcandoAOtroJugador() {
//        return (jAlQueMarco != null);
//    }

  public Jugador getMarcaje() {
    return marca;
  }

  public void setMarcaje(Jugador j) {
    marca = j;
  }

  public boolean existeMarcaje() {
    return (marca != null);
  }

  public Jugador[] getCompanerosCercanos() {
    return companerosCercanos;
  }

  public Jugador getCompaneroMasCercano() {
    return companerosCercanos[0];
  }

  public boolean existeCompaneroCercano() {
    return (companerosCercanos.length > 0);
  }

  public Jugador[] getRivalesCercanos() {
    return rivalesCercanos;
  }

  public Jugador getRivalMasCercano() {
    return rivalesCercanos[0];
  }

  public boolean existeRivalCercano() {
    return (rivalesCercanos.length > 0);
  }

  /**
   * @return El companero mas cercano a la posicion especificada
   */
  public Jugador getCompanero(Position pos) {
    return jugadores[pos.nearestIndex(sp.myPlayers())];
  }

  /**
   * @return El rival mas cercano a la posicion especificada
   */
  public Jugador getRival(Position pos) {
    return rivales[pos.nearestIndex(sp.rivalPlayers())];
  }

  /**
   * @return El portero de mis jugadores
   */
  public Jugador getPortero() {
    for (Jugador j : jugadores) {
      if (j.isPortero()) {
        return j;
      }
    }
    return null;
  }

  /**
   * @return Lista con mis defensas
   */
  public List<Jugador> getDefensas() {
    List<Jugador> l = new ArrayList<>();
    for (Jugador j : jugadores) {
      if (j.isDefensa()) {
        l.add(j);
      }
    }
    return l;
  }

  /**
   * @return Lista con mis centrales
   */
  public List<Jugador> getCentrales() {
    List<Jugador> l = new ArrayList<>();
    for (Jugador j : jugadores) {
      if (j.isCentral()) {
        l.add(j);
      }
    }
    return l;
  }

  /**
   * @return Lista con mis delanteros
   */
  public List<Jugador> getDelanteros() {
    List<Jugador> l = new ArrayList<>();
    for (Jugador j : jugadores) {
      if (j.isDelantero()) {
        l.add(j);
      }
    }
    return l;
  }

  /**
   * @return El portero rival
   */
  public Jugador getPorteroRival() {
    for (Jugador j : rivales) {
      if (j.isPortero()) {
        return j;
      }
    }
    return null;
  }

  public void update(GameSituations sp, Jugador[] jugadores, Jugador[] rivales) {
    this.sp = sp;
    this.jugadores = jugadores;
    this.rivales = rivales;

    // Resetear
    recuperador = false;
    interceptor = false;
    rematador = false;
    posActual = (tipo == TIPO_RIVAL) ? sp.rivalPlayers()[idx] : sp.myPlayers()[idx];
    //posActual = posJugadores[idx];
    posBalon = sp.ballPosition();

//        jAlQueMarco = null;
    marca = null;

    // Posiciones de los jugadores
    Position[] posJugadores = (tipo == TIPO_RIVAL) ? sp.rivalPlayers() : sp.myPlayers();
    Position[] posJugadoresRivales = (tipo == TIPO_RIVAL) ? sp.myPlayers() : sp.rivalPlayers();

    // Companeros cercanos
    int[] idxList = posActual.nearestIndexes(posJugadores);
    int idxReal = 0;
    for (int i = 0; i < idxList.length; i++) {
      // No usar a este jugador
      if (this.getIndice() != idxList[i]) {
        companerosCercanos[idxReal] = jugadores[idxList[i]];
        idxReal++;
      }
    }
    // Rivales cercanos
    idxList = posActual.nearestIndexes(posJugadoresRivales);
    for (int i = 0; i < idxList.length; i++) {
      rivalesCercanos[i] = rivales[idxList[i]];
    }

    // Este jugador esta marcado si existe un rival a menos de una distancia concreta
    double dist = posActual.distance(this.getRivalMasCercano().getPosicionActual());
    if (dist <= 15) {
      marca = this.getRivalMasCercano();
    }
  }

  public abstract Command irAPosicionDestino();

  public abstract Command GolpearBalon();

  public void evaluarRecuperacion(Position posRc, Jugador rival, Jugador otroRecuperador) {
    posDestino = posRc;
    otroRecuperador.setPosicionDestino(posRc);

    // Si el otro recuperador es portero, lo descarto
    if (otroRecuperador.isPortero()) {
      recuperador = true;
    }
    // Este jugador es el recuperador mas rapido
    else if (this.getIteracionesAPosDestino() <= otroRecuperador.getIteracionesAPosDestino()) {
      recuperador = true;

      // El recuperador rival mas proximo
      rival.setPosicionDestino(posRc);

      // Determinar de quien es la ventaja en la recuperacion
      int ventaja = this.getIteracionesAPosDestino() - rival.getIteracionesAPosDestino();

      // Ventaja rival
      if (ventaja >= 0) {
        // Si este jugador es portero, lo mantengo en porteria y uso otro recuperador
        if (this.isPortero()) {
          otroRecuperador.setRecuperador(true);
          recuperador = false;
        }
      }
      // Ventaja mia, pero soy portero y lejos del area
      else if (this.isPortero() && Math.abs(posRc.getX()) > 15) {
        otroRecuperador.setRecuperador(true);
        recuperador = false;
      }
    }
    // El otro recuperador no es portero y es más rapido (lo uso)
    else {
      otroRecuperador.setRecuperador(true);
      otroRecuperador.setPosicionDestino(posRc);
    }
  }

  public void evaluarIntercepcion(Position posRc, Jugador otroInterceptor, int iteracion) {
    Jugador itc = this;
    // Si este jugador es receptor o portero no puede ser tambien interceptor
    if (this.isRecuperador() || this.isPortero()) {
      itc = otroInterceptor;
    }

    // Ver si realmente es interceptor
    itc.setPosicionDestino(posRc);

    // Si este jugador llega antes que el ballPosition a la posicion de intercepcion
    // lo marco como interceptor
    if (itc.getIteracionesAPosDestino() <= iteracion) {
      itc.setInterceptor(true);
    }
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (tipo == TIPO_RIVAL) {
      sb.append("> Rival").append(idx);
      if (isPortero()) {
        sb.append(" [portero]");
      }
    } else {
      sb.append("> Jugador").append(idx).append(" [T").append(tipo).append("]");
    }
    sb.append(" Rc").append(this.isRecuperador() ? "1" : "0");
    sb.append(" Ic").append(this.isInterceptor() ? "1" : "0");
    sb.append(" Rt").append(this.isRematador() ? "1" : "0");
    if (this.existeMarcaje()) {
      sb.append(" Marca").append(this.getMarcaje().traza());
    } else {
      sb.append(" Sin marca");
    }
    //sb.append(" (Rival cercano").append(this.getRivalMasCercano().traza()).append(")");

    return sb.toString();
  }

  public String traza() {
    StringBuilder sb = new StringBuilder();
    if (tipo == TIPO_RIVAL) {
      sb.append("> Rival").append(idx);
      if (isPortero()) {
        sb.append(" [portero]");
      }
    } else {
      sb.append("> Jugador").append(idx).append(" [T").append(tipo).append("]");
    }

    return sb.toString();
  }
}
