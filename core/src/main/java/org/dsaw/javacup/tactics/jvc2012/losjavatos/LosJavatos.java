package org.dsaw.javacup.tactics.jvc2012.losjavatos;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.LinkedList;
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

        @Override
        public String getTacticName() {
            return "Los Javatos";
        }

        @Override
        public CountryCode getCountry() {
            return CountryCode.ES;
        }

        @Override
        public String getCoach() {
            return "Angel Alfonso";
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
            return new Color(255, 255, 102);
        }

        @Override
        public Color getSocksColor() {
            return new Color(255, 255, 102);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(242, 59, 40        );
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.FRANJA_DIAGONAL;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(0, 0, 204);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(255, 255, 102);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(255, 255, 102);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(152, 100, 140        );
        }

        @Override
        public UniformStyle getStyle2() {
            return UniformStyle.FRANJA_DIAGONAL;
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

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return this.alineacion1;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion1;
    }
    
   
    
    
    
    LinkedList<Command> comandos = new LinkedList<>();
    
           
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
    

   
