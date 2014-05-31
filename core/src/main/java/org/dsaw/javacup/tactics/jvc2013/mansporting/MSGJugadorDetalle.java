/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;

/**
 * @author MaN
 */
public interface MSGJugadorDetalle extends PlayerDetail, MSGSituacionPartidoListener {

  /**
   * Devuelve el indice del jugador.
   */
  int getIndex();

  /**
   * Devuelve la posición del jugador.
   *
   * @return posición del jugador.
   */
  Punto getPosicion();

  /**
   * Estadísticas del jugador.
   *
   * @return estadísticas del jugador,.
   */
  MSGEstadisticas getEstadisticas();

  /**
   * Indica si el jugador es un rival o no.
   *
   * @return indica si el jugador es un rival o no.
   */
  boolean getRival();

  /**
   * Indica si el jugador puede rematar.
   *
   * @return si puede rematar.
   */
  boolean getPuedeRematar();

  /**
   * Indica si el jugador puede rematar en las i iteraciones.
   *
   * @param iteraciones iteraciones.
   * @return si el jugador puede rematar en las i iteraciones.
   */
  boolean getPuedeRematar(int iteraciones);

  /**
   * Devuelve el número de iteraciones para rematar.
   *
   * @return iteraciones para rematar.
   */
  int getIteracionesParaRematar();

  /**
   * Devuelve el area que cubre el jugador,
   *
   * @param iteraciones interaciones (0 es la actual)
   * @returnar que cubre el jugador.
   */
  Circulo getAreaCubierta(int iteraciones);

  /**
   * Devuelve el alto cubierto por el jugador.
   *
   * @return alto cubierto por el jugador
   */
  double getAltoCubierto();
}
