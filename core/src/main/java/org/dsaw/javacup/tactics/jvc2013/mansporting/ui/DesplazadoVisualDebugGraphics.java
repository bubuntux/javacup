/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;

import java.awt.*;

/**
 * @author MaN
 */
public class DesplazadoVisualDebugGraphics implements VisualDebugGraphics {

  private final VisualDebugGraphics g;
  private final Punto desplazamiento;

  public DesplazadoVisualDebugGraphics(VisualDebugGraphics g, Punto desplazamiento) {
    this.g = g;
    this.desplazamiento = desplazamiento;
  }

  @Override
  public void setColor(Color color) {
    g.setColor(color);
  }

  @Override
  public Color getColor() {
    return g.getColor();
  }

  @Override
  public void fillRect(double x, double y, double width, double height) {
    g.fillRect(x + desplazamiento.getX(), y + desplazamiento.getY(), width, height);
  }

  @Override
  public void fillOval(double x, double y, double width, double height) {
    g.fillOval(x + desplazamiento.getX(), y + desplazamiento.getY(), width, height);
  }

  @Override
  public void drawRect(double x, double y, double width, double height) {
    g.drawRect(x + desplazamiento.getX(), y + desplazamiento.getY(), width, height);
  }

  @Override
  public void drawOval(double x, double y, double width, double height) {
    g.drawOval(x + desplazamiento.getX(), y + desplazamiento.getY(), width, height);
  }

  @Override
  public void drawArc(double x, double y, double width, double height, double startAngle,
                      double arcAngle) {
    g.drawArc(x + desplazamiento.getX(), y + desplazamiento.getY(), width, height, startAngle,
              arcAngle);
  }

  @Override
  public void drawLine(double x1, double y1, double x2, double y2) {
    g.drawLine(x1 + desplazamiento.getX(), y1 + desplazamiento.getY(), x2 + desplazamiento.getX(),
               y2 + desplazamiento.getY());
  }
}
