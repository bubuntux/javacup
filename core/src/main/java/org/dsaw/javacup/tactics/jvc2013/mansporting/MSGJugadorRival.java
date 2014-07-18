/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.PlayerI;

/**
 * @author MaN
 */
public class MSGJugadorRival extends MSGAbstractJugadorDetalle {

  public MSGJugadorRival(int indice, PlayerI jugador) {
    super(
        indice,
        jugador.getHairColor(),
        jugador.getSkinColor(),
        jugador.getName(),
        jugador.getNumber(),
        new MSGEstadisticas.Builder().setPrecision(jugador.getPrecision())
            .setRemate(jugador.getPower()).setVelocidad(jugador.getSpeed()).getMSGEstadisticas(),
        true);
  }
}
