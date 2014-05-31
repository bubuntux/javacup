/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.estado;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Estado;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Parametros;

/**
 *
 * @author Usuario
 */
public class ManejandoBalon extends Estado<Jugador> {

    private ManejandoBalon() {
    }

    public static ManejandoBalon getInstance() {
        return ManejandoBalonHolder.INSTANCE;
    }

    @Override
    public void ejecutar(Jugador jugador) {
        jugador.golpearBalon(Constants.centroArcoSup, true);

        jugador.regatear();

        Position posicionPase = jugador.getEquipo().buscarPase(jugador,
            Parametros.Jugador.DISTANCIA_MINIMA_PASE);
        if (posicionPase != null && posicionPase.isInsideGameField(0)) {
            jugador.golpearBalon(posicionPase, false);
        }

        jugador.getMaquinaEstado().cambiarEstado(Esperando.getInstance());
    }

    private static class ManejandoBalonHolder {
        private static final ManejandoBalon INSTANCE = new ManejandoBalon();
    }
 }
