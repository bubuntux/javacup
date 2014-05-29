package org.dsaw.javacup.tactics.jvc2012.chr2012.accion;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.util.Position;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de pase al hueco del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionPasarAlHueco implements Accion {

	private Command comando;
	private int indiceJugadorCercano;
	private Position posicionAlcancePase;
	private boolean paseHaciaAdelante;
	private boolean paseHaciaElArea;
	
	public AccionPasarAlHueco(Command comando, int indiceJugadorCercano,
							  Position posicionAlcancePase, boolean paseHaciaAdelante, boolean paseHaciaElArea) {
		this.comando = comando;
		this.indiceJugadorCercano = indiceJugadorCercano;
		this.posicionAlcancePase = posicionAlcancePase;
		this.paseHaciaAdelante = paseHaciaAdelante;
		this.paseHaciaElArea = paseHaciaElArea;
	}



	@Override
        public Command comando() {
		return comando;
	}
	
	public int indiceJugadorCercano() {
		return indiceJugadorCercano;
	}

	public Position posicionAlcancePase() {
		return posicionAlcancePase;
	}
	
	public boolean paseHaciaAdelante() {
		return paseHaciaAdelante;
	}

	public boolean paseHaciaElArea() {
		return paseHaciaElArea;
	}
}
