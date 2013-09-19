package org.javahispano.javacup.tacticas.jvc2012.realvencedores.equipo;

import java.awt.Color;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class EquipoImpl implements TacticDetail {

    public String getTacticName() {
        return "Real Vencedores";
    }

    public String getCountry() {
        return "España";
    }

    public String getCoach() {
        return "Yose Mahou";
    }

    public Color getShirtColor() {
        return new Color(204, 0, 0);
    }

    public Color getShortsColor() {
        return new Color(204, 0, 0);
    }

    public Color getShirtLineColor() {
        return new Color(0, 0, 255);
    }

    public Color getSocksColor() {
        return new Color(153, 0, 0);
    }

    public Color getGoalKeeper() {
        return new Color(0, 0, 204);
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    public Color getShirtColor2() {
        return new Color(102, 51, 0);
    }

    public Color getShortsColor2() {
        return new Color(204, 204, 0);
    }

    public Color getShirtLineColor2() {
        return new Color(51, 146, 201);
    }

    public Color getSocksColor2() {
        return new Color(255, 255, 255);
    }

    public Color getGoalKeeper2() {
        return new Color(255, 255, 0);
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.SIN_ESTILO;
    }

    class JugadorImpl implements PlayerDetail {

        String nombre;
        int numero;
        Color piel, pelo;
        double velocidad, remate, presicion;
        boolean portero;
        Position Position;

        public JugadorImpl(String nombre, int numero, Color piel, Color pelo, double velocidad, double remate, double presicion,
                boolean portero) {
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
        return new PlayerDetail[] {
                // 0: Portero
                new JugadorImpl("Iker Estanterias", 1, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.95d, 1.0d, true),
                // 1 - 4: Defensas
                new JugadorImpl("Pepe Rompehuesos", 3, new Color(196, 121, 52), new Color(50, 0, 0), 0.67d, 0.31d, 0.5d, false),
                new JugadorImpl("Sergio Ramos de Flores", 4, new Color(255, 200, 150), new Color(50, 0, 0), 0.76d, 0.3d, 0.5d, false),
                new JugadorImpl("Lassy", 24, new Color(0, 0, 0), new Color(0, 0, 0), 0.92d, 0.37d, 0.34d, false),
                new JugadorImpl("Chavi Alonso", 14, new Color(255, 200, 150), new Color(50, 0, 0), 0.72d, 0.3d, 0.5d, false),
                // 5 - 7: Centrales
                new JugadorImpl("Concentrao", 15, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.69d, 0.64d, false),
                new JugadorImpl("Cristiano Ronaldo", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
                new JugadorImpl("Caca", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.75d, 0.61d, 0.82d, false),
                // 8 - 10: Delanteros
                new JugadorImpl("Cigarrillo Higuain", 20, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 0.77d, false),
                new JugadorImpl("Perdema", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 0.88d, false),
                new JugadorImpl("Collejón", 21, new Color(255, 200, 150), new Color(50, 0, 0), 0.76d, 0.9d, 1.0d, false) };
    }
}
