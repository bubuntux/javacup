/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Recta;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author MaN
 */
public class MSGPortero extends MSGAbstractJugadorPropio {

    public MSGPortero(int index, Color colorPelo, Color colorPiel, String nombre, int numero, MSGEstadisticas estadisticas) {
        super(index, colorPelo, colorPiel, nombre, numero, estadisticas);
    }

    @Override
    public List<Command> ejecuta() {
        List<Command> comando = new MSGComandoList();
        if (getPuedeRematar()) {
            comando.add(pasa());
        } else if (isTiroAPuerta()) {
            comando.add(cubreBalon());
        } else if (isJugadorMasCercano()) {
            comando.add(irABalon());
        }
        return comando.isEmpty() ? Arrays.asList(cubrePorteria()) : comando;
    }

    /**
     * Va a la posición de recuperació de ballPosition.
     * @return posioción de recuperación de balón.
     */
    protected Command recuperaBalon() {
        if (context.getSituacionPartido().getRecoveryBall().length > 1) {
            if (context.getSituacionPartido().getRecoveryBall()[1] == 0) {
                int iteraciones = context.getSituacionPartido().getRecoveryBall()[0];
                Punto3D
                    punto = new Punto3D(context.getSituacionPartido().getTrajectory(iteraciones));
                if (isInArea(punto.getPuntoXY())) {
                    return new CommandMoveTo(index, new Position(punto.getX(), punto.getY()));
                }
            }
        }
        return null;
    }

    protected Boolean isInArea(Punto punto) {
        if (Math.abs(punto.getX()) > Constants.LARGO_AREA_GRANDE / 2) {
            return false;
        }
        if (punto.getY() > (-Constants.ANCHO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_GRANDE) || punto.getY() < -Constants.LARGO_CAMPO_JUEGO / 2) {
            return false;
        }
        return true;
    }

    protected boolean isTiroAPuerta() {
        Punto p0 = new Punto3D(context.getSituacionPartido().getTrajectory(0)).getPuntoXY();
        Punto p1 = new Punto3D(context.getSituacionPartido().getTrajectory(1)).getPuntoXY();
        if (p0.equals(p1)) {
            return false;
        }

        Recta rectaTrayectoria = new Recta(p0, p1);

        Segmento rectaPortero = new Segmento(
                new Punto(Constants.posteIzqArcoInf),
                new Punto(Constants.posteDerArcoInf));

        Punto interseccion = TrigonometriaUtils.getInterseccion(rectaPortero, rectaTrayectoria);
        return interseccion != null;
    }

    protected Command cubrePorteria() {
        Segmento posteIzq = new Segmento(
                new Punto(Constants.posteIzqArcoInf),
                context.getBalon().getPosicion().getPuntoXY());
        Segmento posteDer = new Segmento(
                new Punto(Constants.posteDerArcoInf.getX(), Constants.posteDerArcoInf.getY()),
                context.getBalon().getPosicion().getPuntoXY());

        Segmento rectaPortero = new Segmento(
                new Punto(Constants.posteIzqArcoInf.getX(), alineacion.getPosicion().getY()),
                new Punto(Constants.posteDerArcoInf.getX(), alineacion.getPosicion().getY()));

        Punto puntoIrA = null;
        for (Recta bisectriz : TrigonometriaUtils.getBisectrices(posteIzq.getRecta(), posteDer.getRecta())) {
            Punto interseccion = TrigonometriaUtils.getInterseccion(rectaPortero, bisectriz);
            if (interseccion != null && rectaPortero.isPoint(interseccion)) {
                puntoIrA = interseccion;
                break;
            }
        }
        return puntoIrA == null ? null : new CommandMoveTo(index, new Position(puntoIrA.getX(), puntoIrA.getY()));
    }

    protected Command cubreBalon() {
        Punto p0 = new Punto3D(context.getSituacionPartido().getTrajectory(0)).getPuntoXY();
        Punto p1 = new Punto3D(context.getSituacionPartido().getTrajectory(1)).getPuntoXY();

        Recta rectaTrayectoria = new Recta(p0, p1);

        Recta rectaPortero = new Recta(
                new Punto(Constants.posteIzqArcoInf.getX(), alineacion.getPosicion().getY()),
                new Punto(Constants.posteDerArcoInf.getX(), alineacion.getPosicion().getY()));

        Punto interseccion = TrigonometriaUtils.getInterseccion(rectaPortero, rectaTrayectoria);

        return new CommandMoveTo(index, new Position(interseccion.getX(), interseccion.getY()));
    }

    public double getIteracionesVentajaPase() {
        return MSGConstants.ITERACIONES_MIMIMAS_VENTAJA_PORTERO;
    }
}
