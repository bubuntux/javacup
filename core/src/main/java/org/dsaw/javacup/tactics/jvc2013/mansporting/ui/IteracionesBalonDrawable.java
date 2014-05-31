/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.JugadorBalonData;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;

import java.awt.*;

/**
 * @author MaN
 */
public class IteracionesBalonDrawable extends AbstractDrawable<JugadorBalonData> {

  public IteracionesBalonDrawable(JugadorBalonData element, Color color) {
    super(element, color);
  }

  /**
   * Radio.
   */
  private static final double RADIUS = 1;

  /**
   * Realiza el pintado del elemento.
   *
   * @param g gr�ficos para pintar.
   */
  @Override
  public void paint(VisualDebugGraphics g, Rectangulo canvas) {
    Color defaultColor = g.getColor();
    g.setColor(color);

    g.drawLine(
        element.getJugador().getPosicion().getX() + (canvas.getCentro().getX()
                                                     + canvas.getAncho() / 2),
        -element.getJugador().getPosicion().getY() + (canvas.getCentro().getY()
                                                      + canvas.getAlto() / 2),
        element.getPunto().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2),
        -element.getPunto().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2));
    double
        x =
        element.getPunto().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2) - RADIUS;
    double
        y =
        -element.getPunto().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2) - RADIUS;
    g.fillOval(x, y, RADIUS * 2, RADIUS * 2);
    g.setColor(defaultColor);
  }
}
