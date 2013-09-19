package org.javahispano.javacup.tacticas.jvc2012.chr2012.accion;

import org.javahispano.javacup.model.command.Command;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de regate del jugador.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
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
