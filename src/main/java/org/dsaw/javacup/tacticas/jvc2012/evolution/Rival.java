package org.dsaw.javacup.tacticas.jvc2012.evolution;

import org.dsaw.javacup.model.util.Position;

public class Rival {

	private Integer id;
	private boolean marcado = false;
	private boolean tieneElBalon = false;
	private Position posicion;

	public Rival (int i){
		id = new Integer(i);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	public boolean isTieneElBalon() {
		return tieneElBalon;
	}

	public void setTieneElBalon(boolean tieneElBalon) {
		this.tieneElBalon = tieneElBalon;
	}

	public Position getPosicion() {
		return posicion;
	}

	public void setPosicion(Position posicion) {
		this.posicion = posicion;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
