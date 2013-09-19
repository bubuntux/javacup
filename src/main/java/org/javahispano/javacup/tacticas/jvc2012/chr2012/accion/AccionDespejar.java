package org.javahispano.javacup.tacticas.jvc2012.chr2012.accion;

import org.javahispano.javacup.model.command.Command;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de despeje del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionDespejar implements Accion {

	private Command comando;
	
	
	public AccionDespejar(Command comando) {
		this.comando = comando;
	}


	public Command comando() {
		return comando;
	}

}
