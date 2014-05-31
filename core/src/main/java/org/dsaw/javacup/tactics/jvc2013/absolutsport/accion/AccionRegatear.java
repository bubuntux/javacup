package org.dsaw.javacup.tactics.jvc2013.absolutsport.accion;

import org.dsaw.javacup.model.command.Command;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de regate del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public class AccionRegatear implements Accion {

	private Command comando;
	private Command comandoIrA;
	private boolean regateHaciaAdelante;
	private boolean regateHaciaPorteria;
	
	
	public AccionRegatear(Command comando, Command comandoIrA,
						  boolean regateHaciaAdelante, boolean regateHaciaPorteria) {
		this.comando = comando;
		this.comandoIrA = comandoIrA;
		this.regateHaciaAdelante = regateHaciaAdelante;
		this.regateHaciaPorteria = regateHaciaPorteria;
	}

	

	public Command comando() {
		return comando;
	}
	
	public Command comandoIrA() {
		return comandoIrA;
	}
	
	public boolean regateHaciaAdelante() {
		return regateHaciaAdelante;
	}
	
	public boolean regateHaciaPorteria() {
		return regateHaciaPorteria;
	}
}
