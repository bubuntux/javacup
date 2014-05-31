package org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades;

/**
 * Clase que representa la posici�n del bal�n en un instante dado.
 * 
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public class Balon {

	private double x;
	private double y;
	private double z;
	
	
	public Balon(double x, double y, double z) {
	
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}

	public String toString() {
		return "x: " + x + "\ny: " + y + "\nz: " + z;  
	}
}
