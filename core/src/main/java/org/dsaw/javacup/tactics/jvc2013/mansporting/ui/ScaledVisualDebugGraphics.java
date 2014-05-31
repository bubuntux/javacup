/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import java.awt.*;

/**
 *
 * @author MaN
 */
public class ScaledVisualDebugGraphics implements VisualDebugGraphics {

    private final VisualDebugGraphics g;

    private final double scaleX;

    private final double scaleY;

    public ScaledVisualDebugGraphics(VisualDebugGraphics g, double scaleX, double scaleY) {
        this.g = g;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }


    public void setColor(Color color) {
        g.setColor(color);
    }


    public Color getColor() {
        return g.getColor();
    }


    public void fillRect(double x, double y, double width, double height) {
        g.fillRect(x * scaleX, y * scaleY, width * scaleX, height * scaleY);
    }


    public void fillOval(double x, double y, double width, double height) {
        g.fillOval(x * scaleX, y * scaleY, width * scaleX, height * scaleY);
    }


    public void drawRect(double x, double y, double width, double height) {
        g.drawRect(x * scaleX, y * scaleY, width * scaleX, height * scaleY);
    }


    public void drawOval(double x, double y, double width, double height) {
        g.drawOval(x * scaleX, y * scaleY, width * scaleX, height * scaleY);
    }


    public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
        g.drawArc(x * scaleX, y * scaleY, width * scaleX, height * scaleY, startAngle, arcAngle);
    }


    public void drawLine(double x1, double y1, double x2, double y2) {
        g.drawLine(x1 * scaleX, y1 * scaleY, x2 * scaleX, y2 * scaleY);
    }
}
