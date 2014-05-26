package org.dsaw.javacup.tacticas.jvc2012.romedal.util;

import java.util.TreeMap;
import java.util.TreeSet;

public class OpcionesDisparo implements Comparable<OpcionesDisparo> {
	double										fuerza;
	int											tipoDisparo;
	TreeMap<Double, TreeSet<InfoTrayectoria>>	opciones;
	TreeSet<InfoTrayectoria>					opcionesAngCero;

	public OpcionesDisparo(final double fuerza, final int tipoDisparo) {
		this.fuerza = fuerza;
		this.tipoDisparo = tipoDisparo;
		this.opciones = new TreeMap<Double, TreeSet<InfoTrayectoria>>();
		this.opcionesAngCero = new TreeSet<InfoTrayectoria>();
	}

	public OpcionesDisparo(final double fuerza, final int tipoDisparo, final TreeMap<Double, TreeSet<InfoTrayectoria>> opciones, final TreeSet<InfoTrayectoria> opcionesAngCero) {
		this.fuerza = fuerza;
		this.tipoDisparo = tipoDisparo;
		this.opciones = opciones;
		this.opcionesAngCero = opcionesAngCero;
	}

	public int getTipoDisparo() {
		return tipoDisparo;
	}

	public void setTipoDisparo(final int tipoDisparo) {
		this.tipoDisparo = tipoDisparo;
	}

	public TreeMap<Double, TreeSet<InfoTrayectoria>> getOpciones() {
		return opciones;
	}

	public void setOpciones(final TreeMap<Double, TreeSet<InfoTrayectoria>> opciones) {
		this.opciones = opciones;
	}

	public TreeSet<InfoTrayectoria> getOpcionesAngCero() {
		return opcionesAngCero;
	}

	public void setOpcionesAngCero(final TreeSet<InfoTrayectoria> opcionesAngCero) {
		this.opcionesAngCero = opcionesAngCero;
	}

	public double getFuerza() {
		return fuerza;
	}

	public void setFuerza(final double fuerza) {
		this.fuerza = fuerza;
	}

	@Override
	public int compareTo(final OpcionesDisparo o) {
		if (fuerza != o.getFuerza()) {
			return fuerza > o.getFuerza() ? 1 : -1;
		}
		return tipoDisparo - o.getTipoDisparo();
	}

}
