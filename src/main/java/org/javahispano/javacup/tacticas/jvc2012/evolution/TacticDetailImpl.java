package org.javahispano.javacup.tacticas.jvc2012.evolution;

import java.awt.Color;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.model.util.Position;;

public class TacticDetailImpl implements TacticDetail {

    public String getTacticName() {
        return "San Marino Evolution";
    }

    public String getCountry() {
        return "España";
    }

    public String getCoach() {
        return "Joserra";
    }

    public Color getShirtColor() {
        return new Color(102, 0, 204);
    }

    public Color getShortsColor() {
        return new Color(0, 0, 0);
    }

    public Color getShirtLineColor() {
        return new Color(153, 153, 0);
    }

    public Color getSocksColor() {
        return new Color(0, 0, 0);
    }

    public Color getGoalKeeper() {
        return new Color(204, 204, 204        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.SIN_ESTILO;
    }

    public Color getShirtColor2() {
        return new Color(33, 94, 85);
    }

    public Color getShortsColor2() {
        return new Color(229, 204, 244);
    }

    public Color getShirtLineColor2() {
        return new Color(218, 235, 195);
    }

    public Color getSocksColor2() {
        return new Color(235, 236, 24);
    }

    public Color getGoalKeeper2() {
        return new Color(67, 208, 45        );
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.LINEAS_VERTICALES;
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
        		new JugadorImpl("Nete", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.75d, false),
                new JugadorImpl("Juan", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.8d,0.35d, false),
                new JugadorImpl("Joserra", 15, new Color(255,200,150), new Color(50,0,0),1.0d,0.82d,0.35d, false),
                new JugadorImpl("Lelo", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.77d,0.25d, false),
                new JugadorImpl("Comino", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.86d,0.35d, false),
                new JugadorImpl("Juanvi", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.87d,0.71d, false),
                
                new JugadorImpl("Gabi", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.97d,1.0d, true),
                
                new JugadorImpl("Sebas", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.78d,0.64d, false),
                new JugadorImpl("Miguel", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.79d,0.73d, false),
                new JugadorImpl("Casado", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Juanito", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
        };
    }
}



