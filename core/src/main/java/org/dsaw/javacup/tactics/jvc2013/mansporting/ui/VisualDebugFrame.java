package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Recta;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;

import javax.swing.*;

/**
 * <br> <br> <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class VisualDebugFrame extends JFrame {

  public static final double Z_MAX = Constants.ALTO_ARCO * 5;
  /**
   * Canas.
   */
  private VisualDebugCanvas canvas;

  public VisualDebugFrame() {
  }

  public void initComponents(double scale, double width, double height, double z) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(canvas = new VisualDebugCanvas(scale, width, height, z), BorderLayout.CENTER);
    this.add(panel);
  }

  public VisualDebugCanvas getCanvas() {
    return canvas;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      /**
       * When an object implementing interface <code>Runnable</code> is used
       * to create a thread, starting the thread causes the object's
       * <code>run</code> method to be called in that separately executing
       * thread.
       * <p/>
       * The general contract of the method <code>run</code> is that it may
       * take any action whatsoever.
       *
       * @see Thread#run()
       */
      @Override
      public void run() {
        double scale = 5;
        VisualDebugFrame frame = new VisualDebugFrame();
        frame.setSize((int) (scale * (Constants.ANCHO_CAMPO + Z_MAX)),
                      (int) (scale * (Constants.LARGO_CAMPO + Z_MAX)));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame
            .initComponents(scale, Constants.ANCHO_CAMPO_JUEGO, Constants.LARGO_CAMPO_JUEGO, Z_MAX);

        Punto punto = new Punto(-15, 4);
        Punto punto2 = new Punto(33, -5);

        Punto puntoOrigen = new Punto(punto2.getX() - punto.getX(), punto2.getY() - punto.getY());
        double moduloOrigen = TrigonometriaUtils.getModuloVector(puntoOrigen);
        double anguloOrigen = TrigonometriaUtils.getAnguloVector(puntoOrigen);
        double anguloMayor = anguloOrigen + Math.PI / 4;
        double anguloMenor = anguloOrigen - Math.PI / 4;

        Punto puntoMayor = TrigonometriaUtils.getPunto(anguloMayor, moduloOrigen);
        Punto puntoMenor = TrigonometriaUtils.getPunto(anguloMenor, moduloOrigen);

        Punto
            puntoMayorTrasladado =
            new Punto(puntoMayor.getX() + punto.getX(), puntoMayor.getY() + punto.getY());
        Punto
            puntoMenorTrasladado =
            new Punto(puntoMenor.getX() + punto.getX(), puntoMenor.getY() + punto.getY());

        frame.getCanvas()
            .addDrawableXY(new VectorDrawable(new Segmento(punto, punto2), Color.GREEN));
        frame.getCanvas().addDrawableXY(
            new VectorDrawable(new Segmento(new Punto(0, 0), puntoOrigen), Color.BLUE));
        frame.getCanvas().addDrawableXY(
            new VectorDrawable(new Segmento(new Punto(0, 0), puntoMayor), Color.CYAN));
        frame.getCanvas().addDrawableXY(
            new VectorDrawable(new Segmento(new Punto(0, 0), puntoMenor), Color.CYAN));
        frame.getCanvas().addDrawableXY(
            new VectorDrawable(new Segmento(punto, puntoMayorTrasladado), Color.GRAY));
        frame.getCanvas().addDrawableXY(
            new VectorDrawable(new Segmento(punto, puntoMenorTrasladado), Color.GRAY));

        frame.getCanvas().addDrawableXY(
            new RectaDrawable(new Recta(new Punto(-1, 0), new Punto(1, 0)), Color.BLACK));
        frame.getCanvas().addDrawableXY(
            new RectaDrawable(new Recta(new Punto(0, -1), new Punto(0, 1)), Color.BLACK));

        frame.setVisible(true);
      }
    });
  }
}
