package org.javahispano.javacup.tacticas.jvc2012.romedal.util;

public class Estadistica {
	int opciones;
	int goles;
	int descartes;

	public Estadistica(int opciones, int goles) {
		super();
		this.opciones = opciones;
		this.goles = goles;
		descartes = 0;
	}

	public Estadistica() {
		this(0, 0);
	}

	public int getOpciones() {
		return opciones;
	}

	public void setOpciones(int opciones) {
		this.opciones = opciones;
	}

	public int getGoles() {
		return goles;
	}

	public void setGoles(int goles) {
		this.goles = goles;
	}

	public void addGol() {
		goles++;
	}

	public void addOpcion() {
		opciones++;
	}

	public void addDescarte() {
		descartes++;
	}

	/**
	 * @return the descartes
	 */
	public int getDescartes() {
		return descartes;
	}

	/**
	 * @param descartes
	 *            the descartes to set
	 */
	public void setDescartes(int descartes) {
		this.descartes = descartes;
	}

}
