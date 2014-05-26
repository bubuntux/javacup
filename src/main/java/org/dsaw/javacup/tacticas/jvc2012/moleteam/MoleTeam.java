/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.moleteam;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.render.EstiloUniforme;
import java.awt.Color;
import java.util.*;

/**
 *
 * @author jlosarcos
 */
public class MoleTeam implements Tactic {
    
    Position alineacionRecibiendo[]=new Position[]{
      new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.503496503496503,-26.368778280542987),
        new Position(-3.5664335664335667,-32.30769230769231),
        new Position(7.37062937062937,-33.49547511312217),
        new Position(14.265734265734267,-26.606334841628957),
        new Position(-0.4755244755244755,-19.717194570135746),
        new Position(-6.419580419580419,-7.126696832579185),
        new Position(6.419580419580419,-7.839366515837104),
        new Position(-0.951048951048951,-9.97737556561086),
        new Position(-9.986013986013985,-0.47511312217194573),
        new Position(9.272727272727272,-0.23755656108597287)
    };

   

    Position alineacionSacando[]=new Position[]{
         new Position(0.2595419847328244,-50.41044776119403),
        new Position(-24.965034965034967,-18.766968325791854),
        new Position(-13.076923076923078,-26.84389140271493),
        new Position(10.6993006993007,-26.131221719457013),
        new Position(25.678321678321677,-19.479638009049776),
        new Position(-15.216783216783217,-4.751131221719457),
        new Position(-0.4755244755244755,-9.027149321266968),
        new Position(12.363636363636363,-5.9389140271493215),
        new Position(-2.6153846153846154,-0.23755656108597287),
        new Position(0.23776223776223776,-0.7126696832579186),
        new Position(8.321678321678322,-0.9502262443438915)
    };


    class TacticDetailImpl implements TacticDetail {
        @Override
        public String getTacticName() {
            return "MoleTeam";
        }

        @Override
        public String getCountry() {
            return "España";
        }

        @Override
        public String getCoach() {
            return "Jagoba Los Arcos";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 0, 0);
        }

        @Override
        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 255, 0        );
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(89, 60, 75);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(135, 251, 251);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(171, 1, 167);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(189, 54, 6);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(203, 229, 164        );
        }

        @Override
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
            /*Full Speed*/
             return new PlayerDetail[]{
                new JugadorImpl("Mole", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Periketa", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Periketo", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Perikin", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Perikina", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Periko", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Perika", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Peri", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.8d, false),
                new JugadorImpl("Perikero", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.8d, false),
                new JugadorImpl("Perikera", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.8d, false),
                new JugadorImpl("Perikerin", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.9d, false)
            };
            /*Full error*/
//            return new PlayerDetail[]{
//               new JugadorImpl("Mole", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
//                new JugadorImpl("Periketa", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.4d,1.0d, false),
//                new JugadorImpl("Periketo", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.4d,1.0d, false),
//                new JugadorImpl("Perikin", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.4d,1.0d, false),
//                new JugadorImpl("Perikina", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.4d,1.0d, false),
//                new JugadorImpl("Periko", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
//                new JugadorImpl("Perika", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
//                new JugadorImpl("Peri", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
//                new JugadorImpl("Perikero", 9, new Color(255,200,150), new Color(50,0,0),0.5d,1.0d,1.0d, false),
//                new JugadorImpl("Perikera", 10, new Color(255,200,150), new Color(50,0,0),0.5d,0.8d,1.0d, false),
//                new JugadorImpl("Perikerin", 11, new Color(255,200,150), new Color(50,0,0),0.5d,1.0d,1.0d, false)
//            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
    return alineacionSacando;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacionRecibiendo;
    }
    Variables vars;
    @Override
    public List<Command> execute(GameSituations sp) {
        vars = new Variables(sp);
        for(int i=0;i<11;i++)
        {
             vars.myPlayers.GetPlayerByIndex(i).getCommands(vars);
        }
        return vars.getCommands();
    }
  

}
