/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.bo;

import java.awt.Color;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.render.UniformStyle;

/**
 *
 * @author jsebas
 */
public class TacticSpecification implements TacticDetail {

    @Override
    public String getTacticName() {
        return "Team Chimuelos";
    }

    @Override
    public String getCountry() {
        return "Bolivia";
    }

    @Override
    public String getCoach() {
        return "Mr Sese";
    }

    @Override
    public Color getShirtColor() {
        return new Color(255, 0, 0);
    }

    @Override
    public Color getShortsColor() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getShirtLineColor() {
        return new Color(255, 102, 102);
    }

    @Override
    public Color getSocksColor() {
        return new Color(102, 255, 102);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(153, 153, 255);
    }

    @Override
    public UniformStyle getStyle() {
        return UniformStyle.FRANJA_VERTICAL;
    }

    @Override
    public Color getShirtColor2() {
        return new Color(102, 102, 255);
    }

    @Override
    public Color getShortsColor2() {
        return new Color(12, 121, 219);
    }

    @Override
    public Color getShirtLineColor2() {
        return new Color(194, 175, 182);
    }

    @Override
    public Color getSocksColor2() {
        return new Color(153, 255, 255);
    }

    @Override
    public Color getGoalKeeper2() {
        return new Color(255, 153, 0);
    }

    @Override
    public UniformStyle getStyle2() {
        return UniformStyle.FRANJA_DIAGONAL;
    }
    
    @Override
    public PlayerDetail[] getPlayers() {
        return new PlayerDetail[] {                
                new SoccerPlayer("Carlos Truco", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,1.0d, true),
                new SoccerPlayer("Marcos Sandi", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Julio Cesar Valdivieso", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Desconocido 0", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Desconocido 1", 18, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Desconocido 2", 19, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Desconocido 3", 20, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Cristaldo", 21, new Color(255,200,150), new Color(50,0,0),1.0d,0.35d,1.0d, false),
                new SoccerPlayer("Desconocido 5", 22, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false),
                new SoccerPlayer("El Diablo Etcheverry", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new SoccerPlayer("Botero", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.65d,1.0d, false)
        };
    }
}
