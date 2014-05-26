package org.dsaw.javacup.tacticas.jvc2012.lgarza;
/*
 * pases interceptados
 * */
import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.EstiloUniforme;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;
import java.util.List;
import java.util.LinkedList;

public class Tactica implements Tactic {

    LinkedList<Command> comandos = new LinkedList<Command>();
    boolean delanteroAtacando = false;
    int pasadorAnterior;
    java.util.Random r = new java.util.Random();
    Position posicionAnteriorDelBalon = new Position(0,0);
    final double distanciaRemateAlArco = 24;
    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.984732824427482,-31.6044776119403),
        new Position(7.526717557251908,-11.753731343283583),
        new Position(-8.564885496183205,-11.753731343283583),
        new Position(-24.65648854961832,-2.3507462686567164),
        new Position(23.099236641221374,-2.873134328358209),
        new Position(-14.274809160305344,30.559701492537314),
        new Position(-0.7786259541984732,8.097014925373134),
        new Position(12.717557251908397,29.51492537313433)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-13.79020979020979,-37.53393665158371),
        new Position(-0.23776223776223776,-40.147058823529406),
        new Position(17.594405594405593,-37.77149321266968),
        new Position(-9.748251748251748,-22.805429864253394),
        new Position(6.6573426573426575,-22.805429864253394),
        new Position(-22.825174825174827,-18.054298642533936),
        new Position(22.825174825174827,-20.429864253393667),
        new Position(-14.74125874125874,8.076923076923077),
        new Position(-0.4755244755244755,-7.364253393665159),
        new Position(8.55944055944056,9.264705882352942)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-16.643356643356643,-24.230769230769234),
        new Position(-0.7132867132867133,-33.02036199095023),
        new Position(12.125874125874127,-25.418552036199095),
        new Position(-19.496503496503497,18.529411764705884),
        new Position(8.083916083916083,18.054298642533936),
        new Position(-30.433566433566433,24.705882352941178),
        new Position(28.531468531468533,23.993212669683256),
        new Position(-5.230769230769231,44.18552036199095),
        new Position(-0.951048951048951,31.357466063348415),
        new Position(9.748251748251748,37.05882352941177)
    };
    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(11.16030534351145,-1.3059701492537314),
        new Position(-10.641221374045802,-0.7835820895522387),
        new Position(-27.251908396946565,31.6044776119403),
        new Position(-10.641221374045802,30.559701492537314),
        new Position(9.603053435114505,28.992537313432837),
        new Position(25.69465648854962,28.992537313432837)
    };

   Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.97902097902098,-29.93212669683258),
        new Position(-0.23776223776223776,-42.522624434389144),
        new Position(13.076923076923078,-28.50678733031674),
        new Position(-5.230769230769231,-16.628959276018097),
        new Position(5.230769230769231,-11.877828054298643),
        new Position(-24.251748251748253,-1.4253393665158371),
        new Position(22.349650349650346,-3.800904977375566),
        new Position(-9.748251748251748,-0.7126696832579186),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(4.755244755244756,-1.6628959276018098)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-13.79020979020979,-36.34615384615385),
        new Position(-0.951048951048951,-42.28506787330317),
        new Position(13.076923076923078,-36.34615384615385),
        new Position(-7.37062937062937,-25.656108597285066),
        new Position(2.377622377622378,-15.678733031674208),
        new Position(-20.447552447552447,-23.993212669683256),
        new Position(16.643356643356643,-23.518099547511312),
        new Position(-10.937062937062937,-1.900452488687783),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(11.412587412587413,-0.9502262443438915)
    };
    
    Position alineacion7[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-18.78321678321678,-39.671945701357465),
        new Position(-10.223776223776223,-42.76018099547511),
        new Position(0.0,-38.95927601809955),
        new Position(-9.034965034965035,-28.031674208144796),
        new Position(-0.951048951048951,-21.380090497737555),
        new Position(-27.34265734265734,-38.95927601809955),
        new Position(11.888111888111888,-31.83257918552036),
        new Position(-11.888111888111888,8.076923076923077),
        new Position(-12.363636363636363,-11.402714932126697),
        new Position(5.468531468531468,24.468325791855204)
    };

    Position alineacion8[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-17.832167832167833,-32.07013574660634),
        new Position(-4.755244755244756,-36.82126696832579),
        new Position(2.8531468531468533,-30.88235294117647),
        new Position(-12.125874125874127,-21.855203619909503),
        new Position(-0.951048951048951,-9.027149321266968),
        new Position(-27.81818181818182,-17.816742081447966),
        new Position(13.79020979020979,-9.739819004524888),
        new Position(-15.454545454545453,19.95475113122172),
        new Position(-16.405594405594407,-0.9502262443438915),
        new Position(-0.4755244755244755,33.73303167420815)
    };

    Position alineacion9[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-18.78321678321678,-29.694570135746606),
        new Position(-4.755244755244756,-36.82126696832579),
        new Position(4.9930069930069925,-26.606334841628957),
        new Position(-21.874125874125873,-5.463800904977376),
        new Position(-12.839160839160838,9.502262443438914),
        new Position(-27.34265734265734,8.076923076923077),
        new Position(10.223776223776223,13.778280542986426),
        new Position(-16.167832167832167,34.920814479638004),
        new Position(-8.083916083916083,23.993212669683256),
        new Position(-1.4265734265734267,37.53393665158371)
    };

    Position alineacion10[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-18.78321678321678,-29.694570135746606),
        new Position(-4.755244755244756,-36.82126696832579),
        new Position(4.9930069930069925,-26.606334841628957),
        new Position(-22.58741258741259,9.502262443438914),
        new Position(-3.5664335664335667,16.866515837104075),
        new Position(-29.48251748251748,35.15837104072398),
        new Position(10.461538461538462,27.556561085972852),
        new Position(-13.552447552447552,41.80995475113122),
        new Position(-16.88111888111888,27.08144796380091),
        new Position(2.8531468531468533,39.43438914027149)
    };

        Position alineacion11[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-5.230769230769231,-41.80995475113122),
        new Position(2.8531468531468533,-45.373303167420815),
        new Position(13.552447552447552,-47.98642533936651),
        new Position(6.181818181818182,-29.21945701357466),
        new Position(21.636363636363637,-25.656108597285066),
        new Position(-15.692307692307693,-25.418552036199095),
        new Position(28.293706293706293,-45.373303167420815),
        new Position(-11.174825174825173,27.31900452488688),
        new Position(-0.7132867132867133,-0.9502262443438915),
        new Position(14.027972027972028,10.927601809954751)
    };

    Position alineacion12[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-3.5664335664335667,-33.73303167420815),
        new Position(5.230769230769231,-40.147058823529406),
        new Position(17.832167832167833,-34.68325791855204),
        new Position(2.8531468531468533,-27.08144796380091),
        new Position(16.167832167832167,-14.728506787330318),
        new Position(-27.58041958041958,-16.628959276018097),
        new Position(27.58041958041958,-18.529411764705884),
        new Position(-14.503496503496503,29.93212669683258),
        new Position(0.7132867132867133,0.23755656108597287),
        new Position(9.034965034965035,19.004524886877828)
    };

    Position alineacion13[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-4.041958041958042,-31.357466063348415),
        new Position(3.5664335664335667,-38.009049773755656),
        new Position(18.545454545454543,-28.50678733031674),
        new Position(5.230769230769231,-8.552036199095022),
        new Position(19.25874125874126,5.226244343891403),
        new Position(-16.88111888111888,7.126696832579185),
        new Position(28.531468531468533,6.889140271493213),
        new Position(-3.090909090909091,35.63348416289593),
        new Position(2.6153846153846154,14.728506787330318),
        new Position(12.601398601398602,26.131221719457013)
    };

    Position alineacion14[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-6.419580419580419,-25.893665158371043),
        new Position(3.3286713286713288,-35.15837104072398),
        new Position(22.825174825174827,-27.08144796380091),
        new Position(4.27972027972028,12.352941176470589),
        new Position(19.734265734265733,17.34162895927602),
        new Position(-15.454545454545453,32.54524886877828),
        new Position(28.293706293706293,33.970588235294116),
        new Position(0.0,40.147058823529406),
        new Position(5.944055944055944,27.556561085972852),
        new Position(10.461538461538462,38.95927601809955)
    };
    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Ingenieros United";
        }

        @Override
        public String getCountry() {
            return "México";
        }

        @Override
        public String getCoach() {
            return "Hiram Garza";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 0, 0);
        }

        @Override
        public Color getShortsColor() {
            return new Color(0, 0, 153);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor() {
            return new Color(0, 0, 153);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 102, 255        );
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(102, 105, 93);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(70, 67, 161);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(10, 53, 2);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(108, 94, 232);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(198, 53, 57        );
        }

        @Override
        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_VERTICAL;
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
                new JugadorImpl("Oswaldo", 1, new Color(255,200,150), new Color(0,0,0),1.0d,0.7d,1.0d, true),
                new JugadorImpl("Temerario", 2, new Color(176,128,84), new Color(0,0,0),0.76d,0.76d,0.62d, false),
                new JugadorImpl("Noe", 3, new Color(227,131,44), new Color(0,0,0),0.25d,0.6d,0.77d, false),
                new JugadorImpl("Nestor", 4, new Color(249,219,191), new Color(0,0,0),0.75d,0.21d,0.58d, false),
                new JugadorImpl("Valles", 5, new Color(255,200,150), new Color(0,0,0),0.38d,0.5d,1.0d, false),
                new JugadorImpl("Luna", 20, new Color(255,200,150), new Color(0,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Piñas", 19, new Color(246,171,103), new Color(0,0,0),1.0d,0.75d,1.0d, false),
                new JugadorImpl("Markin", 7, new Color(255,200,150), new Color(0,0,0),1.0d,0.86d,1.0d, false),
                new JugadorImpl("Javier Hernández", 14, new Color(255,200,150), new Color(0,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Rocha", 10, new Color(226,148,77), new Color(0,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Gober", 11, new Color(255,200,150), new Color(0,0,0),1.0d,1.0d,1.0d, false)
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
    return alineacion5;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
    }

    private void recuperarBalon(GameSituations sp)
    {
        //obtener datos de recuperacion de balon
        int[]recuperadores = sp.getRecoveryBall();
        if(recuperadores.length>0)
        {
            double posicionRecuperacion[] = sp.getTrajectory(recuperadores[0]);
            //instruir al jugador mas cercano a ir por el balon
            for(int i=1;i<recuperadores.length & i<2;i++)
            {
                comandos.add(new CommandMoveTo(recuperadores[i],new Position(posicionRecuperacion[0],posicionRecuperacion[1])));
            }
        }
    }
    
    private void pelotazoAlDelantero(GameSituations sp,int pasador,int receptor)
    {
        double potencia = 0.9;
        Position[] misJugadores = sp.myPlayers();
        Position posicionReceptor = misJugadores[receptor];        
        comandos.add(new CommandHitBall(pasador,posicionReceptor,potencia,true));
    }
    private boolean porteroRivalAdelantado(GameSituations sp)
    {
        Position posicionPorteroRival = sp.rivalPlayers()[0];
        double distancia = posicionPorteroRival.distance(Constants.centroArcoSup);
        double parametro = 5d;
        if(distancia>parametro)
            return true;
        return false;
    }
    private void tirar(GameSituations sp)
    {
        Position posicionTiroAlPosteDerecho = new Position(Constants.posteDerArcoSup.getX()-2,Constants.posteDerArcoSup.getY());
        Position posicionTiroAlPosteIzquierdo = new Position(Constants.posteIzqArcoSup.getX()+2,Constants.posteIzqArcoSup.getY());;
        for(int i : sp.canKick())
        {
            if(obtenerCuadranteBalon(sp)==8)
                comandos.add(new CommandHitBall(i,posicionTiroAlPosteDerecho,1,12));
            else
                comandos.add(new CommandHitBall(i,posicionTiroAlPosteIzquierdo,1,12));
        }
    }
    private void paseAlPie(GameSituations sp,int pasador, int receptor)
    {
        Position[] misJugadores = sp.myPlayers();
        Position posicionReceptor = misJugadores[receptor];        
        double distancia = posicionReceptor.distance(misJugadores[pasador]);
        double potenciaPasador = sp.myPlayersDetail()[pasador].getPower();
        double potencia;
        boolean altitud;
        double factorPaseAlto = 1/100;
        double factorPaseBajo = 0.1;   
        double distanciaParaPaseElevado = 22;
        if(distancia > distanciaParaPaseElevado)//si hay una distancia mayor a 30 ejecutar un pase largo
        {
            potencia = 0.8 + ((distancia-5)/5) * factorPaseAlto;
            altitud = true;
        }
        else
        {
            potencia = 0.44 + (distancia-5)/5 * factorPaseBajo+(1-potenciaPasador);
            altitud = false;
        }
        
        comandos.add(new CommandHitBall(pasador,posicionReceptor,potencia,altitud));
        pasadorAnterior = pasador;
    }
    private void delanteroBuscaBalon(GameSituations sp,int delantero)
    {
        int[] iteracionesParaRematarJugadores = sp.iterationsToKick();
        int iteracionesParaRematarDelantero = iteracionesParaRematarJugadores[delantero];
        double[] trayectoriaBalon = sp.getTrajectory(sp.iteration()+iteracionesParaRematarDelantero);
        Position posicionBalon = new Position(trayectoriaBalon[0],trayectoriaBalon[1]);
        comandos.add(new CommandMoveTo(delantero,posicionBalon));
    }
    private boolean estaElBalonEnCampoContrario(GameSituations sp)
    {
        double posicionX = sp.ballPosition().getX();
        double posicionY = sp.ballPosition().getY();
        if(posicionY<0/2)
            return true;
        return false;
    }
    private void despejar(GameSituations sp)
    {
        double potencia = 0.999;
        Position[] misJugadores = sp.myPlayers();
        Position posicionReceptor;
        posicionReceptor = misJugadores[9];        
        comandos.add(new CommandHitBall(0,posicionReceptor,potencia,true));
        comandos.add(new CommandHitBall(1,posicionReceptor,potencia,true));
        comandos.add(new CommandHitBall(2,posicionReceptor,potencia,true));
        comandos.add(new CommandHitBall(3,posicionReceptor,potencia,true));
        comandos.add(new CommandMoveTo(0,sp.ballPosition()));
    }
    private void despejeDePuerta(GameSituations sp)
    {
        double potencia = 0.999;
        Position[] misJugadores = sp.myPlayers();
        Position posicionReceptor;
        comandos.add(new CommandMoveTo(0,sp.ballPosition()));
        comandos.add(new CommandHitBall(0,new Position(0,0),potencia,true));
    }
    private Position posicionPorteroRival(GameSituations sp)
    {
        return new Position(sp.rivalPlayers()[0]);
    }
    private int jugadorMasCercano(GameSituations sp,int jugador)
    {
        
        Position[]misJugadores = sp.myPlayers();
        Position[]rivales = sp.rivalPlayers();
        //obtener la distancia de mis compañeros
        double[]distancia = new double[misJugadores.length];
        for(int i=0;i<misJugadores.length;i++)
        {
            if(i!=jugador&&jugador!=pasadorAnterior&&!hayInterferencia(sp,jugador,i))
            {
                distancia[i] = misJugadores[jugador].distance(misJugadores[i]);
            }
            else
                distancia[i] = 2000+misJugadores[jugador].distance(misJugadores[i]);
        }
        int masCercano = 0;
        double aux = 200000;
        for(int i=0;i<misJugadores.length;i++)
        {
            if(distancia[i]<aux)
            {    
                masCercano = i;
                aux = distancia[i];
            }
        }
        return masCercano;
    }
    private boolean receptorEstaAdelante(GameSituations sp,int pasador,int receptor)
    {
        double posicionYpasador = sp.myPlayers()[pasador].getY();
        double posicionYreceptor = sp.myPlayers()[receptor].getY();
        if (posicionYreceptor >= posicionYpasador)
            return true;
        return false;
    }
    private int jugadorMasCercanoHaciaAdelante(GameSituations sp,int jugador)
    {
        
        Position[]misJugadores = sp.myPlayers();
        Position[]rivales = sp.rivalPlayers();
        //obtener la distancia de mis compañeros
        double[]distancia = new double[misJugadores.length];
        for(int i=0;i<misJugadores.length;i++)
        {
            if(i!=jugador&&i!=pasadorAnterior)
                distancia[i] = misJugadores[jugador].distance(misJugadores[i]);
            else
                distancia[i] = 2000;
        }
        int masCercano = 0;
        double aux = 200000;
        for(int i=0;i<misJugadores.length;i++)
        {
            if(distancia[i]<aux&&receptorEstaAdelante(sp,jugador,i))
            {    
                masCercano = i;
                aux = distancia[i];
            }
        }
        return masCercano;
    }
    private boolean hayInterferencia(GameSituations sp,int pasador,int receptor)
    {
        Position[]rivales = sp.rivalPlayers();
        Position posicionPasador = sp.myPlayers()[pasador];
        Position posicionReceptor = sp.myPlayers()[receptor];
        double cercania = 4.01;
        for(int i=0;i<rivales.length;i++)
        {
            if(rivales[i].getX()+cercania>Math.min(posicionPasador.getX(), posicionReceptor.getX())&&rivales[i].getX()+cercania<Math.max(posicionPasador.getX(), posicionReceptor.getX()))
                if(rivales[i].getY()+cercania>Math.min(posicionPasador.getY(), posicionReceptor.getY())&&rivales[i].getY()+cercania<Math.max(posicionPasador.getY(), posicionReceptor.getY()))
                    return true;
        }
        return false;
    }
    
    private boolean balonAvanzaAPorteriaRival(GameSituations sp)
    {
        if(sp.ballPosition().getY()>=posicionAnteriorDelBalon.getY())
            return true;
        return false;
    }
    private void conducir(GameSituations sp,int jugador)
    {
        double adelantarBalon = 0.55;
        comandos.add(new CommandMoveTo(jugador,Constants.centroArcoSup));
        comandos.add(new CommandHitBall(jugador,Constants.centroArcoSup,adelantarBalon,false));
    }   
    
    
    private void posicionar(GameSituations sp)
    {
        switch(obtenerCuadranteBalon(sp))
        {
            case 1:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion7[i]));
                break;
            case 2:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion8[i]));
                break;
            case 3:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion9[i]));
                break;
            case 4:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion10[i]));
                break;
            case 5:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion11[i]));
                break;    
            case 6:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion12[i]));
                break;
            case 7:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion13[i]));
                break;
            case 8:
                for (int i = 0; i < 11; i++) 
                    comandos.add(new CommandMoveTo(i, alineacion14[i]));
                break;
        }
    }
    private double distanciaAlArco(GameSituations sp)
    {
        return sp.ballPosition().distance(Constants.centroArcoSup);
    }
    private boolean esSaqueDeMeta (GameSituations sp)
    {
        double yAreaChica = -Constants.esqSupIzqAreaChicaInf.getY();
        if(sp.ballPosition().getY()==yAreaChica)
        {
            return true;
        }
        return false;
    }
    private boolean estoyMarcado(GameSituations sp)
    {
        double distanciaMarca = 10;
        Position rivales[] = sp.rivalPlayers();
        Position balon = sp.ballPosition();
        double distancia;
        for(int i=0;i<rivales.length;i++)
        {
            distancia = balon.distance(rivales[i]);
            if(distancia<distanciaMarca)
                return true;
        }
        return false;        
    }
    private boolean estaMarcado(GameSituations sp,int jugador)
    {
        double distanciaMarca = 10;
        Position rivales[] = sp.rivalPlayers();
        Position posicionJugador = sp.myPlayers()[jugador];
        double distancia;
        for(int i=0;i<rivales.length;i++)
        {
            distancia = posicionJugador.distance(rivales[i]);
            if(distancia<distanciaMarca)
                return true;
        }
        return false;        
    }
    private void estrategia1(GameSituations sp)
    {
        //tomar alinacion segun cuadrante de la posicion del balon
        posicionar(sp);
//        
//        
//        if(balonAvanzaAPorteriaRival(sp))
//            for (int i = 0; i < 11; i++) 
//                //Ordena a cada jugador que se ubique segun la alineacion3
//                comandos.add(new CommandMoveTo(i, alineacion3[i]));
//        else
//            for (int i = 0; i < 11; i++) 
//                //Ordena a cada jugador que se ubique segun la alineacion2
//                comandos.add(new CommandMoveTo(i, alineacion2[i]));
        if(sp.isRivalStarts())
            delanteroAtacando = false;
        
        Position[] misJugadores = sp.myPlayers();
        Position posicionDelantero = new Position(0,40);
        if(sp.iteration()==1)
        {
            //ALINEACION
            /*for(int i=0;i<11;i++)
                System.out.println(i+".- "+sp.myPlayersDetail()[i].getPlayerName());*/
            
        }
        if(sp.isStarts()&&esSaqueDeMeta(sp))
        {
            despejeDePuerta(sp);
        }
        
        else if(!sp.isRivalStarts())
        {
            recuperarBalon(sp);
            int[]tiradores = sp.canKick();
            for(int i=0;i<tiradores.length;i++)
            {
//                //con la posesion del balon, irse al frente
//                for (int j = 0; j < 11; j++) 
//                    //Ordena a cada jugador que se ubique segun la alineacion3
//                    comandos.add(new CommandMoveTo(j, alineacion3[j]));
//                
                //si la tiene el delantero, y el otro delantero esta desmarcado y mas cerca de la porteria q nosotros, pasarle el balon
                if(tiradores[i]==10&&!estaMarcado(sp,8)&&sp.myPlayers()[8].distance(Constants.centroArcoSup)>sp.myPlayers()[i].distance(Constants.centroArcoSup))
                {
                    paseAlPie(sp,tiradores[i],8);
                }
                else if(tiradores[i]==8&&!estaMarcado(sp,10)&&sp.myPlayers()[i].distance(Constants.centroArcoSup)>sp.myPlayers()[8].distance(Constants.centroArcoSup))
                {
                    paseAlPie(sp,tiradores[i],10);
                }
                //tirar al arco si el que tiene el balon es delantero y esta a la distancia de rematar o bien cualquier jugador si esta mas cerca
                else if((tiradores[i]==10||tiradores[i]==8)&&distanciaAlArco(sp)<=distanciaRemateAlArco||distanciaAlArco(sp)<=distanciaRemateAlArco-10)
                {    
                    //if(r.nextInt(2)==1)
                        tirar(sp);
                    //else
                        //conducir(sp,tiradores[i]);
                }
                else
                    if(tiradores[i]==0)
                        //despejar si el balon le cae al portero
                        despejar(sp);
                    else    
                    {
                        if(!estoyMarcado(sp))
                        {
                            conducir(sp,tiradores[i]);
                        }
                        else
                            paseAlPie(sp,tiradores[i],jugadorMasCercanoHaciaAdelante(sp,tiradores[i]));
                    }    
            }
        }
        //almacenar la posicion del balon para la proxima iteracion
        posicionAnteriorDelBalon = sp.ballPosition();        
        
    }
    private void estrategiaPasesAleatorios(GameSituations sp)
    {
        recuperarBalon(sp);
        int[]tiradores = sp.canKick();
            for(int i=0;i<tiradores.length;i++)
            {
                paseAlPie(sp,tiradores[i],r.nextInt(11));
            }
            
    }
    private int obtenerCuadranteBalon(GameSituations sp)
    {
        double x = sp.ballPosition().getX()+Constants.ANCHO_CAMPO_JUEGO/2;
        double y = sp.ballPosition().getY()+Constants.LARGO_CAMPO_JUEGO/2;
        double ancho = Constants.ANCHO_CAMPO_JUEGO;
        double largo = Constants.LARGO_CAMPO_JUEGO;
        int x1 = (int)(2*x/ancho);
        int y1 = (int)(4*y/largo);
        return x1*4+y1+1;
    }
    
    @Override
    public List<Command> execute(GameSituations sp) {
        comandos.clear();
        //estrategiaPasesAleatorios(sp);
        estrategia1(sp);
        return comandos;
        
    }
}