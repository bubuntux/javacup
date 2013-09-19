package org.javahispano.javacup.tacticas.jvc2012.evolution;

import org.javahispano.javacup.model.util.Position;

public class Zona {

	private int id;
	
	private Position abajoDerecha;
	
	private Position abajoIzquierda;
	
	private Position arribaDerecha;
	
	private Position arribaIzquierda;
	
	private Position centro;

	public Zona (int id){
		this.id=id;
	}
	
	
	
	public Zona(int id, Position abajoDerecha, Position abajoIzquierda,
			Position arribaDerecha, Position arribaIzquierda) {
		super();
		this.id = id;
		this.abajoDerecha = abajoDerecha;
		this.abajoIzquierda = abajoIzquierda;
		this.arribaDerecha = arribaDerecha;
		this.arribaIzquierda = arribaIzquierda;
		this.centro = calcularCentro();
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Position getAbajoDerecha() {
		return abajoDerecha;
	}

	public void setAbajoDerecha(Position abajoDerecha) {
		this.abajoDerecha = abajoDerecha;
	}

	public Position getAbajoIzquierda() {
		return abajoIzquierda;
	}

	public void setAbajoIzquierda(Position abajoIzquierda) {
		this.abajoIzquierda = abajoIzquierda;
	}

	public Position getArribaDerecha() {
		return arribaDerecha;
	}

	public void setArribaDerecha(Position arribaDerecha) {
		this.arribaDerecha = arribaDerecha;
	}

	public Position getArrivaIzquierda() {
		return arribaIzquierda;
	}

	public void setArrivaIzquierda(Position arribaIzquierda) {
		this.arribaIzquierda = arribaIzquierda;
	}

	public Position getCentro() {
		return centro;
	}

	public void setCentro(Position centro) {
		this.centro = centro;
	}
	
	private Position calcularCentro(){	
		return Position.medium(Position.medium(abajoDerecha, abajoIzquierda), Position.medium(arribaDerecha, arribaIzquierda));
	}
	
	public boolean estaEnZona(Position posicion){
		
		if(posicion.getX()<=getAbajoDerecha().getX() && posicion.getX()>=getAbajoIzquierda().getX()
				&& posicion.getY()>=getAbajoDerecha().getY() && posicion.getY()<=getArribaDerecha().getY()){
			return true;
		}else{
			return false;
		}
		
	}
	
}
