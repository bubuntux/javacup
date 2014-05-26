package org.dsaw.javacup.tacticas.jvc2012.iria;

import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.EstiloUniforme;

public class IriaDetail implements TacticDetail {

    public String getTacticName() {
        return "Iria";
    }

    public String getCountry() {
        return "España";
    }

    public String getCoach() {
        return "Juan Carlos";
    }

    public Color getShirtColor() {
        return new Color(51, 204, 255);
    }

    public Color getShortsColor() {
        return new Color(255, 0, 102);
    }

    public Color getShirtLineColor() {
        return new Color(255, 255, 255);
    }

    public Color getSocksColor() {
        return new Color(0, 204, 255);
    }

    public Color getGoalKeeper() {
        return new Color(0, 0, 0        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    public Color getShirtColor2() {
        return new Color(255, 0, 204);
    }

    public Color getShortsColor2() {
        return new Color(0, 204, 255);
    }

    public Color getShirtLineColor2() {
        return new Color(255, 255, 255);
    }

    public Color getSocksColor2() {
        return new Color(255, 0, 204);
    }

    public Color getGoalKeeper2() {
        return new Color(255, 255, 0        );
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    class JugadorImpl implements PlayerDetail {

        String nombre;
        int numero;
        Color piel, pelo;
        double velocidad, remate, presicion;
        boolean portero;
        Position Position;

        public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                double velocidad, double remate, double presicion, boolean portero) {
            this.nombre=nombre;
            this.numero=numero;
            this.piel=piel;
            this.pelo=pelo;
            this.velocidad=velocidad;
            this.remate=remate;
            this.presicion=presicion;
            this.portero=portero;
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
            new JugadorImpl("Perlis", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.71d,1.0d, true),
            new JugadorImpl("Wilkes", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.25d,1.0d, false),
            new JugadorImpl("Hamming", 3, new Color(255,200,150), new Color(255,0,51),1.0d,0.25d,1.0d, false),
            new JugadorImpl("Minsky", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.25d,1.0d, false),
            new JugadorImpl("Wilkinson", 5, new Color(255,200,150), new Color(0,102,102),1.0d,0.36d,1.0d, false),
            new JugadorImpl("McCarthy", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.36d,1.0d, false),
            new JugadorImpl("Dijkstra", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.36d,1.0d, false),
            new JugadorImpl("Bachman", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.36d,1.0d, false),
            new JugadorImpl("Knut", 9, new Color(255,200,150), new Color(255,255,0),1.0d,1.0d,1.0d, false),
            new JugadorImpl("Newell", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.8d,1.0d, false),
            new JugadorImpl("Simon", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.8d,1.0d, false)
        };
    }
}
