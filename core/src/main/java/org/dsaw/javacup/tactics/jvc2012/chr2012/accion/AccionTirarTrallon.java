package org.dsaw.javacup.tactics.jvc2012.chr2012.accion;

import org.dsaw.javacup.model.command.Command;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de tiro a trallon del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionTirarTrallon implements Accion {

	private Command comando;

	public AccionTirarTrallon(Command comando) {
		this.comando = comando;
	}
	
	@Override
        public Command comando() {
		return comando;
	}
}
