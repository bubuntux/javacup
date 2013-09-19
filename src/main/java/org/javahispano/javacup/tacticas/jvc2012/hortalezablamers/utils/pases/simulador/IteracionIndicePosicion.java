package org.javahispano.javacup.tacticas.jvc2012.hortalezablamers.utils.pases.simulador;

import org.javahispano.javacup.model.util.Position;

public class IteracionIndicePosicion {

	protected int iteracion;
	protected int indice;
	protected Position position;
	
	public IteracionIndicePosicion(int iteracion,
			int indice, Position position) {
		super();
		this.iteracion = iteracion;
		this.indice = indice;
		this.position = position;
	}

	public int getIteracion() {
		return iteracion;
	}

	public int getIndice() {
		return indice;
	}

	public Position getPosition() {
		return position;
	}
}
