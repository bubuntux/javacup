package org.dsaw.javacup.tactics.jvc2013.absolutsport.accion;

import org.dsaw.javacup.model.command.Command;

/**
 * Interfaz de definici�n de las acciones a realizar por el jugador que tenga la posesi�n del
 * bal�n.
 *
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public interface Accion {

  public Command comando();
}