package org.dsaw.javacup.tactics.jvc2013.emandem.utils;

import org.dsaw.javacup.model.util.Position;

public class CirculoMarcacion {
    private double jugadorPosicionX;
    private double jugadorPosicionY; // coordenadas del centro
    private double radioZona;  // radio del c�rculo
    
    /** 
     * Crea un c�rculo a partir de su origen su radio.
     * @param jugadorPosicionX La coordenada x del centro del c�rculo.
     * @param jugadorPosicionY La coordenada y del centro del c�rculo.
     * @param radioZona El radio del c�rculo. Debe ser mayor o igual a 0.
     */
    public CirculoMarcacion(Position jugador, double radioAMarcar) {
        this.jugadorPosicionX=jugador.getX(); 
        this.jugadorPosicionY=jugador.getY();
        this.radioZona=radioAMarcar;
    }   
    
    /** 
     * C�lculo del �rea de este c�rculo.
     * @return El �rea (mayor o igual que 0) del c�rculo.
     */
     public double area() {
        return Math.PI*radioZona*radioZona;
     }
     
     /** 
      * Indica si un punto est� dentro del c�rculo.
      * @param px componente x del punto
      * @param py componente y del punto
      * @return true si el punto est� dentro del c�rculo o false en otro caso.
      */
     public boolean estoyMarcado(Position enemigo) {
        /* Calculamos la distancia de (px,py) al centro del c�rculo (x,y),
           que se obtiene como ra�z cuadrada de (px-x)^2+(py-y)^2 */
        double distanciaEnemigo = Math.sqrt((enemigo.getX()-jugadorPosicionX)*(enemigo.getX()-jugadorPosicionX)+(enemigo.getY()-jugadorPosicionY)*(enemigo.getY()-jugadorPosicionY));
        
        // el c�rculo contiene el punto si d es menor o igual al radio
        return  distanciaEnemigo <= radioZona;
     }

	public double getJugadorPosicionX() {
		return jugadorPosicionX;
	}

	public void setJugadorPosicionX(double jugadorPosicionX) {
		this.jugadorPosicionX = jugadorPosicionX;
	}

	public double getJugadorPosicionY() {
		return jugadorPosicionY;
	}

	public void setJugadorPosicionY(double jugadorPosicionY) {
		this.jugadorPosicionY = jugadorPosicionY;
	}
}