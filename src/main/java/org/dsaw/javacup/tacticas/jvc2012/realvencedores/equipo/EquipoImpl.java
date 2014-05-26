package org.dsaw.javacup.tacticas.jvc2012.realvencedores.equipo;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.EstiloUniforme;

public class EquipoImpl implements TacticDetail {

    @Override
    public String getTacticName() {
        return "Real Vencedores";
    }

    @Override
    public String getCountry() {
        return "España";
    }

    @Override
    public String getCoach() {
        return "Yose Mahou";
    }

    @Override
    public Color getShirtColor() {
        return new Color(204, 0, 0);
    }

    @Override
    public Color getShortsColor() {
        return new Color(204, 0, 0);
    }

    @Override
    public Color getShirtLineColor() {
        return new Color(0, 0, 255);
    }

    @Override
    public Color getSocksColor() {
        return new Color(153, 0, 0);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(0, 0, 204);
    }

    @Override
    public EstiloUniforme getStyle() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    @Override
    public Color getShirtColor2() {
        return new Color(102, 51, 0);
    }

    @Override
    public Color getShortsColor2() {
        return new Color(204, 204, 0);
    }

    @Override
    public Color getShirtLineColor2() {
        return new Color(51, 146, 201);
    }

    @Override
    public Color getSocksColor2() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper2() {
        return new Color(255, 255, 0);
    }

    @Override
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

        @Override
        public String getPlayerName() {
            return nombre;
        }

        @Override
        public Color getSkinColor() {
            return piel;
        }

        @Override
        public Color getHairColor() {
            return pelo;
        }

        @Override
        public int getNumber() {
            return numero;
        }

        @Override
        public boolean isGoalKeeper() {
            return portero;
        }

        @Override
        public double getSpeed() {
            return velocidad;
        }

        @Override
        public double getPower() {
            return remate;
        }

        @Override
        public double getPrecision() {
            return presicion;
        }

    }

    @Override
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
