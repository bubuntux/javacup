package org.dsaw.javacup.tacticas.jvc2012.losjavatos;

import java.awt.Color;
import java.util.LinkedList;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.EstiloUniforme;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;
import java.util.List;

public class LosJavatos implements Tactic {
    Position alineacion1[]=new Position[]{
        new Position(0,-51), // PORTERO Nº1
        new Position(20,-25), // LATERAL DERECHO Nº2
        new Position(7,-25), // CENTRAL DERECHO Nº3
        new Position(-7,-25), // CENTRAL IZQUIEDRO Nº4
        new Position(-20,-25), // LATERAL IZQUIEDO Nº5
        new Position(-7,-10), // MEDIOCENTRO IZQUIEROD Nº6
        new Position(-7,-1), // DELANTERO IZQ Nº7
        new Position(7,-10), // MEDIOCENTRO DERECHO Nº8 
        new Position(-20,-1), // MEDIO IZQUIERDA Nº9  
        new Position(7,-1), // DELANTERO Nº10
        new Position(20,-1) // MEDIO DERECHO Nº11
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Los Javatos";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Angel Alfonso";
        }

        public Color getShirtColor() {
            return new Color(0, 0, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(255, 255, 102);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 102);
        }

        public Color getGoalKeeper() {
            return new Color(242, 59, 40        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_DIAGONAL;
        }

        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor2() {
            return new Color(0, 0, 204);
        }

        public Color getShirtLineColor2() {
            return new Color(255, 255, 102);
        }

        public Color getSocksColor2() {
            return new Color(255, 255, 102);
        }

        public Color getGoalKeeper2() {
            return new Color(152, 100, 140        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_DIAGONAL;
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
                new JugadorImpl("Jorge", 1, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Javi", 2, new Color(255,200,150), new Color(255,255,255),1.0d,0.35d,0.35d, false),
                new JugadorImpl("Alex", 3, new Color(255,200,150), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Juan Carlos", 4, new Color(255,200,150), new Color(255,255,255),1.0d,0.3d,0.5d, false),
                new JugadorImpl("Jesús", 5, new Color(255,200,150), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Roberto", 6, new Color(255,200,150), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Angel", 7, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Miguel", 8, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Fran", 9, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Juan H", 10, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Leon", 11, new Color(255,200,150), new Color(255,255,255),1.0d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
        return this.alineacion1;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion1;
    }
    
   
    
    
    
    LinkedList<Command> comandos = new LinkedList<Command>();
    
           
    @Override
    public List<Command> execute(GameSituations sp) {
        comandos.clear();
        Common c = new Common(sp);
        if ((sp.ballPosition().getX() == 0.0) && (sp.ballPosition().getY() == 0.0)) {
                c.sI();
                return c.comCom;
        }
        if ((sp.ballPosition().getY() == 52.5) && sp.isStarts()) {
                c.Co();
                return c.comCom;
        }
        if ((sp.ballPosition().getX() == -34 || sp.ballPosition().getX() == 34) && sp.isStarts()) {
                c.Ba();
                return c.comCom;
        }
        if (c.defense()) {
            Defense de = new Defense(sp);
            de.tactic1();
            return de.comandos;
        } else {
            Attack at = new Attack(sp);
            at.ataque1();
            return at.comandos;
        }
    }
    
       
    
    
    
    
    
    
    
    
    
   
        
   
        
    
    
    
}
    

   
