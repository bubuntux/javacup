package org.javahispano.javacup.tacticas.jvc2012.chr2012.accion;

import org.javahispano.javacup.model.command.Command;

/**
 * Interfaz de definici�n de las acciones a realizar por el jugador que tenga la posesi�n del bal�n. 
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public interface Accion {

    public Command comando();
}