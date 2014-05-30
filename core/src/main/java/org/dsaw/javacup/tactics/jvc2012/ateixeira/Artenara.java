package org.dsaw.javacup.tactics.jvc2012.ateixeira;

import com.neovisionaries.i18n.CountryCode;

import java.awt.Color;
import java.util.LinkedList;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.UniformStyle;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.List;

public class Artenara implements Tactic {
     Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-5.468531468531468,-44.18552036199095),
        new Position(4.041958041958042,-45.61085972850679),
        new Position(14.503496503496503,-38.4841628959276),
        new Position(-17.832167832167833,-36.10859728506787),
        new Position(-0.7132867132867133,-29.93212669683258),
        new Position(-15.454545454545453,-17.104072398190045),
        new Position(12.839160839160838,-21.142533936651585),
        new Position(-11.888111888111888,-7.364253393665159),
        new Position(-0.23776223776223776,-2.1380090497737556),
        new Position(18.307692307692307,-3.088235294117647)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(19.020979020979023,0.23755656108597287),
        new Position(-19.25874125874126,-0.47511312217194573),
        new Position(-0.23776223776223776,25.18099547511312),
        new Position(-13.552447552447552,31.83257918552036),
        new Position(12.125874125874127,32.54524886877828),
        new Position(-8.55944055944056,44.42307692307692),
        new Position(8.55944055944056,45.84841628959276),
        new Position(0.0,41.57239819004525)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-8.797202797202797,-39.90950226244344),
        new Position(5.468531468531468,-42.76018099547511),
        new Position(11.412587412587413,-15.91628959276018),
        new Position(-16.643356643356643,-11.165158371040723),
        new Position(-2.8531468531468533,14.490950226244346),
        new Position(-23.3006993006993,8.552036199095022),
        new Position(21.636363636363637,9.97737556561086),
        new Position(-3.090909090909091,32.30769230769231),
        new Position(-11.888111888111888,38.72171945701358),
        new Position(7.132867132867133,40.147058823529406)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.65034965034965,-40.147058823529406),
        new Position(6.419580419580419,-40.62217194570136),
        new Position(20.20979020979021,-35.15837104072398),
        new Position(-21.874125874125873,-31.59502262443439),
        new Position(10.937062937062937,-15.91628959276018),
        new Position(-10.6993006993007,-13.540723981900454),
        new Position(-18.06993006993007,6.176470588235294),
        new Position(-12.125874125874127,16.628959276018097),
        new Position(3.090909090909091,15.203619909502263),
        new Position(17.594405594405593,4.751131221719457)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-6.419580419580419,-41.80995475113122),
        new Position(7.608391608391608,-43.94796380090498),
        new Position(18.06993006993007,-29.21945701357466),
        new Position(-17.11888111888112,-27.556561085972852),
        new Position(7.37062937062937,-13.303167420814479),
        new Position(-7.846153846153847,-16.391402714932127),
        new Position(23.776223776223777,-10.452488687782806),
        new Position(-9.986013986013985,-7.364253393665159),
        new Position(6.181818181818182,11.877828054298643),
        new Position(-10.223776223776223,22.56787330316742)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-9.748251748251748,-43.94796380090498),
        new Position(10.937062937062937,-44.18552036199095),
        new Position(26.153846153846157,-35.8710407239819),
        new Position(-23.062937062937063,-37.29638009049774),
        new Position(9.510489510489512,-32.782805429864254),
        new Position(-11.65034965034965,-30.16968325791855),
        new Position(-13.314685314685315,-21.142533936651585),
        new Position(15.692307692307693,-21.855203619909503),
        new Position(0.951048951048951,-15.678733031674208),
        new Position(11.412587412587413,-8.314479638009049)
    };

    Position alineacion7[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-7.846153846153847,-47.748868778280546),
        new Position(13.076923076923078,-37.53393665158371),
        new Position(10.6993006993007,-45.13574660633484),
        new Position(-11.888111888111888,-38.72171945701358),
        new Position(2.8531468531468533,-23.755656108597286),
        new Position(-8.55944055944056,-26.606334841628957),
        new Position(23.776223776223777,-18.054298642533936),
        new Position(-25.202797202797203,-16.628959276018097),
        new Position(8.083916083916083,-5.9389140271493215),
        new Position(-9.272727272727272,-0.23755656108597287)
    };

    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Artenara";
        }

        @Override
        public CountryCode getCountry() {
            return CountryCode.ES;
        }

        @Override
        public String getCoach() {
            return "Tex";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 255, 102);
        }

        @Override
        public Color getShortsColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(51, 102, 255);
        }

        @Override
        public Color getSocksColor() {
            return new Color(51, 102, 255);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(204, 255, 204);
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(51, 51, 51);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(204, 255, 204);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(252, 4, 126);
        }

        @Override
        public UniformStyle getStyle2() {
            return UniformStyle.FRANJA_VERTICAL;
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
                new JugadorImpl("H", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.56d,1.0d, true),
                new JugadorImpl("He", 2, new Color(255,200,150), new Color(50,0,0),0.83d,0.5d,0.71d, false),
                new JugadorImpl("Li", 3, new Color(255,200,150), new Color(50,0,0),0.85d,0.5d,0.75d, false),
                new JugadorImpl("Be", 4, new Color(255,200,150), new Color(50,0,0),0.92d,0.67d,0.81d, false),
                new JugadorImpl("B", 5, new Color(255,200,150), new Color(50,0,0),0.92d,0.66d,0.58d, false),
                new JugadorImpl("C", 6, new Color(255,200,150), new Color(50,0,0),0.72d,0.66d,0.61d, false),
                new JugadorImpl("N", 7, new Color(255,200,150), new Color(50,0,0),0.81d,0.69d,0.55d, false),
                new JugadorImpl("O", 8, new Color(255,200,150), new Color(50,0,0),0.98d,0.77d,0.67d, false),
                new JugadorImpl("F", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.91d,0.86d, false),
                new JugadorImpl("Ne", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.94d,0.82d, false),
                new JugadorImpl("Na", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.92d,0.89d, false)
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
        return alineacion1;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion6;
    }

    //Lista de comandos
    LinkedList<Command> comandos = new LinkedList<>();
    
    @Override
    public List<Command> execute(GameSituations sp) {
        comandos.clear();
        //Tomo la posicion del balon
        double PosBalonX = sp.ballPosition().getX();
        double PosBalonY = sp.ballPosition().getY();
        
        //Tomo la posicion de los jugadores
        Position[] MisPosiciones = sp.myPlayers();   
        
        int Pos = 1;
        //Vamos a los posiciones iniciales
        //Probar a mover los jugadores con getTrajectory
        for (int i = 1; i < MisPosiciones.length; i++) {
               //Defensa en mi area
                if(PosBalonY >=-55 && PosBalonY<-40){
                    comandos.add(new CommandMoveTo(i, alineacion7[i]));
                    Pos = 7;
                }
                if(PosBalonY >=-40 && PosBalonY<-23){
                    comandos.add(new CommandMoveTo(i, alineacion5[i]));
                    Pos = 6;
                }
                if(PosBalonY >=-23 && PosBalonY<-7){
                    comandos.add(new CommandMoveTo(i, alineacion5[i]));
                    Pos = 5;
                }
                if(PosBalonY >=-7 && PosBalonY<23){
                    comandos.add(new CommandMoveTo(i, alineacion3[i]));
                    Pos = 4;
                }
                if(PosBalonY >=23 && PosBalonY<40){
                    comandos.add(new CommandMoveTo(i, alineacion3[i]));
                    Pos = 3;
                }
                //Ataque en el area rival
                if(PosBalonY>=40 && PosBalonY<55){
                    comandos.add(new CommandMoveTo(i, alineacion2[i]));
                    Pos = 2;
                }
        }
                
        Position[] FMisPosiciones = alineacion7;
        int FuturoPos = Pos - 1;
        if(FuturoPos == 1)
            FuturoPos = 2;
        if(2 == FuturoPos)
            FMisPosiciones = alineacion2;
        if(3 == FuturoPos)
            FMisPosiciones = alineacion3;
        if(4 == FuturoPos)
            FMisPosiciones = alineacion3;
        if(5 == FuturoPos)
            FMisPosiciones = alineacion5;
        if(6 == FuturoPos)
            FMisPosiciones = alineacion5;
        if(7 == FuturoPos)
            FMisPosiciones = alineacion7;
        
        //Para los jugadores 1 y 2 hay que ver cual es la posicion más adelantada del cualquier juagos contrario y retrasarla hasta esa posición.
        double PosContrarioAdelantada = this.PosicionMasAdelantadaContratio(sp);
        if(MisPosiciones[1].getY() > PosContrarioAdelantada){
            MisPosiciones[1].setPosition(MisPosiciones[1].getX(), PosContrarioAdelantada);
            comandos.add(new CommandMoveTo(1, MisPosiciones[1]));
        }
        if(MisPosiciones[2].getY() > PosContrarioAdelantada){
            MisPosiciones[2].setPosition(MisPosiciones[2].getX(), PosContrarioAdelantada);
            comandos.add(new CommandMoveTo(2, MisPosiciones[2]));
        }
            
                
        if(sp.canKick().length > 0){
            for(int ck=0; ck<sp.canKick().length ; ++ck ){
                int i = sp.canKick()[ck];

                if(i == 1 || i == 2){ //Defensa retrasada
                    Position Jugadores[] = new Position[3] ;
                    Jugadores[0] = MisPosiciones[i];//Jugador que tiene la pelota
                    Jugadores[1] = MisPosiciones[3];
                    Jugadores[2] = MisPosiciones[4];                    
                    
                    Position FJugadores[] = new Position[3] ;
                    FJugadores[0] = FMisPosiciones[i];//Jugador que tiene la pelota
                    FJugadores[1] = FMisPosiciones[3];
                    FJugadores[2] = FMisPosiciones[4];                                        
                    
                    Position Pase = this.DecidirPase(Jugadores, FJugadores, sp);
                    comandos.add(new CommandHitBall(i, Pase, 0.7, true));
                }                
                if(i == 3 || i == 4){ //Defensa adelantada
                    Position Jugadores[] = new Position[4] ;
                    Jugadores[0] = MisPosiciones[i];//Jugador que tiene la pelota
                    Jugadores[1] = MisPosiciones[5];
                    Jugadores[2] = MisPosiciones[6];                                        
                    Jugadores[3] = MisPosiciones[7];   

                    Position FJugadores[] = new Position[4] ;
                    FJugadores[0] = FMisPosiciones[i];//Jugador que tiene la pelota
                    FJugadores[1] = FMisPosiciones[5];
                    FJugadores[2] = FMisPosiciones[6];                                        
                    FJugadores[3] = FMisPosiciones[7];   
                    
                    
                    Position Pase = this.DecidirPase(Jugadores, FJugadores, sp);
                    comandos.add(new CommandHitBall(i, Pase, 0.8, true));
                }
                if(i < 8 || i>= 5){ //Medios                    
                    Position Jugadores[] = new Position[3] ;
                    Jugadores[0] = MisPosiciones[i];//Jugador que tiene la pelota
                    Jugadores[1] = MisPosiciones[8];
                    Jugadores[2] = MisPosiciones[9];

                    Position FJugadores[] = new Position[3] ;
                    FJugadores[0] = FMisPosiciones[i];//Jugador que tiene la pelota
                    FJugadores[1] = FMisPosiciones[8];
                    FJugadores[2] = FMisPosiciones[9];
                    
                    Position Pase = this.DecidirPase(Jugadores, FJugadores, sp);
                    comandos.add(new CommandHitBall(i, Pase, 1, 0.2));                    
                }
                if(i == 8 ||  9 == i){ //Delanteros
                    int Opcion = this.RangoAleatorio(1, 3);
                    if(1 == Opcion){
                        Position Tiro = this.TiroAPuerta(sp.myPlayers()[i], sp.rivalPlayers()[0]);
                        comandos.add(new CommandHitBall(i, Tiro , 1, 5));                                        
                    }else{
                        int Pase = this.RangoAleatorio(8, 10);
                        comandos.add(new CommandHitBall(i, FMisPosiciones[Pase], 0.9, 1));            
                    }
                }
                if(i == 10){
                     Position Tiro = this.TiroAPuerta(sp.myPlayers()[i], sp.rivalPlayers()[0]);
                     comandos.add(new CommandHitBall(i, Tiro , 1, 5));   
                }
            }
        }
                
        
        //Si no saca el rival
        boolean PorteroActivo = false;
        if (!sp.isRivalStarts()) {
            //Obtiene los datos de recuperacion del ballPosition
            int[] recuperadores = sp.getRecoveryBall();
            //Si existe posibilidad de recuperar el ballPosition
            if (recuperadores.length > 1) {
                //Obtiene las coordenadas del ballPosition en el instante donde
                //se puede recuperar el ballPosition
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
                //Recorre la lista de jugadores que pueden recuperar
                for (int i = 1; i < recuperadores.length; i++) { //Solo 2 van a recuperar
                    //Ordena a los jugadores recuperadores que se ubique
                    //en la posicion de recuperacion
                    if(recuperadores[i] != 0){
                        comandos.add(new CommandMoveTo(recuperadores[i], new Position(posRecuperacion[0], posRecuperacion[1])));
                    }else{
                        PorteroActivo = true;
                        double Profundidad = sp.ballPosition().getY();
                        if(Profundidad > 10)
                            Profundidad = 10;
                        comandos.add(new CommandMoveTo(recuperadores[i], new Position(posRecuperacion[0], Profundidad)));
                        Position Jugadores[] = new Position[3];
                        Jugadores[0] = MisPosiciones[0];//Jugador que tiene la pelota
                        Jugadores[1] = MisPosiciones[3];
                        Jugadores[2] = MisPosiciones[4];     
                        
                        Position FJugadores[] = new Position[3] ;
                        FJugadores[0] = FMisPosiciones[i];//Jugador que tiene la pelota
                        FJugadores[1] = FMisPosiciones[3];
                        FJugadores[2] = FMisPosiciones[4];
                        
                        Position Pase = this.DecidirPase(Jugadores, FJugadores, sp);                    
                        comandos.add(new CommandHitBall(0, Pase, 1, true));
                    }
                }   
            }
            if(!PorteroActivo){
                double x = sp.ballPosition().getX();
                if(x > Constants.posteDerArcoInf.getX())
                    x = Constants.posteDerArcoInf.getX() - 5;
                if(x < Constants.posteIzqArcoInf.getX())
                    x = Constants.posteIzqArcoInf.getX() + 5;
                
                comandos.add(new CommandMoveTo(0, new Position(x,Constants.posteIzqArcoInf.getY()+4)));                
            }
        }
        
        
        return comandos;
    }
    
    /**
     * Genera un numero aleatorio entre dos dados
     * @param M Valor inferior
     * @param N Valor superior
     * @return Número entero
     */
    private int RangoAleatorio(int M, int N){
        int valorEntero = (int)Math.floor(Math.random()*(N-M+1)+M); 
        return valorEntero;
    }
    
    /**
     * Calculo del tiro
     * @param PosDelantero
     * @param PosPortero
     * @return 
     * @todo Decidir la posicion del tipo, a la derecha o a la  izquierda
     */
    private Position TiroAPuerta(Position PosDelantero, Position PosPortero){
        double a;
        double b;
        double x;
        
        //y = ax + b
        a = PosPortero.getY() - PosDelantero.getY() / PosPortero.getX() - PosDelantero.getX();
        b = (2*PosDelantero.getX()*PosDelantero.getY() -PosDelantero.getX()*PosPortero.getY() + PosDelantero.getY()*PosDelantero.getX()) / (PosPortero.getX()*PosPortero.getX());

        //X contiene el valor de la posicion del portero
        x = (Constants.LARGO_CAMPO_JUEGO / 2 - b) / a ;

        //Vemos primero si el portero está fuera de la porteria, puede dar si el delantero está ladeado y el portero adelantado
        if(x > Constants.posteDerArcoSup.getX() || x < Constants.posteIzqArcoSup.getX())
            x = PosPortero.getX();
        
        //Diferencia con el poster derecho
        if (x > 0){
            x = Constants.posteDerArcoSup.getX() - Math.random() - 0.8;
        }
        else{
            x = Constants.posteIzqArcoSup.getX() + Math.random() + 0.8;
         }
        
        Position Tiro = new Position(x, Constants.posteIzqArcoSup.getY());        
        return Tiro;
    }
    
    /**
     * Distancia entre los puntos A y B
     * @param A
     * @param B
     * @return 
     */
    private double Distancia(Position A, Position B){
        double D;
        double I;
        double J;
        I = B.getX() - A.getX();
        J = B.getY() - A.getY();
        D = Math.sqrt(Math.pow(I, 2) + Math.pow(J,2));
        return D;
    }
    
    /**
     * El pase se decide determinando que jugador está más cerca
     * @param array             Jugadores       Posicion actual 
     * @param array             JugadoresFuturo Posicion futura
     * @param GameSituations    sp    
     * @todo Hacer que se tenga en cuenta si un jugador si está solo o no.
     * @return 
     */
    private Position DecidirPase(Position Jugadores[], Position JugadoresFuturo[], GameSituations sp){
        int Pos = 0; //Posicion del jugador seleccionado
        double MenorDistancia = Constants.LARGO_CAMPO; //Menor distancia detectada
        for(int Cont=1; Cont<Jugadores.length; ++Cont){
            //Calculamos distancias
            boolean RivalCercano = false;
            for (int ContC=1; ContC<sp.rivalPlayers().length; ++ContC){
                double Distancia = this.Distancia(Jugadores[Cont], sp.rivalPlayers()[ContC]);
                if(15>Distancia)
                    RivalCercano = true;
            }            
            
            double Distancia = this.Distancia(Jugadores[0], Jugadores[Cont]);
            if(!RivalCercano)
                if(Distancia < MenorDistancia){
                    MenorDistancia = Distancia;
                    Pos = Cont;
                }            
        }
        if(0 == Pos)
            Pos = this.RangoAleatorio(1, Jugadores.length - 1);
        
        return JugadoresFuturo[Pos];
    }
    
    /**
     * Determina la posicion más adelantada del contrario.
     * @param sp
     * @return 
     */
    private double PosicionMasAdelantadaContratio(GameSituations sp){
        double Pos = Constants.posteDerArcoSup.getY(); //Iniciado a la posicion de la puerta contaria
        for(int Cont=0; Cont < sp.rivalPlayers().length; ++Cont){
            if(sp.rivalPlayers()[Cont].getY() < Pos)
                Pos = sp.rivalPlayers()[Cont].getY();
        }
        return Pos;
    }
            
}