/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;

import java.awt.*;

/**
 * @author MaN
 */
public class SegmentoDrawable extends AbstractDrawable<Segmento> {

  /**
   * Constructor.
   *
   * @param canvas  canvas.
   * @param element elemento.
   */
  public SegmentoDrawable(Segmento element, Color color) {
    super(element, color);
  }

  @Override
  public void paint(VisualDebugGraphics g, Rectangulo canvas) {
    Color defaultColor = g.getColor();
    g.setColor(color);

    g.drawLine(
        element.getPuntoA().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2),
        -element.getPuntoA().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2),
        element.getPuntoB().getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2),
        -element.getPuntoB().getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2));

    g.setColor(defaultColor);
  }
}
