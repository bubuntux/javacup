/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javahispano.javacup.tacticas.jvc2012.abonilla;


import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.render.EstiloUniforme;
import java.awt.Color;
import java.util.LinkedList;
import org.javahispano.javacup.model.*;
import java.util.List;
import java.util.Random;

public class Arsenal4 implements Tactic {

    Position alineacion1[] = new Position[]{
        new Position(0.951048951048951,-49.64932126696832),
        new Position(-19.020979020979023,-31.59502262443439),
        new Position(0.7132867132867133,-28.50678733031674),
        new Position(19.25874125874126,-31.59502262443439),
        new Position(1.6643356643356644,-7.126696832579185),
        new Position(-15.692307692307693,-7.364253393665159),
        new Position(-23.3006993006993,11.877828054298643),
        new Position(17.594405594405593,12.115384615384617),
        new Position(-26.867132867132867,35.8710407239819),
        new Position(-5.468531468531468,16.628959276018097),
        new Position(-1.188811188811189,40.38461538461539)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(27.251908396946565, -27.94776119402985),
        new Position(-29.84732824427481, -26.902985074626866),
        new Position(8.564885496183205, -7.574626865671642),
        new Position(-10.641221374045802, -7.052238805970149),
        new Position(27.251908396946565, 4.440298507462686),
        new Position(-29.32824427480916, 3.3955223880597014),
        new Position(-0.2595419847328244, 19.067164179104477),
        new Position(-0.2595419847328244, 35.78358208955224)
    };
    Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(26.732824427480914, -20.111940298507463),
        new Position(-29.32824427480916, -21.67910447761194),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(-18.946564885496183, -0.26119402985074625),
        new Position(18.946564885496183, -0.26119402985074625),
        new Position(-19.46564885496183, 35.78358208955224),
        new Position(-0.2595419847328244, 19.067164179104477),
        new Position(18.946564885496183, 35.26119402985075)
    };
    Position alineacion4[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(11.16030534351145, -1.3059701492537314),
        new Position(-10.641221374045802, -0.7835820895522387),
        new Position(-27.251908396946565, 31.6044776119403),
        new Position(-10.641221374045802, 30.559701492537314),
        new Position(9.603053435114505, 28.992537313432837),
        new Position(25.69465648854962, 28.992537313432837)
    };
    Position alineacion5[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(5.969465648854961, -5.485074626865671),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(22.580152671755727, -1.3059701492537314)
    };
    Position alineacion6[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(6.4885496183206115, -6.529850746268657),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(22.580152671755727, -1.3059701492537314)
    };

    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Arsenal4";
        }

        @Override
        public String getCountry() {
            return "Colombia";
        }

        @Override
        public String getCoach() {
            return "Anyela";
        }

        @Override
        public Color getShirtColor() {
              return new Color(255, 51, 0);
        }

        @Override
        public Color getShortsColor() {
                return new Color(255, 51, 0);
        }

        @Override
        public Color getShirtLineColor() {
                return new Color(255, 51, 0);
        }

        @Override
        public Color getSocksColor() {
            return new Color(2, 206, 51);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 0, 0);
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getShirtLineColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(10, 171, 214);
        }

        @Override
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
                this.nombre = nombre;
                this.numero = numero;
                this.piel = piel;
                this.pelo = pelo;
                this.velocidad = velocidad;
                this.remate = remate;
                this.presicion = presicion;
                this.portero = portero;
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
                        new JugadorImpl("Christofer", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, true),
                        new JugadorImpl("Ruben", 2, new Color(0, 0, 0), new Color(0, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Gabriel", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Gari", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Miguel", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Hugo", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Mark", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Mauricio", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Humberto", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
                        new JugadorImpl("Mago", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.81d, false),
                        new JugadorImpl("Alexis", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticDetailImpl();

    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacion5;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion6;
    }
    //Lista de Commands
    LinkedList<Command> Commands = new LinkedList<Command>();
    
    int cont;
    boolean activarZigZag = false;
    double[] zigzageñosX = {-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40};
    
    @Override
    public List<Command> execute(GameSituations sp) {
        //Limpia la lista de Commands
        Commands.clear();
        //enPosInicial = true;
        //Obtiene las Positiones de tus jugadores
        Position[] jugadores = sp.myPlayers();
        Position PositionPorteroContrario = sp.rivalPlayers()[0];
        for (int i = 0; i < jugadores.length; i++) {
            //Ordena a cada jugador que se ubique segun la alineacion1
            if(  i!=10 && i != 8 && i != 6 ){
                Commands.add(new CommandMoveTo(i, alineacion1[i]));
            } else if ( i==10 ){
                Position PositionBalon = new Position( sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]);
              //  Commands.add(new CommandMoveTo(i,Position.media(PositionPorteroContrario, PositionBalon)));
                Position PositionMedia = Position.medium(PositionPorteroContrario, jugadores[8]);
                PositionMedia = Position.medium(PositionPorteroContrario, PositionMedia );
                Commands.add(new CommandMoveTo(i, Position.medium(PositionPorteroContrario,PositionMedia)));
            } else if (i==8 || i == 6  ) {
                Position PositionNueva = null;
                if (  jugadores[i].distance(alineacion1[i]) < 10 )   {
                    activarZigZag = true;
                } else if (!activarZigZag ){
                        PositionNueva = alineacion1[i];
                } 
                if ( activarZigZag){
                    if ( jugadores[i].distance(alineacion1[i]) > 10 ){
                       activarZigZag = false;
                       PositionNueva = alineacion1[i];
                    } else {
                         cont++;
                        int dir = 0;
                        if ( jugadores[i].getX() < 30 && (jugadores[i].getX()- zigzageñosX[i]) > 0  ){
                            dir = 10;
                        } else if ( jugadores[i].getX() > -30 && (jugadores[i].getX()- zigzageñosX[i]) < 0 ) {
                            dir = -10;
                        } else if ( jugadores[i].getX() < -30 ) {
                            dir = 10;
                        } else if ( jugadores[i].getX() > 30  ){
                            dir = -10;
                        }
                        zigzageñosX[i] = jugadores[i].getX();
                        PositionNueva = new Position(jugadores[i].getX()+dir,jugadores[i].getY());
                   }
                }
                Commands.add(new CommandMoveTo(i, PositionNueva));
                } 
                
            
            
        }
    
        
        //Si no saca el rival
        if (!sp.isRivalStarts()) {
            //Obtiene los datos de recuperacion del balon
            int[] recuperadores = sp.getRecoveryBall();
            //Si existe posibilidad de recuperar el balon
            if (recuperadores.length > 1) {
                //Obtiene las coordenadas del balon en el instante donde
                //se puede recuperar el balon
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
                //Recorre la lista de jugadores que pueden recuperar
                for (int i = 1; i < 2; i++) {
                    //Ordena a los jugadores recuperadores que se ubique
                    //en la Position de recuperacion
                    Commands.add(new CommandMoveTo(recuperadores[i],
                            new Position(posRecuperacion[0], posRecuperacion[1])));
                }
            }
        }
        //Instancia un generador aleatorio
        Random r = new Random();
        //Recorre la lista de mis jugadores que pueden rematar
        for (int i : sp.canKick()) {
            //Si el jugador es de indice 8 o 10
            if ( i == 10) {
                Commands.add(new CommandHitBall(i,Position.medium(PositionPorteroContrario,Constants.centroArcoSup), 1, 12 + r.nextInt(6)));
            } else if (i == 8 ) {
                double fuerza = (jugadores[10].distance(jugadores[i])/40)+(1-sp.getMyPlayerPower(i));
                Commands.add(new CommandHitBall(i,jugadores[10], fuerza, 45 ));
            } 
            else {
                //inicia contador en cero
                int count = 0;
                int jugadorDestino;
                //Repetir mientras el jugador destino sea igual al jugador que remata
                while (((jugadorDestino = r.nextInt(11)) == i
                        //o mientras la coordenada y del jugador que remata
                        //es mayor que la coordenada y del que recibe
                        || jugadores[i].getY() > jugadores[jugadorDestino].getY())
                        //Y mientras el contador es menor a 20
                        && count < 20) {
                    //incrementa el contador
                    count++;
                }
                //Si el receptor del balon es el que remata
                if (i == jugadorDestino) {
                    while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
                    }
                }
                //Ordena que el jugador que puede rematar que de un pase
                //al jugador destino
                double anguloZ = 45;
                jugadorDestino = 8;
                double fuerza = (jugadores[jugadorDestino].distance(jugadores[i])/70)+(1-sp.getMyPlayerPower(i)); 
                //System.out.println("Position 9 y 11 "+i+" a "+jugadores[8]+" "+jugadores[10]);
                Commands.add(new CommandHitBall(i, jugadores[jugadorDestino], fuerza,anguloZ));
            }
        }
        //Retorna la lista de Commands
        return Commands;
    }
}
