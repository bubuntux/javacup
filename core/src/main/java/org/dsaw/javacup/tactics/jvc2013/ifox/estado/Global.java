/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.estado;

import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Estado;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Mensaje;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Telegrama;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;

/**
 *
 * @author Usuario
 */
public class Global extends Estado<Jugador> {

    private Global() {
    }

    public static Global getInstance() {
        return GlobalHolder.INSTANCE;
    }

    @Override
    public void ejecutar(Jugador jugador) {
    }

    @Override
    public boolean mensajeRecibido(Jugador jugador, Telegrama telegrama) {
        Mensaje mensaje = telegrama.getMensaje();
        switch (mensaje) {
            case PASAR_BALON:
                Jugador solicitante = (Jugador) telegrama.getExtra();
                //Posicion posicionPase = jugador.getEquipo().calcularMejorPaseAReceptor(jugador, solicitante);
                //if (posicionPase != null) {
                    jugador.golpearBalon(solicitante.getPosicion(), false);
                //}
                return true;
            case RECIBIR_BALON:
                jugador.setPosicionPase((Position) telegrama.getExtra());
                return true;
            default:
                return false;
        }
    }

    private static class GlobalHolder {
        private static final Global INSTANCE = new Global();
    }
 }
