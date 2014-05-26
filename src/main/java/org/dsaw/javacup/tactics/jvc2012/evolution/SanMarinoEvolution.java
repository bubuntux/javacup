package org.dsaw.javacup.tactics.jvc2012.evolution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class SanMarinoEvolution implements Tactic {
	
	private LinkedList<Command> comandos = new LinkedList<>();
	private static boolean inicio = true;
	private static Jugador [] jugadores;
    private static Rival [] rivales;
	private int espera = 50;
	private static boolean esperando = false;
	private static Jugador jugadorQueSaca;
	private static ArrayList<Rival> rivalesDetrasDefensa;
	private static ArrayList<Rival> rivalesDetrasCentro;
	private static boolean tengoPosesion = false;
	public static final int DEFENDER = 1;
	public static final int ATACAR = 2;
	public static final int SAQUE_A_FAVOR = 3;
	public static final int SAQUE_EN_CONTRA = 4;
	public static boolean tactica442 = false;
	public static boolean tactica532 = true;
	public static boolean tactica433 = false;
	public static boolean tactica343 = false;
	public static boolean tactica541 = false;
	public static int estadoJuego = 1;
	public static int portero = 6;
	public static boolean saque = false;
	
	
    Position alineacionNormal[]=new Position[]{
    		new Position(6,-8),
            new Position(-20,-31),
            new Position(6,-31),
            new Position(20,-31),
            new Position(-6,-31),
            new Position(-6,-8),
            
            new Position(0,-51.5),
            
            new Position(20,-8),
            new Position(-20,-8),
            new Position(6,18),
            new Position(-6,18)
        };
    
    Position alineacionStarts[]=new Position[]{
    		new Position(-9.510489510489512,-12.828054298642533),
            new Position(-21.3986013986014,-33.257918552036195),
            new Position(-6.895104895104895,-33.970588235294116),
            new Position(7.37062937062937,-34.20814479638009),
            new Position(19.97202797202797,-33.49547511312217),
            new Position(-23.538461538461537,-13.065610859728507),
            
            new Position(0.2595419847328244,-50.41044776119403),
            
            new Position(7.37062937062937,-13.303167420814479),
            new Position(20.685314685314687,-13.303167420814479),
            new Position(0.2595419847328244,-0.26119402985074625),
            new Position(9.986013986013985,-0.23755656108597287)
        };

        Position alineacionNoStarts[]=new Position[]{
        		new Position(-6.6573426573426575,-16.628959276018097),
                new Position(-19.734265734265733,-29.457013574660635),
                new Position(-5.944055944055944,-32.07013574660634),
                new Position(5.706293706293707,-31.83257918552036),
                new Position(19.734265734265733,-31.119909502262445),
                new Position(-19.496503496503497,-14.96606334841629),
                
                new Position(0.2595419847328244,-50.41044776119403),
                
                new Position(4.755244755244756,-15.91628959276018),
                new Position(20.685314685314687,-15.441176470588236),
                new Position(-3.804195804195804,-8.552036199095022),
                new Position(4.755244755244756,-7.839366515837104)
        };
        
	TacticDetail detalle = new TacticDetailImpl();

	@Override
        public TacticDetail getDetail() {
		return detalle;
	}

	@Override
        public Position[] getStartPositions(GameSituations sp) {
		return alineacionStarts;
	}

	@Override
        public Position[] getNoStartPositions(GameSituations sp) {
		return alineacionNoStarts;
	}

	@Override
    public List<Command> execute(GameSituations sp) {
        //Limpia la lista de comandos
        comandos.clear();

        //Inicializamos el partido
        if(inicio){
        	jugadores = JugadoresManager.obtenerJugadores(sp.myPlayers(),5,3,2);
        	rivales = RivalesManager.obtenerRivales(sp.rivalPlayers());
            inicio=false;
        }
        
        
        
        jugadores = calcularCambioTactica(jugadores, sp);
        
        //Actualizamos el estado de los jugadores
        JugadoresManager.actualizarPosiciones(jugadores, sp);
        RivalesManager.actualizarRivales(rivales, sp);
        JugadoresManager.actualizarRivalesEnZona(jugadores, sp, rivales);
        
        estadoJuego = estadoDelJuego(sp);
        
        
        switch(estadoJuego){
        
        
        case DEFENDER:
        	//DEFENDER
        	comandos = tacticaDefensa(sp, jugadores, rivales);
        	break;
        case ATACAR:
        	//ATACAR
        	resetDefensa();
        	comandos = tacticaAtaque(sp, jugadores, rivales);
        	break;
        case SAQUE_A_FAVOR:
        	//SAQUE A FAVOR
        	resetDefensa();
        	
        	jugadorQueSaca = TacticasUtil.calcularMasCercano(sp.ballPosition(), jugadores); 
    		
        	comandos.add(new CommandMoveTo(jugadorQueSaca.getId(), sp.ballPosition()));
        	
    		for (int i = 0; i < jugadores.length; i++) {
    			if(i!=jugadorQueSaca.getId() ){
    				if(sp.ballPosition().getY()>15){
    					comandos.add(new CommandMoveTo(i, jugadores[i].getPosicionAtaqueBase()));
    				}else if(jugadores[i].getRol() == Jugador.DEFENSA){
    					Rival rival = TacticasUtil.calcularRivalMasCercano(Constants.centroArcoInf, rivales);
    					comandos.add(new CommandMoveTo(i, new Position(jugadores[i].getPosicionBase().getX(),rival.getPosicion().getY())));
    				}
    				else{
    					comandos.add(new CommandMoveTo(i, jugadores[i].getPosicionBase()));
    				}
    			}
            }
    		
        	if(!esperando){
        		esperando=true;
        		espera=50;
        	}else if(espera==0){
        		esperando=false;

        		//Jugador jugadorPase = TacticasUtil.calcularMejorPaseSaque(jugadorQueSaca, jugadores);

        		Rival rival = TacticasUtil.calcularRivalMasCercano(jugadorQueSaca.getPosicion(), rivales);
//    			if(tiro != null){
//    				
//    				comandos.add(tiro);
//    			
//    			}
//    			else 
        		if(sp.ballPosition().getY()<=Constants.ANCHO_AREA_GRANDE+Constants.centroArcoInf.getY()){
    				comandos.add(new CommandHitBall(jugadorQueSaca.getId(), jugadores[10].getPosicion(), 1, 35));
        		}
    			else
    			if((jugadorQueSaca.getRivalesZona()==null 
    					|| (jugadorQueSaca.getRivalesZona()!=null && jugadorQueSaca.getRivalesZona().length==0))
    					&& (rival.getPosicion().distance(jugadorQueSaca.getPosicion())>20)){

    				comandos.add(new CommandHitBall(jugadorQueSaca.getId(), new Position(-jugadorQueSaca.getPosicion().getX(),jugadorQueSaca.getPosicion().getY()+5), 0.20, false));
    				
    			}else{
        			Pase pase = TacticasUtil.calcularPosicionPase(jugadorQueSaca, jugadores, rivales, sp);
            		if(pase!=null && pase.getJugadorDestino()!=null){
            			comandos.add(new CommandHitBall(jugadorQueSaca.getId(), pase.getPosicionPase(), 
                				TacticasUtil.getPotenciaPase(jugadorQueSaca.getPosicion(), 
                						pase.getPosicionPase()), 30));
            			comandos.add(new CommandMoveTo(pase.getJugadorDestino().getId(), pase.getPosicionPase()));
            		}else{
            			Position pos = TacticasUtil.posicionJugadorMasAdelantado(jugadorQueSaca,jugadores);
            			comandos.add(new CommandHitBall(jugadorQueSaca.getId(),pos, 1, 35));
            		}
            		
        		}
        		
        	}else{
        		espera--;
        	}
        	

    		
        	break;
        case SAQUE_EN_CONTRA:
        	//SAQUE EN CONTRA
        	comandos = tacticaDefensa(sp, jugadores, rivales);
        	break;
        	
        default:
        	//OTRO ESTADO
        	comandos = tacticaDefensa(sp, jugadores, rivales);
        }
     
		if(sp.ballPosition().getX()==0 && sp.ballPosition().getY()==0 && sp.canKick().length>0){
			comandos.add(new CommandHitBall(sp.canKick()[0],jugadores[1].getPosicion(), 0.8, 10));
			saque=true;
		}
		if(jugadorQueSaca!=null && sp.getRecoveryBall().length>1 && sp.getRecoveryBall()[1]!=portero && jugadorQueSaca.getId()!=sp.getRecoveryBall()[1]){
			
			double[] posRecuperacion = sp.getTrajectory(sp.getRecoveryBall()[0]);
			Position pos = new Position(posRecuperacion[0], posRecuperacion[1]);
			
			comandos.add(new CommandMoveTo(sp.getRecoveryBall()[1], pos));
			
		}

        //Retorna la lista de comandos
        return comandos;
    }	
	
	
	private static LinkedList<Command> tacticaDefensa(GameSituations sp, Jugador[] jugadores,
			Rival[] rivales){
		LinkedList<Command> comandos = new LinkedList<>();
		
		MovimientosDefensa.calcularLineasHorizontales(rivales, sp.ballPosition(), jugadores);
		
		if(rivalesDetrasDefensa==null){
			rivalesDetrasDefensa=new ArrayList<>();
		}
		if(rivalesDetrasCentro==null){
			rivalesDetrasCentro=new ArrayList<>();
		}
		
		
		ArrayList<Rival> rivalesDetras = TacticasUtil.calcularRivalesDetrasLinea(rivales, MovimientosDefensa.LINEA_CENTROCAMPO_BASE);
		Jugador jugadorAux;
		
		//Calcular marcajes
		for (int i = 0; i < rivalesDetras.size(); i++) {
			
			if(!rivalesDetrasCentro.contains(rivalesDetras.get(i))){
				rivalesDetrasCentro.add(rivalesDetras.get(i));
			}
			
			if(!rivalesDetras.get(i).isMarcado()){
				jugadorAux = TacticasUtil.calcularMasCercanoSinMarca(rivalesDetras.get(i).getPosicion(), jugadores, Jugador.DEFENSA);
				if(jugadorAux==null){
					jugadorAux = TacticasUtil.calcularMasCercanoSinMarca(rivalesDetras.get(i).getPosicion(), jugadores, Jugador.CENTROCAMPISTA);
				}
				
				if(jugadorAux!=null){
					rivalesDetras.get(i).setMarcado(true);
					jugadorAux.setRivalMarcado(rivalesDetras.get(i));
				}
			}
		}
		
		
		//Liberar marcajes
		for (int i = 0; i < rivalesDetrasCentro.size(); i++) {
			
			if(!rivalesDetras.contains(rivalesDetrasCentro.get(i))){
				rivalesDetrasCentro.get(i).setMarcado(false);
				
				for (int j = 0; j < jugadores.length; j++) {
					if(jugadores[j].getRivalMarcado()!=null && 
							jugadores[j].getRivalMarcado().getId()==rivalesDetrasCentro.get(i).getId()){
						jugadores[j].setRivalMarcado(null);
					}
				}
				
			}
		}
		
		//Movemos al portero
		comandos.add(movimientoDelPortero(jugadores, rivales, sp));
		
		
		Position posicionBalon = sp.ballPosition();
		
		
		int defensas = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.DEFENSA);
		int centro = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.CENTROCAMPISTA);
		int delanteros = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.DELANTERO);
		
		double [] cortesVerticalesDefensa = JugadoresManager.calcularCortes(defensas,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesCentro = JugadoresManager.calcularCortes(centro,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesDelantera = JugadoresManager.calcularCortes(delanteros,Constants.ANCHO_CAMPO_JUEGO/2);
		int j=0,x=0,y=0;
		
		//Movemos al resto de jugadores
		for (int i = 0; i < jugadores.length; i++) {
			
			if(jugadores[i].getRol()==Jugador.DEFENSA){
				comandos.add(MovimientosDefensa.moverDefensa(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesDefensa[j++],MovimientosDefensa.getLineaDefensaActual())));
			}else if(jugadores[i].getRol()==Jugador.CENTROCAMPISTA){
				comandos.add(MovimientosDefensa.moverCentrocampo(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesCentro[x++],MovimientosDefensa.getLineaCentroActual())));
			}else if(jugadores[i].getRol()==Jugador.DELANTERO){
				comandos.add(MovimientosDefensa.moverDelantera(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesDelantera[y++],MovimientosDefensa.getLineaDelanteraActual())));
			}

		}
		
		Jugador masCercaBalon = TacticasUtil.calcularMasCercano(posicionBalon, jugadores);
		
		if(!TacticasUtil.tengoBalon(masCercaBalon.getId(), sp.canKick()) && sp.ballAltitude()<=Constants.ALTURA_CONTROL_BALON){
			comandos.add(new CommandMoveTo(masCercaBalon.getId(), posicionBalon));
		}
		

		return comandos;
	}
	
	private static LinkedList<Command> tacticaAtaque(GameSituations sp, Jugador[] jugadores,
			Rival[] rivales){
		LinkedList<Command> comandos = new LinkedList<>();
		
		
		Position posicionBalon = sp.ballPosition();
		ArrayList <Jugador> colocados = new ArrayList<>();
		
		
		int defensas = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.DEFENSA);
		int centro = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.CENTROCAMPISTA);
		int delanteros = TacticasUtil.obtenerNumeroJugadores(jugadores, Jugador.DELANTERO);
		
		double [] cortesVerticalesDefensa = JugadoresManager.calcularCortes(defensas,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesCentro = JugadoresManager.calcularCortes(centro,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesDelantera = JugadoresManager.calcularCortes(delanteros,Constants.ANCHO_CAMPO_JUEGO/2);
		int j=0,x=0,y=0;
		if(sp.canKick().length>0){
			Jugador conBalon = jugadores[sp.canKick()[0]];
			colocados.add(conBalon);
			Pase pase = TacticasUtil.calcularPosicionPase(conBalon, jugadores, rivales, sp);
			Position puedoConducir = TacticasUtil.puedoConducir(conBalon, rivales);
			CommandHitBall tiro = TacticasUtil.calcularTiro(conBalon, jugadores, rivales, sp);
			
			if(tiro != null){
				
				comandos.add(tiro);
			
			}else if(conBalon.getRivalesZona()==null || (conBalon.getRivalesZona()!=null && conBalon.getRivalesZona().length==0)){
				//Rival rival = TacticasUtil.calcularRivalMasCercano(conBalon.getPosicion(), rivales);
				//comandos.add(new CommandHitBall(conBalon.getId(), new Position(-rival.getPosicion().getX(),conBalon.getPosicion().getY()+10), 0.38, false));
				//double potencia = TacticasUtil.calcularPotencia(conBalon, sp);
				comandos.add(new CommandHitBall(conBalon.getId(), Constants.centroArcoSup, 0.38, false));
				
			}else if(puedoConducir!=null){
				comandos.add( new CommandHitBall(conBalon.getId(), puedoConducir, 0.38, false));
			}else if(pase!=null){
				comandos.add(new CommandHitBall(conBalon.getId(), pase.getPosicionPase(), 
        				TacticasUtil.getPotenciaPase(conBalon.getPosicion(), 
        						pase.getPosicionPase()), TacticasUtil.getAlturaPase(conBalon.getPosicion(), 
                						pase.getPosicionPase())));
				
				comandos.add(new CommandMoveTo(pase.getJugadorDestino().getId(), pase.getJugadorDestino().getPosicion().movePosition(0, 10)));
				colocados.add(pase.getJugadorDestino());
				
			}else{
				Position pos = TacticasUtil.posicionJugadorMasAdelantado(conBalon, jugadores);
				comandos.add(new CommandHitBall(conBalon.getId(), pos.movePosition(0, 0), 
        				TacticasUtil.getPotenciaPase(conBalon.getPosicion(), 
        						pos), TacticasUtil.getAlturaPase(conBalon.getPosicion(), pos)));
			}
			
		}else if(sp.getRecoveryBall().length>2){
			Jugador conBalon = jugadores[sp.getRecoveryBall()[1]];
			colocados.add(conBalon);
			
			comandos.add(new CommandMoveTo(conBalon.getId(), TacticasUtil.puedoRecuperar(conBalon, rivales, sp)));
		}
		
		
		//Movemos al resto de jugadores
		for (int i = 0; i < jugadores.length; i++) {
			
			if(jugadores[i].getRol()==Jugador.DEFENSA && !colocados.contains(jugadores[i])){
				comandos.add(MovimientosAtaque.moverDefensa(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesDefensa[j++],MovimientosAtaque.getLineaDefensaActual())));
			}else if(jugadores[i].getRol()==Jugador.CENTROCAMPISTA && !colocados.contains(jugadores[i])){
				comandos.add(MovimientosAtaque.moverCentrocampo(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesCentro[x++],MovimientosAtaque.getLineaCentroActual())));
			}else if(jugadores[i].getRol()==Jugador.DELANTERO && !colocados.contains(jugadores[i])){
				comandos.add(MovimientosAtaque.moverDelantera(jugadores[i], posicionBalon, jugadores, rivales, sp,new Position(cortesVerticalesDelantera[y++],MovimientosAtaque.getLineaDelanteraActual())));
			}

		}
		
		Jugador masCercaBalon = TacticasUtil.calcularMasCercano(posicionBalon, jugadores);
		
		if(!TacticasUtil.tengoBalon(masCercaBalon.getId(), sp.canKick()) && sp.ballAltitude()<=Constants.ALTURA_CONTROL_BALON){
			comandos.add(new CommandMoveTo(masCercaBalon.getId(), posicionBalon));
		}
		
		//Movemos al portero
		comandos.add(movimientoDelPortero(jugadores, rivales, sp));
		
		if(saque && sp.canKick().length>0){
			comandos.add(new CommandHitBall(sp.canKick()[0],jugadores[10].getPosicion().movePosition(0, 5),1,40));
			saque=false;
		}
		
		return comandos;
	}
	
	private static LinkedList<Command> saqueBandaFavor(GameSituations sp, Jugador[] jugadores,
			Rival[] rivales){
		LinkedList<Command> comandos = new LinkedList<>();
		

		

		return comandos;
	}
	
	private static LinkedList<Command> cornerAFavor(GameSituations sp, Jugador[] jugadores,
			Rival[] rivales){
		LinkedList<Command> comandos = new LinkedList<>();
		

		

		return comandos;
	}
	
	private static Command movimientoDelPortero(Jugador [] jugadores, Rival [] rivales, GameSituations sp){
    	/*************** COLOCACION PORTERO *****************************/
    	
		//System.out.println("Iteracion: "+iteracion+" - X: "+posicionBalon.getX()+" Y: "+posicionBalon.getY());
		//Movimiento lateral del porteros
		Position posicionBalon = new Position(sp.getTrajectory(0)[0],sp.getTrajectory(0)[1]);
		Position posRecuperacion = TacticasUtil.puedoRecuperar(jugadores[portero], rivales, sp);
		Position parada = TacticasUtil.calcularParada(sp);
		
//		Position posicionPortero = jugadores[portero].getPosicionBase();
//		if(posicionBalon.getX()<=1 && posicionBalon.getX()>=-1 ){
//			posicionPortero = posicionPortero.setPosition(0, posicionPortero.getY());
//	    }else if(posicionBalon.getX()>=Constants.posteDerArcoInf.getX()){
//	    	posicionPortero = posicionPortero.setPosition(Constants.posteDerArcoInf.getX()/2, posicionPortero.getY());
//		}else if(posicionBalon.getX()<=Constants.posteIzqArcoInf.getX()){
//			posicionPortero = posicionPortero.setPosition(Constants.posteIzqArcoInf.getX()/2, posicionPortero.getY());
//		}else if(posicionBalon.getX()>=0 && posicionBalon.getX()<Constants.posteDerArcoInf.getX()){
//			posicionPortero = posicionPortero.setPosition(Constants.posteDerArcoInf.getX()/2, posicionPortero.getY());
//		}else if(posicionBalon.getX()<=0 && posicionBalon.getX()>Constants.posteIzqArcoInf.getX()){
//			posicionPortero = posicionPortero.setPosition(Constants.posteIzqArcoInf.getX()/2, posicionPortero.getY());
//		}

		if(TacticasUtil.tengoBalon(portero, sp.canKick())){
			//Jugador jugadorPase = TacticasUtil.calcularMejorPaseSaque(jugadores[0], jugadores);
			
    		Pase pase = TacticasUtil.calcularPosicionPase(jugadores[portero], jugadores, rivales, sp);
    		if(pase!=null && pase.getJugadorDestino()!=null){
    			return new CommandHitBall(jugadores[portero].getId(), pase.getPosicionPase(), 
        				TacticasUtil.getPotenciaPase(jugadores[portero].getPosicion(), 
        						pase.getPosicionPase()), 30);
    		}else{
    			return new CommandHitBall(jugadores[portero].getId(), new Position(0,0), 1, 45);
    		}
    		
//			Jugador jugadorPase = jugadores[8];
//			if(jugadorPase!=null){
//    			return new CommandHitBall(0, jugadorPase.getPosicion(), 
//        				TacticasUtil.getPotenciaPase(jugadores[0].getPosicion(), 
//        						jugadorPase.getPosicion()), 35);
//    		}else{
//    			return new CommandHitBall(0, new Position(Constants.ANCHO_CAMPO_JUEGO/2,0), 1, 45);
//    		}
		}else if(parada!=null){
	        return new CommandMoveTo(portero,parada);
		}
		else if(posRecuperacion!=null){
			
			Rival rival = TacticasUtil.calcularRivalMasCercano(posRecuperacion, rivales);
			
			if(rival.getPosicion().distance(posRecuperacion)<=jugadores[portero].getPosicion().distance(posRecuperacion)-6){
				return new CommandMoveTo(jugadores[portero].getId(),posRecuperacion);
			}
		}
		
		
//		else if(jugadores[0].isPuedoRecuperar()){
//			int[] recuperadores = sp.getRecoveryBall();
//			
//			if(recuperadores.length>0){
//				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
//				
//				double x = posRecuperacion[0];
//				double y = posRecuperacion[1];
//				
//				if(y<=Constants.centroArcoInf.getY()+1){
//					y=Constants.centroArcoInf.getY()+1;
//				}
//				
//				Rival rival = TacticasUtil.calcularRivalMasCercano(new Position(x,y), rivales);
//				
//				
//				double distanciaRival = rival.getPosicion().distance(new Position (posRecuperacion[0],posRecuperacion[1]));
//				double distanciaPortero = jugadores[0].getPosicion().distance(new Position (posRecuperacion[0],posRecuperacion[1]));
//				
//				if(sp.getTrajectory(0)[2]>3){
//					if(distanciaRival>distanciaPortero+5){
//						return new CommandMoveTo(jugadores[0].getId(),new Position(x, y));
//					}
//				}else{
//					if(distanciaRival>distanciaPortero){
//						return new CommandMoveTo(jugadores[0].getId(),new Position(x, y));
//					}
//				}
//				
//			}
//		}

		Position centro = Constants.centroArcoInf.movePosition(0, -1);
        return new CommandMoveTo(portero, centro.moveAngle(centro.angle(sp.ballPosition()), 3));

        /*************** COLOCACION PORTERO *****************************/
	}
	
	public static void resetDefensa(){
		
		for (int j = 0; j < jugadores.length; j++) {
			jugadores[j].setRivalMarcado(null);
			rivales[j].setMarcado(false);
		}
		
		
		rivalesDetrasDefensa=new ArrayList<>();
		rivalesDetrasDefensa=new ArrayList<>();
		
	}

	public static int estadoDelJuego(GameSituations sp){
		
		
		if(sp.isRivalStarts()){
			tengoPosesion=false;
			saque=false;
			return SAQUE_EN_CONTRA;
		}else if(sp.isStarts()){
			tengoPosesion=true;
			return SAQUE_A_FAVOR;
		}else if(sp.canKick().length>0 && sp.rivalCanKick().length>0){
			
//			double disJugador, disRival;
//			
//			disJugador = TacticasUtil.calculaDistancia(sp.ballPosition(), jugadores[sp.canKick()[0]].getPosicion());
//			disRival =  TacticasUtil.calculaDistancia(sp.ballPosition(), rivales[sp.rivalCanKick()[0]].getPosicion());
//			
//			if(disJugador<disRival){
//				tengoPosesion=true;
//				return ATACAR;
//			}else{
				tengoPosesion=false;
				saque=false;
				return DEFENDER;
//			}
			
		}else if(sp.canKick().length>0 && sp.rivalCanKick().length==0){
			tengoPosesion=true;
			return ATACAR;
		}else if(sp.canKick().length==0 && sp.rivalCanKick().length>0){
			saque=false;
			tengoPosesion=false;
			return DEFENDER;
		}if(tengoPosesion){
			return ATACAR;
		}else{
			return estadoJuego;
		}
		
	}
	
	public static Jugador[] calcularCambioTactica(Jugador [] jugadores, GameSituations sp){
		
		if((sp.rivalGoals()-sp.myGoals()>1 || (sp.myGoals()<sp.rivalGoals() 
				&& (Constants.ITERACIONES-sp.iteration())<1200))){
			if(!tactica433){
				tactica433=true;
				tactica442=false;
				tactica343=false;
				tactica532=false;
				tactica541=false;
				jugadores = JugadoresManager.cambiarTactica(jugadores, 4, 3, 3);
			}
			
		}else if(sp.myGoals()==sp.rivalGoals()
				&& (Constants.ITERACIONES-sp.iteration())<1200){
			if(!tactica433){
				tactica433=true;
				tactica442=false;
				tactica343=false;
				tactica532=false;
				tactica541=false;
				jugadores = JugadoresManager.cambiarTactica(jugadores, 4, 3, 3);
			}
			
		}else if(sp.myGoals()-sp.rivalGoals()==1 
				&& (Constants.ITERACIONES-sp.iteration())<1200){
			if(!tactica541){
				tactica433=false;
				tactica442=false;
				tactica343=false;
				tactica532=false;
				tactica541=true;
				jugadores = JugadoresManager.cambiarTactica(jugadores, 5, 4, 1);
			}
			
		}else if(!tactica532){
			tactica433=false;
			tactica442=false;
			tactica343=false;
			tactica532=true;
			tactica541=false;
			jugadores = JugadoresManager.cambiarTactica(jugadores, 5, 3, 2);
		}
		
		return jugadores;
	}
}
