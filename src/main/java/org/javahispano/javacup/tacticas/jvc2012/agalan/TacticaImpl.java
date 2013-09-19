package org.javahispano.javacup.tacticas.jvc2012.agalan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class TacticaImpl {

	GameSituations sp = null;
	Alineaciones alineaciones = null;
	Random r = new Random();
	int tipo = 1;
	static int inicioRetrasoSaquePorteria = -1;
	static int porteroRival = 0;
	List<Position> desmarques = new ArrayList<Position>();

	
	public TacticaImpl(GameSituations gameSituation) {
		sp = gameSituation;
		alineaciones = new Alineaciones();
		tipo = Constantes.ALINEACION_INICIAL;
//		getTipoAlineacion();
		setearPorteroRival();
	}
	
	public void setearPorteroRival(){
		int i=0;
		for (i=0;i<sp.rivalPlayersDetail().length && !sp.rivalPlayersDetail()[i].isGoalKeeper();i++);
		porteroRival = i;
		
	}
	
	public void getTipoAlineacion() {
		if ( sp.rivalGoals() > sp.myGoals() ) {
			tipo = Constantes.ALINEACION_INICIAL;	
		} else if (sp.rivalGoals() < sp.myGoals()
				&& Constants.ITERACIONES - sp.iteration() <= Constantes.NUMERO_INTERACIONES_RESTANTES_DEFENDER) {
			tipo = Constantes.ALINEACION_ULTRADEFENSIVO;
		} 
	}
	
	
	public void recuperarBalon( LinkedList<Command> comandos) {
		 //Obtiene los datos de recuperacion del ballPosition
        int[] recuperadores = sp.getRecoveryBall();
        //Si existe posibilidad de recuperar el ballPosition
        if (recuperadores.length > 1) {
        	if (isInsideAreaPropia(sp.ballPosition())){
        		double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        		comandos.add(new CommandMoveTo(recuperadores[1],new Position(posRecuperacion[0], posRecuperacion[1])));
        	} else {
        		double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        		if (recuperadores[1] != alineaciones.getPortero()){
        			comandos.add(new CommandMoveTo(recuperadores[1],new Position(posRecuperacion[0], posRecuperacion[1])));
        		} 
        	}
        }
	}
		
	public Position jugadorAdelantadoMasCercano (Position punto) {
		Position pos = null;
		Position posOptima = null;
		List<Position> jug = new ArrayList<Position>();
		// Obtenemos los jugadores adelantados
		int margen = 10;
		for(Position p : sp.myPlayers()){
			if (p.getY() > punto.getY() - margen && !p.equals(punto)){
				jug.add(p);
			}
		}
		
		// Obtenemos el que este m�s cerca
		double distancia = 0;
		double aux;
		for(Position p : jug){
			aux = p.distance(punto);
			if ( ( distancia == 0 || aux < distancia ) && !isInside(p, margen/2, margen/2, sp.rivalPlayers())){
				distancia = aux;
				posOptima = p;
			}
		}
		
		if (posOptima != null){
			pos = posOptima;
		} 
		
		return pos;
	}
	
	public boolean isSaqueCentro() {
		return sp.ballAltitude() == 0 && sp.ballPosition().getX() == 0 && sp.ballPosition().getX() == 0; 
	}
	
	public void situarEquipo(LinkedList<Command> comandos) {
        //Obtiene las posiciones de tus jugadores
        Position[] jugadores = sp.myPlayers();
        //Ordena a cada jugador que se ubique segun el parametro tipo
        for (int i = 0; i < jugadores.length -1; i++) {
        	comandos.add(new CommandMoveTo(i, alineaciones.getAlineacion(tipo)[i]));
        }
        // Situa cada una de las lineas
        situarPortero(comandos);
        comandosDefensa(comandos);
        comandosDelantera(comandos);
        comandosCentro(comandos);        
        recuperarBalon(comandos);
        conducir(comandos);
       	realizarSaques(comandos);
	}
	
	public void realizarSaques(LinkedList<Command> comandos) {
		if(sp.isStarts() && (sp.ballPosition()==Constants.cornerSupDer || sp.ballPosition()==Constants.cornerSupDer)){
	    	for (int i : sp.canKick()) {
	    		if (isInside(sp.myPlayers()[i], 5, 5, sp.rivalPlayers())) {
	    			int index = elegirMejorDelantero(i);
		    		Position dest = sp.myPlayers()[index];
			        comandos.add(new CommandHitBall(i,dest,1,true));
	    		}
	    	}
	    }
	}
	public void conducir(LinkedList<Command> comandos) {
		if (!sp.isStarts()){
			for (int i : sp.canKick()) {
//					if (alineaciones.getMedios().contains(i) || alineaciones.getDelanteros().contains(i)) {
				if (alineaciones.getExtremos().contains(i) || alineaciones.getDelanteros().contains(i)) {
					calcularSiguientePosicion(i, comandos);
				}
			}
		} else {
			for (int i : sp.canKick()) {
				if (!isInsideAreaPropia(sp.ballPosition()))
					if ( alineaciones.getDefensores().contains(i) || alineaciones.getMedios().contains(i) || alineaciones.getDelanteros().contains(i)) {
						calcularSiguientePosicion(i, comandos);
					}
			}
		}
	}		
	
	private Position calcularPosicionEnDiagonal (int cuadrante, Position jug,int margen) {
		Position position = null;
		// en diagonal
		switch (cuadrante) {
		case 0: // No hay rival cerca
			position = avanzaLineaRecta(jug, margen);
			break;
		case 1: //rival Arriba derecha
			position = new Position(jug.getX() - margen,jug.getY()+ margen);
			break;
		case 2: //rival Abajo derecha
//			position = new Position(jug.getX() - margen,jug.getY()+ margen);
			position = avanzaLineaRecta(jug, margen);
			break;
		case 3: // rival abajo izq
//			position = new Position(jug.getX() + margen,jug.getY()+ margen);
			position = avanzaLineaRecta(jug, margen);
			break;
		case 4: // rival Arriba izq
			position = new Position(jug.getX() + margen,jug.getY()+ margen);
			break;
		}

		if (position != null && !position.isInsideGameField(0)){ // Avanza en linea recta
			position = null;
		}
		return position;
	}
	
	/**
	 * Devuelve el cuadrante donde se encuentra el punto p respecto del punto de referencia.
	 * En el orden de las agujas del reloj empezando por Arriba derecha
	 * 
	 * @param referencia
	 * @param p  1 Arriba derecha 2 Abajo derecha 3 Abajo izquierda 4 Arriba izquierda
	 * @return
	 */
	private int situarRival(Position referencia, Position p, int margen){
		
		int resultado = 0; 
		if( referencia.distance(p) < margen /2 ){
			if ( p.getX() < referencia.getX() ) { // Sector izquierda
				if (p.getY() < referencia.getY()) { // Abajo
					resultado = 3;	
//					System.out.println(" abajo izquierda ");
				}else { // Arriba
					resultado = 4;
//					System.out.println(" arriba izquierda ");
				}
			} else { // Sector derecha
				if (p.getY() < referencia.getY()) { // Abajo
					resultado = 2;
//					System.out.println(" abajo derecha ");
				}else { // Arriba
					resultado = 1;
//					System.out.println(" arriba derecha ");
				}
			}
		}
		return resultado;
		
	}
	
	
	private void calcularSiguientePosicion(int jugador,LinkedList<Command> comandos) {
		Position p = null;
		Position jug = sp.myPlayers()[jugador];
		Position rival = null;
		int margen = 5;
		int [] rivalesCercanos = jug.nearestIndexes(sp.rivalPlayers());
		for (int i = 0; i < rivalesCercanos.length; i++ ){
			Position it = sp.rivalPlayers()[rivalesCercanos[i]];
			if (isInside(jug, margen,margen, it)) {
				rival = it;
//				System.out.println(" el rival mas cercano es : " + rivalesCercanos[i]);
				break;
			}
		}
		int posRival = -1;
		if ( rival == null){ // No hay rivales 
			p = avanzaLineaRecta(jug,margen);	
		}else if (rival != null && rival.distance(jug) > margen/2) {
			posRival = situarRival(jug, rival,2*margen);
			p = calcularPosicionEnDiagonal(posRival, jug, margen);
		} else {
			p = null;
		}
		
		
		if (isInsideAreaContraria(sp.myPlayers()[jugador])) {
			if (alineaciones.getExtremos().contains(jugador) && !hayRivalesEnAreaContraria()){
				int index = elegirMejorDelantero(jugador);
				comandos.add(darPase(jugador, sp.myPlayers()[jugador], sp.myPlayers()[index]) );
			} else {
				comandos.add(tirarPuerta(jugador,sp.rivalPlayers()[porteroRival] ));
			}
		}
		else if (p != null) {
			double fuerza;
			if ( sp.myPlayersDetail()[jugador].getPower() == 1){
				fuerza = 0.3;
			} else if ( sp.myPlayersDetail()[jugador].getPower() == 0.75){
				fuerza = 0.4;
			} else {
				fuerza = 0.5;
			}			
			
			comandos.add(new CommandHitBall(jugador,p,fuerza,0));
			comandos.add(new CommandMoveTo(jugador,p));
		}

	}
	
	public int numRivalesZona ( Position p, int margen ) {
		int resultado = 0;
		int [] rivalesCercanos = p.nearestIndexes(sp.rivalPlayers());
		for (int i = 0; i < rivalesCercanos.length; i++ ){
			Position it = sp.rivalPlayers()[rivalesCercanos[i]];
			if (isInside(p, margen,margen, it)) {
				resultado++;
			}
		}
		return resultado;
	}
	
	public Position elegirJugadorMejorColocado( Position p){
		Position res = null;
		Position desmarque = elegirMejorDesmarque(p);
		Position adelantado = jugadorAdelantadoMasCercano(p);
		
		if (desmarque != null ) { 
			res = desmarque;
		} else if (adelantado != null ) { 
			res = adelantado;
		} else {
			res = sp.myPlayers()[9];
		}
		return res;
	}
	
	
	private Position avanzaLineaRecta(Position jug, int margen){
		Position p = null; 
			
		if ( (-1)*Constants.esqSupIzqCampoJuego.getY() - margen < jug.getY() && jug.getX() <  (-1)*Constants.esqSupIzqCampoJuego.getY()){
			// Estoy en la linea de fondo del rival
			if ( jug.getX() < 0) { // Si estoy en la izquierda
				p = new Position(jug.getX() + margen,jug.getY());
			} else { // Si estoy en la derecha
				p = new Position(jug.getX() - margen,jug.getY());
			}
		} else if ( Constants.esqSupIzqCampoJuego.getX() - margen < jug.getX() && jug.getX() < Constants.centroArcoSup.getX()
				&& jug.getY() > Constants.centroArcoSup.getY() * 2/3  ){
			// Estoy en la banda derecha 
			p = new Position(jug.getX() + margen,jug.getY() + margen);
		} else if ( Constants.centroArcoSup.getX() < jug.getX()  && jug.getX() < -Constants.esqSupIzqCampoJuego.getX() - margen 
				&& jug.getY() > Constants.centroArcoSup.getY() * 2/3  ){
			// Estoy en la banda izquierda
			p = new Position(jug.getX() - margen,jug.getY() + margen);
		} else {
			// SAQUE DE BANDA ??
			if ( sp.isStarts() && Constants.esqSupIzqCampoJuego.getX() < jug.getX() && jug.getX() < Constants.centroArcoSup.getX() ) {
				p = new Position(jug.getX() + margen,jug.getY() + margen);	
			} else if (sp.isStarts() && Constants.centroArcoSup.getX() < jug.getX()  && jug.getX() < -Constants.esqSupIzqCampoJuego.getX()  ) {
				p = new Position(jug.getX() - margen,jug.getY() + margen);	
			} else {
				// Recto hacia la porteria rival
				p = new Position(jug.getX(),jug.getY() + margen);
			}
		}		
		
		if (p != null && !p.isInsideGameField(0)){ // Avanza en linea recta
			p = null;
		}
		
		return p;
	}
	
	private boolean isInside(Position referencia, int margenX, int margenY, Position p){
		
		double maxX = referencia.getX() + margenX;
		double minX = referencia.getX() - margenX;
		double maxY = referencia.getY() + margenY;
		double minY = referencia.getY() - margenY;
		
		return minX < p.getX() && p.getX() < maxX && minY < p.getY() && p.getY() < maxY;
		
	}
	
	private boolean isInside(Position referencia, int margenX, int margenY, Position [] pos){
		
		double maxX = referencia.getX() + margenX;
		double minX = referencia.getX() - margenX;
		double maxY = referencia.getY() + margenY;
		double minY = referencia.getY() - margenY;
		boolean resultado = false;
		
		for(Position p : pos){
			resultado = resultado || ( minX < p.getX() && p.getX() < maxX && minY < p.getY() && p.getY() < maxY);
		}
		
		return resultado;
		
	}
	
	public void situarPortero(LinkedList<Command> comandos) {
        double [] pos;
        Position posBalon = sp.ballPosition();
        Position posP = Constants.centroArcoInf;
        int iteracion = 0;
        int randon = r.nextInt(1);
        if ( !sp.isStarts()){
        	inicioRetrasoSaquePorteria = -1;
        }
        
        if (sp.isStarts() && isInsideAreaPropia(posBalon)){
        	if (inicioRetrasoSaquePorteria == -1){
        		comandos.add(new CommandMoveTo(alineaciones.getPortero(),posBalon));
        		inicioRetrasoSaquePorteria = 0;
        	} else if (inicioRetrasoSaquePorteria  == Constants.ITERACIONES_SAQUE ) {
        		comandos.add(despejar(alineaciones.getPortero(), sp.myPlayers()[(Integer)alineaciones.getExtremos().get(randon)]));
        		inicioRetrasoSaquePorteria = -1;
        	} else {
        		inicioRetrasoSaquePorteria++;
        	}
        }  else {
	        for (int i=0;i<Constants.ITERACIONES; i++){
	        	pos = sp.getTrajectory(i);
	        	
	        	if ( Constants.posteIzqArcoInf.getX() < pos[0] 
	        	        && pos[0] < Constants.posteDerArcoInf.getX() 
	        			&& pos[2] < Constants.ALTO_ARCO 
	        			&& pos[1] <= posP.getY() ) {
	        		iteracion = i;
	        		break;
	        	}
	        }

	        if (iteracion != 0){
	        	double[] posIt = sp.getTrajectory(iteracion-1);
	        	int cont = 0;
	        	for (int i=iteracion-1; i > 0; i--){
		        	pos = sp.getTrajectory(i);
		        	if ( pos[1] < posP.getY()) {
		        		posIt = sp.getTrajectory(i);
		        	}
		        	cont++;
	        	}
//	        	System.out.println("############### CONT " + cont);
	        	Position p = new Position(posIt[0],posIt[1]);
	        	if (!p.isInsideGameField(0)){
	        		p = new Position(posIt[0],posP.getY());
	        	}
	        	double ang = p.angle(posBalon);
        		p.moveAngle(ang,0);
	        	comandos.add(new CommandMoveTo(alineaciones.getPortero(),p));
	        } else {
	        	Position centroP = new Position(Constants.centroArcoInf.getX(),Constants.centroArcoInf.getY() - 1);
	            double ang = centroP.angle(posBalon);
	            Position p = centroP.moveAngle(ang, 4);
	            p.movePosition(posBalon.getX()/6,0);
	        	        	
	        	comandos.add(new CommandMoveTo(alineaciones.getPortero(),p));
	        }
        }
	}
	
	public Position getPosicionBalon(int it) {
		return new Position(sp.getTrajectory(it)[0],sp.getTrajectory(it)[1]);
	}
	
	public void comandosDefensa( LinkedList<Command> comandos) {
		Position[] jugadores = sp.myPlayers();
		int index = r.nextInt(1);
		// Busco los rivales mas cercano a mi portero		
		int [] pos = jugadores[alineaciones.getPortero()].nearestIndexes(sp.rivalPlayers());
		Position rivalMasAdelantado = null;
		int jugador = 0;
		double distanciaMinima = 0, distancia = 0;
		Integer indice;
		int idRemove = 0;
		
		List<Integer> aux = new ArrayList<Integer>();
		for (int j=0;j<alineaciones.getDefensores().size();j++){
			aux.add((Integer) alineaciones.getDefensores().get(j));
		}
		
		for (int j=0;j<alineaciones.getDefensores().size();j++){
			distanciaMinima = 99999999;
			rivalMasAdelantado = sp.rivalPlayers()[pos[j]];
			for ( int i=0;i < aux.size();i++){
				indice = (Integer) aux.get(i);
				distancia = rivalMasAdelantado.distance(sp.myPlayers()[indice]);
				if ( distancia < distanciaMinima ) {
					distanciaMinima = distancia;
					jugador = indice;
					idRemove = i;
				}
			}
			comandos.add(new CommandMoveTo(jugador,rivalMasAdelantado.movePosition(0, -1)));
			aux.remove(idRemove);
		}
		
		

		for (int i : sp.canKick()) {
			if ( ( i == alineaciones.getPortero()  && sp.isStarts() && isInsideAreaPropia(sp.ballPosition()) && inicioRetrasoSaquePorteria  == Constants.ITERACIONES_SAQUE -2)
					|| (i == alineaciones.getPortero() && !sp.isStarts()) || alineaciones.getDefensores().contains(i)) {
				comandos.add(despejar(i,jugadores[(Integer)alineaciones.getExtremos().get(index)]));
				break;
			}
		}		


	}
	
	public void comandosCentro( LinkedList<Command> comandos) {
		Position nuevaPos = null;
		int margen = 10;
		for (int j=0;j<sp.myPlayers().length;j++){
			if (alineaciones.getExtremos().contains(j) && sp.ballPosition().getY() > 0){
				nuevaPos = new Position(sp.myPlayers()[j].getX(), sp.myPlayers()[j].getY() + margen);
				if (isInside(sp.myPlayers()[j], margen, margen * 2, sp.rivalPlayers()) 
						&& nuevaPos.isInsideGameField(0)){
					nuevaPos = new Position(sp.myPlayers()[j].getX(), sp.myPlayers()[j].getY() + margen);
					comandos.add(new CommandMoveTo(j,nuevaPos));
					desmarques.add(nuevaPos);
				}
			} else if ( alineaciones.getMedios().contains(j)){
//					nuevaPos = sp.myPlayers()[j];
//					comandos.add(darPase(j, nuevaPos, elegirJugadorMejorColocado(nuevaPos)));
//					cont++;
			}
		}
		
		for (int i : sp.canKick()) {
			if (alineaciones.getMedios().contains(i)) {
				if (alineaciones.getExtremos().contains(i)) {
					int index = elegirMejorDelantero(i);
					Position jugadorDestino = sp.myPlayers()[index];
					comandos.add(darPase(i, sp.myPlayers()[i], jugadorDestino));
					break;
				} else if (desmarques.isEmpty()){
					Position jugadorDestino = elegirJugadorMejorColocado(sp.myPlayers()[i]);
		            comandos.add(darPase(i, sp.myPlayers()[i], jugadorDestino));
		            break;
				}else {
					Position p = desmarques.get(r.nextInt(desmarques.size()));
		            comandos.add(darPase(i, sp.myPlayers()[i], p));
		            break;
				}
	        }
	    }
	}
	
	public int elegirMejorDelantero(int j) {
		int index = 9;
		boolean res = false;
		for( int i=0; i < alineaciones.getDelanteros().size() && !res; i++ ){
			if ( i !=j ) {
				index = (Integer)alineaciones.getDelanteros().get(i);
				res = isInsideAreaContraria(sp.myPlayers()[index]) && isInside(sp.myPlayers()[index], 3, 3, sp.rivalPlayers());
			}
		}	
		
		return index;
	}
	

	public Position elegirMejorDesmarque(Position pasador) {
		Position mejor = null;
		Double minima = null;
		Double dist;
		for ( Position p : desmarques ) {
			dist = p.distance(pasador);
			if ( minima == null ) minima = dist;
			if ( !isInside(p, 5,5, sp.rivalPlayers()) && dist.doubleValue() <= minima.doubleValue()) {
				mejor = p;
				minima = dist;
			}
		}
		
		return mejor;
	}
	
	public void comandosDelantera( LinkedList<Command> comandos) {
		Position posicionPorteroContrario = sp.rivalPlayers()[porteroRival];
		Position[] jugadores = sp.myPlayers();

//		Position [] nuevaPos = buscarZonaLibre(alineaciones.getDelanteros().size(),5);
//		int cont = 0;		
//		for (int j=0;j<sp.myPlayers().length;j++){
//			if ( alineaciones.getDelanteros().contains(j) ){
//				if ( nuevaPos[cont] != null) {
//					desmarques.add(nuevaPos[cont]);
//					comandos.add(new CommandMoveTo(j,nuevaPos[cont]));
//				}
//				cont++;
//			}
//		}
				
		for (int i : sp.canKick()) {
			if (alineaciones.getDelanteros().contains(i)) {
				if (isSaqueCentro()){
					int extremo = (Integer)alineaciones.getExtremos().get(r.nextInt(2));
					comandos.add(new CommandHitBall(i,jugadores[extremo], 1, 0));
				}else {
					if ( isInsideAreaContraria(jugadores[i]) || i == 0 || !isInsideAreaPequeñaContraria(posicionPorteroContrario)) {
						comandos.add(tirarPuerta(i, posicionPorteroContrario));
					} else {
//						comandos.add(darPaseRaso(i, jugadores[i], jugadorAdelantadoMasCercano(jugadores[i])));
						comandos.add(darPase(i, jugadores[i], sp.myPlayers()[elegirMejorDelantero(i)]));
					}
				}
				break;
			}
		}
		
		
	}
	
	public Position[] buscarZonaLibre(int num,int margen) {
	Position rival = null;
	Position[] resultado = new Position[num];
	Position ref = null;
	Position aux = null;

	int cont =0;
	
	int x = (int) -Constants.ANCHO_CAMPO_JUEGO / 2 + margen;
	int maxX = (int) Constants.ANCHO_CAMPO_JUEGO / 2 - margen;
	int y = (int) Constants.LARGO_CAMPO_JUEGO / 2 + margen;
	int maxY = (int) Constants.LARGO_CAMPO_JUEGO / 2 - margen;
	
	boolean encontrado = false;
	
	for (int i = x; i < maxX && cont<num; i++) {
		encontrado = false;
		for (int j = y; j < maxY && cont<num; j++) {
			rival = null;
			ref = new Position( i , j);
			for (Position it : sp.rivalPlayers()){
				if (isInside(ref, margen,margen, it)) {
					rival = it;
					break;
				}
			}
			if (rival == null) { // No hay rivales
				encontrado = false;
				for( int z=0;z< sp.myPlayers().length && !encontrado;z++){
					aux = sp.myPlayers()[z];
					encontrado = isInside(ref, margen/2,margen/2, aux); 
				}
				for ( int z=0; z< resultado.length; z++  ) {
					aux = resultado[z];
					if (aux != null) {
						encontrado = isInside(ref, margen,margen, aux);
					}
				}
				if(!encontrado){
					resultado[cont] = ref;
					cont++;
					encontrado=true;
				}
			}
		}			
	}
	
	return resultado;
	}
	
	public boolean isInsideAreaPropia (Position p) {
		
		double mitadLargoArea = Constants.LARGO_AREA_GRANDE/2 ;		
		
		return 	(Math.abs(p.getX()) <= mitadLargoArea 
				&& p.getY() <= Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_GRANDE);
	}
	
	public boolean isInsideAreaContraria (Position p) {
		
		double mitadLargoArea = Constants.LARGO_AREA_GRANDE/2 ;		
		
		return 	(Math.abs(p.getX()) <= mitadLargoArea 
				&& p.getY() >= Constants.centroArcoSup.getY() - Constants.ANCHO_AREA_GRANDE);
	}
	
	public boolean isInsideAreaPequeñaContraria ( Position p ) {
		
		double mitadLargoArea = Constants.LARGO_AREA_CHICA/2 ;		
		
		return 	(Math.abs(p.getX()) <= mitadLargoArea 
				&& p.getY() >= Constants.centroArcoSup.getY() - Constants.ANCHO_AREA_GRANDE);
		
	}
	
	public CommandHitBall darPase(int jugadorPasador, Position posPasador, Position destino) {
		
		double [] res = calcularPase(jugadorPasador, posPasador, destino);
		CommandHitBall pase = new CommandHitBall(jugadorPasador,destino,res[0],res[1]);
		return pase;
	}
	
		  
	  public double [] calcularPase(int jugador,Position posAct,Position posObj){
		  double fuerza = 0;		  
		  double velocidad = 0;
		  double [] resultado = new double [2];
		  double angulo = 0;
		  boolean encontrado = false;
		  double distancia = posAct.distance(posObj);
		  for(fuerza=1 ;fuerza>=0.1 && !encontrado;fuerza-=0.1){
			  velocidad = Constants.getVelocidadRemate(fuerza);
			  angulo = getAngulo ( distancia,velocidad);
			  if ( angulo < Constants.ANGULO_VERTICAL_MAX) {
				  encontrado = true;
				  break;
				  
			  }
		  }

		  resultado[0] = encontrado ? fuerza : fuerza;
		  resultado[1] = encontrado ? angulo : 45;
	      return resultado;
	  }
  
	public double getAngulo (double distancia,double velocidad ) {
	  double sin = (distancia / Math.pow(velocidad, 2)) / 10;
	  double dobleAng = Math.asin( sin );
	  double angulo = Math.toDegrees(dobleAng) / 2;
	  return angulo;
	}
	  

	public CommandHitBall despejar(int jugadorPasador, Position destino) {
		CommandHitBall pase = new CommandHitBall(jugadorPasador, destino,1,45);
		return pase;
	}

	public boolean hayRivalesEnAreaContraria() {
		boolean res = true;
		for (Position it : sp.rivalPlayers()){
			if (!sp.rivalPlayers()[porteroRival].equals(it))
				res = res && isInsideAreaContraria(it);
		}		
		return res;
	}
	
	public Position buscarDelanteroDentroArea(int pasador) {
		Position p = null;
		Position pas = sp.myPlayers()[pasador];
		for (int j=0;j<sp.myPlayers().length && p == null;j++){
			if ( alineaciones.getDelanteros().contains(j) ){
				if ( isInsideAreaContraria(sp.myPlayers()[j]) && !(sp.myPlayers()[j]).equals(pas)) {
					p = sp.myPlayers()[j];
				}
			}
		}

		return p;
	}
	
	public CommandHitBall tirarPuerta(int j, Position porteroRival) {
		Position p = null;
		Position jug = sp.myPlayers()[j];
		CommandHitBall res;
//		p = buscarDelanteroDentroArea(j);
//		if ( !hayRivalesEnAreaContraria() && p != null && !isInsideAreaPequeñaContraria(porteroRival)) {
//			res = darPaseArea(j, jug, p);
//		}else {
			p = null;
			if ( jug.getX() < Constants.posteIzqArcoSup.getX() ) { // Estamos a la izquierda de la porteria
				p = calcularPtoTiro(j,jug,porteroRival,-1,-1);
			} else if ( Constants.posteIzqArcoSup.getX() < jug.getX() && jug.getX() < Constants.posteDerArcoSup.getX()) { // Centrados
				p = calcularPtoTiro(j,jug,porteroRival,1,-1);
			} else { // A la derecha
				p = calcularPtoTiro(j,jug,porteroRival,1,1);
			}
			
			double distancia = sp.myPlayers()[j].distance(p);
			double velocidad = Constants.getVelocidadRemate(1);
			double ang = getAngulo(distancia, velocidad);
			res = new CommandHitBall(j, p, 1, Double.isNaN(ang) ? 45 : ang);
//		}
		
		
		return res;
	}
	
	public Position calcularPtoTiro(int j,Position jug, Position porteroRival, int signo1,int signo2) {
		Position p = null;
		Position aux = new Position(jug.getX(),Constants.posteIzqArcoSup.getY());
		double ang = 0;
		double angGrados = 0;
		double angMasError = 0;
		double angMenosError = 0;
		double angError2 = sp.myPlayersDetail()[j].getPrecision()/100 * 180 / 2;
		double angError = Math.toDegrees(Constants.getErrorAngular(sp.myPlayersDetail()[j].getPrecision()));
		double angDelta = 0;
		double angOmega = 0;
		double dist =  jug.distance(aux);
		double h1 = 0;
		double h2 = 0;
		double x1 = 0;
		double x2 = 0;
		double p1 = 0;
		double p2 = 0;
		double distPortero = 0;
		double distPorteroAux = 0;
		Position pRivalIzq = new Position(porteroRival.getX() - Constants.DISTANCIA_CONTROL_BALON_PORTERO  , Constants.posteIzqArcoSup.getY());
		Position pRivalDer = new Position(porteroRival.getX() + Constants.DISTANCIA_CONTROL_BALON_PORTERO  , Constants.posteIzqArcoSup.getY());
		Position xResultado = null;
		
		if (pRivalDer.distance(Constants.posteDerArcoSup) > pRivalIzq.distance(Constants.posteIzqArcoSup)) {
			xResultado = Position.medium(pRivalDer,Constants.posteDerArcoSup);
			distPortero = xResultado.distance(pRivalIzq);
		} else {
			xResultado = Position.medium(pRivalIzq,Constants.posteIzqArcoSup);
			distPortero = xResultado.distance(pRivalDer);
		}
		
		for ( double i= Constants.posteIzqArcoSup.getX();i < Constants.posteDerArcoSup.getX();i= i + 0.25){
			p = new Position (i,Constants.posteIzqArcoSup.getY());
//			if (!( pRivalIzq.getX() < p.getX() &&  p.getX() < pRivalDer.getX() )){
				ang = jug.angle(p);	
				angGrados = Math.toDegrees(ang);
				angMasError = angGrados + angError;
				angMenosError = angGrados - angError;
				
				angDelta = Math.abs(90 + (signo1)*angMasError);
				angOmega = Math.abs(90 + (signo2)*angMenosError);
				
				h1 = Math.sqrt(Math.pow(dist, 2)/ (1- Math.pow(Math.sin(angDelta), 2)));
				h2 = Math.sqrt(Math.pow(dist, 2)/ (1- Math.pow(Math.sin(angOmega), 2)));
				
				x1 = h1 * Math.sin(angDelta);
				x2 = h2 * Math.sin(angOmega);
							
				p1 = jug.getX() +(signo1)*x1;
				p2 = jug.getX() +(signo1)*x2;
				
				if ( Constants.posteIzqArcoSup.getX() < p1 && p1 < Constants.posteDerArcoSup.getX() 
						&&  Constants.posteIzqArcoSup.getX() < p2 && p2 < Constants.posteDerArcoSup.getX() ) {
					
					distPorteroAux  = p.distance(porteroRival); 
					
					if ( xResultado == null || distPorteroAux > distPortero ){
						xResultado	= p;
						distPortero  = distPorteroAux; 
					}				
				}				
//			}
		}		
		
		return xResultado;
	}
}
