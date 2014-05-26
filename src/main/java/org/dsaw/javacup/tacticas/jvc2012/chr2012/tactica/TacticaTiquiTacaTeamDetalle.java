package org.dsaw.javacup.tacticas.jvc2012.chr2012.tactica;

import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.render.EstiloUniforme;
import java.awt.Color;


/**
 * Clase que implementa los detalles requeridos por el framework para la t�ctica desarrollada.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class TacticaTiquiTacaTeamDetalle  implements TacticDetail {

    public String getTacticName() {
        return "Tiqui-Taca Team";
    }

    public String getCountry() {
        return "España";
    }

    public String getCoach() {
        return "El Sabio de Hortaleza";
    }

    
    public Color getShirtColor() {
        return new Color(0, 0, 0);
    }

    public Color getShortsColor() {
        return new Color(0, 0, 0);
    }

    public Color getShirtLineColor() {
        return new Color(255, 255, 0);
    }

    public Color getSocksColor() {
        return new Color(0, 0, 0);
    }

    public Color getGoalKeeper() {
        return new Color(255, 255, 0        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.SIN_ESTILO;
    }

    public Color getShirtColor2() {
        return new Color(255, 255, 0);
    }

    public Color getShortsColor2() {
        return new Color(255, 255, 0);
    }

    public Color getShirtLineColor2() {
        return new Color(0, 0, 0);
    }

    public Color getSocksColor2() {
        return new Color(255, 255, 0);
    }

    public Color getGoalKeeper2() {
        return new Color(0, 0, 0        );
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