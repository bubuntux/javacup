/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.Mou_team;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

/**
 *
 * @author Administrador
 */
class TacticaDetalleImpl implements TacticDetail {

    public String getTacticName() {
        return "Mou_Team";
    }

    public CountryCode getCountry() {
        return CountryCode.ES;
    }

    public String getCoach() {
        return "Mou";
    }

    public Color getShirtColor() {
        return new Color(255, 255, 255);
    }

    public Color getShortsColor() {
        return new Color(0, 0, 0);
    }

    public Color getShirtLineColor() {
        return new Color(204, 0, 0);
    }

    public Color getSocksColor() {
        return new Color(51, 0, 51);
    }

    public Color getGoalKeeper() {
        return new Color(204, 0, 51);
    }

    public UniformStyle getStyle() {
        return UniformStyle.LINEAS_VERTICALES;
    }

    public Color getShirtColor2() {
        return new Color(173, 64, 234);
    }

    public Color getShortsColor2() {
        return new Color(168, 184, 28);
    }

    public Color getShirtLineColor2() {
        return new Color(100, 230, 102);
    }

    public Color getSocksColor2() {
        return new Color(13, 107, 21);
    }

    public Color getGoalKeeper2() {
        return new Color(113, 165, 3);
    }

    public UniformStyle getStyle2() {
        return UniformStyle.LINEAS_HORIZONTALES;
    }

    class JugadorImpl implements PlayerDetail {

        String nombre;
        int numero;
        Color piel, pelo;
        double velocidad, remate, presicion;
        boolean portero;
        Position posicion;

        public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                double velocidad, double remate, double presicion, boolean portero) {
            this.nombre = nombre;
            this.numero = numero;
            this.piel = piel;
            this.pelo = pelo;
            this.velocidad = velocidad;
            this.remate = remate;
            this.presicion = presicion;
            this.portero = portero;
        }

        public String getPlayerName() {
            return nombre;
        }

        public Color getSkinColor() {
            return piel;
        }

        public Color getHairColor() {
            return pelo;
        }

        public int getNumber() {
            return numero;
        }

        public boolean isGoalKeeper() {
            return portero;
        }

        public double getSpeed() {
            return velocidad;
        }

        public double getPower() {
            return remate;
        }

        public double getPrecision() {
            return presicion;
        }
    }

    public PlayerDetail[] getPlayers() {
        return new PlayerDetail[]{
                    new JugadorImpl("Jugador", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false), //delantero,
                    new JugadorImpl("Jugador", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d, 0.5d, false), // central izq
                    new JugadorImpl("Jugador", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d, 0.5d, false),// central
                    new JugadorImpl("Jugador", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d, 0.5d, false),//central derecho
                    new JugadorImpl("Jugador", 5, new Color(255, 200, 150), new Color(50, 0, 0), 0.94d, 0.60d, 0.70d, false), //  pivote derecha
                    new JugadorImpl("Jugador", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.94d, 0.60d, 0.70d, false), //  pivote izquierda
                    new JugadorImpl("Jugador", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.97d, 0.60d, 0.6d, false),//  pivote
                    new JugadorImpl("Jugador", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false), //  extremo derehca
                    new JugadorImpl("Jugador", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//  extremo izquierdo
                    new JugadorImpl("Jugador", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d, 0.8d, false), // media punta
                    new JugadorImpl("Jugador", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.85d, 0.5d, true)//  PORTERO
                };




    }
}
