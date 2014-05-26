package org.dsaw.javacup.tacticas.jvc2012.chr2012.tactica.utilidades;

import java.util.Hashtable;


/**
 * Clase que representa los jugadores de mi equipo, en la cual se almacenar� toda la informaci�n necesaria que
 * se desee conocer de ellos.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class Jugador {

    private int indice;
    private Zona zona;
    private Rival marcaje;
    private double distanciaGolpeoBajo;
    private Hashtable<Integer, Double> tablaGolpeoBajo;
	private double distanciaGolpeoAlto;
    private double fuerzaGolpeoAvanzar;
    private Balon informacionAlcanceBalon;
    private int iteracionesAlcanceBalon;
    private Double ySinBalon; 
    
    
    // El ángulo del jugador para realizar un disparo.
    private double anguloTiro;

    
    // Método que dada una distancia, devuelve la fuerza necesaria para envía el balón mediante una pase bajo.
    public double getFuerzaGolepoBajo (double distancia) {
    	
    	double fuerza = 0;
    	
    	if (distancia >= distanciaGolpeoBajo) {
    		fuerza = 1;
    	}
    	else {

    		// Redondeamos hacia abajo el valor double.
    		int distAux = (int)distancia;
	    	while (distAux > 0) {
	    		
	    		if (tablaGolpeoBajo.containsKey(distAux)) {
	    			
	    			fuerza = tablaGolpeoBajo.get(distAux);
	    			distAux = -1;
	    		}
	    		
	    		distAux --;
	    	}
	    	
	    	while (fuerza == 0 && (distAux < (int)distanciaGolpeoBajo)) {
	    		
	    		if (tablaGolpeoBajo.containsKey(distAux)) {
	    			
	    			fuerza = tablaGolpeoBajo.get(distAux);
	    			distAux = (int)distanciaGolpeoBajo;
	    		}
	    		
	    		distAux ++;
	    	}
    	}
    	
    	
    	
    	return fuerza;
    }
    
    
    public double getDistanciaGolpeoAlto() {
        return distanciaGolpeoAlto;
    }

    public void setDistanciaGolpeoAlto(double distanciaGolpeoAlto) {
        this.distanciaGolpeoAlto = distanciaGolpeoAlto;
    }

    public double getDistanciaGolpeoBajo() {
        return distanciaGolpeoBajo;
    }

    public void setDistanciaGolpeoBajo(double distanciaGolpeoBajo) {
        this.distanciaGolpeoBajo = distanciaGolpeoBajo;
    }

    public Hashtable<Integer, Double> getTablaGolpeoBajo() {
		return tablaGolpeoBajo;
	}

	public void setTablaGolpeoBajo(Hashtable<Integer, Double> tablaGolpeoBajo) {
		this.tablaGolpeoBajo = tablaGolpeoBajo;
	}
    
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Rival getMarcaje() {
        return marcaje;
    }

    public void setMarcaje(Rival marcaje) {
        this.marcaje = marcaje;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

	public double getAnguloTiro() {
		return anguloTiro;
	}

	public void setAnguloTiro(double anguloTiro) {
		this.anguloTiro = anguloTiro;
	}

	public Balon getInformacionAlcanceBalon() {
		return informacionAlcanceBalon;
	}

	public void setInformacionAlcanceBalon(Balon informacionAlcanceBalon) {
		this.informacionAlcanceBalon = informacionAlcanceBalon;
	}

	public int getIteracionesAlcanceBalon() {
		return iteracionesAlcanceBalon;
	}

	public void setIteracionesAlcanceBalon(int iteracionesAlcanceBalon) {
		this.iteracionesAlcanceBalon = iteracionesAlcanceBalon;
	}

	public double getFuerzaGolpeoAvanzar() {
		return fuerzaGolpeoAvanzar;
	}

	public void setFuerzaGolpeoAvanzar(double fuerzaGolpeoAvanzar) {
		this.fuerzaGolpeoAvanzar = fuerzaGolpeoAvanzar;
	}

	public Double getYSinBalon() {
		return ySinBalon;
	}

	public void setYSinBalon(Double sinBalon) {
		ySinBalon = sinBalon;
	}

}