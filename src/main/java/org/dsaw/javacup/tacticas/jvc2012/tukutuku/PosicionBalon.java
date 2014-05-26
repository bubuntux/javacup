package org.dsaw.javacup.tacticas.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.util.Position;



/**
 * Clase interna para guardar informaci�n sobre la posici�n de los jugadores
 * 
 *
 */
public class PosicionBalon 
{
    double x, y, z;

    public PosicionBalon() 
    {
        x = y = z = 0;
    }
    public PosicionBalon(PosicionBalon p) 
    {
        x = p.x;
        y = p.y;
        z = p.z;
    }
    public PosicionBalon(Position p) 
    {
        x = p.getX();
        y = p.getY();
        z = 0;
    }
    public PosicionBalon(double x, double y) 
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public PosicionBalon(double x, double y, double z) 
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    
    /**
     * Distancia con relacion al punto (0, 0)
     * @return
     */
    public double distancia() 
    {
        return this.distancia(new PosicionBalon());
    }
    
    /**
     * Distancia respecto a un punto
     * @param px Coordenada x del punto
     * @param py
     * @return
     */
    public double distancia(double px, double py) 
    {
        return Math.sqrt((x - px) * (x - px) + (y - py) * (y - py));
    }
    /**
     * Distancia respecto a otro objeto PosicionBalon
     * @param p
     * @return
     */
    public double distancia(PosicionBalon p) 
    {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
    
    /**
     * Distancia respecto a un objeto Position del framework
     * @param p
     * @return
     */
    public double distancia(Position p) 
    {
        return this.distancia(new PosicionBalon(p));
    }
    
    /**
     * Indica si la posicion actual est� fuera del campo de juego
     * @return
     */
    public boolean estaFuera()
    {
    	return !(new Position(x, y).isInsideGameField(0));
    }
    
    public Position getPosicion() 
    {
        return new Position(x,y);
    }
    
    public PosicionBalon getPosicionDesplazada(double dx, double dy) 
    {
        return new PosicionBalon(this.x + dx, this.y + dy);
    }

    public String toString() 
    {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}