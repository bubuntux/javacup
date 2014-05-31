/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MaN
 */
public class MSGDefaultPositionHandler implements MSGPosicionHandler {

    private final static Map<Integer, MSGAlineacionPosicion> alineacion = new HashMap<Integer, MSGAlineacionPosicion>();

    static {
        alineacion.putAll(MSGPosicionInicialHandler.alineacionSaco);

        // 1) Primera linea de defensas.
        alineacion.put(1, MSGAlineacionPosicion.getAreaInstance(
                new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, -3 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(2, MSGAlineacionPosicion.getAreaInstance(
                new Punto(0, -3 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(3, MSGAlineacionPosicion.getAreaInstance(
                new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, -3 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));

        // 2) Primera linea de medios
        alineacion.put(4, MSGAlineacionPosicion.getAreaInstance(
                new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, -1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(5, MSGAlineacionPosicion.getAreaInstance(
                new Punto(0, -1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(6, MSGAlineacionPosicion.getAreaInstance(
                new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, -1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));

        // 3) Segunda linea de medios
        alineacion.put(7, MSGAlineacionPosicion.getAreaInstance(
                new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, 1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(8, MSGAlineacionPosicion.getAreaInstance(
                new Punto(0, 1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));
        alineacion.put(9, MSGAlineacionPosicion.getAreaInstance(
                new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, 1 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO / 3,
                Constants.LARGO_CAMPO_JUEGO / 4));

        // 4) delanteros        
        alineacion.put(10, MSGAlineacionPosicion.getAreaInstance(
                new Punto(0, 3 * Constants.LARGO_CAMPO_JUEGO / 8),
                Constants.ANCHO_CAMPO_JUEGO,
                Constants.LARGO_CAMPO_JUEGO / 4));

    }

    public Map<Integer, MSGAlineacionPosicion> getPosicion(MSGTactica tactica, GameSituations sp, Boolean saco) {
        return alineacion;
    }
}
