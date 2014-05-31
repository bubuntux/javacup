/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGJugadorDetalle;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGJugadorPropio;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGSituacionPartidoContext;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGTrayectoria;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaManager;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MaN
 */
public class PaseThread implements IdentificableCallable<MSGJugadorPropio, PaseData> {

    private final MSGJugadorPropio jugadorOrigen;
    private final Punto3D balonEnPase;
    private final MSGJugadorPropio jugadorDestino;
    private final Punto3D puntoDestino;
    private final MSGSituacionPartidoContext context;

    public PaseThread(MSGJugadorPropio jugadorPasador, Punto3D balonEnPase, MSGJugadorPropio jugadorDestino, Punto3D puntoDestino, MSGSituacionPartidoContext context) {
        this.jugadorOrigen = jugadorPasador;
        this.balonEnPase = balonEnPase;
        this.puntoDestino = puntoDestino;
        this.jugadorDestino = jugadorDestino;
        this.context = context;
    }

    public MSGJugadorPropio getId() {
        return jugadorDestino;
    }

    public PaseData call() throws Exception {
        double distancia = TrigonometriaUtils.getDistancia(balonEnPase, puntoDestino);
        List<MSGJugadorDetalle> jugadores = new LinkedList<MSGJugadorDetalle>();
        jugadores.addAll(context.getJugadoresRivales().values());
        jugadores.add(jugadorDestino);

        TrayectoriaPunto candidato = TrayectoriaManager.getInstance().getPasesByDistancia(distancia);
        JugadoresBalonThread thread = new JugadoresBalonThread(
                candidato,
                jugadores,
                MSGTrayectoria.getInstance(candidato, jugadorOrigen, balonEnPase, puntoDestino));

        JugadoresBalonData threadResult = thread.call();
        return new PaseData(puntoDestino, candidato, threadResult);
    }
}
