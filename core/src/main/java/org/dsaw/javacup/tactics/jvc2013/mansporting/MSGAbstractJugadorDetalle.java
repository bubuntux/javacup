/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;

import java.awt.*;

/**
 * @author MaN
 */
public abstract class MSGAbstractJugadorDetalle implements MSGJugadorDetalle {

  protected final int index;
  protected final Color colorPelo;
  protected final Color colorPiel;
  protected final String nombre;
  protected final int numero;
  protected final MSGEstadisticas estadisticas;
  protected final boolean rival;
  protected MSGSituacionPartidoContext context;
  protected Punto posicion;
  protected int iteracionesParaRematar;
  protected boolean puedeRematar;
  private final double altoCubierto;
  private final double control;
  private final double velocidad;

  public MSGAbstractJugadorDetalle(int index, Color colorPelo, Color colorPiel, String nombre,
                                   int numero, MSGEstadisticas estadisticas, boolean rival) {
    this.index = index;
    this.colorPelo = colorPelo;
    this.colorPiel = colorPiel;
    this.nombre = nombre;
    this.numero = numero;
    this.estadisticas = estadisticas;
    this.rival = rival;
    // Aproximación.
    this.altoCubierto = isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;
    this.control =
        isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO
                       : Constants.DISTANCIA_CONTROL_BALON;
    this.velocidad =
        Math.max(Math.min(MSGConstants.VELOCIDAD_JUGADOR.getY(estadisticas.getVelocidad()),
                          Constants.VELOCIDAD_MAX), Constants.VELOCIDAD_MIN);
  }

  @Override
  public final boolean isGoalKeeper() {
    return index == 0;
  }

  @Override
  public final Color getHairColor() {
    return colorPelo;
  }

  @Override
  public final Color getSkinColor() {
    return colorPiel;
  }

  @Override
  public final String getPlayerName() {
    return nombre;
  }

  @Override
  public final int getNumber() {
    return numero;
  }

  @Override
  public final double getPrecision() {
    return estadisticas.getPrecision();
  }

  @Override
  public final double getPower() {
    return estadisticas.getRemate();
  }

  @Override
  public final double getSpeed() {
    return estadisticas.getVelocidad();
  }

  @Override
  public final MSGEstadisticas getEstadisticas() {
    return estadisticas;
  }

  @Override
  public final int getIndex() {
    return index;
  }

  @Override
  public final Punto getPosicion() {
    return posicion;
  }

  @Override
  public final boolean getRival() {
    return rival;
  }

  @Override
  public final int getIteracionesParaRematar() {
    return iteracionesParaRematar;
  }

  @Override
  public boolean getPuedeRematar() {
    return puedeRematar;
  }

  @Override
  public boolean getPuedeRematar(int iteraciones) {
    return puedeRematar || iteracionesParaRematar <= iteraciones;
  }

  @Override
  public void afterUpdate(MSGSituacionPartidoContext context) {
    this.context = context;
    GameSituations situacionPartido = context.getSituacionPartido();
    Position p = rival
                 ? situacionPartido.rivalPlayers()[index]
                 : situacionPartido.myPlayers()[index];
    this.posicion = new Punto(p);
    this.iteracionesParaRematar = rival
                                  ? situacionPartido.rivalIterationsToKick()[index]
                                  : situacionPartido.iterationsToKick()[index];
    this.puedeRematar = false;
    for (int i : rival ? situacionPartido.rivalCanKick() : situacionPartido.canKick()) {
      if (i == index) {
        puedeRematar = true;
        break;
      }
    }
  }

  @Override
  public final Circulo getAreaCubierta(int iteraciones) {
    Punto posicionJugador = posicion == null ? new Punto(0, 0) : posicion;
    return new Circulo(
        posicionJugador,
        velocidad * iteraciones + control);
  }

  @Override
  public double getAltoCubierto() {
    return this.altoCubierto;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MSGAbstractJugadorDetalle other = (MSGAbstractJugadorDetalle) obj;
    if (this.numero != other.numero) {
      return false;
    }
    if (this.rival != other.rival) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 13 * hash + this.numero;
    hash = 13 * hash + (this.rival ? 1 : 0);
    return hash;
  }

  @Override
  public String toString() {
    return "MSGAbstractJugadorDetalle{" + "nombre=" + nombre + "numero=" + numero + "precision="
           + getPrecision() + "remate=" + getPower() + "velocidad=" + getSpeed() + '}';
  }
}
