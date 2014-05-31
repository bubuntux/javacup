package org.dsaw.javacup.tactics.jvc2013.emandem.enums;

public enum JUGADOR {
	PORTERO(0),
	UNO(0),
	DOS(1),
	TRES(2),
	CUATRO(3),
	CINCO(4),
	SEIS(5),
	SIETE(6),
	OCHO(7),
	NUEVE(8),
	DIEZ(9),
	ONCE(10);
	
	private int jugadorNumero;

	private JUGADOR(int jugadorNumero) {
		this.jugadorNumero = jugadorNumero;
	}

	public int numero() {
		return this.jugadorNumero;
	}
}
