/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.tray;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGConstants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Recta;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.CirculoDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.RectaDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.VisualDebugFrame;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

/**
 *
 * @author malvarez
 */
public class TrayectoriaMain {

    public static void main(String[] args) throws InterruptedException, IOException {

        final VisualDebugFrame frame = new VisualDebugFrame();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                frame.setSize((int) ((Constants.ANCHO_CAMPO + VisualDebugFrame.Z_MAX) * MSGConstants.VISUAL_DEBUG_SCALE), (int) ((Constants.LARGO_CAMPO + VisualDebugFrame.Z_MAX) * MSGConstants.VISUAL_DEBUG_SCALE));
                frame.setResizable(false);
                frame.initComponents(MSGConstants.VISUAL_DEBUG_SCALE, Constants.ANCHO_CAMPO_JUEGO, Constants.LARGO_CAMPO_JUEGO, VisualDebugFrame.Z_MAX);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        TrayectoriaManager manager = TrayectoriaManager.getInstance();
        /*Trayectorias trayectorias = manager.getTrayectorias();

        Trayectoria maximoDesplazamientoHorizontal = null;
        Trayectoria maximaVelocidadHorizontal = null;

        for (final Trayectoria trayectoria : trayectorias.getTrayectoria()) {
        if (maximoDesplazamientoHorizontal == null) {
        maximoDesplazamientoHorizontal = trayectoria;
        } else if (maximoDesplazamientoHorizontal.getPuntos().get(maximoDesplazamientoHorizontal.getPuntos().size() - 1).getDesplazamientoH() < trayectoria.getPuntos().get(trayectoria.getPuntos().size() - 1).getDesplazamientoH()) {
        maximoDesplazamientoHorizontal = trayectoria;
        }

        if (maximaVelocidadHorizontal == null) {
        maximaVelocidadHorizontal = trayectoria;
        } else if (maximaVelocidadHorizontal.getPuntos().get(maximaVelocidadHorizontal.getPuntos().size() - 1).getVelocidadH() < trayectoria.getPuntos().get(trayectoria.getPuntos().size() - 1).getVelocidadH()) {
        maximaVelocidadHorizontal = trayectoria;
        }

        Thread.sleep(100L);
        SwingUtilities.invokeLater(new Runnable() {

        public void run() {
        frame.getCanvas().clearDrawablesXY();
        frame.getCanvas().clearDrawablesXZ();
        frame.getCanvas().clearDrawablesZY();

        frame.getCanvas().addDrawableXZ(new RectaDrawable(new Recta(new Punto(0, Constants.ALTURA_CONTROL_BALON), new Punto(1, Constants.ALTURA_CONTROL_BALON)), Color.GREEN));
        frame.getCanvas().addDrawableZY(new RectaDrawable(new Recta(new Punto(Constants.ALTURA_CONTROL_BALON, 0), new Punto(Constants.ALTURA_CONTROL_BALON, 1)), Color.GREEN));

        for (TrayectoriaPunto desplazamiento : trayectoria.getPuntos()) {
        Color color;
        if(desplazamiento.getProbabilidadControl() > 0.9)
        color = new Color(0, 255, 0, (int) (desplazamiento.getProbabilidadControl() * 255));
        else if(desplazamiento.getProbabilidadControl() > 0.8 && desplazamiento.getProbabilidadControl() <= 0.9)
        color = new Color(255, 255, 0, (int) (desplazamiento.getProbabilidadControl() * 255));
        else
        color = new Color(255, 0, 0, (int) (desplazamiento.getProbabilidadControl() * 255));
        frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), color));
        frame.getCanvas().addDrawableXZ(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoV()), Constants.RADIO_BALON * 2), color));
        frame.getCanvas().addDrawableZY(new CirculoDrawable(new Circulo(new Punto(desplazamiento.getDesplazamientoV(), desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), color));
        }
        frame.getCanvas().repaint();
        }
        });

        }
        System.out.println(String.format("Maximo desplazamiento horizontal: Velocidad[%s] Angulo[%s]", maximoDesplazamientoHorizontal.getVelocidad(), maximoDesplazamientoHorizontal.getAnguloVertical()));
        final Trayectoria mdh = maximoDesplazamientoHorizontal;
        SwingUtilities.invokeLater(new Runnable() {

        public void run() {
        frame.getCanvas().clearDrawablesXY();
        frame.getCanvas().clearDrawablesXZ();
        frame.getCanvas().clearDrawablesZY();

        frame.getCanvas().addDrawableXZ(new RectaDrawable(new Recta(new Punto(0, Constants.ALTURA_CONTROL_BALON), new Punto(1, Constants.ALTURA_CONTROL_BALON)), Color.GREEN));
        frame.getCanvas().addDrawableZY(new RectaDrawable(new Recta(new Punto(Constants.ALTURA_CONTROL_BALON, 0), new Punto(Constants.ALTURA_CONTROL_BALON, 1)), Color.GREEN));

        for (TrayectoriaPunto desplazamiento : mdh.getPuntos()) {
        frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        frame.getCanvas().addDrawableXZ(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoV()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        frame.getCanvas().addDrawableZY(new CirculoDrawable(new Circulo(new Punto(desplazamiento.getDesplazamientoV(), desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        }
        frame.getCanvas().repaint();
        }
        });

        Thread.sleep(1000L);
        System.out.println(String.format("Maxim velocidad horizontal: Velocidad[%s] Angulo[%s]", maximaVelocidadHorizontal.getVelocidad(), maximaVelocidadHorizontal.getAnguloVertical()));
        final Trayectoria mvh = maximaVelocidadHorizontal;
        SwingUtilities.invokeLater(new Runnable() {

        public void run() {
        frame.getCanvas().clearDrawablesXY();
        frame.getCanvas().clearDrawablesXZ();
        frame.getCanvas().clearDrawablesZY();

        frame.getCanvas().addDrawableXZ(new RectaDrawable(new Recta(new Punto(0, Constants.ALTURA_CONTROL_BALON), new Punto(1, Constants.ALTURA_CONTROL_BALON)), Color.GREEN));
        frame.getCanvas().addDrawableZY(new RectaDrawable(new Recta(new Punto(Constants.ALTURA_CONTROL_BALON, 0), new Punto(Constants.ALTURA_CONTROL_BALON, 1)), Color.GREEN));

        for (TrayectoriaPunto desplazamiento : mvh.getPuntos()) {
        frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        frame.getCanvas().addDrawableXZ(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoV()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        frame.getCanvas().addDrawableZY(new CirculoDrawable(new Circulo(new Punto(desplazamiento.getDesplazamientoV(), desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), new Color(0, 128, 0, (int) (desplazamiento.getProbabilidadControl() * 255))));
        }
        frame.getCanvas().repaint();
        }
        });*/

        List<Double> keys = new ArrayList(manager.getDisparosByDistancia().keySet());
        Collections.sort(keys);
        for (final Double key : keys) {

            final TrayectoriaPunto value = manager.getDisparosByDistancia().get(key);

            Thread.sleep(500L);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    frame.getCanvas().clearDrawablesXY();
                    frame.getCanvas().clearDrawablesXZ();
                    frame.getCanvas().clearDrawablesZY();

                    frame.getCanvas().addDrawableXZ(new RectaDrawable(new Recta(new Punto(0, Constants.ALTURA_CONTROL_BALON), new Punto(1, Constants.ALTURA_CONTROL_BALON)), Color.GREEN));
                    frame.getCanvas().addDrawableZY(new RectaDrawable(new Recta(new Punto(Constants.ALTURA_CONTROL_BALON, 0), new Punto(Constants.ALTURA_CONTROL_BALON, 1)), Color.GREEN));

                    for (TrayectoriaPunto desplazamiento : value.getTrayectoria().getPuntos()) {
                        Color color;
                        if (desplazamiento.getDesplazamientoH() >= key) {
                            color = new Color(0, 255, 0, (int) (desplazamiento.getProbabilidadControl() * 255));
                        }  else {
                            color = new Color(255, 0, 0, (int) (desplazamiento.getProbabilidadControl() * 255));
                        }
                        frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), color));
                        frame.getCanvas().addDrawableXZ(new CirculoDrawable(new Circulo(new Punto(0, desplazamiento.getDesplazamientoV()), Constants.RADIO_BALON * 2), color));
                        frame.getCanvas().addDrawableZY(new CirculoDrawable(new Circulo(new Punto(desplazamiento.getDesplazamientoV(), desplazamiento.getDesplazamientoH()), Constants.RADIO_BALON * 2), color));
                    }
                    frame.getCanvas().repaint();
                }
            });

        }

    }
}
