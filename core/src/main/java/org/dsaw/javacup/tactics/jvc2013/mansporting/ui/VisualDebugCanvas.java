package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * <br>
 * Canvas para las operaciones.
 * <br>
 * <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class VisualDebugCanvas extends Canvas {

    /**
     * Rectangulo donde pintar.
     */
    private Rectangulo drawingDimension;

    /**
     * Rect�ngulo donde dibujar la XY.
     */
    private final Rectangulo marcoXY;
    /**
     * Rectángulo donde dibujar la XZ
     */
    private final Rectangulo marcoXZ;
    /**
     * Rectángulo donde dibujar la YZ
     */
    private final Rectangulo marcoZY;
    /**
     * Figuras a pintar.
     */
    private List<Drawable> drawablesXY;
    /**
     * Figuras a pintar.
     */
    private List<Drawable> drawablesXZ;
    /**
     * Figuras a pintar.
     */
    private List<Drawable> drawablesZY;
    /**
     * Escala.
     */
    private final double scale;

    /**
     * Constructs a new Canvas.
     *
     * @param dimension del canvas.
     */
    public VisualDebugCanvas(double scale, double width, double height, double altura) {
        this.marcoXY = new Rectangulo(new Punto(0, 1), width, height);
        this.marcoXZ = new Rectangulo(new Punto(1, height + 2), width, altura);
        this.marcoZY = new Rectangulo(new Punto(width + 2, 1), altura, height);
        this.drawablesXY = new LinkedList<Drawable>();
        this.drawablesXZ = new LinkedList<Drawable>();
        this.drawablesZY = new LinkedList<Drawable>();
        this.scale = scale;
        this.drawingDimension = new Rectangulo(new Punto(0, 0), width  + altura + 1, height + altura + 1);
    }

    /**
     * Paints this canvas.
     * <p/>
     * Most applications that subclass <code>Canvas</code> should
     * override this method in order to perform some useful operation
     * (typically, custom painting of the canvas).
     * The default operation is simply to clear the canvas.
     * Applications that override this method need not call
     * super.paint(g).
     *
     * @param g the specified Graphics context
     * @see #update(java.awt.Graphics)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(getWidth(), getHeight());

        VisualDebugGraphics ig = new OverridenVisualDebugGraphics(image.getGraphics());
        ig = new ScaledVisualDebugGraphics(ig, scale, scale);
        ig.setColor(Color.WHITE);
        ig.fillRect(0, 0, drawingDimension.getAncho() + 20, drawingDimension.getAlto() + 20);
        ig.setColor(Color.BLACK);
        drawMarco(ig, marcoXY, drawablesXY);
        drawMarco(new DesplazadoVisualDebugGraphics(ig, new Punto(0,marcoXZ.getAlto() / 2)), marcoXZ, drawablesXZ);
        drawMarco(new DesplazadoVisualDebugGraphics(ig, new Punto(- marcoZY.getAncho() / 2, 0)), marcoZY, drawablesZY);
        g.drawImage(image, 0, 0, null);
    }

    private void drawMarco(VisualDebugGraphics g, Rectangulo marco, List<Drawable> drawables) {
        for (Drawable drawable : drawables) {
            drawable.paint(g, marco);
        }
    }

    @Override
    public void update(Graphics g) {
        this.paint(g);
    }

    /**
     * Limpia los elementos pintables.
     */
    public void clearDrawablesXY() {
        drawablesXY.clear();
    }

    /**
     * Añade un elemento pintable.
     * @param element elemento.
     */
    public void addDrawableXY(Drawable element) {
        drawablesXY.add(element);
    }

    /**
     * Limpia los elementos pintables.
     */
    public void clearDrawablesZY() {
        drawablesZY.clear();
    }

    /**
     * Añade un elemento pintable.
     * @param element elemento.
     */
    public void addDrawableZY(Drawable element) {
        drawablesZY.add(element);
    }

    /**
     * Limpia los elementos pintables.
     */
    public void clearDrawablesXZ() {
        drawablesXZ.clear();
    }

    /**
     * Añade un elemento pintable.
     * @param element elemento.
     */
    public void addDrawableXZ(Drawable element) {
        drawablesXZ.add(element);
    }

    public Rectangulo getMarcoXY() {
        return marcoXY;
    }

    public Rectangulo getMarcoXZ() {
        return marcoXZ;
    }

    public Rectangulo getMarcoZY() {
        return marcoZY;
    }
}
