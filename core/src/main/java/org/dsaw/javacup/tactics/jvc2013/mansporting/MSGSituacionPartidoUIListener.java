/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.CirculoDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.JugadorBalonDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.RectanguloDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.SegmentoDrawable;
import org.dsaw.javacup.tactics.jvc2013.mansporting.ui.VisualDebugFrame;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

/**
 *
 * @author malvarez
 */
public class MSGSituacionPartidoUIListener implements MSGSituacionPartidoListener {

    /**
     * Instancia estática.
     */
    private static MSGSituacionPartidoUIListener INSTANCE;
    /**
     * Frame que es visible.
     */
    private VisualDebugFrame frame;
    /**
     * Segmentos en los que evaluar los pases.
     */
    public List<Segmento> segmentosEvaluarPase = new LinkedList<Segmento>();
    /**
     * Segmentos en los que evaluar los pases.
     */
    public List<Segmento> segmentosEvaluarDesmarcaje = new LinkedList<Segmento>();
    /**
     * Marcajes.
     */
    public List<Segmento> segmentosMarcaje = new LinkedList<Segmento>();
    /**
     * Tiro centrado.
     */
    public Segmento segmentoTiroCentrado = null;
    /**
     * Tiro centrado.
     */
    public Segmento segmentoTiroMaximo = null;
    /**
     * Tiro centrado.
     */
    public Segmento segmentoTiroMinimo = null;

    public List<Punto> puntosPatetics = new LinkedList<Punto>();

    /**
     * Constructor.
     */
    private MSGSituacionPartidoUIListener() {
        if (MSGConstants.VISUAL_DEBUG) {
            this.frame = frame = new VisualDebugFrame();
            Runnable runnable = new Runnable() {

                public void run() {
                    frame.setSize((int) ((Constants.ANCHO_CAMPO + VisualDebugFrame.Z_MAX) * MSGConstants.VISUAL_DEBUG_SCALE), (int) ((Constants.LARGO_CAMPO + VisualDebugFrame.Z_MAX) * MSGConstants.VISUAL_DEBUG_SCALE));
                    frame.setResizable(false);
                    frame.initComponents(MSGConstants.VISUAL_DEBUG_SCALE, Constants.ANCHO_CAMPO_JUEGO, Constants.LARGO_CAMPO_JUEGO, VisualDebugFrame.Z_MAX);
                    frame.setVisible(true);
                }
            };
            if (SwingUtilities.isEventDispatchThread()) {
                runnable.run();
            } else {
                SwingUtilities.invokeLater(runnable);
            }
        }
    }

    public static synchronized MSGSituacionPartidoUIListener getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MSGSituacionPartidoUIListener();
        }
        return INSTANCE;
    }

    public void afterUpdate(final MSGSituacionPartidoContext context) {
        if (MSGConstants.VISUAL_DEBUG) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    frame.getCanvas().clearDrawablesXY();
                    frame.getCanvas().clearDrawablesXZ();
                    frame.getCanvas().clearDrawablesZY();

                    // Porterías
                    frame.getCanvas().addDrawableXY(new SegmentoDrawable(new Segmento(new Punto(Constants.posteDerArcoInf), new Punto(Constants.posteIzqArcoInf)), Color.GREEN));
                    frame.getCanvas().addDrawableXY(new SegmentoDrawable(new Segmento(new Punto(Constants.posteDerArcoSup), new Punto(Constants.posteIzqArcoSup)), Color.GREEN));
                    frame.getCanvas().addDrawableXZ(new RectanguloDrawable(new Rectangulo(new Punto(0, Constants.ALTO_ARCO / 2), Math.abs(Constants.posteDerArcoSup.getX() - Constants.posteIzqArcoSup.getX()), Constants.ALTO_ARCO), Color.GREEN));
                    frame.getCanvas().addDrawableZY(new SegmentoDrawable(new Segmento(new Punto(0, Constants.posteDerArcoInf.getY()), new Punto(Constants.ALTO_ARCO, Constants.posteIzqArcoInf.getY())), Color.GREEN));
                    frame.getCanvas().addDrawableZY(new SegmentoDrawable(new Segmento(new Punto(0, Constants.posteDerArcoSup.getY()), new Punto(Constants.ALTO_ARCO, Constants.posteDerArcoSup.getY())), Color.GREEN));

                    // Jugadores propios
                    for (MSGJugadorPropio situacion : context.getJugadoresPropios().values()) {
                        frame.getCanvas().addDrawableXY(new RectanguloDrawable(situacion.getAlineacion().getArea(), new Color(0, 255, 255, 128)));
                        frame.getCanvas().addDrawableXY(new CirculoDrawable(situacion.getAreaCubierta(1), new Color(255, 0, 0, 64)));
                        if (situacion.isJugadorMasCercano()) {
                            frame.getCanvas().addDrawableXY(new RectanguloDrawable(new Rectangulo(situacion.getPosicion(), situacion.getAreaCubierta(5).getRadio(), situacion.getAreaCubierta(5).getRadio()), new Color(255, 0, 0, 255)));
                        }
                    }

                    // Jugadores rivales.
                    for (MSGJugadorDetalle situacion : context.getJugadoresRivales().values()) {
                        frame.getCanvas().addDrawableXY(new CirculoDrawable(situacion.getAreaCubierta(1), new Color(0, 0, 255, 64)));
                    }

                    // Balon y trayectoria.
                    MSGBalon balon = context.getBalon();

                    // Posición del balón
                    frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(balon.getPosicion().getPuntoXY(), Constants.RADIO_BALON), Color.BLACK));
                    frame.getCanvas().addDrawableXZ(new CirculoDrawable(new Circulo(balon.getPosicion().getPuntoXZ(), Constants.RADIO_BALON), Color.BLACK));
                    frame.getCanvas().addDrawableZY(new CirculoDrawable(new Circulo(balon.getPosicion().getPuntoZY(), Constants.RADIO_BALON), Color.BLACK));


                    if (context.getJugadoresCercanosBalonData() != null) {
                        frame.getCanvas().addDrawableXY(new JugadorBalonDrawable(context.getJugadoresCercanosBalonData()));
                    }

                    for (Segmento segmento : segmentosMarcaje) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmento, Color.PINK));
                    }

                    for (Segmento segmento : segmentosEvaluarPase) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmento, Color.ORANGE));
                    }

                    for (Segmento segmento : segmentosEvaluarDesmarcaje) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmento, Color.BLUE));
                    }

                    if (segmentoTiroCentrado != null) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmentoTiroCentrado, Color.GRAY));
                    }

                    if (segmentoTiroMaximo != null) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmentoTiroMaximo, Color.RED));
                    }

                    if (segmentoTiroMinimo != null) {
                        frame.getCanvas().addDrawableXY(new SegmentoDrawable(segmentoTiroMinimo, Color.BLUE));
                    }

                    for(Punto punto : puntosPatetics){
                        frame.getCanvas().addDrawableXY(new CirculoDrawable(new Circulo(punto, 5*Constants.RADIO_BALON), Color.GREEN));
                    }

                    frame.getCanvas().repaint();


                    segmentosEvaluarDesmarcaje.clear();
                    segmentosMarcaje.clear();
                }
            });
        }
    }
}
