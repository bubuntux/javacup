package org.dsaw.javacup.tacticas.jvc2012.mijarojos;

import java.util.LinkedList;

import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Anticipacion {
	
	private GameSituations sp;
	private Situacion sit;
	
	public Anticipacion(GameSituations sp){
		this.sp = sp;
		this.sit = new Situacion(sp);
	}
	
    public LinkedList<CommandMoveTo> execute(){

		LinkedList<CommandMoveTo> comandos = new LinkedList<>();
		
		Position[] misJugadores = sp.myPlayers();
		Position[] jugadoresRival = sp.rivalPlayers();
    	// Obtiene los datos de recuperacion del balon
		int[] recuperadores = sit.getRecoveryBall();
		// Obtiene los datos de recuperacion del balon
		int[] recuperadoresRival = sit.getRivalRecoveryBall();
		
		switch(sit.getActual()){
		
			// SAQUE DE BANDA (evitamos que saque el portero)
			case SacoBandaCorner:
				int nearestIndexExceptoPortero = sp.ballPosition()
					.nearestIndex(misJugadores, 0);
				comandos.add(new CommandMoveTo(
					nearestIndexExceptoPortero, sp.ballPosition()));
				break;		

			// SAQUE DE PUERTA
			case SacoPuerta:
			// IR A ROBARLA
			case LlegoAntes:
			case LlegamosALaVez:
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecupDouble = sp
						.getTrajectory(recuperadores[0]);

				if(recuperadores.length > 1){
					int indiceMenorY = recuperadores[1];
					// Recorre la lista de jugadores que pueden recuperar
					for (int i = 2; i < recuperadores.length; i++) {
						if(misJugadores[recuperadores[i]].getY() < 
								misJugadores[indiceMenorY].getY()){
							indiceMenorY = i;
						}
					}
					Position posRecup = new Position(posRecupDouble[0], posRecupDouble[1]);
					comandos.add(new CommandMoveTo(indiceMenorY,
							posRecup.moveAngle(posRecup.angle(this.sp.ballPosition()), Constants.DISTANCIA_CONTROL_BALON)));

				}
				break;
			case LlegaRivalAntes:
				//lista de los jugadores m�s cercanos a los que pueden recuperar del rival
				ListaIndices lstIndPaseRival = new ListaIndices(false);
				int n = 2;
				for(int i = 1; i < recuperadoresRival.length; i++){					
					int[] nearRecup = jugadoresRival[recuperadoresRival[i]].nearestIndexes(jugadoresRival);
					lstIndPaseRival.meter(nearRecup, n);
				}
				
				//lista de mis jugadores para ir extrayendo
				ListaIndices lstMisJugadores = new ListaIndices(true);
				//quitamos al portero
				lstMisJugadores.sacar(0);
				//lista de mis jugadores que presionar�n a los m�s cercanos que pueden recuperar del rival
				ListaIndices lstIndPase = new ListaIndices(false);
				for(Integer i : lstIndPaseRival){
					int indice = jugadoresRival[i.intValue()].nearestIndex(
							misJugadores, lstMisJugadores.toIntArray());

					if(indice >= 1){
						comandos.add(new CommandMoveTo(indice, jugadoresRival[i]));
						lstMisJugadores.meter(indice);
					}
				}
				///////////
				ListaIndices lstMisJugadoresAlHombre = new ListaIndices(false);
				lstMisJugadoresAlHombre.meter(0);
				int[] indicesAtacantes = Constants.centroArcoInf.nearestIndexes(jugadoresRival);
				int nCubrir = 3;
				int indice = -1;
				for(int i = 0; i < nCubrir; i++){
					indice = jugadoresRival[indicesAtacantes[i]].nearestIndex(
							misJugadores, lstMisJugadoresAlHombre.toIntArray());
					if(indice >= 1){
						comandos.add(new CommandMoveTo(indice, jugadoresRival[indicesAtacantes[i]]));
						lstMisJugadoresAlHombre.meter(indice);
					}
				}
				
		}
		
		return comandos;
    }
}
