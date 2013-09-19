package org.javahispano.javacup.tacticas.jvc2012.sotilezos;

import java.awt.Color;
import org.javahispano.javacup.model.*;
import org.javahispano.javacup.model.util.*;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.engine.GameSituations;

import java.util.LinkedList;
import java.util.List;

public class Sotilezos implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(-3.090909090909091,-21.61764705882353),
        new Position(13.076923076923078,-27.31900452488688),
        new Position(8.55944055944056,-34.44570135746606),
        new Position(-10.6993006993007,-34.68325791855204),
        new Position(-17.356643356643357,-27.794117647058822),
        new Position(-0.23776223776223776,-34.68325791855204),
        new Position(-11.174825174825173,-9.027149321266968),
        new Position(13.314685314685315,-17.104072398190045),
        new Position(-0.951048951048951,-8.552036199095022),
        new Position(-18.307692307692307,-16.628959276018097),
        new Position(8.083916083916083,-9.027149321266968)
    };

    Position alineacion2[]=new Position[]{
        new Position(-3.090909090909091,-21.61764705882353),
        new Position(13.076923076923078,-27.31900452488688),
        new Position(8.55944055944056,-34.44570135746606),
        new Position(-10.6993006993007,-34.68325791855204),
        new Position(-17.356643356643357,-27.794117647058822),
        new Position(-0.23776223776223776,-34.68325791855204),
        new Position(-11.174825174825173,-9.027149321266968),
        new Position(13.314685314685315,-17.104072398190045),
        new Position(-1.4265734265734267,-9.264705882352942),
        new Position(-18.307692307692307,-16.628959276018097),
        new Position(8.083916083916083,-9.027149321266968)
    };

    Position alineacion3[]=new Position[]{
        new Position(-2.377622377622378,-20.90497737556561),
        new Position(13.076923076923078,-27.31900452488688),
        new Position(8.55944055944056,-34.44570135746606),
        new Position(-10.6993006993007,-34.68325791855204),
        new Position(-17.356643356643357,-27.794117647058822),
        new Position(-0.23776223776223776,-34.68325791855204),
        new Position(-11.174825174825173,-9.027149321266968),
        new Position(13.314685314685315,-17.104072398190045),
        new Position(-0.951048951048951,-8.552036199095022),
        new Position(-18.307692307692307,-16.628959276018097),
        new Position(8.083916083916083,-9.027149321266968)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Sotilezos";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Roberto";
        }

        public Color getShirtColor() {
            return new Color(255, 0, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 51, 153);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 153);
        }

        public Color getSocksColor() {
            return new Color(0, 0, 153);
        }

        public Color getGoalKeeper() {
            return new Color(255, 0, 51        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
        }

        public Color getShirtColor2() {
            return new Color(244, 133, 3);
        }

        public Color getShortsColor2() {
            return new Color(102, 201, 103);
        }

        public Color getShirtLineColor2() {
            return new Color(194, 2, 42);
        }

        public Color getSocksColor2() {
            return new Color(57, 46, 58);
        }

        public Color getGoalKeeper2() {
            return new Color(58, 167, 138        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.LINEAS_HORIZONTALES;
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
                new JugadorImpl("Kasiyas", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Halbaro Harveloa", 2, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Halviol", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.0d,0.0d, false),
                new JugadorImpl("Zerjio Ramos", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.0d,0.0d, false),
                new JugadorImpl("Marrzelo", 5, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Las Diara", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.42d,0.08d, false),
                new JugadorImpl("Savi Halonso", 7, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Kristiano Ronaldo", 8, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Bencena", 9, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Hozil", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Kayegon", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion1;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
    }

    public List<Command> execute(GameSituations sp) {
    	LinkedList<Command> comandos = new LinkedList<Command>();
    	Position[] jug_propios = sp.myPlayers();
    	
     	if(!sp.isRivalStarts() && !sp.isStarts()){
	    	if(sp.iteration()%4==0){
		   		for (int i = 0; i < 11; i++) {
		   			double x = jug_propios[i].getX();
		   			double y = jug_propios[i].getY()+0.5;
	    			comandos.add(new CommandMoveTo(i, new Position( x, y)));
		   		}
	    	}else if(sp.iteration()%4==1){
		   		for (int i = 0; i < 11; i++) {
		   			double x = jug_propios[i].getX()+0.5;
		   			double y = jug_propios[i].getY();
	    			comandos.add(new CommandMoveTo(i, new Position( x, y)));
		   		}
	    	}else if(sp.iteration()%4==2){
		   		for (int i = 0; i < 11; i++) {
		   			double x = jug_propios[i].getX();
		   			double y = jug_propios[i].getY()-0.5;
	    			comandos.add(new CommandMoveTo(i, new Position( x, y)));
		   		}
	    	}else if(sp.iteration()%4==3){
		   		for (int i = 0; i < 11; i++) {
		   			double x = jug_propios[i].getX()-0.5;
		   			double y = jug_propios[i].getY();
	    			comandos.add(new CommandMoveTo(i, new Position( x, y)));
		   		}
	    	} 
     	}
    	
        return comandos;
    }



}
