package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.util.Position;

import java.awt.*;

/**
 * Implementación de PlayerDetail.
 */
public class JugadorImpl implements PlayerDetail {

    // ATRIBUTOS =================================================================================//
    
    private String nombre;
    private int numero;
    private Color piel, pelo;
    private double velocidad, remate, presicion;
    private boolean portero;
    private int[][] referenciasPase;
    private Position[] referenciasDrible;
    private boolean driblador;
    private boolean rematador;
    private boolean controlador;
    private Position proyeccionPase;
    private double radioMarcacion;

    
    // CONSTRUCTORES =============================================================================//
    
    public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
	    double velocidad, double remate, double presicion, boolean portero,
	    int[][] referenciasPase, Position[] referenciasDrible, boolean driblador,
	    boolean rematador, boolean controlador, Position proyeccionPase, double radioMarcacion) {
	this.nombre = nombre;
	this.numero = numero;
	this.piel = piel;
	this.pelo = pelo;
	this.velocidad = velocidad;
	this.remate = remate;
	this.presicion = presicion;
	this.portero = portero;
	this.referenciasPase = referenciasPase;
	this.referenciasDrible = referenciasDrible;
	this.driblador = driblador;
	this.rematador = rematador;
	this.controlador = controlador;
	this.proyeccionPase = proyeccionPase;
	this.radioMarcacion = radioMarcacion;
    }

    
    // MÉTODOS ===================================================================================//
    
    @Override
    public String getPlayerName() {
	return nombre;
    }

    @Override
    public Color getSkinColor() {
	return piel;
    }

    @Override
    public Color getHairColor() {
	return pelo;
    }

    @Override
    public int getNumber() {
	return numero;
    }

    @Override
    public boolean isGoalKeeper() {
	return portero;
    }

    @Override
    public double getSpeed() {
	return velocidad;
    }

    @Override
    public double getPower() {
	return remate;
    }

    @Override
    public double getPrecision() {
	return presicion;
    }
    
    /**
     * Indica si este jugador se caracteriza por controlar el balón.
     * 
     * @return <code>true</code> si este jugador se caracteriza por controlar el
     *         balón, <code>false</code> de lo contrario.
     */
    public boolean esControlador() {
	return controlador;
    }

    /**
     * Indica si este jugador se caracteriza por transportar el balón.
     * 
     * @return <code>true</code> si este jugador se caracteriza por transportar el
     *         balón, <code>false</code> de lo contrario.
     */
    public boolean esDriblador() {
	return driblador;
    }

    /**
     * Retorna las posiciones de la cancha a las que este jugador tiene como
     * referencias para driblar. Los primeros elementos son de mayor
     * preferencia que los ultimos.
     * 
     * @return Un arreglo con las posiciones de la cancha a las que este jugador
     *         tiene como referencias para driblar.
     */
    public Position[] getReferenciasDrible() {
	return referenciasDrible;
    }

    /**
     * Retorna los indices de los jugadores a los que este jugador tiene como
     * referencias para pasar el balón en cada uno de los cuartos de la cancha.
     * Para cada cuarto, los primeros elementos son de mayor preferencia que los
     * ultimos.
     * 
     * @return Un arreglo con los indices de los jugadores a los que este 
     *         jugador tiene como referencias para pasar el balón en cada uno de
     *         los cuartos de la cancha.
     */
    public int[][] getReferenciasPase() {
	return referenciasPase;
    }

    /**
     * Retorna los indices de los jugadores a los que este jugador tiene como
     * referencias para pasar el balón en un determinado cuarto de la cancha.
     * Los primeros elementos son de mayor preferencia que los ultimos.
     *
     * @param cuartoCancha El cuarto de la cancha en el que se encuentra el
     *        balón.
     * @return Un arreglo con los indices de los jugadores a los que este
     *         jugador tiene como referencias para pasar el balón en un cuarto
     *         determinado de la cancha.
     */
    public int[] getReferenciasPaseXCuarto(int cuartoCancha) {
        return referenciasPase[cuartoCancha];
    }

    /**
     * Indica si este jugador se caracteriza por disparar a la porteria.
     * 
     * @return <code>true</code> si este jugador se caracteriza por disparar a la
     *         porteria, <code>false</code> de lo contrario.
     */
    public boolean esRematador() {
	return rematador;
    }

    /**
     * Obtiene la variación de la posición en la cual este jugador recibe pases.
     * 
     * @return la variación de la posición en la cual este jugador recibe pases.
     */
    public Position getProyeccionPase() {
	return proyeccionPase;
    }

    /**
     * Retorna la distancia máxima de desplazamiento de este jugador para realizar una marcación.
     * 
     * @return la distancia máxima de desplazamiento de este jugador para realizar una marcación.
     */
    public double getRadioMarcacion() {
	return radioMarcacion;
    }
    
}
