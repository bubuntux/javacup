package org.javahispano.javacup.tacticas.jvc2012.chr2012.tactica.utilidades;

import java.util.ArrayList;
import org.javahispano.javacup.model.util.Position;

/**
 * Clase que representa a los rivales, en la cual se almacenar� toda la informaci�n necesaria que se desee
 * conocer de ellos.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class Rival {

    private int indice;
    private Position posicion;
    private char sector;
    private ArrayList<Position> movimientos = new ArrayList<Position>();
    private Balon informacionAlcanceBalon;
    private int iteracionesAlcanceBalon;

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public ArrayList<Position> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<Position> movimientos) {
        this.movimientos = movimientos;
    }

    public Position getPosicion() {
        return posicion;
    }

    public void setPosicion(Position posicion) {
        this.posicion = posicion;
    }

    public char getSector() {
        return sector;
    }

    public void setSector(char sector) {
        this.sector = sector;
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
    
}
