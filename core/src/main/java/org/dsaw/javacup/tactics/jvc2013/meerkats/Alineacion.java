package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.awt.*;
import java.util.LinkedList;

/**
 * Clase que controla los comportamientos grupales del equipo.
 */
public class Alineacion {

    // CONSTANTES ================================================================================//
    
    public static final int INDICE_ARQUERO = 0;
    public static final int INDICE_CENTRAL_I = 1;
    public static final int INDICE_LIBERO = 2;
    public static final int INDICE_CENTRAL_D = 3;
    public static final int INDICE_LATERAL_D = 4;
    public static final int INDICE_LATERAL_I = 5;
    public static final int INDICE_VOLANTE_I = 6;
    public static final int INDICE_VOLANTE_D = 7;
    public static final int INDICE_DELANTERO = 8;
    public static final int INDICE_ARMADOR = 9;
    public static final int INDICE_CONTENCION = 10;

    /**
     * Arquero. Juega al pase con los laterales y en salidas largas con el 
     * armador o el delantero. No es driblador ni rematador ni controlador.
     */
    public static final JugadorImpl ARQUERO = new JugadorImpl("Van der Saar", 1, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 1.0d, 0.67d, 1.0d, true,
	    new int[][]{{INDICE_LATERAL_D, INDICE_LATERAL_I, INDICE_ARMADOR, INDICE_DELANTERO}, 
		        {},
			{},
			{}}, 
	    new Position[]{}, false, false, false, new Position(0, 0), 0);

    /**
     * Central Izquierdo. Juega al pase con el lateral de su costado, con el
     * volante de contención, con el volante de su costado, con el armador y en 
     * salidas largas con el delantero. No es driblador ni rematador ni controlador.
     */
    public static final JugadorImpl CENTRAL_I = new JugadorImpl("Ivan R. Cordoba", 2, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 1.0d, 0.67d, 0.65d, false,
	    new int[][]{{INDICE_ARMADOR, INDICE_LATERAL_I, INDICE_VOLANTE_I, INDICE_DELANTERO, INDICE_CONTENCION},
			{INDICE_ARMADOR, INDICE_LATERAL_I, INDICE_VOLANTE_I, INDICE_DELANTERO, INDICE_CONTENCION}, 
			{}, 
			{}}, 
	    new Position[]{}, false, false, false, new Position(0, 0), 11);

    /**
     * Arquero. Juega al pase con los laterales y en salidas largas con el 
     * armador o el delantero. No es driblador ni rematador ni controlador.
     */
    public static final JugadorImpl LIBERO = new JugadorImpl("Mario A. Yepes", 3, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 1.0d, 0.5d, 0.7d, false,
	    new int[][]{{INDICE_ARMADOR, INDICE_LATERAL_I, INDICE_LATERAL_D, INDICE_DELANTERO},
			{INDICE_ARMADOR, INDICE_LATERAL_I, INDICE_LATERAL_D, INDICE_CONTENCION, INDICE_DELANTERO}, 
			{}, 
			{}}, 
	    new Position[]{}, false, false, false, new Position(0, 0), 0);

    /**
     * Central Derecho. Juega al pase con el lateral de su costado, con el
     * volante de contención, con el volante de su costado, con el armador y en 
     * salidas largas con el delantero. No es driblador ni rematador ni controlador.
     */
    public static final JugadorImpl CENTRAL_D = new JugadorImpl("Caceres", 4, new Color(0, 0, 204), 
	    new Color(0, 0, 204), 1.0d, 0.67d, 0.65d, false,
	    new int[][]{{INDICE_ARMADOR, INDICE_LATERAL_D, INDICE_VOLANTE_D, INDICE_DELANTERO, INDICE_CONTENCION},
			{INDICE_ARMADOR, INDICE_LATERAL_D, INDICE_VOLANTE_D, INDICE_DELANTERO, INDICE_CONTENCION}, 
			{}, 
			{}}, 
	    new Position[]{}, false, false, false, new Position(0, 0), 11);

    /**
     * Lateral Derecho. Juega al pase con el volante de su costado, con el
     * armador o con el delantero y en cambios de frente con el lateral del
     * costado contrario. Driblador con un único punto de referencia al costado 
     * derecho del área rival. Rematador. No controlador.
     */
    public static final JugadorImpl LATERAL_D = new JugadorImpl("Cafu", 5, new Color(0, 0, 204), 
	    new Color(0, 0, 204), 0.97d, 0.67d, 1.0d, false,
	    new int[][]{{INDICE_VOLANTE_D, INDICE_ARMADOR, INDICE_DELANTERO, INDICE_LATERAL_I},
			{INDICE_VOLANTE_D, INDICE_ARMADOR, INDICE_DELANTERO, INDICE_LATERAL_I},
			{INDICE_ARMADOR, INDICE_DELANTERO, INDICE_VOLANTE_D, INDICE_LATERAL_I},
			{INDICE_DELANTERO}}, 
	    new Position[]{new Position(26.6, 40.1)}, true, true, false, new Position(0, 5), 10);

    /**
     * Lateral Izquierdo. Juega al pase con el volante de su costado, con el
     * armador o con el delantero y en cambios de frente con el lateral del
     * costado contrario. Driblador con un único punto de referencia al costado 
     * izquierdo del área rival. Rematador. No controlador.
     */
    public static final JugadorImpl LATERAL_I = new JugadorImpl("Roberto Carlos", 6, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 1.0d, 0.67d, 1.0d, false,
	    new int[][]{{INDICE_VOLANTE_I, INDICE_ARMADOR, INDICE_DELANTERO, INDICE_LATERAL_D},
			{INDICE_VOLANTE_I, INDICE_ARMADOR, INDICE_DELANTERO, INDICE_LATERAL_D},
			{INDICE_ARMADOR, INDICE_DELANTERO, INDICE_VOLANTE_I, INDICE_LATERAL_D},
			{INDICE_DELANTERO}}, 
	    new Position[]{new Position(-26.6, 40.1)}, true, true, false, new Position(0, 5), 10);

    /**
     * Volante Izquierdo. Juega al pase con el lateral de su costado, con el
     * armador o con el delantero y en cambios de frente con el lateral del
     * costado contrario. Driblador con un único punto de referencia a 3 / 4 de 
     * cancha por su costado. No es rematador. Controlador.
     */
    public static final JugadorImpl VOLANTE_I = new JugadorImpl("Steven Gerrard", 7, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 0.8d, 0.67d, 1.0d, false,
	    new int[][]{{INDICE_LATERAL_I, INDICE_LATERAL_D, INDICE_DELANTERO, INDICE_ARMADOR},
			{INDICE_LATERAL_I, INDICE_ARMADOR, INDICE_DELANTERO},
			{INDICE_LATERAL_I, INDICE_ARMADOR, INDICE_VOLANTE_D, INDICE_DELANTERO}, 
			{}}, 
	    new Position[]{new Position(-16.6, 36)}, true, true, true, new Position(0, 0), 10);

    /**
     * Volante Derecho. Juega al pase con el lateral de su costado, con el
     * armador o con el delantero y en cambios de frente con el lateral del
     * costado contrario. Driblador con un único punto de referencia a 3 / 4 de 
     * cancha por su costado. No es rematador. Conrolador.
     */
    public static final JugadorImpl VOLANTE_D = new JugadorImpl("Fredy Grisales", 8, 
	    new Color(0, 0, 204), new Color(0, 0, 204), 0.8d, 0.67d, 1.0d, false,
	    new int[][]{{INDICE_LATERAL_D, INDICE_LATERAL_I, INDICE_DELANTERO, INDICE_ARMADOR},
			{INDICE_LATERAL_D, INDICE_ARMADOR, INDICE_DELANTERO},
			{INDICE_LATERAL_D, INDICE_ARMADOR, INDICE_VOLANTE_I, INDICE_DELANTERO}, 
			{}}, 
	    new Position[]{new Position(16.6, 36)}, true, true, true, new Position(0, 0), 10);

    /**
     * Delantero. Juega al pase con el armador o con los laterales. Driblador
     * con tres puntos de referencia al área rival. Rematador. No controlador.
     */
    public static final JugadorImpl DELANTERO = new JugadorImpl("Diego Forlan", 9, new Color(0, 0, 204), 
	    new Color(0, 0, 204), 1.0d, 1.0d, 1.0d, false,
	    new int[][]{{},
			{INDICE_VOLANTE_I, INDICE_VOLANTE_D, INDICE_ARMADOR},
			{INDICE_ARMADOR}, 
			{}}, 
	    new Position[]{Constants.centroArcoSup, new Position(-11.8, 47), new Position(11.8, 47)}, true, true, true, new Position(10, 0), 0);

    /**
     * Armador. Juega al pase con el delantero, con los laterales o con los
     * volantes. Driblador con tres puntos de referencia al borde del área 
     * rival. Rematador. Controlador.
     */
    public static final JugadorImpl ARMADOR = new JugadorImpl("Carlos Valderrama", 10, 
	    new Color(0, 0, 204), 
	    new Color(0, 0, 204), 0.73d, 1.0d, 1.0d, false,
	    new int[][]{{},
			{INDICE_DELANTERO, INDICE_VOLANTE_D, INDICE_VOLANTE_I},
			{INDICE_DELANTERO, INDICE_LATERAL_D, INDICE_LATERAL_I, INDICE_VOLANTE_D, INDICE_VOLANTE_I}, 
			{}}, 
	    new Position[]{Constants.penalSup, new Position(20, 36), new Position(-20, 36)}, true, true, true, new Position(5, 0), 12);
    
    /**
     * Volante de Contención. Juega al pase con los volantes, con el armador o
     * con el delantero. No es dribaldor ni rematador ni controlador.
     */
    public static final JugadorImpl CONTENCION = new JugadorImpl("Leonel Alvarez", 16, new Color(0, 0, 204), 
	    new Color(0, 0, 204), 0.68d, 0.67d, 0.65d, false,
	    new int[][]{{INDICE_VOLANTE_D, INDICE_VOLANTE_I, INDICE_ARMADOR, INDICE_DELANTERO, INDICE_LATERAL_D, INDICE_LATERAL_I},
			{INDICE_LATERAL_D, INDICE_LATERAL_I, INDICE_VOLANTE_D, INDICE_VOLANTE_I},
			{INDICE_ARMADOR, INDICE_VOLANTE_D, INDICE_VOLANTE_I, INDICE_DELANTERO}, 
			{}}, 
	    new Position[]{}, false, false, false, new Position(0, 0), 12);
    
    /**
     * Alineación.
     */
    public static final JugadorImpl[] alineacion = new JugadorImpl[]{
		    ARQUERO,
		    CENTRAL_I,
		    LIBERO,
		    CENTRAL_D,
		    LATERAL_D,
		    LATERAL_I,
		    VOLANTE_I,
		    VOLANTE_D,
		    DELANTERO,
		    ARMADOR,
		    CONTENCION
		};
    
    
    // ATRIBUTOS =================================================================================//
    
    /** Posiciones de los jugadores dentro de la alineación */
    private Position[] posiciones;

    
    // CONSTRUCTORES =============================================================================//
    
    /**
     * Constructor a partir de posiciones de los jugadores en la alineación.
     * @param posiciones ubicación de los jugadores en la alineación.
     */
    public Alineacion(Position[] posiciones) {
	this.posiciones = posiciones;
    }


    // MÉTODOS ===================================================================================//

    /**
     * Ordena a los jugadores tomar posición de acuerdo a la alineación elegida.
     *
     * @param comandos Lista de comandos que será modificada
     * @return Objeto {@link Comando } que contiene las ordenes para que los jugadores tomen posiciones.
     */
    public LinkedList<Command> alinearEquipo(GameSituations sp, LinkedList<Command> comandos, boolean atacando) {
	for (int i = 0; i < posiciones.length; i++) {
	    Position posicionJugador = sp.myPlayers()[i];
	    Position posicionDestino = getPosiciones()[i];
	    if(atacando && (posicionJugador.getY() > posicionDestino.getY())) {
		posicionDestino = new Position(posicionDestino.getX(), posicionJugador.getY());
	    } else if(!atacando && (posicionJugador.getY() < posicionDestino.getY())) {
		posicionDestino = new Position(posicionDestino.getX(), posicionJugador.getY());
	    }  
	    comandos.add(new CommandMoveTo(i, posicionDestino));
	}
	return comandos;
    }
    
    /**
     * Obtiene el arreglo de las posciones de los jugadores dentro de la alineación.
     * 
     * @return Las posciones de los jugadores dentro de la alineación
     */
    public Position[] getPosiciones() {
	return posiciones;
    }

    /**
     * Asigna el arreglo de las posiciones de los jugadores dentro de la alineación.
     * 
     * @param posiciones Arreglo de objetos {@link Posicion} que representa posiciones de los jugadores 
     * dentro de la alineación.
     */
    public void setPosiciones(Position[] posiciones) {
	this.posiciones = posiciones;
    }

    
    // MÉTODOS ESTÁTICOS =========================================================================//
    
    /**
     * Obtiene el arreglo con los detalles de los jugadores.
     * @return Arreglo de objetos {@link JugadorImpl} que representa los detalles de los jugadores.
     */
    public static JugadorImpl[] getJugadores() {
	return alineacion;
    }
    
}
