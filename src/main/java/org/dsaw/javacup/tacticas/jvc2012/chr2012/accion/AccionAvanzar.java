package org.dsaw.javacup.tacticas.jvc2012.chr2012.accion;

import org.dsaw.javacup.model.command.Command;


/**
 * Clase que implementa el interfaz Action y que representa la acci�n de avance del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionAvanzar implements Accion {

	private Command comando;
	private Command comandoIrA;

	public AccionAvanzar(Command comando, Command comandoIrA) {
		
		this.comando = comando;
		this.comandoIrA = comandoIrA;
	}

	
	@Override
        public Command comando() {
		return comando;
	}


	public Command comandoIrA() {
		return comandoIrA;
	}
}
