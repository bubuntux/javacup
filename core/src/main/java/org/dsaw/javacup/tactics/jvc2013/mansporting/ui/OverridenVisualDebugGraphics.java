/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;

/**
 * @author MaN
 */
public class OverridenVisualDebugGraphics implements VisualDebugGraphics {

  private final Graphics g;

  public OverridenVisualDebugGraphics(Graphics g) {
    this.g = g;
  }

  @Override
  public void fillRect(double x, double y, double width, double height) {
    g.fillRect((int) x, (int) y, (int) width, (int) height);
  }

  @Override
  public void fillOval(double x, double y, double width, double height) {
    g.fillOval((int) x, (int) y, (int) width, (int) height);
  }

  @Override
  public void drawRect(double x, double y, double width, double height) {
    g.drawRect((int) x, (int) y, (int) width, (int) height);
  }

  @Override
  public void drawOval(double x, double y, double width, double height) {
    g.drawOval((int) x, (int) y, (int) width, (int) height);
  }

  @Override
  public void drawLine(double x1, double y1, double x2, double y2) {
    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
  }

  @Override
  public void drawArc(double x, double y, double width, double height, double startAngle,
                      double arcAngle) {
    startAngle = TrigonometriaUtils.radianToSexagesimal(startAngle);
    arcAngle = TrigonometriaUtils.radianToSexagesimal(arcAngle);
    g.drawArc((int) x, (int) y, (int) width, (int) height, (int) startAngle, (int) arcAngle);
  }

  @Override
  public void setColor(Color color) {
    g.setColor(color);
  }

  @Override
  public Color getColor() {
    return g.getColor();
  }
}
