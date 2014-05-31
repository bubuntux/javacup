package org.dsaw.javacup.tactics.jvc2013.absolutsport.accion;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Jugador;


/**
 * Clase que implementa el interfaz Action y que representa la acci�n de pase al area del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public class AccionPasarAlArea implements Accion {

	private Command comando;
	private Jugador jugadorDestino;
	
	
	public AccionPasarAlArea(Command comando, Jugador jugadorDestino) {

		this.comando = comando;
		this.jugadorDestino = jugadorDestino;
	}



	public Command comando() {
		return comando;
	}

	public Jugador jugadorDestino() {
		return jugadorDestino;
	}
}
