package org.dsaw.javacup.tactics.jvc2012.evolution;

import org.dsaw.javacup.model.util.Position;

public class Pase {

	
	private Jugador jugadorDestino;
	private Position posicionPase;

	
	public Pase(Jugador jugadorDestino, Position posicionPase) {
		this.jugadorDestino = jugadorDestino;
		this.posicionPase = posicionPase;
	}
	
	public Jugador getJugadorDestino() {
		return jugadorDestino;
	}
	public void setJugadorDestino(Jugador jugadorDestino) {
		this.jugadorDestino = jugadorDestino;
	}
	public Position getPosicionPase() {
		return posicionPase;
	}
	public void setPosicionPase(Position posicionPase) {
		this.posicionPase = posicionPase;
	}
	
	
}
