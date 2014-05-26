package org.dsaw.javacup.tacticas.jvc2012.losdesistemas;

import java.awt.Color;

import org.dsaw.javacup.render.EstiloUniforme;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.model.TacticDetail;

public class LosDetalles implements TacticDetail{
	
	public String getTacticName() {
        return "LOS DE SISTEMAS";
    }

    public String getCountry() {
        return "Argentina";
    }

    public String getCoach() {
        return "Los Pibes";
    }

    public Color getShirtColor() {
        return new Color(0, 0, 255);
    }

    public Color getShortsColor() {
        return new Color(255, 255, 255);
    }

    public Color getShirtLineColor() {
        return new Color(255, 255, 255);
    }

    public Color getSocksColor() {
        return new Color(255, 255, 255);
    }

    public Color getGoalKeeper() {
        return new Color(255, 255, 255        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    public Color getShirtColor2() {
        return new Color(255, 255, 0);
    }

    public Color getShortsColor2() {
        return new Color(0, 0, 255);
    }

    public Color getShirtLineColor2() {
        return new Color(0, 0, 255);
    }

    public Color getSocksColor2() {
        return new Color(255, 255, 0);
    }

    public Color getGoalKeeper2() {
        return new Color(255, 255, 0        );
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.FRANJA_HORIZONTAL;
    }

    class PlayerDetailImpl implements PlayerDetail {

        String nombre;
        int numero;
        Color piel, pelo;
        double velocidad, remate, presicion;
        boolean portero;
        Position posicion;

        public PlayerDetailImpl(String nombre, int numero, Color piel, Color pelo,
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
        		new PlayerDetailImpl("Messi", 1, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.59d, true),
                new PlayerDetailImpl("Defensa", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Defesa2", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Defensa3", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Medio", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Medio2", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Medio3", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Medio4", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Medio5", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false),
                new PlayerDetailImpl("Delantero1", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new PlayerDetailImpl("Delantero2", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false)
        };
    }
}
