package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

/**
 * Objeto para almacenar en memoria un instante de la situación del partido.
 */
public class SituacionPartidoCache {

    // ATRIBUTOS =================================================================================//
    
    private Position balon = new Position();
    private double alturaBalon;
    private int golesMios, golesContrarios, iteracion;
    private boolean saco, sacaRival, atacando;
    private Position[] misJugadores = new Position[Utilitarios.NUMERO_JUGADORES];
    private Position[] rivales = new Position[Utilitarios.NUMERO_JUGADORES];
    private int[] iteracionesParaRematar = new int[Utilitarios.NUMERO_JUGADORES];
    private int[] iteracionesParaRematarRival = new int[Utilitarios.NUMERO_JUGADORES];
    private int[] puedenRematar = new int[Utilitarios.NUMERO_JUGADORES];
    private int[] puedenRematarRival = new int[Utilitarios.NUMERO_JUGADORES];

    
    // CONSTRUCTORES =============================================================================//
    
    public SituacionPartidoCache() {
	for (int i = 0; i < Utilitarios.NUMERO_JUGADORES; i++) {
	    misJugadores[i] = new Position();
	    rivales[i] = new Position();
	    iteracionesParaRematar[i] = 0;
	    iteracionesParaRematarRival[i] = 0;
	    puedenRematar[i] = i; // Al construir el objeto, todos los jugadores pueden rematar
	    puedenRematarRival[i] = i; // Al construir el objeto, todos los jugadores pueden rematar
	}
    }
    
    
    // MÉTODOS ===================================================================================//

    /**
     * Actualiza la situación del partido.
     * 
     * @param sp La situación del partido que se desea almacenar. 
     */
    public void update(GameSituations sp) {
	balon = sp.ballPosition();
	alturaBalon = sp.ballAltitude();
	golesMios = sp.myGoals();
	golesContrarios = sp.rivalGoals();
	iteracion = sp.iteration();
	saco = sp.isStarts();
	sacaRival = sp.isRivalStarts();
	atacando = Utilitarios.isAtacando(sp);
	for (int i = 0; i < Utilitarios.NUMERO_JUGADORES; i++) {
	    misJugadores[i] = sp.myPlayers()[i];
	    rivales[i] = sp.rivalPlayers()[i];
	    iteracionesParaRematar[i] = sp.iterationsToKick()[i];
	    iteracionesParaRematarRival[i] = sp.rivalIterationsToKick()[i];
	}
	puedenRematar = sp.canKick();
	puedenRematarRival = sp.rivalCanKick();
    }

    /**
     * Retorna la posicion de mis jugadores
     */
    public Position[] misJugadores() {
	return misJugadores;
    }

    /**
     * Retorna la posicion de los jugadores rivalPlayers
     */
    public Position[] rivales() {
	return rivales;
    }

    /**
     * Arreglo de indices de jugadores propios que pueden rematar
     */
    public int[] puedenRematar() {
	return puedenRematar;
    }

    /**
     * Arreglo de indices de jugadores rivalPlayers que pueden rematar
     */
    public int[] puedenRematarRival() {
	return puedenRematarRival;
    }

    /**
     * Retorna las iteraciones que restan para que mis jugadores puedan rematar
     */
    public int[] iteracionesParaRematar() {
	return iteracionesParaRematar;
    }

    /**
     * Retorna las iteraciones que restan para que los rivalPlayers puedan rematar
     */
    public int[] iteracionesParaRematarRival() {
	return iteracionesParaRematarRival;
    }

    /**
     * Retorna la posicion del ballPosition
     */
    public Position balon() {
	return balon;
    }

    /**
     * Retorna la altura del ballPosition
     */
    public double alturaBalon() {
	return alturaBalon;
    }

    /**
     * Retorna la cantidad de goles convertidos
     */
    public int golesPropios() {
	return golesMios;
    }

    /**
     * Retorna la cantidad de goles convertidos por el rival
     */
    public int golesRival() {
	return golesContrarios;
    }

    /**
     * Retorna true si tengo que realizar un saque
     */
    public boolean saco() {
	return saco;
    }

    /**
     * Retorna true si el rival realizara un saque
     */
    public boolean sacaRival() {
	return sacaRival;
    }

    /**
     * Retorna el numero de iteraciones cursadas en el partido, el total de 
     * iteraciones esta dado por Constantes.ITERACIONES.
     */
    public int iteracion() {
	return iteracion;
    }
    
    /**
     * Indica si estoy atacando.
     * 
     * @return <code>true</code> si estoy atacando, <code>false</code> en otro caso.
     */
    public boolean isAtacando(){
	return atacando;
    }
    
}
