package org.dsaw.javacup.tactics.jvc2013.CTeam.jugador;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.ICTeam2011;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.PosicionCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Disparo;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Triangulation;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.dsaw.javacup.model.util.Constants.AMPLIFICA_VEL_TRAYECTORIA;
import static org.dsaw.javacup.model.util.Constants.DISTANCIA_CONTROL_BALON;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.PROPIO;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.RIVAL;

public abstract class AbstractJugador implements IJugadorCT {

  private final Equipo equipo;

  private final int indice;

  private final PlayerDetail detalle;

  private final Deque<PosicionCT> posiciones;

  private PosicionCT actual;

  protected PosicionCT siguiente;

  private boolean controlBalon;

  protected GameSituations partido;

  protected ICTeam2011 tactica;

  protected Triangulation propios, rivales, todos;

  protected int propiosCercanos[];

  protected int rivalesCercanos[];

  protected boolean puedeRecuperar;

  protected int itRecuperacion;

  protected PosicionCT destinoDisparo;

  protected Disparo disparo;

  public AbstractJugador(Equipo equipo, int indice, PlayerDetail detalle, ICTeam2011 tactica) {
    this.equipo = equipo;
    this.indice = indice;
    this.detalle = detalle;
    this.posiciones = new LinkedList<PosicionCT>();
    this.tactica = tactica;
    this.actual = new PosicionCT(0, 0);
    this.siguiente = null;
  }

  @Override
  public void iniciarTurno(GameSituations sp, boolean puedeRematar) {
    partido = sp;
    if (equipo == PROPIO) {
      actual = new PosicionCT(sp.myPlayers()[indice]);
    } else {
      actual = new PosicionCT(sp.rivalPlayers()[indice]);
    }
    siguiente = actual;
    controlBalon = puedeRematar;
    posiciones.push(actual);
    propiosCercanos = sp.myPlayers()[indice].nearestIndexes(sp.myPlayers(), 0, indice);
    rivalesCercanos = sp.myPlayers()[indice].nearestIndexes(sp.rivalPlayers());
    destinoDisparo = null;
    disparo = null;
  }

  /**
   * determina si este jugador puede realizar un pase a la posicion de destino<br> <br> retorna null
   * si el pase no es posible, o el Disparo con el cual se debe realizar el pase
   */
  @Override
  public Disparo paseLibre(PosicionCT des, int itRematar) {
    if (!controlBalon) {
      return null;
    }

    PosicionCT act = getActual();
    double dTotal = act.distancia(des);
    if (dTotal < DISTANCIA_CONTROL_BALON * 2) {
      return null;
    }

    double fuerza = dTotal < 5 ? 0.3 : dTotal < 15 ? 0.6 : dTotal < 30 ? 0.8 : 1.0;
    boolean interceptado = false;
    double angVer = (0 * Math.PI) / 180;
    double vel = fuerza * Constants.getVelocidadRemate(detalle.getPower());
    AbstractTrajectory
        trayectoria =
        new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel,
                          0, 0);

    List<IJugadorCT> rivales = tactica.getJugadores(RIVAL);
    for (int idx : rivalesCercanos) {
      IJugadorCT rival = rivales.get(idx);
      PosicionCT r = rival.getActual();
      double h = act.distancia(r);
      double dist = r.distanciaALinea(act, des, true);
      if (Math.abs(h - dist) < 0.0000001) {
        continue;
      }

      double velRival = Constants.getVelocidad(rival.getDetalle().getSpeed());
      double it = (dist / velRival) + 2.0;
      double time = (double) it / 60d;
      double despXY = trayectoria.getX(time) * AMPLIFICA_VEL_TRAYECTORIA;
      // double despZ = trayectoria.getY(time) *
      // AMPLIFICA_VEL_TRAYECTORIA * 2;
      // double x = act.getX() + despXY * Math.cos(angle);
      // double y = act.getY() + despXY * Math.sin(angle);

      if (despXY > dTotal + 1.0) {
        continue;
      }

      double dist2 = Math.sqrt(h * h - dist * dist);
      if (dist2 > despXY - DISTANCIA_CONTROL_BALON) {
        interceptado = true;
        break;
      }
    }

    if (!interceptado) {
      return new Disparo(0, fuerza);
    }

    return null;
  }

  @Override
  public void disparar(PosicionCT destino, Disparo d) {
    this.destinoDisparo = destino;
    this.disparo = d;
  }

  @Override
  public PosicionCT posicionVoronoi() {
    Map<PosicionCT, List<PosicionCT>> voronoi = todos.voronoi();
    PosicionCT[] keys = voronoi.keySet().toArray(new PosicionCT[0]);
    int idx = actual.indiceMasCercano(keys);
    return keys[idx];
  }

  @Override
  public void setSiguiente(PosicionCT siguiente) {
    this.siguiente = siguiente;
  }

  /**
   * retornal la distancia que puede recorred el jugador en el numero de iteraciones indicado
   */
  public double distanciaEn(int iteraciones) {
    return (double) iteraciones * Constants.getVelocidad(detalle.getSpeed());
  }

  @Override
  public void setPuedeRecuperarBalon(boolean puede, int it) {
    puedeRecuperar = puede;
    itRecuperacion = it;
  }

  protected Position balonRecuperacion() {
    int it = Math.max(0, itRecuperacion);
    double[] b = partido.getTrajectory(it);
    Position posBalon = new Position(b[0], b[1]);
    return posBalon;
  }

  @Override
  public Equipo getEquipo() {
    return equipo;
  }

  @Override
  public int getIndice() {
    return indice;
  }

  @Override
  public PlayerDetail getDetalle() {
    return detalle;
  }

  @Override
  public Deque<PosicionCT> getPosiciones() {
    return posiciones;
  }

  @Override
  public PosicionCT getActual() {
    return actual;
  }

  @Override
  public PosicionCT getSiguiente() {
    return siguiente;
  }

  @Override
  public boolean isControlBalon() {
    return controlBalon;
  }

  @Override
  public void setVoronoi(Triangulation propios, Triangulation rivales, Triangulation todos) {
    this.propios = propios;
    this.rivales = rivales;
    this.todos = todos;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
    result = prime * result + indice;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractJugador other = (AbstractJugador) obj;
    if (equipo != other.equipo) {
      return false;
    }
    if (indice != other.indice) {
      return false;
    }
    return true;
  }

}
