/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGJugadorDetalle;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGPuntoTrayectoria;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGTrayectoria;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGUtils;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MaN
 */
public class JugadoresBalonThread<I> implements IdentificableCallable<I, JugadoresBalonData> {

    private final I id;
    private final List<MSGJugadorDetalle> jugadores;
    private final MSGTrayectoria trayectoria;

    public JugadoresBalonThread(I id, List<MSGJugadorDetalle> jugadores, MSGTrayectoria trayectoria) {
        this.id = id;
        this.jugadores = new LinkedList<MSGJugadorDetalle>(jugadores);
        this.trayectoria = trayectoria;
    }

    public I getId() {
        return id;
    }

    public JugadoresBalonData call() throws Exception {
        JuadoresBalonContainer result = new JuadoresBalonContainer();

        for (int i = 0; i <= trayectoria.getIteracionMax(); i++) {
            MSGPuntoTrayectoria punto = this.trayectoria.getPosicion(i);
            Double x = null;
            Double y = null;
            if (punto.getPunto().getX() < -Constants.ANCHO_CAMPO_JUEGO / 2) {
                x = -Constants.ANCHO_CAMPO_JUEGO / 2;
            } else if (punto.getPunto().getX() > Constants.ANCHO_CAMPO_JUEGO / 2) {
                x = Constants.ANCHO_CAMPO_JUEGO / 2;
            }

            if (punto.getPunto().getY() < -Constants.LARGO_CAMPO_JUEGO / 2) {
                y = -Constants.LARGO_CAMPO_JUEGO / 2;
            } else if (punto.getPunto().getY() > Constants.LARGO_CAMPO_JUEGO / 2) {
                y = Constants.LARGO_CAMPO_JUEGO / 2;
            }

            if (x != null || y != null) {
                Punto3D
                    puntoFueraCampo = new Punto3D(x == null ? punto.getPunto().getX() : x, y == null ? punto.getPunto().getY() : y, 0);
                handleMasCercano(result, true, puntoFueraCampo, i);
                return new JugadoresBalonData(result.contrario, result.propio);

            } else {
                for (MSGJugadorDetalle jugador : jugadores) {
                    if (MSGUtils.getAccesible(punto, i, jugador)) {
                        if (result.contrario == null && jugador.getRival()) {
                            result.contrario = new JugadorBalonData(jugador, false, i, punto.getPunto());
                        } else if(result.propio == null && !jugador.getRival()) {
                            result.propio = new JugadorBalonData(jugador, false, i, punto.getPunto());
                        }
                    }

                    if (result.contrario != null && result.propio != null) {
                        return new JugadoresBalonData(result.contrario, result.propio);
                    }
                }
            }
        }

        int i = trayectoria.getIteracionMax();
        handleMasCercano(result, false, trayectoria.getPosicion(i).getPunto(), i);
        return new JugadoresBalonData(result.contrario, result.propio);
    }

    private void handleMasCercano(JuadoresBalonContainer valores, boolean fueraCampo, Punto3D punto, int i) {

        if (valores.contrario != null && valores.propio != null) {
            return;
        }

        Double distanciaRival = null;
        MSGJugadorDetalle jugadorDetalleRival = null;

        Double distanciaPropio = null;
        MSGJugadorDetalle jugadorDetallePropio = null;

        for (MSGJugadorDetalle detalle : jugadores) {
            double d = TrigonometriaUtils.getDistancia(punto.getPuntoXY(), detalle.getPosicion());
            if (valores.contrario == null && detalle.getRival()) {
                if (jugadorDetalleRival == null || d < distanciaRival) {
                    distanciaRival = d;
                    jugadorDetalleRival = detalle;
                }
            } else if (valores.propio == null && !detalle.getRival()) {
                if (jugadorDetallePropio == null || d < distanciaPropio) {
                    distanciaPropio = d;
                    jugadorDetallePropio = detalle;
                }
            }
        }

        if (valores.contrario == null) {
            int iteracionesSumaRival = (int) Math.ceil(distanciaRival / jugadorDetalleRival.getAreaCubierta(1).getRadio());
            valores.contrario = new JugadorBalonData(jugadorDetalleRival, fueraCampo, iteracionesSumaRival, punto);
        }

        if (valores.propio == null) {
            int iteracionesSumPropio = (int) Math.ceil(distanciaPropio / jugadorDetallePropio.getAreaCubierta(1).getRadio());
            valores.propio = new JugadorBalonData(jugadorDetallePropio, fueraCampo, iteracionesSumPropio, punto);
        }
    }

    private final class JuadoresBalonContainer {

        private JugadorBalonData contrario = null;
        private JugadorBalonData propio = null;
    }
}
