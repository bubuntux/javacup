package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;

import java.awt.*;

/**
 * <br> Marcado para marcar determinados sitios. <br> <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class MarkerDrawable extends AbstractDrawable<Punto> {

  /**
   * Radio.
   */
  private static final double RADIUS = 1;

  /**
   * Constructor.
   *
   * @param canvas  canvas.
   * @param element elemento.
   */
  public MarkerDrawable(Punto element, Color color) {
    super(element, color);
  }

  /**
   * Realiza el pintado.
   *
   * @param g gr�ficos.
   */
  @Override
  public void paint(VisualDebugGraphics g, Rectangulo canvas) {
    Color oldColor = g.getColor();
    g.setColor(color);

    double x = element.getX() + (canvas.getCentro().getX() + canvas.getAncho() / 2) - RADIUS;
    double y = -element.getY() + (canvas.getCentro().getY() + canvas.getAlto() / 2) - RADIUS;
    g.drawOval(x, y, RADIUS * 2, RADIUS * 2);

    g.setColor(oldColor);
  }
}
