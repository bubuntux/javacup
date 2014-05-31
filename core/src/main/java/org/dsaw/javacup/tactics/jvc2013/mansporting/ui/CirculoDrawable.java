package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;

import java.awt.*;

/**
 * <br>
 * <br>
 * <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class CirculoDrawable extends AbstractDrawable<Circulo> {

    public CirculoDrawable(Circulo element, Color color) {
        super(element, color);
    }

    public void paint(VisualDebugGraphics g, Rectangulo canvas) {
        Color defaultColor = g.getColor();
        g.setColor(color);
        g.fillOval(
                element.getCentro().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2) - element.getRadio(),
                -element.getCentro().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2) - element.getRadio(),
                element.getRadio() * 2,
                element.getRadio() * 2);
        g.setColor(defaultColor);
    }
}
