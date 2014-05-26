package org.dsaw.javacup.tacticas.jvc2012.evolution;

import java.util.ArrayList;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class TacticasUtil {

	public static Jugador calcularMasCercano(Position posicion, Jugador [] jugadores){
		
		int resultado = 11;
		double distancia, masCerca=1000;
		for(int i=0;i<jugadores.length;i++){
			distancia = posicion.distance(jugadores[i].getPosicion());
			
			if(distancia<masCerca){
				resultado=i;
				masCerca=distancia;
			}
		}		
		return jugadores[resultado];
	}
	
	public static Jugador calcularMasCercanoSinMarca(Position posicion, Jugador [] jugadores, int rol){
		
		int resultado = -1;
		double distancia, masCerca=1000;
		for(int i=0;i<jugadores.length;i++){
			if(jugadores[i].getRol()==rol){
				distancia = posicion.distance(jugadores[i].getPosicion());
				
				if(distancia<masCerca && jugadores[i].getRivalMarcado()==null){
					resultado=i;
					masCerca=distancia;
				}
			}
		}		
		
		if(resultado!=-1){
			return jugadores[resultado];
		}else{
			return null;
		}
	}
	
	public static Rival calcularRivalMasCercano(Position posicion, Rival [] rivales){
		
		int resultado = 10;
		double distancia, masCerca=1000;
		for(int i=0;i<rivales.length;i++){
		
			distancia = posicion.distance(rivales[i].getPosicion());
			
			if(distancia<masCerca){
				resultado=i;
				masCerca=distancia;
			}
		}		
		return rivales[resultado];
	}
	
	
	public static double calculaDistancia(Position posicion1, Position posicion2){
		double distancia,x,y;
			x = Math.pow(posicion1.getX()-posicion2.getX(),2);
			y = Math.pow(posicion1.getY()-posicion2.getY(),2);
			
			distancia = Math.sqrt(x+y);
		return distancia;		
	}
	

	public static Jugador calcularMejorPaseSaque(Jugador jugador, Jugador [] jugadores){
		
		Jugador mejorPase =null;
		double distanciaAPorteria=1000, aux;
		
//		for(int i=0; i<jugadores.length;i++){
//			aux = Constants.centroArcoSup.distance(jugadores[i].getPosicion());
//			if(jugadores[i].getId()!=jugador.getId() && jugadores[i].getRivalesEnMiZona().size()==0 
//					&& distanciaAPorteria>aux){
//					distanciaAPorteria=aux;
//					mejorPase = jugadores[i];
//			}
//		}
		
		return mejorPase;	
	}
	
	/*
	public static void defensaIndividual(Rival [] rivales, Jugador [] jugadores){
        
        for(int j = rivales.length-1;j>0; j--){
    		
    		if(rivales[j].getPosicion().getY()<=Constants.centroCampoJuego.getY() && !rivales[j].isMarcado()){
    			int masCercano = calcularMasCercano(rivales[j].getPosicion(), jugadores);
    			rivales[j].setMarcado(true);
    			if(!jugadores[masCercano].isColocado()){
    				jugadores[masCercano].setColocado(true);
    				comandos.add(new CommandMoveTo(masCercano, rivales[j].getPosicion()));
    			}else{
    				int sinMarca = jugadorSinMarca(jugadores);
    				jugadores[sinMarca].setColocado(true);
    				comandos.add(new CommandMoveTo(sinMarca, rivales[j].getPosicion()));
    			}
    			
    		}		
    	}
	}
	*/
	
	public static CommandMoveTo jugadorMasCercaAlBalon(Position posicionBalon, Jugador [] jugadores){
        /******** Mandamos a los jugadores m�s cercanos a recuperar el bal�n ********/
        
        int cercaBalon = calcularMasCercano(posicionBalon, jugadores).getId();
        return new CommandMoveTo(cercaBalon, posicionBalon);
	}
	

	public static Position posicionJugadorMasAdelantado(Jugador jug, Jugador [] jugadores){
		double Y = -52;
		int jugador=10;
		for(int i=1;i<jugadores.length;i++){
			if(jugadores[i].getPosicion().getY()>Y && jug.getId()!=jugadores[i].getId()){
				Y=jugadores[i].getPosicion().getY();
				jugador=i;
			}
		}
		return jugadores[jugador].getPosicion();
	}
	
	public static Position posicionRivalMasAdelantado(Rival [] rivales){
		double Y = Constants.LARGO_CAMPO_JUEGO/2;
		int rival=10;
		for(int i=1;i<rivales.length;i++){
			if(rivales[i].getPosicion().getY()<Y){
				Y=rivales[i].getPosicion().getY();
				rival=i;
			}
		}
		return rivales[rival].getPosicion();
	}
	
	public static int rivalesEntreLineas(Rival [] rivales, double lineaBaja, double lineaAlta){
		int numRivales=0;
		for(int i=1;i<rivales.length;i++){
			if(rivales[i].getPosicion().getY()<=lineaAlta && rivales[i].getPosicion().getY()>=lineaBaja){
				numRivales++;
			}
		}
		return numRivales;
	}
	
	public static double getAlturaPase(Position origen, Position destino){
		double distancia = calculaDistancia(origen, destino);
		
		if(distancia>45){
			return 37;
		}else if(distancia>40){
			return 35;
		}else if(distancia>35 && 40<distancia){
			return 33;
		}else if(distancia>30 && 35<distancia){
			return 30;
		}else if(distancia>25 && 30<distancia){
			return 28;
		}else if(distancia>20 && 25<distancia){
			return 25;
		}else if(distancia>15 && 20<distancia){
			return 20;
		}else if(distancia>10 && 15<distancia){
			return 15;
		}else{
			return 10;
		}
	}
	
	public static double getPotenciaPase(Position origen, Position destino){
		
		double distancia = calculaDistancia(origen, destino);
		
		if(distancia>40){
			return 1;
		}else if(distancia>35 && 40<distancia){
			return 1;
		}else if(distancia>30 && 35<distancia){
			return 0.9;
		}else if(distancia>25 && 30<distancia){
			return 0.8;
		}else if(distancia>20 && 25<distancia){
			return 0.7;
		}else if(distancia>15 && 20<distancia){
			return 0.7;
		}else if(distancia>10 && 15<distancia){
			return 0.6;
		}else{
			return 0.5;
		}
		
	}
	
	public static boolean isRivalConBalon(Rival rival, GameSituations gp){
		
		int [] cankick = gp.rivalCanKick();
		
		for (int i = 0; i < cankick.length; i++) {
			if(rival.getId()==cankick[i]){
				return true;
			}
		}
		return false;
	}
	
	public static boolean enElArea(Position posicion) {
		if(Math.abs(posicion.getX()) <= Constants.LARGO_AREA_GRANDE/2 &&
				posicion.getY() <= Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_GRANDE){
			return true;
		}
		return false;
	}
	
	public static Position calcularParada(GameSituations sp){
		Position resultado = null;

		for (int i = 0; i < Constants.ITERACIONES-sp.iteration(); i++) {
			
			if(sp.getTrajectory(i)[1]<=Constants.centroArcoInf.getY() && sp.getTrajectory(i)[1]>Constants.centroArcoInf.getY()-3 
					&& sp.getTrajectory(i)[0]>Constants.posteIzqArcoInf.getX()
					&& sp.getTrajectory(i)[0]<Constants.posteDerArcoInf.getX()){
				
					int j = 0;
					for (j = i-1; j >= 0
									&& sp.getTrajectory(j)[1]<Constants.centroArcoInf.getY() 
									&& sp.getTrajectory(j)[2]<5; j--) {
					}		
					resultado=new Position(sp.getTrajectory(j)[0],sp.getTrajectory(j)[1]);
				
				break;
			}
			
		}
		
		return resultado;
	}
	
	
	public static Position calcularPosicionCortePase(Jugador jugador, GameSituations sp, Jugador [] jugadores, Rival [] rivales) {
		Position resultado = null;
		Position aux;
		Position anterior = new Position();;
		Jugador jugadorMasCerca;
		Rival rivalMasCerca;
		double distanciaAnterior = 100;
		double distanciaAux = 100;

		int posicionCorte;
		
		double metrosIter = Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()));

		for (int i = 0; i < Constants.ITERACIONES 
			&& (new Position(sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]).distance(new Position(sp.getTrajectory(i+1)[0],sp.getTrajectory(i+1)[1]))!=0); i++) {

			for (int j = 0; j < rivales.length; j++) {
				
				
				
			}
			distanciaAux = jugador.getPosicion().distance(new Position(sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]));
			
			if(distanciaAux>distanciaAnterior){
				break;
			}
			
			if(sp.getTrajectory(i)[2]<=3){

				if(distanciaAux/i<metrosIter){
					resultado=new Position(sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]);
					break;
				}				
			}
			distanciaAnterior=distanciaAux;
		}	
		

		return null;
	}
	
	public static ArrayList<Rival> calcularRivalesDetrasLinea (Rival [] rivales, double linea){
		ArrayList <Rival> rivalesDetras = new ArrayList <Rival>();
		for (int i = 0; i < rivales.length; i++) {
			if(rivales[i].getPosicion().getY()<linea){
				rivalesDetras.add(rivales[i]);
			}
		}
		return rivalesDetras;
	}
	
	
	public static int obtenerNumeroJugadores(Jugador [] jugadores, int rol){
		
		int numJug=0;
		
		for (int i = 0; i < jugadores.length; i++) {
			
			if(jugadores[i].getRol()==rol){
				numJug++;
			}
			
		}
		return numJug;
	}

	
	public static boolean tengoBalon(int i, int [] jugadoresConBalon){
		
		for(int j =0; j<jugadoresConBalon.length;j++){
			if(i==jugadoresConBalon[j]){
				return true;
			}
		}
		return false;
		
	}
	
	public static Position calcularCortePaseMarca(Jugador jugador, GameSituations sp){
		
		Position resultado = null;
		
		return jugador.getRivalMarcado().getPosicion().moveAngle(jugador.getRivalMarcado().getPosicion().angle(sp.ballPosition()), 1);

		
//		Position balon;
//		Rival rival = jugador.getRivalMarcado();
//		Zona zona = new Zona(0);
//		int RADIO_ZONA = 2;
//		zona.setAbajoDerecha(new Position(rival.getPosicion().getX()+RADIO_ZONA,rival.getPosicion().getY()-RADIO_ZONA));
//		zona.setAbajoIzquierda(new Position(rival.getPosicion().getX()-RADIO_ZONA,rival.getPosicion().getY()-RADIO_ZONA));
//		zona.setArribaDerecha(new Position(rival.getPosicion().getX()+RADIO_ZONA,rival.getPosicion().getY()+RADIO_ZONA));
//		zona.setArrivaIzquierda(new Position(rival.getPosicion().getX()-RADIO_ZONA,rival.getPosicion().getY()+RADIO_ZONA));
//		
//		double metrosIter = Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()));
//		double distancia, aux;
//		for (int i = 0; i < 100 
//		&& (new Position(sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]).distance(new Position(sp.getTrajectory(i+1)[0],sp.getTrajectory(i+1)[1]))!=0); i++) {
//			
//			balon = new Position(sp.getTrajectory(i)[0],sp.getTrajectory(i)[1]);
//			
//			if(zona.estaEnZona(balon)){
//
//				distancia = jugador.getPosicion().distance(new Position(sp.getTrajectory(i-1)[0],sp.getTrajectory(i-1)[1]));
//				
//				if(distancia<((i-1)*metrosIter)){
//					resultado = new Position(sp.getTrajectory(i-1)[0],sp.getTrajectory(i-1)[1]);
//				}
//				
//				int j = 0;
//				for (j = i-1; j >= 0; j--) {
//					aux = jugador.getPosicion().distance(new Position(sp.getTrajectory(j)[0],sp.getTrajectory(j)[1]));
//					if(aux<distancia){
//						
//						if(aux<((j)*metrosIter)){
//							resultado = new Position(sp.getTrajectory(j)[0],sp.getTrajectory(j)[1]);
//						}	
//					}
//				}			
//			}
//		}

		//return resultado;
		
	}
	
	public static Position puedoRecuperar(Jugador jugador, Rival [] rivales, GameSituations sp){
		
		Position resultado = null;
		
		if(sp.getRecoveryBall().length>1 && sp.getRecoveryBall()[1]==jugador.getId()){
			int[] recuperadores = sp.getRecoveryBall();
			
			if(recuperadores.length>0){
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				Position pos = new Position(posRecuperacion[0], posRecuperacion[1]);
				Rival cercano = TacticasUtil.calcularRivalMasCercano(pos, rivales);
				if(cercano.getPosicion().distance(pos)>jugador.getPosicion().distance(pos)){
					resultado = pos;
				}
			}
		}
		return resultado;
	}
	
	
	public static boolean comprobarRival(Position inicio, Position fin, Rival [] rivales){

		
			return true;
//		
//		
//		ArrayList <Rival> rivalesZona = new ArrayList<Rival>();
//		Position media = Position.medium(inicio, fin);
//		double izquierda1=0;
//		double derecha1 = 0;
//		double arriba1=0;
//		double abajo1=0;
//		double izquierda2=0;
//		double derecha2 = 0;
//		double arriba2=0;
//		double abajo2=0;
//		
//		if(inicio.getX()<fin.getX()){
//			izquierda1 = inicio.getX(); 
//			izquierda2 = media.getX();
//			derecha1 = media.getX();
//			derecha2 = fin.getX();
//		}else{
//			izquierda1 = fin.getX(); 
//			izquierda2 = media.getX();
//			derecha1 = media.getX();
//			derecha2 = inicio.getX();
//		}
//		
//		if(inicio.getY()<fin.getY()){
//			arriba1 = media.getY();
//			arriba2 = fin.getY();
//			abajo1 = inicio.getY();
//			abajo2 = media.getY();
//		}else{
//			arriba1 = media.getY();
//			arriba2 = inicio.getY();
//			abajo1 = fin.getY();
//			abajo2 = media.getY();
//			
//		}
//		
//		Zona zona1 = new Zona(0);
//
//		zona1.setAbajoDerecha(new Position(derecha1,abajo1));
//		zona1.setAbajoIzquierda(new Position(izquierda1,abajo1));
//		zona1.setArribaDerecha(new Position(derecha1,arriba1));
//		zona1.setArrivaIzquierda(new Position(izquierda1,arriba1));
//		
//		Zona zona2 = new Zona(0);
//
//		zona2.setAbajoDerecha(new Position(derecha2,abajo2));
//		zona2.setAbajoIzquierda(new Position(izquierda2,abajo2));
//		zona2.setArribaDerecha(new Position(derecha2,arriba2));
//		zona2.setArrivaIzquierda(new Position(izquierda2,arriba2));
//		
//		Zona zona3 = new Zona(0);
//		int RADIO_ZONA = 5;
//		zona3.setAbajoDerecha(new Position(media.getX()+RADIO_ZONA,media.getY()-RADIO_ZONA));
//		zona3.setAbajoIzquierda(new Position(media.getX()-RADIO_ZONA,media.getY()-RADIO_ZONA));
//		zona3.setArribaDerecha(new Position(media.getX()+RADIO_ZONA,media.getY()+RADIO_ZONA));
//		zona3.setArrivaIzquierda(new Position(media.getX()-RADIO_ZONA,media.getY()+RADIO_ZONA));
//		
//		
//		for (int i = 0; i < rivales.length; i++) {
//			if(zona1.estaEnZona(rivales[i].getPosicion())
//					|| zona2.estaEnZona(rivales[i].getPosicion())
//							|| zona3.estaEnZona(rivales[i].getPosicion())){
//				rivalesZona.add(rivales[i]);
//			}
//		}
//		
//		if(rivalesZona.isEmpty()){
//			return true;
//		}else{
//			return false;
//		}
	
	}
	
	
	public static Pase calcularPosicionPase(Jugador jugador, Jugador [] jugadores ,Rival[] rivales, GameSituations sp){
		Pase resultado = null;
		Position pos=null;
		Jugador jugadorPase=null;
		double distancia = 1000;

		if(jugador.getRol()==Jugador.DEFENSA && jugador.getPosicion().getY()<-30){
			for (int i = 0; i < jugadores.length; i++) {			
				if(jugador.getId()!=jugadores[i].getId()){				
					if(jugadores[i].getRol()==Jugador.DELANTERO){
						if(i==jugadores.length-1){
							return new Pase(jugadores[i],jugadores[i].getPosicion().movePosition(-5, +5));
						}else{
							if(jugadores[i].getRivalesZona()==null || jugadores[i].getRivalesZona().length==0){
								return new Pase(jugadores[i],jugadores[i].getPosicion().movePosition(+5, +5));
							}
						}
					}
				}			
			}
		}
		
		ArrayList <Jugador> jugadoresSolos = new ArrayList <Jugador> ();
		
		for (int i = 0; i < jugadores.length; i++) {			
			Position posConducir = puedoConducir(jugadores[i], rivales);
			if(jugador.getId()!=jugadores[i].getId() 
					&& jugadores[i].getPosicion().getY()>jugador.getPosicion().getY() 
					&& posConducir!=null
					&& (jugadores[i].getRivalesZona()==null 
							|| (jugadores[i].getRivalesZona()!=null && jugadores[i].getRivalesZona().length==0))){				
				return new Pase(jugadores[i], jugadores[i].getPosicion().movePosition(0, +5));
			}			
		}
		
		
		for (int i = 0; i < jugadores.length; i++) {			
			if(jugador.getId()!=jugadores[i].getId()){				
				if((jugadores[i].getRivalesZona()==null 
						|| (jugadores[i].getRivalesZona()!=null && jugadores[i].getRivalesZona().length==0))
						&& comprobarRival(jugador.getPosicion(), jugadores[i].getPosicion().movePosition(0, +5), rivales)){
					jugadoresSolos.add(jugadores[i]);
				}
			}			
		}
		
		double dis;
		for (int i = 0; i < jugadoresSolos.size(); i++) {
			
			dis = jugadoresSolos.get(i).getPosicion().distance(Constants.centroArcoSup);
			
			if(dis<distancia 
					&& jugadoresSolos.get(i).getPosicion().getY()>=jugador.getPosicion().getY()
					&& comprobarRival(jugador.getPosicion(), jugadoresSolos.get(i).getPosicion().movePosition(0, +5), rivales)){
				distancia=dis;
				jugadorPase = jugadoresSolos.get(i);
				pos=jugadorPase.getPosicion();
			}
			
		}
		
		if(jugadorPase!=null && pos!=null){
			return new Pase(jugadorPase, pos.movePosition(0, +5));
		}
		
		for (int i = 0; i < jugadores.length; i++) {			
			if(jugador.getId()!=jugadores[i].getId()){				
				if(jugadores[i].getRol()==Jugador.DELANTERO){
					if(i==jugadores.length-1){
						return new Pase(jugadores[i],jugadores[i].getPosicion().movePosition(-5, +5));
					}else{
						if(jugadores[i].getRivalesZona()==null || jugadores[i].getRivalesZona().length==0){
							return new Pase(jugadores[i],jugadores[i].getPosicion().movePosition(+5, +5));
						}
					}
				}
			}			
		}

		return resultado;
		
	}
	
	public static CommandHitBall calcularTiro(Jugador jugador, Jugador [] jugadores ,Rival[] rivales, GameSituations sp){
		CommandHitBall resultado = null;
		double movePos = 3;
		double angulo = 15;
		
		
		Jugador masAdelante = TacticasUtil.calcularMasCercano(Constants.centroArcoSup, jugadores);
		
		if(masAdelante.getId()==jugador.getId() 
				&& jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE-20){
			resultado = new CommandHitBall(jugador.getId(),Constants.centroArcoSup.movePosition(0, 0), 1, 12);
		}
		
		if(jugador.getRol()==Jugador.DELANTERO){
			if(jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE-15
					&& !(jugador.getPosicion().getX()<-35) && !(jugador.getPosicion().getX()>35)){
				
				
				
				if(jugador.getPosicion().getX()<-10){
					
					if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = -2;
						angulo = 13;
					}else if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-(Constants.ANCHO_AREA_GRANDE/2)
							&& jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = -1.5;
						angulo = 10;
					}else{
						movePos = -1;
						angulo =6;
					}
					
					resultado = new CommandHitBall(jugador.getId(),Constants.posteDerArcoSup.movePosition(movePos, 0), 1, angulo);
					
				}else if(jugador.getPosicion().getX()>10){
					
					if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = 2;
						angulo = 14;
					}else if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-(Constants.ANCHO_AREA_GRANDE/2)
							&& jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = 1.5;
						angulo = 10;
					}else{
						movePos = 1;
						angulo = 6;
					}
					
					
					resultado = new CommandHitBall(jugador.getId(),Constants.posteIzqArcoSup.movePosition(movePos, 0), 1, angulo);
				}else{
					
					if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = 2;
						angulo = 14;
					}else if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-(Constants.ANCHO_AREA_GRANDE/2)
							&& jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
						movePos = 1.5;
						angulo = 5;
					}else{
						movePos = 1;
						angulo = 6;
					}
					
					Rival portero = getPortero(rivales, sp);

					if(portero.getPosicion().getX()>0){
						resultado = new CommandHitBall(jugador.getId(),Constants.posteIzqArcoSup.movePosition(+movePos, 0), 1, angulo);
					}else{
						resultado = new CommandHitBall(jugador.getId(),Constants.posteDerArcoSup.movePosition(-movePos, 0), 1, angulo);
					}
				}

			}
		}else{
			if(jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE-5){
				Rival portero = getPortero(rivales, sp);

				if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
					movePos = 3.5;
					angulo = 12;
				}else if(jugador.getPosicion().getY()<Constants.centroArcoSup.getY()-(Constants.ANCHO_AREA_GRANDE/2)
						&& jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
					movePos = 2.5;
					angulo = 10;
				}else{
					movePos = 2;
					angulo = 6;
				}
				
				if(portero.getPosicion().getX()>0){
					resultado = new CommandHitBall(jugador.getId(),Constants.posteIzqArcoSup.movePosition(+movePos, 0), 1, angulo);
				}else{
					resultado = new CommandHitBall(jugador.getId(),Constants.posteDerArcoSup.movePosition(-movePos, 0), 1, angulo);
				}
				//resultado = new CommandHitBall(jugador.getId(),Constants.centroArcoSup.movePosition(+3, 0), 1, 15);
			}
		}
		
		
		
		

		return resultado;
		
	}
	
	public static Rival getPortero(Rival [] rivales, GameSituations sp){
		PlayerDetail [] detail = sp.rivalPlayersDetail();
		for (int i = 0; i < detail.length; i++) {
			if(detail[i].isGoalKeeper()){
				return rivales[i];
			}
		}
		
		return null;
	}
	
	public static Rival [] obtenerRivalesZona(Jugador jugador ,Rival[] rivales){
		
		ArrayList <Rival> rivalesZona = new ArrayList<Rival>();
		
		Zona zona = new Zona(0);
		int RADIO_ZONA = 13;
		zona.setAbajoDerecha(new Position(jugador.getPosicion().getX()+RADIO_ZONA,jugador.getPosicion().getY()-RADIO_ZONA));
		zona.setAbajoIzquierda(new Position(jugador.getPosicion().getX()-RADIO_ZONA,jugador.getPosicion().getY()-RADIO_ZONA));
		zona.setArribaDerecha(new Position(jugador.getPosicion().getX()+RADIO_ZONA,jugador.getPosicion().getY()+RADIO_ZONA));
		zona.setArrivaIzquierda(new Position(jugador.getPosicion().getX()-RADIO_ZONA,jugador.getPosicion().getY()+RADIO_ZONA));
		
		for (int i = 0; i < rivales.length; i++) {
			if(zona.estaEnZona(rivales[i].getPosicion())){
				rivalesZona.add(rivales[i]);
			}
		}
		
		Rival[] resultado = null;
		
		if(!rivalesZona.isEmpty()){
			
			resultado = new Rival [rivalesZona.size()];
			for (int i = 0; i < rivalesZona.size(); i++) {
				resultado [i] = rivalesZona.get(i);
			}
			
		}
		
		
		return resultado;
	}
	
	public static Position puedoConducir(Jugador jugador, Rival[] rivales){
		
		ArrayList <Rival> rivalesZona = new ArrayList<Rival>();
		
		if(jugador.getPosicion().getY()>Constants.centroArcoSup.getY()-Constants.ANCHO_AREA_GRANDE){
			return null;
		}
		
		Zona zona = new Zona(0);
		int RADIO_X = 5;
		int RADIO_Y = 13;
		int ATRAS = 3;
		zona.setAbajoDerecha(new Position(jugador.getPosicion().getX()+RADIO_X,jugador.getPosicion().getY()-ATRAS));
		zona.setAbajoIzquierda(new Position(jugador.getPosicion().getX()-RADIO_X,jugador.getPosicion().getY()-ATRAS));
		zona.setArribaDerecha(new Position(jugador.getPosicion().getX()+RADIO_X,jugador.getPosicion().getY()+RADIO_Y));
		zona.setArrivaIzquierda(new Position(jugador.getPosicion().getX()-RADIO_X,jugador.getPosicion().getY()+RADIO_Y));
		
		for (int i = 0; i < rivales.length; i++) {
			if(zona.estaEnZona(rivales[i].getPosicion())){
				rivalesZona.add(rivales[i]);
			}
		}

		if(rivalesZona.isEmpty()){
			
			return jugador.getPosicion().movePosition(0, 25);
			
		}
		
		
		return null;
	}
	
	public static double calcularPotencia(Jugador jugador, GameSituations sp){
		double resultado = 0.01;
		double aux;
		double velocidad = Constants.getVelocidadRemate(0.35);
		boolean encontrado = false;
		
		while (!encontrado){
			aux=Constants.getVelocidadRemate(resultado) * sp.getMyPlayerPower(jugador.getId());
			if(aux>=velocidad){
				encontrado=true;
			}else{
				resultado+=0.01;
			}
		}
		
		return resultado;
	}
	
	
//	public static Position calcularPosicionMasCercana(Jugador jugador, Rival [] rivales, GameSituations sp){
//		Position pos = null;
//		Position resultado = null;
//		double distancia;
//		double disRival;
//		Rival rival;
//		double metrosIter = Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()));
//		double metrosRival;
//		double llegoAntes = 0, antes;
//		for (double x = jugador.getPosicion().getX()-10; x < jugador.getPosicion().getX()+10; x=x+0.1) {
//			for (double y = jugador.getPosicion().getY()-10; y < jugador.getPosicion().getY()+10; y=y+0.1) {			
//				pos = new Position(x, y);
//				distancia = jugador.getPosicion().distance(pos);
//				rival = calcularRivalMasCercano(pos, rivales);
//				disRival = rival.getPosicion().distance(pos);
//				metrosRival = Constants.getVelocidad(sp.getRivalPlayerSpeed(rival.getId()));
//				
//				antes = (disRival/metrosRival)-(distancia/metrosIter);
//				
//				if(antes>0
//						&& antes>llegoAntes){
//					resultado = pos;
//					llegoAntes=antes;
//				}
//			}
//		}
//
//		//return resultado;
//		return jugador.getPosicion();
//	}
}
