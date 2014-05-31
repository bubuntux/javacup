/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Recta;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MaN
 */
public class MSGConstants {

    /*
     * Precion en los ejecutores.
     */
    public static final double PRECISION_COMPARAR_DISTANCIAS = 1E-6;
    /**
     * Mostrar ayuda visual.
     */
    public static final boolean VISUAL_DEBUG = false;
    /**
     * Escala para mostrar la ayuda visual.
     */
    public static final double VISUAL_DEBUG_SCALE = 5D;
    /**
     * Recta cuyas X es el valor de la velocidad y la Y es la velocidad real
     */
    public static final Recta VELOCIDAD_JUGADOR = new Recta(new Punto(0, 0.25), new Punto(1, 0.5));
    /**
     * Ancho de la portería.
     */
    public static final double ANCHO_PORTERIA_REAL = Constants.posteDerArcoSup.getX() - Constants.posteIzqArcoSup.getX() - Constants.RADIO_BALON;

    /**
     * Valor de la Y.
     */
    public static final double DISTANCIA_NORMALIZACION_TIRO = Constants.LARGO_CAMPO_JUEGO / 2;

    /**
     * Resolución en X
     */
    public static final Map<Integer, Double> DESMARQUE_RESOLUCION;

    static {
        DESMARQUE_RESOLUCION = new HashMap<Integer, Double>();
        DESMARQUE_RESOLUCION.put(1, 3D);
        DESMARQUE_RESOLUCION.put(2, 3D);
        DESMARQUE_RESOLUCION.put(3, 4D);
        DESMARQUE_RESOLUCION.put(4, 4D);
        DESMARQUE_RESOLUCION.put(5, 5D);
        DESMARQUE_RESOLUCION.put(6, 9D);
        DESMARQUE_RESOLUCION.put(7, 9D);
        DESMARQUE_RESOLUCION.put(8, 11D);
        DESMARQUE_RESOLUCION.put(9, 11D);
        DESMARQUE_RESOLUCION.put(10, 13D);
        DESMARQUE_RESOLUCION.put(11, 13D);
    }
    /**
     *
     * Indica si hay que usar hilos.
     */
    public static final boolean EXECUTE_THREADED = false;

    /**
     * Número de iteraciones de ventaja para que el delantero pase.
     */
    public static final double ITERACIONES_MIMIMAS_VENTAJA_DELANTERO = 3;

    /**
     * Número de iteraciones de ventaja para que el medio pase.
     */
    public static final double ITERACIONES_MIMIMAS_VENTAJA_MEDIO = 2;

    /**
     * Número de iteraciones de ventaja para que el defensa pase.
     */
    public static final double ITERACIONES_MIMIMAS_VENTAJA_DEFENSA = 8;

    /**
     * Número de iteraciones de ventaja para que el portero pase.
     */
    public static final double ITERACIONES_MIMIMAS_VENTAJA_PORTERO = 10;
}
