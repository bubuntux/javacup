package org.dsaw.javacup.tactics.jvc2012.chr2012.tactica.utilidades;

/**
 * Clase que representa la posici�n del bal�n en un instante dado.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
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
