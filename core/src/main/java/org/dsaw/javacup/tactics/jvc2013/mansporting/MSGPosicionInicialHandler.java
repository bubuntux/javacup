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
public class MSGPosicionInicialHandler implements MSGPosicionHandler {

    public final static Map<Integer, MSGAlineacionPosicion> alineacionSaco = new HashMap<Integer, MSGAlineacionPosicion>();
    public final static Map<Integer, MSGAlineacionPosicion> alineacionRecive = new HashMap<Integer, MSGAlineacionPosicion>();

    static {
        // 1) Portero
        alineacionSaco.put(0, MSGAlineacionPosicion.getPointInstance(new Punto(Constants.centroArcoInf.getX(), Constants.centroArcoInf.getY() + 1)));

        // 2) Primera linea de defensas.
        alineacionSaco.put(1, MSGAlineacionPosicion.getPointInstance(new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, -3 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(2, MSGAlineacionPosicion.getPointInstance(new Punto(0, -3 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(3, MSGAlineacionPosicion.getPointInstance(new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, -3 * Constants.LARGO_CAMPO_JUEGO / 8)));

        // 3) Primera linea de medios
        alineacionSaco.put(4, MSGAlineacionPosicion.getPointInstance(new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, -2 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(5, MSGAlineacionPosicion.getPointInstance(new Punto(0, -2 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(6, MSGAlineacionPosicion.getPointInstance(new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, -2 * Constants.LARGO_CAMPO_JUEGO / 8)));

        // 4) Segunda linea de medios
        alineacionSaco.put(7, MSGAlineacionPosicion.getPointInstance(new Punto(-2 * Constants.ANCHO_CAMPO_JUEGO / 6, -1 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(8, MSGAlineacionPosicion.getPointInstance(new Punto(0, -1 * Constants.LARGO_CAMPO_JUEGO / 8)));
        alineacionSaco.put(9, MSGAlineacionPosicion.getPointInstance(new Punto(2 * Constants.ANCHO_CAMPO_JUEGO / 6, -1 * Constants.LARGO_CAMPO_JUEGO / 8)));

        // 5) Delanteros
        alineacionSaco.put(10, MSGAlineacionPosicion.getPointInstance(new Punto(0, 0)));

        alineacionRecive.putAll(alineacionSaco);
    }

    public Map<Integer, MSGAlineacionPosicion> getPosicion(MSGTactica tactica, GameSituations sp, Boolean saco) {
        return saco ? alineacionSaco : alineacionRecive;
    }
}
