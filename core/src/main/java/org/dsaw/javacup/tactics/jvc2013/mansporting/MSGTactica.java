/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.ThreadOperationManager;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaManager;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MaN
 */
public class MSGTactica implements Tactic {

    /**
     * Log.
     */
    private static final Logger log = Logger.getLogger(MSGTactica.class.getName());
    /**
     * Detalle de la táctica.
     */
    private final TacticDetail tacticaDetalle;
    private final List<MSGJugadorPropio> jugadores;
    private MSGSituacionPartidoContext situacionPartidoContext;
    private final ThreadOperationManager threadOperationHandler;

    public MSGTactica() {
        jugadores = new LinkedList<MSGJugadorPropio>();
        jugadores.add(new MSGPortero(
                0,
                Color.BLACK,
                Color.WHITE,
                "Juan Pablo",
                1,
                new MSGEstadisticas.Builder().setPrecision(0.5).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGDefensa(
                1,
                Color.BLACK,
                Color.WHITE,
                "Cote",
                2,
                new MSGEstadisticas.Builder().setPrecision(0.4).setRemate(0.4).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGDefensa(
                2,
                Color.BLACK,
                Color.WHITE,
                "Gregory",
                3,
                new MSGEstadisticas.Builder().setPrecision(0.4).setRemate(0.4).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGDefensa(
                3,
                Color.BLACK,
                Color.WHITE,
                "Botía",
                4,
                new MSGEstadisticas.Builder().setPrecision(0.4).setRemate(0.4).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                4,
                Color.BLACK,
                Color.WHITE,
                "Lora",
                5,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                5,
                Color.BLACK,
                Color.WHITE,
                "Diego Castro",
                6,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                6,
                Color.BLACK,
                Color.WHITE,
                "Eguren",
                7,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                7,
                Color.BLACK,
                Color.WHITE,
                "Rivera",
                8,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                8,
                Color.BLACK,
                Color.WHITE,
                "Ayoze",
                9,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGMedioCentro(
                9,
                Color.BLACK,
                Color.WHITE,
                "Nacho Novo",
                10,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(0.8).setVelocidad(1).getMSGEstadisticas()));
        jugadores.add(new MSGDelantero(
                10,
                Color.BLACK,
                Color.WHITE,
                "Sangoy",
                11,
                new MSGEstadisticas.Builder().setPrecision(1).setRemate(1).setVelocidad(1).getMSGEstadisticas()));
        /*jugadores.add(new MSGTestJugador(10));*/
        tacticaDetalle = new MSGTacticaDetalle(jugadores);


        // Inicializamos los hilos
        threadOperationHandler = ThreadOperationManager.initInstance();

        // cargamos la clase.
        TrayectoriaManager.getInstance();
    }

    @Override
    protected void finalize() throws Throwable {
        // No mola hacerlo aquí pero no hay otra forma de controlar el ciclo de vida de la clase
        threadOperationHandler.realeaseInstance();
        super.finalize();
    }

    public synchronized MSGSituacionPartidoContext getSituacionPartidoContext() {
        if (situacionPartidoContext == null) {
            situacionPartidoContext = new MSGSituacionPartidoContext();
        }
        return situacionPartidoContext;
    }

    public synchronized TacticDetail getDetail() {
        return tacticaDetalle;
    }

    public List<Command> execute(GameSituations sp) {
        long start = System.currentTimeMillis();
        List<Command> comandos = new MSGComandoList();
        try {
            // 1) Recuperamos la alineación de este momento
            Map<Integer, MSGAlineacionPosicion> alineacion = MSGPosiciontHandlerManager.getPosicion(this, sp, null);

            // 2) Actualizamos el contexto con la situación del partido actual.
            this.getSituacionPartidoContext().update(this, alineacion, sp);

            // 3) Ejecutamos los comandos oportunos.
            for (MSGJugadorPropio jugador : jugadores) {
                List<Command> comando = jugador.ejecuta();
                if (comando != null) {
                    comandos.addAll(comando);
                }
            }
            return comandos;
        } catch (Throwable e) {
            log.log(Level.SEVERE, "Ha ocurrido un error en el procesamiento", e);
        } finally {
            long end = System.currentTimeMillis();
            long val = end - start;
            if (max == null || val > max) {
                max = val;
            }
            //log.log(Level.INFO, String.format("Se ha tardado [%s] ms en ejecutar la lógica, maximo [%s] ms", val, max));
        }
        return comandos;
    }
    private Long max = null;

    public Position[] getStartPositions(GameSituations sp) {
        Map<Integer, MSGAlineacionPosicion> alineacion = MSGPosiciontHandlerManager.getPosicion(this, sp, Boolean.TRUE);
        return MSGUtils.convertPosicion(alineacion);
    }

    public Position[] getNoStartPositions(GameSituations sp) {
        Map<Integer, MSGAlineacionPosicion> alineacion = MSGPosiciontHandlerManager.getPosicion(this, sp, Boolean.FALSE);
        return MSGUtils.convertPosicion(alineacion);
    }

    public List<MSGJugadorPropio> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public ThreadOperationManager getThreadOperationHandler() {
        return threadOperationHandler;
    }
}
