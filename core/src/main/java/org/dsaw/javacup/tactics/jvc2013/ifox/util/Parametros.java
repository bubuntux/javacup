/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

/**
 *
 * @author Usuario
 */
public class Parametros {

    public class Equipo {

        public static final int INTENTOS_MARCAR_GOL = 5;
        public static final int NUM_POSICIONES_HORIZONTALES = 8;
        public static final int NUM_POSICIONES_VERTICALES = 16;
        public static final int REGIONES_ANCHO = 4;
        public static final int REGIONES_ALTO = 8;
    }

    public class Jugador {

        public static final double RANGO_OBJETIVO_CUADRADO = 1.5;
        public static final double AREA_SEGURA = 200.0;
        public static final double AREA_SEGURA_DEFENSA = 40.0;
        public static final double AREA_SEGURA_PORTERO = 900.0;
        public static final double VELOCIDAD_FINAL_REMATE = 0.6;
        public static final double DISTANCIA_REMATE_CUADRADA = 900.0;;
        public static final double DISTANCIA_MINIMA_PASE = 5.0;
    }
}
