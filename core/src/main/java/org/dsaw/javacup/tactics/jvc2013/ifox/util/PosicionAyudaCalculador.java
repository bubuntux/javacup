/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Equipo;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PosicionAyudaCalculador {

    private static final double FRECUENCIA = 1.0;
    private static final double PUNTUACION_PASE_SEGURO = 2.0;
    private static final double PUNTUACION_POSIBLE_GOL = 1.0;
    private static final double PUNTUACION_LEJOS_PASADOR = 2.0;
    private static final double DISTANCIA_PASE_OPTIMO = 25.0;
    private Equipo equipo;
    private List<PosicionAyuda> posiciones;
    private PosicionAyuda mejorPosicion;
    private ReguladorTiempo regulador;

    public PosicionAyudaCalculador(Equipo equipo, int numX, int numY) {
        this.equipo = equipo;

        posiciones = new ArrayList<PosicionAyuda>();

        double regionAncho = Constants.ANCHO_CAMPO_JUEGO * 0.8;
        double regionAlto = Constants.LARGO_CAMPO_JUEGO * 0.9;
        double divisionX = regionAncho / numX;
        double divisionY = regionAlto / numY;

        double izquierda = -Constants.ANCHO_CAMPO_JUEGO / 2.0
                + (Constants.ANCHO_CAMPO_JUEGO - regionAncho) / 2.0
                + divisionX / 2.0;
        double arriba = regionAlto / 2.0
                - (divisionY / 2.0);

        for (int x = 0; x < numX; x++) {
            for (int y = 0; y < (numY / 2) - 1; y++) {
                Vector2d posicion = new Vector2d(izquierda + x * divisionX,
                        arriba - y * divisionY);
                PosicionAyuda posicionAyuda = new PosicionAyuda(posicion, 0.0);
                posiciones.add(posicionAyuda);
            }
        }

        regulador = new ReguladorTiempo(FRECUENCIA);
    }

    public Vector2d calcular(Jugador jugadorAAyudar) {
        if (!regulador.isListo() && mejorPosicion != null) {
            return mejorPosicion.getPosicion();
        }

        mejorPosicion = null;
        double mejorPuntuacion = 0.0;
        for (PosicionAyuda posicionAyuda : posiciones) {
            posicionAyuda.setPuntuacion(1.0);

            if (equipo.isPaseSeguroDeTodoOponente(equipo.getJugadorAAyudar(),
                    null, posicionAyuda.getPosicion().toPosicion())) {
                posicionAyuda.aumentarPuntuacion(PUNTUACION_PASE_SEGURO);
            }

            Jugador jugadorAyudante = equipo.getJugadorAyudante();
            if (jugadorAyudante != null) {
                if (equipo.puedeRematar(equipo.getJugadorAyudante(), 1.0, Parametros.Jugador.DISTANCIA_REMATE_CUADRADA) != null) {
                    posicionAyuda.aumentarPuntuacion(PUNTUACION_POSIBLE_GOL);
                }

                double distancia = equipo.getJugadorAAyudar().getPosicion()
                        .distance(posicionAyuda.getPosicion().toPosicion());
                double radio = Math.abs(DISTANCIA_PASE_OPTIMO - distancia);
                if (radio < DISTANCIA_PASE_OPTIMO) {
                    posicionAyuda.aumentarPuntuacion(PUNTUACION_LEJOS_PASADOR
                            * (DISTANCIA_PASE_OPTIMO - radio)
                            / DISTANCIA_PASE_OPTIMO);
                }
            }

            if (posicionAyuda.getPuntuacion() > mejorPuntuacion) {
                mejorPuntuacion = posicionAyuda.getPuntuacion();
                mejorPosicion = posicionAyuda;
            }
        }
        return mejorPosicion.getPosicion();
    }

    public Vector2d getMejorPosicion(Jugador jugadorAAyudar) {
        if (mejorPosicion != null) {
            return mejorPosicion.getPosicion();
        } else {
            return calcular(jugadorAAyudar);
        }
    }

    class PosicionAyuda {

        private Vector2d posicion;
        private double puntuacion;

        public PosicionAyuda() {
        }

        public PosicionAyuda(Vector2d posicion, double puntuacion) {
            this.posicion = posicion;
            this.puntuacion = puntuacion;
        }

        public void aumentarPuntuacion(double puntuacion) {
            this.puntuacion += puntuacion;
        }

        public void disminuirPuntuacion(double puntuacion) {
            this.puntuacion -= puntuacion;
        }

        public Vector2d getPosicion() {
            return posicion;
        }

        public void setPosicion(Vector2d posicion) {
            this.posicion = posicion;
        }

        public double getPuntuacion() {
            return puntuacion;
        }

        public void setPuntuacion(double puntuacion) {
            this.puntuacion = puntuacion;
        }
    }
}
