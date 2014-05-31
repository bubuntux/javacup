package org.dsaw.javacup.tactics.jvc2013.Rajaos;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author Zafiu
 */
public class Rajaos11 implements Tactic {
    RajaosDetalle detalle = new RajaosDetalle();
    LinkedList<Command> comandos = new LinkedList<Command>();
    int []marcaMio = new int[11];    

    Position[]buenos = new Position[11];
    Position []buenosAnt = new Position[11];
    Position []malos = new Position[11];
    Position []alinActual = new Position[11];
  //numero de iteraciones que se va a predecir el movimiento del ballPosition
    int iterMax = 50;
    
    int jugMinIter[] = new int[11];
    int posMinIter = 0;
    
    PlayerDetail[] detBuenos = new PlayerDetail[11];
    PlayerDetail[] detMalos = new PlayerDetail[11];
    int porteroMalo = -1;
    
    Position balon = null;
    Position balonAnt = null;
    Position balonFin = null;
    double alturaBalon =0;
    double alturaBalonAnt =0;
    GameSituations sitP = null;
    boolean posesion = true;
    boolean despuesSaquePuerta = false;
    //distancia m�xima de marcaje
    double distMaxMarcaje = Constants.LARGO_CAMPO/3;
    double distMaxDisparo = Constants.LARGO_CAMPO/3;
    int numMaxMarcajes = 6;    
    //numero de iteraciones a partir del cual se predice la posicion del ballPosition
    //porque el ballPosition empieza a ser controlable
    int inicioIter = 0;
    //zona del campo donde se esta jugando
    int zonaCampo = 0;
    int contIterSaque = 0;
    int numIterTrasSaque = 80;
    int iterTrasSaque = numIterTrasSaque;
    int iterRecup;
    Position posRecup;
    double alturaRecup;
    int jugRecup;
    

    Position alineacion1[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(7.608391608391608,-39.671945701357465),
            new Position(-1.6643356643356644,-39.671945701357465),
            new Position(-9.986013986013985,-40.859728506787334),
            new Position(-19.734265734265733,-43.710407239819006),
            new Position(4.9930069930069925,-22.330316742081447),
            new Position(-22.11188811188811,-30.407239819004527),
            new Position(-9.510489510489512,-23.993212669683256),
            new Position(-4.041958041958042,9.97737556561086),
            new Position(-17.11888111888112,-4.98868778280543),
            new Position(10.937062937062937,0.7126696832579186),
        };

        Position alineacion2[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(12.839160839160838,-41.0972850678733),
            new Position(3.804195804195804,-41.334841628959275),
            new Position(-4.9930069930069925,-41.57239819004525),
            new Position(-12.601398601398602,-43.23529411764706),
            new Position(9.748251748251748,-28.269230769230766),
            new Position(-9.986013986013985,-29.93212669683258),
            new Position(0.23776223776223776,-27.556561085972852),
            new Position(-1.188811188811189,-6.414027149321266),
            new Position(-19.734265734265733,-14.96606334841629),
            new Position(18.78321678321678,-15.203619909502263),
        };

        Position alineacion3[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(15.93006993006993,-43.47285067873303),
            new Position(8.321678321678322,-40.62217194570136),
            new Position(-1.902097902097902,-41.0972850678733),
            new Position(-10.461538461538462,-37.53393665158371),
            new Position(23.538461538461537,-31.59502262443439),
            new Position(-4.755244755244756,-23.042986425339365),
            new Position(11.65034965034965,-24.705882352941178),
            new Position(10.223776223776223,8.789592760180994),
            new Position(-8.55944055944056,0.0),
            new Position(19.020979020979023,-6.414027149321266),
        };

        Position alineacion4[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(13.79020979020979,-22.56787330316742),
            new Position(-0.4755244755244755,-27.794117647058822),
            new Position(-13.076923076923078,-32.782805429864254),
            new Position(-28.290076335877863,-28.470149253731343),
            new Position(-0.4755244755244755,-11.402714932126697),
            new Position(-15.692307692307693,-16.628959276018097),
            new Position(-8.797202797202797,-2.8506787330316743),
            new Position(-15.454545454545453,15.441176470588236),
            new Position(-27.104895104895103,0.23755656108597287),
            new Position(3.804195804195804,11.877828054298643),
        };

        Position alineacion5[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(18.545454545454543,-25.893665158371043),
            new Position(7.846153846153847,-31.59502262443439),
            new Position(-7.132867132867133,-32.07013574660634),
            new Position(-18.307692307692307,-27.08144796380091),
            new Position(6.419580419580419,-14.490950226244346),
            new Position(-8.55944055944056,-15.203619909502263),
            new Position(-2.13986013986014,-2.6131221719457014),
            new Position(1.902097902097902,14.015837104072398),
            new Position(-20.20979020979021,0.9502262443438915),
            new Position(19.734265734265733,2.6131221719457014),
        };

        Position alineacion6[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(26.867132867132867,-28.744343891402718),
            new Position(12.839160839160838,-29.694570135746606),
            new Position(0.0,-25.418552036199095),
            new Position(-13.076923076923078,-22.330316742081447),
            new Position(14.265734265734267,-16.628959276018097),
            new Position(-2.8531468531468533,-12.828054298642533),
            new Position(7.846153846153847,-1.6628959276018098),
            new Position(9.034965034965035,13.540723981900454),
            new Position(-12.601398601398602,5.9389140271493215),
            new Position(27.104895104895103,-4.751131221719457),
        };

        Position alineacion7[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(13.79020979020979,-4.513574660633484),
            new Position(-0.4755244755244755,-10.690045248868778),
            new Position(-15.216783216783217,-14.25339366515837),
            new Position(-27.34265734265734,-9.502262443438914),
            new Position(1.188811188811189,13.778280542986426),
            new Position(-24.013986013986013,9.027149321266968),
            new Position(-11.888111888111888,18.29185520361991),
            new Position(-9.510489510489512,31.59502262443439),
            new Position(-27.34265734265734,23.755656108597286),
            new Position(7.608391608391608,28.269230769230766),
        };

        Position alineacion8[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(20.447552447552447,-6.651583710407239),
            new Position(8.321678321678322,-14.25339366515837),
            new Position(-6.6573426573426575,-13.778280542986426),
            new Position(-20.923076923076923,-6.176470588235294),
            new Position(12.125874125874127,16.866515837104075),
            new Position(-12.839160839160838,15.441176470588236),
            new Position(-0.951048951048951,9.502262443438914),
            new Position(-2.6153846153846154,31.59502262443439),
            new Position(-20.923076923076923,31.119909502262445),
            new Position(17.356643356643357,31.83257918552036),
        };

        Position alineacion9[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(27.34265734265734,-10.214932126696834),
            new Position(14.74125874125874,-15.91628959276018),
            new Position(0.951048951048951,-13.303167420814479),
            new Position(-12.839160839160838,-7.601809954751132),
            new Position(24.251748251748253,11.402714932126697),
            new Position(-4.755244755244756,9.027149321266968),
            new Position(10.223776223776223,13.540723981900454),
            new Position(6.419580419580419,32.54524886877828),
            new Position(-9.748251748251748,23.042986425339365),
            new Position(25.44055944055944,26.368778280542987),
        };

        Position alineacion10[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(19.25874125874126,14.015837104072398),
            new Position(0.4755244755244755,12.59049773755656),
            new Position(-5.944055944055944,0.7126696832579186),
            new Position(-26.867132867132867,-0.23755656108597287),
            new Position(4.041958041958042,24.230769230769234),
            new Position(-20.20979020979021,13.065610859728507),
            new Position(-14.503496503496503,27.556561085972852),
            new Position(-7.846153846153847,40.147058823529406),
            new Position(-26.867132867132867,40.38461538461539),
            new Position(6.419580419580419,41.80995475113122),
        };

        Position alineacion11[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(20.923076923076923,7.839366515837104),
            new Position(7.846153846153847,0.7126696832579186),
            new Position(-9.986013986013985,-0.23755656108597287),
            new Position(-21.874125874125873,8.552036199095022),
            new Position(6.895104895104895,21.380090497737555),
            new Position(-9.986013986013985,22.092760180995477),
            new Position(4.041958041958042,36.10859728506787),
            new Position(-2.13986013986014,42.28506787330317),
            new Position(-14.97902097902098,42.522624434389144),
            new Position(13.076923076923078,44.18552036199095),
        };

        Position alineacion12[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(24.727272727272727,4.751131221719457),
            new Position(8.083916083916083,1.900452488687783),
            new Position(-5.468531468531468,7.601809954751132),
            new Position(-18.307692307692307,15.678733031674208),
            new Position(19.496503496503497,18.766968325791854),
            new Position(-0.4755244755244755,25.656108597285066),
            new Position(12.363636363636363,34.20814479638009),
            new Position(2.8531468531468533,38.95927601809955),
            new Position(-11.65034965034965,40.859728506787334),
            new Position(27.104895104895103,40.62217194570136),
        };

        Position alineacion13[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(23.062937062937063,-24.943438914027148),
            new Position(11.174825174825173,-32.07013574660634),
            new Position(-9.272727272727272,-31.83257918552036),
            new Position(-24.48951048951049,-26.84389140271493),
            new Position(14.97902097902098,-13.540723981900454),
            new Position(-16.405594405594407,-14.015837104072398),
            new Position(-0.951048951048951,-9.97737556561086),
            new Position(-0.23776223776223776,0.0),
            new Position(-26.391608391608393,-1.4253393665158371),
            new Position(22.580152671755727,-1.3059701492537314),
        };

        Position alineacion14[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(23.062937062937063,-27.794117647058822),
            new Position(12.717557251908397,-35.26119402985075),
            new Position(-8.797202797202797,-34.68325791855204),
            new Position(-22.825174825174827,-27.31900452488688),
            new Position(10.461538461538462,-19.004524886877828),
            new Position(-12.601398601398602,-19.479638009049776),
            new Position(5.944055944055944,-8.314479638009049),
            new Position(-7.608391608391608,-7.126696832579185),
            new Position(-22.349650349650346,-0.7126696832579186),
            new Position(18.78321678321678,-1.1877828054298643),
        };               

    public Rajaos11()
    {
    	
    }

    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public List<Command> execute(GameSituations sp)
    {
        int diferencia_goles=0;
       
        comandos.clear();
        for (int i=0;i<11;i++)
        	marcaMio[i]=-1;
        marcaMio[0]=0;
        buenosAnt = buenos;
        buenos = sp.myPlayers();
        malos = sp.rivalPlayers();
        
        detBuenos = sp.myPlayersDetail();
        detMalos = sp.rivalPlayersDetail();
        
        if (porteroMalo <0) porteroMalo = buscaPorteroMalo();
        
        sitP = sp;
        balonAnt = balon;
        balon = sp.ballPosition();
        alturaBalonAnt = alturaBalon;
        alturaBalon = sp.ballAltitude();
                
        //balonFin = predecirMovimientoBalon(ballPosition, balonAnt, ballAltitude, alturaBalonAnt);
        
        int[] arrayRecup =  null;        
        arrayRecup = sp.getRecoveryBall();
        if (arrayRecup != null && arrayRecup.length > 0)
        {       
        	iterRecup = arrayRecup[0];
        	double[] balonRecup = sp.getTrajectory(iterRecup);
        	jugRecup = arrayRecup[1];
        	if (balonRecup != null && balonRecup.length > 0)
        	{        		
        		posRecup = new Position(balonRecup[0],balonRecup[1]);
        		alturaRecup = balonRecup[2];        		
        	}
        	else
        	{
        		posRecup = balon;
        		alturaRecup = sp.ballAltitude();
        	}
        }       
        else
        {
        	posRecup = balon;
        	alturaRecup = sp.ballAltitude();
        }
        
        balonFin = posRecup;
        
        if (despuesSaquePuerta == true && balon != balonAnt)
        {
        	iterTrasSaque --;
        	if (iterTrasSaque == 0)
        	{
        		despuesSaquePuerta = false;
        		iterTrasSaque = numIterTrasSaque;
        	}
        }
        
        if (esSaquePuerta() == true)
           	contIterSaque++;           	        
        else
        	contIterSaque = 0;
        
		diferencia_goles = sp.myGoals() - sp.rivalGoals();
				
    	// miramos quien va a tener la posesion
    	posesion = predecirPosesion();
		setAlineacion();
		//Debug.println(sp.ballPosition().toString());
		moverJugadoresSinBalon();			
		//buscamos delanteros contrarios para realizar marcaje
		marcajeDelanteros();		
		
		moverJugadoresConBalon();

        return comandos;
    }
    
    private int iteracionesPosicion(Position pDesde, Position pHasta, double velocidad)
    {    	
    	double distancia = pDesde.distance(pHasta);
    	return (int)(distancia/velocidad);
    }       
    private int iteracionesBalon(Position p, double velocidad)
    {    
    	double distancia=0;
    	int iter = 0;
    	int minIter = 100;
    	
    	for (int i=0; i<50;i++)
    	{
    		double[] trayectoria = sitP.getTrajectory(i);
    		Position posBalon = new Position(trayectoria[0],trayectoria[1]);
    		if (posBalon != null)
    		{
    			distancia = p.distance(posBalon);
    			iter = (int)(distancia/velocidad);
    			if (iter < minIter)
    			{
    				minIter = iter;
    				posMinIter = i;
    			}
    		}
    	}
    	return minIter;
    }
    private boolean cercaBuenoBalon(int bueno, int malo)
    {
    	int iterBueno = iteracionesBalon(buenos[bueno],detBuenos[bueno].getSpeed());
    	int iterMalo = iteracionesBalon(malos[malo],detMalos[malo].getSpeed());
    	if (iterBueno < iterMalo)
    		return true;
    	else
    		return false;
    	
    }
    private void moverJugadoresSinBalon()
    {
    	// movemos jugadores segun zona del campo
		for(int i=1;i<11;i++)
		{			
			comandos.add(new CommandMoveTo(i, alinActual[i]));
		}    	
    }
    
    private void moverJugadoresConBalon()
    {
    	if (esSaquePuerta() == true)
    	{
    		if (contIterSaque < Constants.ITERACIONES_SAQUE/2)
    		{
    			comandos.add(new CommandMoveTo(0, balonFin));
    			return;
    		}
    		else
    		{
    			chutarPuerta(0);
    			//buscaMejorAccion(0);
    		}
    	}
    	colocarPortero();
    	        		        	        			
		comandos.add(new CommandMoveTo(jugRecup, posRecup));
		if (sitP.canKick().length >0)
			buscaMejorAccion(jugRecup);        	
    	
    }
    
    private void moverJugadoresConBalon2()
    {
    	//movemos uno o varios jugadores tras el bal�n
    	if (esSaquePuerta() == true)
    	{
    		if (contIterSaque < Constants.ITERACIONES_SAQUE/2)
    		{
    			comandos.add(new CommandMoveTo(0, balonFin));
    			return;
    		}
    		else
    		{
    			chutarPuerta(0);
    			//buscaMejorAccion(0);
    		}
    	}
		int [] cercanos = null;
		if (posesion == true)
			cercanos = getJugadoresCercaBalon(1);
			//cercanos = getJugadoresCercaPosicion(1, balonFin);
		else
			cercanos = getJugadoresCercaBalon(1);
			//cercanos = getJugadoresCercaPosicion(2, balonFin);

		colocarPortero();
		
		for(int i=0;i<cercanos.length;i++)
		{
			//movemos todos menos el portero que ya lo hemos colocado antes
			if (cercanos[i]!=0) 
			{				
				//comandos.add(new ComandoIrA(cercanos[i], posBalon[jugMinIter[cercanos[i]]]));
				double[] trayectoria = sitP.getTrajectory(jugMinIter[cercanos[i]]);
				Position irA = new Position(trayectoria[0],trayectoria[1]);
				comandos.add(new CommandMoveTo(cercanos[i], irA));
			}
			if (sitP.canKick().length >0) buscaMejorAccion(cercanos[i]);
		}
    	
    }    
    
    private boolean enAreaChicaBuenos(Position p)
    {    	
    	boolean by = (p.getY() > -Constants.LARGO_CAMPO_JUEGO/2 && p.getY() < Constants.LARGO_AREA_CHICA - Constants.LARGO_CAMPO_JUEGO/2);
    	boolean bx = (p.getX() > 0 - Constants.ANCHO_AREA_CHICA/2 && p.getY() < Constants.ANCHO_AREA_CHICA/2);
    	return (bx && by);
    }
    
    private void colocarPortero()
    {    	
    	if (balonAnt == null) return;
    	if (zonaCampo > 3)
    	{
			comandos.add(new CommandMoveTo(0, Constants.centroArcoInf));
			return;    	
		}
    	//colocamos portero en situacion relativa al ballPosition
		double rel = Constants.ANCHO_CAMPO/(Constants.posteDerArcoInf.getX() - Constants.posteIzqArcoInf.getX());
		comandos.add(new CommandMoveTo(0, new  Position(balon.getX()/rel,Constants.posteDerArcoInf.getY())));
    	//Miramos si el portero sale a por el ballPosition
    	if (cercaBuenoBalon(0, balon.nearestIndex(malos)) == true && balonFin.getY() >= Constants.centroArcoInf.getY())
    	{
    		comandos.add(new CommandMoveTo(0, balonFin));
    	}
    	else
    	{
    		Position p = Position.Intersection(balonAnt, balon, Constants.posteIzqArcoInf, Constants.posteDerArcoInf);
    		if (p == null)
    		{
    			if (enAreaChicaBuenos(balonFin) == true)
    			{
    				comandos.add(new CommandMoveTo(0, balonFin));
    				return;
    			}
    			else
    			{
    				//colocamos portero en situacion relativa al ballPosition
    				rel = Constants.ANCHO_CAMPO/(Constants.posteIzqArcoInf.getX() - Constants.posteDerArcoInf.getX());
    				comandos.add(new CommandMoveTo(0, new  Position(Constants.posteDerArcoInf.getX() + balon.getX()/rel,Constants.posteDerArcoInf.getY())));
    				return;
    			}
    		}
    		if (p.getX() > Constants.posteIzqArcoInf.getX() && p.getX() < Constants.posteDerArcoInf.getX())
    		{
    			comandos.add(new CommandMoveTo(0, p));
    		}
    	}    	
    }
    
    
    //retorna true si la posesion va a ser de mis jugadores    
    private boolean predecirPosesion()
    {    	
    	int[] buenosR = sitP.canKick();
    	int[] malosR = sitP.rivalCanKick();
    	//if (malosR.length > buenosR.length) return false;
    	//if (malosR.length < buenosR.length) return true;
    	if (malosR.length > 0 && buenosR.length == 0) return false;
    	if (malosR.length == 0 && buenosR.length > 0) return true;
    	if (malosR.length > 0 && buenosR.length > 0)
    	{
    		//buscamos cual est� m�s cerca para decidir
    		int cercaBueno = balon.nearestIndex(buenos);
    		int cercaMalo = balon.nearestIndex(malos);
    		return cercaBuenoBalon(cercaBueno, cercaMalo);    		    			    		
    	}
    	if (malosR.length == 0 && buenosR.length == 0)
    	{
    		int[] iterMalosBalon = iteracionesMalosPosicion(posRecup);
    		for (int i=0;i<iterMalosBalon.length;i++)
    		{
    			if (iterMalosBalon[i] < iterRecup)
    			{
    				if (iteracionesPosicion(buenos[jugRecup], posRecup, detBuenos[jugRecup].getSpeed()) > iterMalosBalon[i])
    					//return true;
    					return false;
    				else
    					return true;
    			}
    		}    		
    	}
    	return true;
    }
    
    private int[] iteracionesMalosPosicion(Position pos)
    {
    	int[] temp = new int[11];
    	for (int i=0;i<11;i++)
    	{
    		temp[i] = iteracionesPosicion(malos[i], pos, detMalos[i].getSpeed());
    	}   
    	return temp;
    }
    
    
    private boolean esSaquePuerta()
    {
    	if (sitP.isStarts() == true &&
    		balon.getY() <= Constants.LARGO_CAMPO_JUEGO/2*(-1) + Constants.ANCHO_AREA_CHICA &&
    		balon.getX() <= 9.16 &&
    		balon.getX() >= -9.16)
    	{
    		despuesSaquePuerta = true;
    		return true;
    	}
    	else
    		return false;
    }
    
    private int buscaPorteroMalo()
    {    
    	for (int i=0;i<malos.length;i++)
    	{
    		if (detMalos[i].isGoalKeeper() == true)
    			return i;
    	}
    	return 0;
    }
    
    private void chutarPuerta(int jug)
    {
    	double fuerza=0;
    	double altura=0;
    	double distPuerta = Constants.centroArcoSup.distance(balon);
    	double distPI = Constants.posteIzqArcoSup.distance(balon);
    	double distPD = Constants.posteDerArcoSup.distance(balon);
    	double distPorteroI = Constants.posteIzqArcoSup.distance(malos[porteroMalo]);
    	double distPorteroD = Constants.posteDerArcoSup.distance(malos[porteroMalo]);
    	Position direccion = Constants.centroArcoSup;
    	    	
    	//chutamos a puerta    		
		fuerza = 1; //(0.3/(Constants.LARGO_CAMPO/4)*distPuerta) + 0.65;
		altura = (10/(Constants.LARGO_CAMPO/5) * distPuerta) + 6;
		if (distPI > distPD)
		{
			if (distPI/distPD > 1.5)
				direccion = new Position(Constants.posteIzqArcoSup.getX()+distPorteroI/2,Constants.centroArcoSup.getY());
			if (distPD/distPI > 1.5)
				direccion = new Position(Constants.posteIzqArcoSup.getX()+distPorteroI/2,Constants.centroArcoSup.getY());
		}
		if (distPorteroI > distPorteroD)
			direccion = new Position(Constants.posteIzqArcoSup.getX()+distPorteroI/2,Constants.centroArcoSup.getY());
		else
			direccion = new Position(Constants.posteDerArcoSup.getX()-distPorteroD/2,Constants.centroArcoSup.getY());
		if (distPuerta < Constants.LARGO_CAMPO/5)
		{
			altura = Constants.ANGULO_VERTICAL_MAX*1/6;
			fuerza = 1;			
		}
		comandos.add(new CommandHitBall(jug, direccion,fuerza,altura));
    }
    
    private boolean porteroMaloEnMedio(Position p1, double angDesv)
    {    	
    	Position p2 = Constants.centroArcoSup;
    	double distP2 = p1.distance(p2);
    	double angP2 = p1.angle(p2) * (180d/Math.PI);
    	if (angP2 < 0) angP2 = 360 + angP2;
    	int cercaM[] = p2.nearestIndexes(malos);
    	for (int i=0; i < cercaM.length;i++)
    	{
    		if (cercaM[i] == 0)
    			continue;
    		double angM = malos[cercaM[i]].angle(p2)  * (180d/Math.PI);
    		if (angM < 0) angM = 360 + angM;
    		double distM = p2.distance(malos[cercaM[i]]);
    		if (distM > distP2)
    			return false;
    		if (angP2 - angDesv/2 < angM && angM < angP2 + angDesv/2)
    			return true;
    	}
    	return false;
    
    }
    
    // comprueba si algun jugador contrario est� entre ambas posiciones
    // con un angulo de desviacion m�ximo
    private boolean malosEnMedio(Position p1, Position p2, double angDesv)
    {
    	double distP2 = p1.distance(p2);
    	double angP2 = p1.angle(p2) * (180d/Math.PI);
    	if (angP2 < 0) angP2 = 360 + angP2;
    	int cercaM[] = p1.nearestIndexes(malos);
    	for (int i=0; i < cercaM.length;i++)
    	{
    		if (cercaM[i] == 0)
    			continue;
    		double angM = p1.angle(malos[cercaM[i]])  * (180d/Math.PI);
    		if (angM < 0) angM = 360 + angM;
    		double distM = p2.distance(malos[cercaM[i]]);
    		if (distM > distP2)
    			continue;
    		if (angP2 - angDesv/2 < angM && angM < angP2 + angDesv/2)
    			return true;
    	}
    	return false;
    	
    }
    
    private boolean malosEnMedio(Position p1, Position p2, double angDesv, int distMin)
    {    	
    	//return malosEnMedio(p1,p2,angDesv);
    	double distP2 = p1.distance(p2);
    	double angP2 = p1.angle(p2) * (180d/Math.PI);
    	if (angP2 < 0) angP2 = 360 + angP2;
    	int cercaM[] = p1.nearestIndexes(malos);
    	for (int i=0; i < cercaM.length;i++)
    	{
    		if (cercaM[i] == 0)
    			continue;
    		double angM = p1.angle(malos[cercaM[i]])  * (180d/Math.PI);
    		if (angM < 0) angM = 360 + angM;
    		double distM = p2.distance(malos[cercaM[i]]);
    		if (distM > distP2)
    			continue;
    		if (angP2 - angDesv/2 < angM && angM < angP2 + angDesv/2)
    		{
    			if (distMin/2 <= distM)
    				return true;    			
    		}
    		if (angP2 - angDesv/4 < angM && angM < angP2 + angDesv/4)
    		{
    			return true;    		
    		}
    	}
    	return false;
    	
    }
    
    private void pararBalon(int jug)
    {
    	comandos.add(new CommandHitBall(jug, Constants.centroArcoSup ,0.1,0));
    }
    
    private void realizarPase(int jug1, int jug2)
    {
    	double fuerza=0;
    	double altura=0;
    	double distPase = 0;
    	Position pasePos;
    	//buscamos en que direccion esta el jugador a pasar
    	//double angJ = ballPosition.angulo(buenos[jug2]) * (180d/Math.PI);
    	//double distJ = ballPosition.distancia(buenos[jug2]);
    	//buscamos en que direccion y a que distancia esta el jugador marcador
		int marcador = buenos[jug2].nearestIndex(malos);
		double angM = buenos[jug2].angle(malos[marcador]);// * (180d/Math.PI);
		double distM = malos[marcador].distance(buenos[jug2]);
		distPase = balon.distance(buenos[jug2]);
		double distPuerta = Constants.centroArcoSup.distance(buenos[jug2]);
		if (malosEnMedio(buenos[jug2], Constants.centroArcoSup, 40,20) == false && distPuerta < distMaxDisparo/1.5)
		{			
			//pasamos en direccion a porteria
			pasePos = buenos[jug2].moveAngle(buenos[jug2].angle(Constants.centroArcoSup),distPase/5 + 1);
		}
		else if (distM < 5) 
		{
			//pasamos alejado del marcaje
			pasePos = buenos[jug2].moveAngle(angM, distM);
			//pasePos= buenos[jug2];
		}
		else  
		{
			//pasamos en direccion a jugador que pasa
			
			pasePos = buenos[jug2].moveAngle(buenos[jug2].angle(balon),1);
			//pasePos= buenos[jug2];
		}
		//pasePos= buenos[jug2];
		//pasePos = buenos[jug2].moverAngulo(angM, 9-distM);
		//distPase = ballPosition.distancia(buenos[jug2]);
		distPase = balon.distance(pasePos);
		//if (angJ - 10 < angM && angM < angJ +10 && distM < distJ)
		double angDesv = 20;
		if (jug1 == 0)
			angDesv = 50;
		if (malosEnMedio(buenos[jug1], pasePos, angDesv,20) == true || malosEnMedio(pasePos,balon, angDesv,20) == true  || distM <= Constants.DISTANCIA_CONTROL_BALON)
		{
			//hay jugadores contrarios entre el ballPosition y el receptor del pase
			//balones elevados
			if (distPase <7)
			{    		
				fuerza = 0.4;
				altura = 60;
			}
			else if (distPase >=7 && distPase < 16)
			{
				fuerza = 0.55;
				altura = 40;			
			}
			else if (distPase >=16 && distPase < 25)
			{
				fuerza = 0.7;
				altura = 30;
			}
			else if (distPase >=25 && distPase < 35)
			{
				fuerza = 0.85;
				altura = 30;
			}
			else if (distPase >=35)
			{
				fuerza = 1;
				altura = 25;
			}
		}
		else
		{
			//pase raso posible
			if (distPase <7)
			{    		
				fuerza = 0.4;
				altura = 0;
			}
			else if (distPase >=7 && distPase < 16)
			{
				fuerza = 0.55;
				altura = 0;			
			}
			else if (distPase >=16 && distPase < 25)
			{
				fuerza = 0.80;
				altura = 0;
			}
			else if (distPase >=25)
			{
				fuerza = 1;
				altura = 0;
			}
		}		
    	 	     		 
    	//comandos.add(new CommandHitBall(jug1, buenos[jug2],fuerza,altura));
    	comandos.add(new CommandHitBall(jug1, pasePos,fuerza,altura));
    	comandos.add(new CommandMoveTo(jug2, pasePos));
    	comandos.add(new CommandMoveTo(jug1, alinActual[jug1]));
    }
    
    int[] jugadoresDelante(int jug,int dist)
    {
    	int[] temp1 = new int[11];
    	int n = 0;
    	for (int i=0;i<11;i++)
    	{
    		if (buenos[i].getY() > buenos[jug].getY() + dist)
    		{
    			temp1[n] = i;
    			n++;    			
    		}    		
    	}
    	
    	return Arrays.copyOf(temp1, n);
    }
    
    int[] jugadoresDetras(int jug,int dist)
    {
    	int[] temp1 = new int[11];
    	int n = 0;
    	for (int i=0;i<11;i++)
    	{
    		if (buenos[i].getY() < buenos[jug].getY() - dist)
    		{
    			temp1[n] = i;
    			n++;    			
    		}    		
    	}
    	
    	return Arrays.copyOf(temp1, n);
    }
    
    int[] jugadoresParalelo(int jug,int dist)
    {
    	int[] temp1 = new int[11];
    	int n = 0;
    	for (int i=0;i<11;i++)
    	{
    		if (buenos[i].getY() > buenos[jug].getY() - dist && buenos[i].getY() < buenos[jug].getY() + dist )
    		{
    			temp1[n] = i;
    			n++;    			
    		}    		
    	}
    	
    	return Arrays.copyOf(temp1, n);
    }
    
    private int valorarAvance(int jug)
    {    	
    	int[][] malosDelante = malosDelanteMasCercanos(jug, 5);
    	if (malosDelante != null)    		
    		return malosDelante.length;
    	else
    		return 0;
    }
    private void buscaMejorAccion(int jug)
	{
		//avanzarConBalon2(jugRecup, 11, 11);
		double fuerza=0;
		double altura=0;
		double distPuerta = Constants.centroArcoSup.distance(buenos[jug]);
		
		double[] valor = new double[3];
		int[] mejorPase = null;
		if (jug == 0 || esSaquePuerta())
		{
			//es el portero o saque de puerta esperamos que suba la defensa
			if (jug ==0 && esSaquePuerta() && contIterSaque < Constants.ITERACIONES_SAQUE/2) return;
			mejorPase = buscaMejorPase(jug,valor);
			if (valor[0] >= 1)
				realizarPase(jug, mejorPase[0]);
			else if (valor[1] >= 1)
				realizarPase(jug, mejorPase[1]);
			else if (valor[2] >= 1)
				realizarPase(jug, mejorPase[2]);
			else
				realizarPase(jug, mejorPase[0]);
			return;
		}        	
		//buscamos mejor pase
		int valorAvance = valorarAvance(jug);
		if (valorAvance == 0 && sitP.isStarts() == false)
		{
			if (avanzarConBalon(jug,11,1) == true)
				return;
		}
		mejorPase = buscaMejorPase(jug,valor);
		if (valor[0] < 0.5 && valor[1] < 0.5 && valor[2] < 0.5)
		{
			if (avanzarConBalon(jug,11,0) == true)
				return;
		}    	
		if (distPuerta < distMaxDisparo/2)
		{       		
			if (valor[0] < 3 && valor[1] < 10 && valor[2] < 20)
			{
				chutarPuerta(jug);
			}
			else
			{
				if (valor[0] >= 3)
					realizarPase(jug, mejorPase[0]);
				else if (valor[1] >= 10 && porteroMaloEnMedio(buenos[mejorPase[1]],10))
					realizarPase(jug, mejorPase[1]);
				//else if (valor[2] >= 12)
				//	realizarPase(jug, mejorPase[2]);
				else
					chutarPuerta(jug);
			}    		    	
		}    	
		else if (distPuerta < distMaxDisparo)
		{       		
			if (valor[0] < 1 && valor[1] < 3 && valor[2] < 15)
			{
				chutarPuerta(jug);
			}
			else
			{
				if (valor[0] >= 1)
					realizarPase(jug, mejorPase[0]);
				else if (valor[1] >= 3)
					realizarPase(jug, mejorPase[1]);
				//else if (valor[2] >= 3)
				//	realizarPase(jug, mejorPase[2]);
				//else if (avanzarConBalon(jug, 11, 2)==true)
				//	return;
				else
					chutarPuerta(jug);
			}
		}
		else
		{    		
			if (valor[0] >= 1)
				realizarPase(jug, mejorPase[0]);
			else if (valor[1] >= 2.5)
				realizarPase(jug, mejorPase[1]);
			else if (valor[2] >= 20)
				realizarPase(jug, mejorPase[2]);
			else if (avanzarConBalon(jug, 11, 0)==true)
				return;	
			else
			{
				int[] iterMalos = iteracionesMalosPosicion(balon);
				Arrays.sort(iterMalos);
				if (iterMalos[0] < 10)
				{				
					if (mejorPase[0] > 0.75)
						realizarPase(jug, mejorPase[0]);
					else if (mejorPase[1] > 1)
						realizarPase(jug, mejorPase[1]);
					else if (mejorPase[2] > 1)
						realizarPase(jug, mejorPase[2]);
					else
						realizarPase(jug, mejorPase[0]);
				}
				else
				{
					pararBalon(jug);
				}
			}
		}    	
		
	}

	//devuelve el indice del jugador cuyo pase est� mejor valorado
    //a igualdad de valoracion siempre el m�s cercano a la porteria           
    private int[] buscaMejorPase(int jug,double[] valor)
    {
    	//int[] cercanos = ballPosition.indicesMasCercanos(buenos);
    	int[] jugV = new int[3];    	
    	int[] cercanos = null;
    	int ind = -1;
    	valor[0] = 0;
    	int distN = 10;
    	if (jug == 0) distN = 20;
    	double distPuerta = Constants.centroArcoSup.distance(buenos[jug]);
    	if (distPuerta < distMaxDisparo)
    	{    		
    		distN = 6;
    	}
    	if (distPuerta < distMaxDisparo/2)
    	{    		
    		distN = 3;
    	}
    	for (int g=0;g<3;g++)
    	{
	    	cercanos = null;
	    	if (g == 0)	    	
	    		cercanos = jugadoresDelante(jug,distN);	    	    	
	    	if (g == 1)
	    		cercanos = jugadoresParalelo(jug,distN);	    		    	
	    	if (g == 2)
	        	cercanos = jugadoresDetras(jug,distN);    		
	    	
	    	double[] valoracion = new double[11];
	    	if (cercanos == null || cercanos.length == 0)
	    	{
	    		valor[g] = -1;
	    		jugV[g] = -1;
	    		continue;
	    	}
	    	for( int i=0;i<cercanos.length;i++)
	    	{
	    		if (i!= jug)
	    		{
	    			//if (jug == 0)    				
	    			//	valoracion[i] = valorarDespeje(cercanos[i]);
	    			//else
	    				valoracion[i] = valorarPase(cercanos[i]);
	    		}
	    		/*else
	    		{
	    			valoracion[i][0] = -99;
	    			valoracion[i][1] = -99;
	    		}*/
	    		
	    	}
	    	double maxA = -99;
	    	double maxB = -99;
	    	ind = -1;
	    	double dist = 100;	    	
	    	
	    	for (int i=0;i<cercanos.length;i++)
	    	{
	    		if (valoracion[i] == maxA)
	    		{
	    			if (valoracion[i] > maxB)
	    			{
	    				maxB = valoracion[i];
	    				ind = i;    				
	    			}
	    			else if (valoracion[i] == maxB)
	    			{    				
	    				double dist2 = buenos[cercanos[i]].distance(Constants.centroArcoSup);
	    				if (dist > dist2)
	    				{
	    					ind = i;
	    					dist = dist2;
	    				}
	    			}
	    		}
	    		else if (valoracion[i] > maxA)
	    		{
	    			maxA = valoracion[i];
	    			ind = i;
	    			dist = buenos[cercanos[i]].distance(Constants.centroArcoSup);
	    		}
	    	}
	    	
	    	if (ind <0)
	    		ind = 10;
	    	valor[g] = maxA;
	    	jugV[g] = cercanos[ind];	    	
    	}    	
    	return jugV;    	
    }
    
    double valorarPase(int jug)
    {
    	int malo = buenos[jug].nearestIndex(malos);
    	double distMalo = iteracionesPosicion(buenos[jug], malos[malo], detMalos[malo].getSpeed());
    	double distPase = 0;
    	double distPuerta = Constants.centroArcoSup.distance(buenos[jug]);
    	double distPuertaBalon = Constants.centroArcoSup.distance(balon);
    	if (malosEnMedio(balon, buenos[jug], 25) == true )//|| distMalo <= Constants.DISTANCIA_CONTROL_BALON)
    		distPase = iteracionesPosicion(balon, buenos[jug], Constants.DELTA_REMATE_VELOCIDAD*1.2);
    	else
    		distPase = iteracionesPosicion(balon, buenos[jug], Constants.DELTA_REMATE_VELOCIDAD*2)/2;
    	
    	double valor = -1;
    	if (jug == 0)
    		distPase = distPase/2;
    	if (distPase == 0)
    		valor = 0;
    	else
    		valor = distMalo/distPase; 
    	if (distPuertaBalon > distPuerta)
    		valor = valor + 0.5;
    	if (malosEnMedio(buenos[jug],Constants.centroArcoSup, distMaxDisparo/4) == false || distMalo <= Constants.DISTANCIA_CONTROL_BALON)
    	{
    		if (distPuerta < distMaxDisparo/2)
    		{    			
    			valor = valor+4;
    		}
    		if (distPuerta < distMaxDisparo)
    		{    			
    			valor = valor+1;
    		}
    	}
    		
    	return valor;
    }
    
    private double valorarDespeje(int jug)
    {    
    	int ptos =0;
    	//valoramos cercania al ballPosition
    	double distBalon=0;
    	double distPorteria=0;
    	double dist = balon.distance(buenos[jug]);
    	if (dist >= 0 && dist < 20) ptos += 0;
    	else if (dist >= 20 && dist < 40) ptos += 1;
    	else if (dist >= 50) ptos = 2;
    	//valoramos cercan�a malos
    	int malo = buenos[jug].nearestIndex(malos);
    	dist = buenos[jug].distance(malos[malo]);
    	if (dist >= 0 && dist < 5) ptos += -1;
    	else if (dist >= 5 && dist < 10) ptos += 0;
    	else if (dist >= 10 && dist < 20) ptos += 1;
    	else if (dist >= 20) ptos += 2;
    	//valoramos cercan�a porteria propia   	
    	distPorteria = buenos[jug].distance(Constants.centroArcoInf);
    	if (distPorteria >= 0 && distPorteria < 10) ptos += -2;
    	else if (distPorteria >= 10 && distPorteria < 20) ptos += -1;
    	else if (distPorteria >= 20 && distPorteria < 30) ptos += 0;    
    	else if (distPorteria >= 30 && distPorteria < 40) ptos += 1;
    	else if (distPorteria >= 40) ptos += 2;
    	else if (distPorteria >= 50) ptos += -2;
    	
    	//return ptos;
    	return valorarPase(jug);
    }
    
    private int maloDelanteMasCercano()
    {
    	int cercanos[] = balon.nearestIndexes(malos);
    	double ang =0;
    	for (int i=0;i<cercanos.length;i++)
    	{    		
    		ang = balon.angle(malos[cercanos[i]]);
    		if (ang >0 && ang < 180)
    			return cercanos[i];
    	}
    	return -1;
    	
    }
    
    private int[][] malosDelanteMasCercanos(int jug,int dist)
    {
    	int cercanos[] = buenos[jug].nearestIndexes(malos);
    	int[][] malosDelante = new int[11][3];
    	int[] iterMalos = iteracionesMalosPosicion(buenos[jug]);
    	double ang =0;
    	int iter = -1;
    	int j=0;
    	for (int i=0;i<cercanos.length;i++)
    	{    		
    		ang = buenos[jug].angle(malos[cercanos[i]]);
    		ang = ang *(180d/Math.PI);
    		iter = iterMalos[cercanos[i]];
    		if (iter <= 1)
    		{
    			if ((ang >= 0 && ang <= 180) && (iter <= dist))
    			{
    				malosDelante[j][0] = cercanos[i];
    				malosDelante[j][1] = (int)ang;
    				malosDelante[j][2] = iter;
    				j++;
    			}
    		}
    		else
    		{
    			if ((ang >= 20 && ang <= 160) && (iter <= dist))
    			{
    				malosDelante[j][0] = cercanos[i];
    				malosDelante[j][1] = (int)ang;
    				malosDelante[j][2] = iter;
    				j++;
    			}
    		}
    	}    			    		
    	if (j > 0)
    		return Arrays.copyOf(malosDelante,j);
    	else
    		return null;
    	
    }
    
    private void regate(int jug, int malo)
    {
    	double angulo = 0;
    	if (malos[malo].getY() > buenos[jug].getY())
    	{
    		if (malos[malo].getX() > buenos[jug].getX())
    			angulo = 180;  		
    		else
    			angulo = 90;
    	}
		comandos.add(new CommandHitBall(jug,angulo,0.5,0));
    }
    
    private void regate(int jug, double anguloPorteria, double anguloMalo, int iteracionesMalo)
    {
    	double angulo = 0;
    	if (iteracionesMalo > 2)
    	{
    		if (anguloMalo > anguloPorteria)
    			angulo = anguloMalo - 90 - 100-(iteracionesMalo*10);    					
    		else
    			angulo = anguloMalo + 90 + 100-(iteracionesMalo*10);
    	}
    	else
    	{
    		if (anguloMalo > anguloPorteria)
    			angulo = anguloMalo - 75;    					
    		else
    			angulo = anguloMalo + 75;
    	}
		comandos.add(new CommandHitBall(jug,angulo,0.5,0));
    }
    
    private boolean avanzarConBalon(int jug, int avanceIter, int regateIter)
    {
    	//if (1==1) return false;
    	//si es un saque de banda no avanzamos
    	if (Math.abs(balon.getX()) == Constants.ANCHO_CAMPO_JUEGO/2)
    		return false;
    	//miramos si no tiene a nadie cerca para avanzar con el bal�n    	
    	int malo = balon.nearestIndex(malos);
    	double distMalo = balon.distance(malos[malo]);
    	if (distMalo-3 <= Constants.DISTANCIA_CONTROL_BALON)
    		return false;
    	if ( malo == 0 && distMalo-3 <= Constants.DISTANCIA_CONTROL_BALON_PORTERO)
    		return false;
    	int maloDelante = maloDelanteMasCercano();
    	if (maloDelante <0)
    		maloDelante = malo;
    	int iter = iteracionesBalon(malos[malo], detMalos[malo].getSpeed());
    	int iterDelante = iteracionesBalon(malos[maloDelante], detMalos[maloDelante].getSpeed());
    	double anguloMalo = balon.angle(malos[malo]) * (180d/Math.PI);
    	double anguloMaloDelante = balon.angle(malos[maloDelante]) * (180d/Math.PI);
    	if (anguloMalo < 0) anguloMalo = 360 + anguloMalo;
    	double anguloPorteria = balon.angle(Constants.centroArcoSup)* (180d/Math.PI);
    	double angBalon = balon.angle()* (180d/Math.PI);
    	double fuerza = 0.4;
    	if (malo == maloDelante)
    		fuerza = 0.35;
    	if (sitP.isStarts() == false)
    	{
    		if (iter < Constants.DISTANCIA_CONTROL_BALON)
    		{
    			//el contrario est� muy cerca. Regateamos
    			//regate(jug,anguloPorteria, anguloMalo,iterDelante);
    			//regate(jug,malo);
    			//return true;
    			return false;
    		}
    		//miramos si avanzamos con el ballPosition
    		if (iterDelante > avanceIter)// + Constants.ITERACIONES_GOLPEAR_BALON)
    		{   
    			//avanzamos con el ballPosition sin miedo
    			//comandos.add(new ComandoIrA(jug, Constants.centroArcoSup));
    			comandos.add(new CommandHitBall(jug,Constants.centroArcoSup,0.3,0));
    			return true;
    		}
    		else if (iterDelante > regateIter)
    		{
    			//regateamos o avanzamos
    			//if (anguloMalo > 180)
    			//{
    			//	// el malo est� por detras avanzamos hacia porteria
    			//	comandos.add(new ComandoIrA(jug, Constants.centroArcoSup));
        		//	comandos.add(new CommandHitBall(jug));
        		//	return true;
    			//}
    			//else
    			if ( Math.abs(anguloMaloDelante - anguloPorteria) >= 90)    				
    			{
    				//avanzamos hacia porteria
    				//comandos.add(new ComandoIrA(jug, Constants.centroArcoSup));
    				comandos.add(new CommandHitBall(jug,Constants.centroArcoSup,0.3,0));
        			return true;
    			}
    			else if (Math.abs(anguloMaloDelante - anguloPorteria) < 90)
    			{
    				//avanzamos perpendicularmente al malo que viene y en direccion cercana a la porteria
    				//if (detMalos[maloDelante].isGoalKeeper() == false)
    					//regate(jug,anguloPorteria,anguloMaloDelante,iterDelante);
    					//regate(jug,malo);
    				//else if (detMalos[maloDelante].isGoalKeeper() == true && iterDelante < Constants.DISTANCIA_CONTROL_BALON_PORTERO + 5)
    					//regate(jug,anguloPorteria,anguloMaloDelante,iterDelante);
    					//regate(jug,malo);
        			//return true;
    					return false;
    			}
    			return false;
    		}
    	}
    	return false;
    }
    
    private void enviaMarcaje(Position p)
    {    	
    	int jug = p.nearestIndex(buenos,marcaMio);
    	double angulo = p.angle(Constants.centroArcoInf);
    	//double angulo = p.angulo(ballPosition);
    	comandos.add(new CommandMoveTo(jug, p.moveAngle(angulo, 0)));
    	for (int i=0;i<11;i++)
    	{
    		if (marcaMio[i] == -1)
    		{
    			marcaMio[i] = jug;
    			return;
    		}
    	}
    }
    
    private void marcajeDelanteros()
    {    	
    	if ((zonaCampo > 9 && predecirPosesion() == true) || esSaquePuerta() == true)// || iterTrasSaque < numIterTrasSaque)
    		return; //retornamos puesto que el ballPosition esta lejos de nuestro campo
    	int[] cercanos = Constants.centroArcoInf.nearestIndexes(malos);
    	for (int i=0;i<numMaxMarcajes;i++)
    	{
    		if (Constants.centroArcoInf.distance(malos[cercanos[i]]) < distMaxMarcaje)
    		{
    			enviaMarcaje(malos[cercanos[i]]);
    		}
    		else 
    			return;
    	}
    	
    }
    
    private void setAlineacion()
    {    	
    	double largo = Constants.LARGO_CAMPO/4;
    	double ancho = Constants.ANCHO_CAMPO/3;
    	double largo1 = 0-(largo*2);
    	double largo2 = 0 - largo;
    	double largo3 = 0;
    	double largo4 = 0 + largo;
    	double ancho1 = 0- ancho - (ancho/2);
    	double ancho2 = 0 - ancho/2;
    	double ancho3 = 0 + ancho/2;
        
    	Position p = balon;
    	if (esSaquePuerta()==true)
    	{
    		alinActual = alineacion8;
    		zonaCampo = 8;
    		return;
    	}
    	if (p.getY() > largo4)
    	{
    		//estamos en defensa
    		if (posesion == true)
    		{
    			if (p.getX() > ancho3){alinActual = alineacion12;zonaCampo = 12;}    			
    			else if (p.getX() > ancho2) {alinActual = alineacion11;zonaCampo = 11;}
    			else if (p.getX() > ancho1) {alinActual = alineacion10;zonaCampo = 10;}
    		}
    		else
    		{
    			if (p.getX() > ancho3){alinActual = alineacion12;zonaCampo = 12;}
        		else if (p.getX() > ancho2) {alinActual = alineacion11;zonaCampo = 11;}
        		else if (p.getX() > ancho1) {alinActual = alineacion10;zonaCampo = 10;}
    		}
    	}
    	else if (p.getY() > largo3)
    	{
    		//estamos en medio defensa
    		if (posesion == true)
    		{
    			if (p.getX() > ancho3){alinActual = alineacion9;zonaCampo = 9;}
    			else if (p.getX() > ancho2) {alinActual = alineacion8;zonaCampo = 8;}
    			else if (p.getX() > ancho1) {alinActual = alineacion7;zonaCampo = 7;}
    		}
    		else
    		{
    			if (p.getX() > ancho3) {alinActual = alineacion6;zonaCampo = 6;}
        		else if (p.getX() > ancho2) {alinActual = alineacion5;zonaCampo = 5;}
        		else if (p.getX() > ancho1) {alinActual = alineacion4;zonaCampo = 4;}   			
    		}
    	}
    	else if (p.getY() > largo2)
    	{
    		//estamos en medio ataque
    		if (posesion == true)
    		{
    			if (p.getX() > ancho3) {alinActual = alineacion6;zonaCampo = 6;}
    			else if (p.getX() > ancho2) {alinActual = alineacion5;zonaCampo = 5;}
    			else if (p.getX() > ancho1) {alinActual = alineacion4;zonaCampo = 4;}
    		}
    		else
    		{
    			if (p.getX() > ancho3) {alinActual = alineacion3;zonaCampo = 3;}
        		else if (p.getX() > ancho2) {alinActual = alineacion2;zonaCampo = 2;}
        		else if (p.getX() > ancho1) {alinActual = alineacion1;zonaCampo = 1;}
    		}
    	}
    	else if (p.getY() > largo1)
    	{
    		//estamos en ataque
    		if (p.getX() > ancho3) {alinActual = alineacion3;zonaCampo = 3;}
    		else if (p.getX() > ancho2) {alinActual = alineacion2;zonaCampo = 2;}
    		else if (p.getX() > ancho1) {alinActual = alineacion1;zonaCampo = 1;}
    	}
    	//despues de saque de puerta los jugadores deben subir al ataque
    	if (zonaCampo < 7 && iterTrasSaque < numIterTrasSaque)
    	{
    		zonaCampo = 5;
    		alinActual = alineacion5;
    	}
    }
    
    private int[] indicesMasBajos(int[] lista)
    {
    	int[] aBajos = new int[lista.length];
    	int masBajo = 1000;
    	int ultimoBajo = -1;
    	int ind = 0;
    	while (ind < aBajos.length)
    	{
    		for (int i=0;i < lista.length;i++)
    		{
    			if (masBajo > lista[i] && lista[i] > ultimoBajo)
    			{
    				masBajo = lista[i];    			
    			}
    		}
    		ultimoBajo = masBajo;
    		for (int i=0;i < lista.length;i++)
    		{
    			if (masBajo == lista[i])
    			{
    				aBajos[ind] = i;
    				ind++;    			
    			}
    		}    
    		masBajo++;
    	}
    	return aBajos;
    }
    
    public int[] getJugadoresCercaBalon(int pNum)
    {
    	int[] aCercanos = null;
    	int[] recupBalon = sitP.getRecoveryBall();
    	/*if (recupBalon.length > 1)
    	{
    		aCercanos = new int[recupBalon.length-1];    	
    		for (int i=1;i<recupBalon.length;i++)
    		{
    			aCercanos[i-1] = recupBalon[i];    		
    		}
    		//return aCercanos;
    	}
    	else*/    	
    		aCercanos = new int[pNum];
    	
    	int[] aIter = new int[11];
    	for (int i=0;i<11;i++)
    	{
    		aIter[i] = iteracionesBalon(buenos[i],detBuenos[i].getSpeed());
    		jugMinIter[i] = posMinIter; 
    	}
    	int masBajos[] = indicesMasBajos(aIter);
    	for (int i=0;i<pNum;i++)
    	{
    		aCercanos[i] = masBajos[i]; 
    	}
    	return aCercanos;
    }
    
    public int[] getJugadoresCercaPosicion(int pNum, Position p)
    {
    	int[] aCercanos = new int[pNum];
    	int[] aJugadores = p.nearestIndexes(buenos);
    	for (int i=0;i<pNum;i++)
    	{
    		aCercanos[i] = aJugadores[i];
    	}
    		
    	return aCercanos;	
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacion13;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion14;
    }
}
