package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.movimientos;

public class IndiceYDistancia implements Comparable<IndiceYDistancia> {

	protected int indice;
	protected double distancia;
	
	public IndiceYDistancia(int indice, double distancia) {
		super();
		this.indice = indice;
		this.distancia = distancia;
	}

	public int getIndice() {
		return indice;
	}

	public double getDistancia() {
		return distancia;
	}

	@Override
	public int compareTo(IndiceYDistancia o) {
		return (int)(100 * (this.distancia - o.distancia));
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof IndiceYDistancia))
			return false;
					
		IndiceYDistancia other = (IndiceYDistancia) obj;
		return this.distancia == other.distancia;
	}
	
	
}
