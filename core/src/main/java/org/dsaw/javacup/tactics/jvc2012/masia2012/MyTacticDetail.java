package org.dsaw.javacup.tactics.jvc2012.masia2012;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class MyTacticDetail implements TacticDetail{
	
	@Override
        public String getTacticName() {
        return "La Masia";
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.CU;
    }

    @Override
    public String getCoach() {
        return "Karel Osorio";
    }

    @Override
    public Color getShirtColor() {
        return new Color(51, 0, 153);
    }

    @Override
    public Color getShortsColor() {
        return new Color(51, 0, 153);
    }

    @Override
    public Color getShirtLineColor() {
        return new Color(153, 0, 0);
    }

    @Override
    public Color getSocksColor() {
        return new Color(51, 0, 153);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(51, 51, 51        );
    }

    @Override
    public UniformStyle getStyle() {
        return UniformStyle.LINEAS_VERTICALES;
    }

    @Override
    public Color getShirtColor2() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getShortsColor2() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getShirtLineColor2() {
        return new Color(255, 204, 0);
    }

    @Override
    public Color getSocksColor2() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getGoalKeeper2() {
        return new Color(51, 51, 255);
    }

    @Override
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
        return new PlayerDetail[]{
        		new PlayerDetailImpl("Valdes", 1, new Color(255,200,150), new Color(50,0,0),	1.0d,0.85d,1.0d, true),
        		new PlayerDetailImpl("Pique", 3, new Color(255,200,150), new Color(50,0,0),		1.0d,0.70d,0.0d, false),
                new PlayerDetailImpl("Puyol", 5, new Color(255,200,150), new Color(50,0,0),		1.0d,0.70d,0.0d, false),
                new PlayerDetailImpl("Alba", 4, new Color(255,200,150), new Color(50,0,0),		1.0d,0.70d,0.0d, false),
                new PlayerDetailImpl("Montoya", 2, new Color(255,200,150), new Color(50,0,0),	1.0d,0.70d,0.0d, false),
                new PlayerDetailImpl("Busquet", 7, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,0.95d, false),
                new PlayerDetailImpl("Xavi", 6, new Color(255,200,150), new Color(50,0,0),		1.0d,1.0d,0.95d, false),
                new PlayerDetailImpl("Iniesta", 8, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,0.95d, false),
                new PlayerDetailImpl("Cuenca", 9, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,1.0d, false),
                new PlayerDetailImpl("Messi", 10, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,1.0d, false),
                new PlayerDetailImpl("Tello", 11, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,1.0d, false)
        };
    }
}
