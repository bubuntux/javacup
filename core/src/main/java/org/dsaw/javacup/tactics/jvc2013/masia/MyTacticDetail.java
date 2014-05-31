package org.dsaw.javacup.tactics.jvc2013.masia;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class MyTacticDetail implements TacticDetail {
	
	public String getTacticName() {
        return "La Masia";
    }

    public CountryCode getCountry() {
        return  CountryCode.CU;
    }

    public String getCoach() {
        return "Pep Ram�rez";
    }

    public Color getShirtColor() {
        return new Color(51, 0, 153);
    }

    public Color getShortsColor() {
        return new Color(51, 0, 153);
    }

    public Color getShirtLineColor() {
        return new Color(153, 0, 0);
    }

    public Color getSocksColor() {
        return new Color(51, 0, 153);
    }

    public Color getGoalKeeper() {
        return new Color(51, 51, 51        );
    }

    public UniformStyle getStyle() {
        return UniformStyle.LINEAS_VERTICALES;
    }

    public Color getShirtColor2() {
        return new Color(255, 255, 0);
    }

    public Color getShortsColor2() {
        return new Color(51, 51, 51);
    }

    public Color getShirtLineColor2() {
        return new Color(153, 0, 0);
    }

    public Color getSocksColor2() {
        return new Color(255, 255, 0);
    }

    public Color getGoalKeeper2() {
        return new Color(0, 0, 153        );
    }

    public UniformStyle getStyle2() {
        return UniformStyle.SIN_ESTILO;
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
        		new PlayerDetailImpl("Valdes", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,1.0d, true),
                new PlayerDetailImpl("Pique", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Puyol", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Muniesa", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Montoya", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Busquet", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Xavi", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Iniesta", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new PlayerDetailImpl("Bojan", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false),
                new PlayerDetailImpl("Messi", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new PlayerDetailImpl("Pedro", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false)
        };
    }
}
