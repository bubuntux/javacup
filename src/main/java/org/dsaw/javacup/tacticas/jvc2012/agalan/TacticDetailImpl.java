package org.dsaw.javacup.tacticas.jvc2012.agalan;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.render.UniformStyle;

class TacticDetailImpl implements TacticDetail {

    @Override
    public String getTacticName() {
        return "AA_New_Team";
    }

    @Override
    public String getCountry() {
        return "España";
    }

    @Override
    public String getCoach() {
        return "José Mourinho";
    }

    @Override
    public Color getShirtColor() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getShortsColor() {
        return new Color(8, 35, 252);
    }

    @Override
    public Color getShirtLineColor() {
        return new Color(0, 0, 255);
    }

    @Override
    public Color getSocksColor() {
        return new Color(0, 0, 255);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(255, 0, 0        );
    }

    @Override
    public UniformStyle getStyle() {
        return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
        return new Color(92, 131, 50);
    }

    @Override
    public Color getShortsColor2() {
        return new Color(62, 4, 67);
    }

    @Override
    public Color getShirtLineColor2() {
        return new Color(140, 160, 102);
    }

    @Override
    public Color getSocksColor2() {
        return new Color(85, 92, 177);
    }

    @Override
    public Color getGoalKeeper2() {
        return new Color(167, 211, 223        );
    }

    @Override
    public UniformStyle getStyle2() {
        return UniformStyle.LINEAS_HORIZONTALES;
    }
    
    @Override
    public PlayerDetail[] getPlayers() {
        return new PlayerDetail[]{
    		new JugadorImpl("Paquete", 11, new Color(255,200,150), new Color(50,0,0),1,1,1,false),
    		new JugadorImpl("Conchita", 2, new Color(255,200,150), new Color(50,0,0),1,0.5,0.5,false),
    		new JugadorImpl("San", 3, new Color(255,200,150), new Color(50,0,0),1,0.5,0.5,false),
    		new JugadorImpl("Bernardo", 4, new Color(255,200,150), new Color(50,0,0),1,0.5,0.5,false),
    		new JugadorImpl("Oleeee", 5, new Color(255,200,150), new Color(50,0,0),1,0.5,0.5,false),
    		new JugadorImpl("Rubia", 6, new Color(255,200,150), new Color(50,0,0),1,0.5,0.5,false),
    		new JugadorImpl("Morena", 7, new Color(255,200,150), new Color(50,0,0),1,0.75,1,false),
    		new JugadorImpl("Pelijara", 8, new Color(255,200,150), new Color(50,0,0),1,0.75,1,false),
    		new JugadorImpl("Lo", 9, new Color(255,200,150), new Color(50,0,0),1,1,1,false),
    		new JugadorImpl("Peto", 10,new Color(255,200,150), new Color(50,0,0),1,1,1,false),
    		new JugadorImpl("Embudo", 1, new Color(255,200,150), new Color(50,0,0),1,1,1,true)
        };
    }
}
