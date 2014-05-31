/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.futbol;

import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.FloorTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Parametros;

/**
 *
 * @author Usuario
 */
public class Util {

    public static double fuerzaDisparo(Jugador rematador, Position objetivo) {
        double distancia = rematador.getPosicion().distance(objetivo);
        distancia /= Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double velocidad = Math.sqrt(Parametros.Jugador.VELOCIDAD_FINAL_REMATE
                * Parametros.Jugador.VELOCIDAD_FINAL_REMATE
                + 8 * distancia);
        double fuerza = velocidad / rematador.getMaxVelocidadRemate();
        return fuerza;
    }

    public static int iteracionesPase(Jugador pasador, Position posicion) {
        double fuerza = Util.fuerzaDisparo(pasador, posicion);
        double vx0 = fuerza * pasador.getMaxVelocidadRemate();
        double angulo = pasador.getPosicion().angle(posicion);
        AbstractTrajectory trayectoria = new FloorTrajectory(vx0, 0);

        double maxTrX = trayectoria.getX(trayectoria.getDt())
                * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        Position maxDistancia = new Position(maxTrX * Math.cos(angulo),
                maxTrX * Math.sin(angulo));

        double distancia = pasador.getPosicion()
                .distance(posicion);
        if (distancia > maxDistancia.distance()) {
            return -1;
        }

        int iteraciones = 0;
        double distanciaActual = 0;
        while (distanciaActual < distancia) {
            double tiempo = (iteraciones + 1) / 60.0;
            double trX = trayectoria.getX(tiempo)
                    * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            Position posFutura = new Position(trX * Math.cos(angulo),
                trX * Math.sin(angulo));
            distanciaActual = posFutura.distance();
            iteraciones++;
        }

        return iteraciones / 2;
    }



}
