package org.dsaw.javacup.tactics.jvc2012.chr2012.tactica;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;


/**
 * Clase que implementa los detalles requeridos por el framework para la t�ctica desarrollada.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class TacticaTiquiTacaTeamDetalle  implements TacticDetail {

    @Override
    public String getTacticName() {
        return "Tiqui-Taca Team";
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.ES;
    }

    @Override
    public String getCoach() {
        return "El Sabio de Hortaleza";
    }

    
    @Override
    public Color getShirtColor() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getShortsColor() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getShirtLineColor() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getSocksColor() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(255, 255, 0        );
    }

    @Override
    public UniformStyle getStyle() {
        return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getShortsColor2() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getShirtLineColor2() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getSocksColor2() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getGoalKeeper2() {
        return new Color(0, 0, 0        );
    }

    @Override
    public UniformStyle getStyle2() {
        return UniformStyle.SIN_ESTILO;
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
    	 																					// velocidad, remate, presicion
        return new PlayerDetail[]{
        	new JugadorImpl("Jugador 1",   1, new Color(255,200,150), new Color(204,204,0),	1.0d,1.0d,1.0d,   true),
            new JugadorImpl("Jugador 2",   2, new Color(255,200,150), new Color(0,0,0),		1.0d,0.50d,0.50d, false),
            new JugadorImpl("Jugador 3",   3, new Color(255,200,150), new Color(50,0,0),	1.0d,0.50d,0.50d, false),
            new JugadorImpl("Jugador 4",   4, new Color(255,200,150), new Color(50,0,0),	1.0d,0.50d,0.50d, false),
            new JugadorImpl("Jugador 5",   5, new Color(255,200,150), new Color(0,0,0),		1.0d,0.50d,0.50d, false),
            new JugadorImpl("Jugador 6",   6, new Color(51,0,0),      new Color(51,0,0),	1.0d,0.55d,0.7d,  false),
            new JugadorImpl("Jugador 7",   7, new Color(255,200,150), new Color(50,0,0),	1.0d,1.0d,1.0d,   false),
            new JugadorImpl("Jugador 8",   8, new Color(255,200,150), new Color(0,0,0),		1.0d,0.55d,0.7d,  false),
            new JugadorImpl("Jugador 9",   9, new Color(255,200,150), new Color(0,0,0),		1.0d,1.0d,1.0d,   false),
            new JugadorImpl("Jugador 10", 10, new Color(255,200,150), new Color(255,204,51),1.0d,1.0d,1.0d,   false),
            new JugadorImpl("Jugador 11", 11, new Color(255,200,150), new Color(0,0,0),		1.0d,1.0d,1.0d,   false)
            };
    }
}