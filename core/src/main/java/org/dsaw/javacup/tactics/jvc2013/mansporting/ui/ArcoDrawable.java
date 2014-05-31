/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Arco;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;

/**
 *
 * @author MaN
 */
public class ArcoDrawable extends AbstractDrawable<Arco> {

    public ArcoDrawable(Arco element, Color color) {
        super(element, color);
    }

    public void paint(VisualDebugGraphics g, Rectangulo canvas) {

        Punto puntoADesplazado = new Punto(
                element.getPuntoA().getX() - element.getCentro().getX(),
                element.getPuntoA().getY() - element.getCentro().getY());

        Punto puntoBDesplazado = new Punto(
                element.getPuntoB().getX() - element.getCentro().getX(),
                element.getPuntoB().getY() - element.getCentro().getY());

        double anguloA = TrigonometriaUtils.getAnguloVector(puntoADesplazado);
        double anguloB = TrigonometriaUtils.getAnguloVector(puntoBDesplazado);

        double angle = anguloB - anguloA;

        Color defaultColor = g.getColor();
        g.setColor(color);
        g.drawArc(
                element.getCentro().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2) - element.getCirculo().getRadio(),
                -element.getCentro().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2) - element.getCirculo().getRadio(),
                element.getCirculo().getRadio() * 2,
                element.getCirculo().getRadio() * 2,
                anguloA,
                angle);
        g.setColor(defaultColor);
    }
}
