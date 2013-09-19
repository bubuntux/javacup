package org.javahispano.javacup.tacticas.jvc2012.romedal.util;

import java.util.TreeSet;

import org.javahispano.javacup.tacticas.jvc2012.romedal.TacticaRomedalusTeam;

public class InfoTrayectoria implements Comparable<InfoTrayectoria> {
	double			fuerza;
	double			potencia;
	double			angulo;
	double			distancia;
	int				iteraciones;
	TreeSet<Double>	distancias;

	public InfoTrayectoria(final double fuerza, final double potencia, final double angulo, final double distancia, final int iteraciones, final TreeSet<Double> distancias) {
		super();
		this.fuerza = fuerza;
		this.potencia = potencia;
		this.angulo = angulo;
		this.distancia = distancia;
		this.iteraciones = iteraciones;
		this.distancias = distancias;
	}

	public InfoTrayectoria(final InfoTrayectoria info) {
		this(info.getFuerza(), info.getPotencia(), info.getAngulo(), info.getDistancia(), info.getIteraciones(), info.getDistancias());
	}

	public double getFuerza() {
		return fuerza;
	}

	public void setFuerza(final double fuerza) {
		this.fuerza = fuerza;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(final double angulo) {
		this.angulo = angulo;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(final double distancia) {
		this.distancia = distancia;
	}

	public double getPotencia() {
		return potencia;
	}

	public void setPotencia(final double potencia) {
		this.potencia = potencia;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(final int iteraciones) {
		this.iteraciones = iteraciones;
	}

	public TreeSet<Double> getDistancias() {
		return distancias;
	}

	public void setDistancias(final TreeSet<Double> distancias) {
		this.distancias = distancias;
	}

	@Override
	public int compareTo(final InfoTrayectoria o) {

		double dif = iteraciones - o.getIteraciones();
		if (dif < 0) {
			return -1;
		}
		if (dif > 0) {
			return 1;
		}
		dif = angulo - o.getAngulo();
		if (dif > 0) {
			return 1;
		}
		if (dif < 0) {
			return -1;
		}
		dif = potencia - o.getPotencia();
		if (dif > 0) {
			return 1;
		}
		if (dif < 0) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "F: " + TacticaRomedalusTeam.round(fuerza, 2) + "\tP: " + TacticaRomedalusTeam.round(potencia, 2) + "\tAng: " + TacticaRomedalusTeam.round(angulo * TacticaRomedalusTeam.TO_ANG, 2) + "\tDist:" + distancia + "\tIter: " + iteraciones
				+ "\n";
	}
}
