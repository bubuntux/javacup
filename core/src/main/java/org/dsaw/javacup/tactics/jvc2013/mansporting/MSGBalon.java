/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;

/**
 *
 * @author MaN
 */
public class MSGBalon implements MSGSituacionPartidoListener {

    private Punto3D posicion;
    private double probabilidadControl;

    public MSGBalon() {
    }

    public void afterUpdate(MSGSituacionPartidoContext context) {
        GameSituations situacionPartido = context.getSituacionPartido();

        Punto3D punto = new Punto3D(
                situacionPartido.ballPosition().getX(),
                situacionPartido.ballPosition().getY(),
                situacionPartido.ballAltitude());
        this.probabilidadControl = MSGUtils.getProbabilidadControl(punto, this.posicion);
        this.posicion = punto;
    }

    public Punto3D getPosicion() {
        return posicion;
    }

    public double getProbabilidadControl() {
        return probabilidadControl;
    }

    @Override
    public String toString() {
        return "MSGSituacionBalon{posicion=[" + getPosicion() + "]}";
    }
}
