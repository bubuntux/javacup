package org.dsaw.javacup.tacticas.jvc2012.mijarojos;

import java.util.LinkedList;
import java.util.Random;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Chuts {
	private GameSituations sp;
	private Random r;
	private Position[] misJugadores;
	private Position[] rivales;
	private Position porteroRival;
	private static final Position ABAJO_IZQ = new Position(
			Constants.posteIzqArcoSup.getX() + 1,
			Constants.posteIzqArcoSup.getY());
	private static final Position ABAJO_DCHA = new Position(
			Constants.posteDerArcoSup.getX() - 1,
			Constants.posteDerArcoSup.getY());
	private Situacion sit;
	
	public Chuts(GameSituations sp){
		this.sp = sp;
		this.sit = new Situacion(this.sp);
		// Instancia un generador aleatorio
		this.r = new Random();
		int cont = 0;
		misJugadores = sp.myPlayers();
		rivales = sp.rivalPlayers();
		for(PlayerDetail pd : sp.rivalPlayersDetail()){
			if(pd.isGoalKeeper()){
				this.porteroRival = rivales[cont];
				break;
			}
			cont++;
		}
	}
	
	private CommandHitBall getMejorPase(int indexBalon){
		Position jugOrigen = this.misJugadores[indexBalon];
		//cuanto m�s atr�s estemos m�s cuidado habr� que tener con los pases
		final int factorPrincipal = 13;
		
		double factor = factorPrincipal - (((jugOrigen.getY() + 52.5) / Constants.LARGO_CAMPO_JUEGO) * 8.0);
		for(int i = 10; i > 0; i--){
			if(i == indexBalon){
				continue;
			}
			Position jugDestino = this.misJugadores[i];
			double distRival;
			double distComp;
			double dX = jugDestino.getX() - jugOrigen.getX();
			double dY = jugDestino.getY() - jugOrigen.getY();
			boolean porAlto = false;
			boolean peligro = false;
			Position posTmp = jugOrigen;
			final int DIV = 5;
			boolean[] peligros = new boolean[DIV];
			for(int j = 1; j <= DIV; j++){
				posTmp = new Position (jugOrigen.getX() + ((dX/DIV) * j), 
						jugOrigen.getY() + ((dY/DIV) * j));
				Position rivalCercano = this.rivales[posTmp.nearestIndex(this.rivales)];		
				Position compCercano = this.misJugadores[posTmp.nearestIndex(this.misJugadores)];	
				//hemos encontrado un rival cercano en la trayectoria del pase
				distRival = rivalCercano.distance(posTmp);
				distComp = compCercano.distance(posTmp);
				if(distRival < distComp && distRival < (factor * j)){
					peligros[j - 1] = true;
				}
			}
			if(peligros[0] || peligros[DIV - 1]){
				peligro = true;
				break;
			} else{
				for(int h = 1; h <= DIV - 1; h++){
					if(peligros[h]){
						porAlto = true;
						break;
					}
				}
			}
			if(!peligro && dY > -10){
				double fuerza = 1d;
				double distanciaPase = this.misJugadores[indexBalon].distance(jugDestino);
				if(distanciaPase < 30d){
					fuerza = distanciaPase / 30;
				}
				return new CommandHitBall(indexBalon, jugDestino, 
						fuerza, porAlto);
			}
		}
		return null;
	}
	
	private Position getDestinoTiro(){
		if(this.porteroRival.getX() > 0 || (this.porteroRival.getX() == 0 && this.sp.ballPosition().getX() < 0)){
			return new Position(Constants.posteIzqArcoSup.getX() + 1, Constants.posteIzqArcoSup.getY());
		} else {
			return new Position(Constants.posteDerArcoSup.getX() - 1, Constants.posteDerArcoSup.getY());
		} 
	}
	
	
	/**
	 * Comprueba si el jugador de mi equipo est� m�s adelantado que todos los rivales (excepto el portero)
	 * @param index
	 * @return
	 */
	private boolean esElUltimoConRivales(int index){
		PlayerDetail[] detallesRivales = this.sp.rivalPlayersDetail();
		for(int i = 0; i < rivales.length; i++){
			if(this.misJugadores[index].getY() < this.rivales[i].getY() &&
					detallesRivales[i].isGoalKeeper() == false){			
				return false;
			}
		}
		return true;
	}

	/**
	 * Comprueba si el jugador de mi equipo est� m�s adelantado que el resto de mis jugadores
	 * @param index
	 * @return
	 */
	private boolean esElMasAdelantado(int index){
		for(int i = 0; i < this.misJugadores.length; i++){
			if(this.misJugadores[index].getY() < this.misJugadores[i].getY()){			
				return false;
			}
		}
		return true;
	}
	
	public LinkedList<CommandHitBall> execute(){
		Position[] misJugadores = this.sp.myPlayers();
		LinkedList<CommandHitBall> comandos = new LinkedList<CommandHitBall>();
		// Recorre la lista de mis jugadores que pueden rematar
		for (int i : sp.canKick()) {
			if(this.esElUltimoConRivales(i)){
				if(this.misJugadores[i].getY() < Constants.LARGO_CAMPO_JUEGO / 4){
					comandos.add(new CommandHitBall(i));
				} else {
					comandos.add(new CommandHitBall(
							i, this.getDestinoTiro(), 1, Constants.ANGULO_VERTICAL_MAX / 6));
				}
			} else if(this.esElMasAdelantado(i) && sp.ballPosition().getY() > 0){
				comandos.add(new CommandHitBall(i, this.getDestinoTiro(),
						1, Constants.ANGULO_VERTICAL_MAX / 3));
			} else if (sp.ballPosition().getY() >			
					(Constants.LARGO_CAMPO_JUEGO / 4)){
				comandos.add(new CommandHitBall(i, this.getDestinoTiro(),
						1, Constants.ANGULO_VERTICAL_MAX / 6));
			} else if (this.sit.getActual() != SituacionActual.SacoBandaCorner){
				CommandHitBall mejorPase = this.getMejorPase(i);
				if(mejorPase != null){
					comandos.add(this.getMejorPase(i));
				} else {
					if(this.sp.ballPosition().getY() < 0)
					{
						Position cercano = rivales[this.sp.ballPosition().nearestIndex(rivales)];
						final int distPeligrosa = 10;
						if(i == 0 && this.sp.ballPosition().distance(cercano) < distPeligrosa){
							comandos.add(new CommandHitBall(i, 
									cercano.getX() < misJugadores[i].getX() ?
									Constants.cornerSupDer : Constants.cornerInfDer,
									1, Constants.ANGULO_VERTICAL_MAX / 2));
						} else {						
							comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
									1, Constants.ANGULO_VERTICAL_MAX / 2));
						}
					} else{
						comandos.add(new CommandHitBall(i, this.getDestinoTiro(),
								1, Constants.ANGULO_VERTICAL_MAX / 3));
					}						
				}				
			} else {			
				// inicia contador en cero
				int count = 0;
				int jugadorDestino;
				// Repetir mientras el jugador destino sea igual al jugador que
				// remata
				while (((jugadorDestino = r.nextInt(11)) == i
				// o mientras la coordenada y del jugador que remata
				// es mayor que la coordenada y del que recibe
						|| misJugadores[i].getY() > misJugadores[jugadorDestino]
								.getY())
						// Y mientras el contador es menor a 20
						&& count < 20) {
					// incrementa el contador
					count++;
				}
				// Si el receptor del balon es el que remata
				if (i == jugadorDestino) {
					while ((jugadorDestino = r.nextInt(misJugadores.length)) == i) {
					}
				}
				// Ordena que el jugador que puede rematar que de un pase
				// al jugador destino
				comandos.add(new CommandHitBall(i, misJugadores[jugadorDestino],
						1, r.nextInt(45)));
			}
		}
		return comandos;
	}
}
