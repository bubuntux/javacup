package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.movimientos;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.Common;

public class MovimientoABalon {

	private boolean intentandoDesplazarRival = false;
	
	public List<Command> correrHaciaBalon(GameSituations gs) {
		LinkedList<Command> comandos = new LinkedList<Command>();

		//Obtiene los datos de recuperacion del balon
		int[] recuperadores = getRecoveryBall(gs);
		int[] recuperadoresRival = getRivalRecoveryBall(gs);
		
		//Si existe posibilidad de recuperar el balon
		if(recuperadores.length > 0 && (recuperadoresRival.length == 0 || recuperadores[0] <= recuperadoresRival[0])) {
			//Obtiene las coordenadas del balon en el instante donde se puede recuperar el balon
			double[] posRecuperacion = gs.getTrajectory(recuperadores[0]);

			//Ordena a los jugadores recuperadores que se ubiquen en la posicion de recuperacion
			Position posicionFuturaBalon = new Position(posRecuperacion[0], posRecuperacion[1]);
//			Position posicionDestino = posicionFuturaBalon.moveAngle(posicionFuturaBalon.angle(gs.myPlayers()[recuperadores[1]]), Constants.DISTANCIA_CONTROL_BALON * 0.9);
//			if(gs.myPlayersDetail()[recuperadores[1]].isGoalKeeper())
//				posicionDestino = posicionFuturaBalon;
			Position posicionDestino = posicionFuturaBalon;
			
			comandos.add(new CommandMoveTo(recuperadores[1], posicionDestino));

//			desplazarRival(gs, recuperadores, recuperadoresRival, posicionFuturaBalon, posicionDestino, comandos);
		}
		//si el rival la va a controlar antes que yo, mejor intentar cubrirle
		else if(recuperadoresRival.length > 0 && (recuperadores.length == 0 || recuperadores[0] > recuperadoresRival[0])) {
			intentandoDesplazarRival = false; // por si la situacion de desplazamiento se va de madre
			double[] posRecuperacionRival = gs.getTrajectory(recuperadoresRival[0]);
			Position pos = new Position(posRecuperacionRival[0], posRecuperacionRival[1]);
			Position posicionDestino = pos.moveAngle(pos.angle(Constants.centroArcoInf), 2);
			// si hay un recuperador que pueda correr a la posicion de destino va el
			Integer indiceJugador = null;
			if(recuperadores.length > 0) {
				indiceJugador = recuperadores[1] >= 0 && recuperadores[1] <= 4 ? 5 : recuperadores[1];
			}
			// si no buscamos el jugador mas cercano a la posicion destino
			else {
				for(int i = 5; i < 11; i++)
					if(indiceJugador == null || posicionDestino.distance(gs.myPlayers()[i]) < posicionDestino.distance(gs.myPlayers()[indiceJugador]))
						indiceJugador = i;

			}
			comandos.add(new CommandMoveTo(indiceJugador, posicionDestino));
		}
		return comandos;
	}

    private void desplazarRival(GameSituations gs, int[] recuperadores, int[] recuperadoresRival, Position posicionFuturaBalon, Position posicionDestino, List<Command> comandos) {
		// si estamos el rival y yo colocados para recuperar el balon, y solo tengo un rival, intento sacarle
		// de la zona de control poniendome entre el y la posicion de intercepcion del balon
		try {
			if(intentandoDesplazarRival || 
					(gs.myPlayers()[recuperadores[1]].equals(posicionDestino) &&
					recuperadoresRival.length == 2 && gs.rivalPlayers()[recuperadoresRival[1]].distance(posicionFuturaBalon) <= Constants.DISTANCIA_CONTROL_BALON * 1.1)) {
				Position nuevaPosicionDestino = Position.medium(posicionFuturaBalon, gs.rivalPlayers()[recuperadoresRival[1]]);
				comandos.add(new CommandMoveTo(recuperadores[1], nuevaPosicionDestino));
				intentandoDesplazarRival = true;
				if(recuperadores[0] == 0)
					intentandoDesplazarRival = false;
			}
		} catch (Exception e) {
			intentandoDesplazarRival = false;
		}
	}

	private static int[] getRecoveryBall(GameSituations gs) {
        int it = 0;
        boolean found = false;
        double dist0, dist;
        int idxFound = -1;
        LinkedList<Double> founds = new LinkedList<Double>();
        while (!found) {
            double[] posBalon = gs.getTrajectory(it);
        	Position posicionBalon = new Position(posBalon[0], posBalon[1]);
            if (!posicionBalon.isInsideGameField(2)) {
                return new int[]{};
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < gs.myPlayersDetail().length; i++) {
                    if (posBalon[2] <= (gs.myPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	Position pJug = gs.myPlayers()[i];
                        dist0 = (double) it * Constants.getVelocidad(gs.myPlayersDetail()[i].getSpeed());
                        dist = pJug.distance(posicionBalon) - (gs.myPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        if (dist0 >= dist && it >= gs.iterationsToKick()[i]) {
                            found = true;
                            founds.add(dist);
                            founds.add((double) i);
                            idxFound = it;
                        }
                    }
                }
            }
            it++;
        }
        for (int i = 2; i < founds.size(); i = i + 2) {
            for (int j = 0; j < i; j = j + 2) {
                if (founds.get(i) < founds.get(j)) {
                    dist0 = founds.get(i);
                    dist = founds.get(i + 1);
                    founds.set(i, founds.get(j));
                    founds.set(i + 1, founds.get(j + 1));
                    founds.set(j, dist0);
                    founds.set(j + 1, dist);
                }
            }
        }
        for (int i = founds.size() - 1; i >= 0; i = i - 2) {
            founds.remove(i - 1);
        }
        founds.add(0, (double) idxFound);
        int[] result = new int[founds.size()];
        for (int i = 0; i < founds.size(); i++) {
            result[i] = (int) founds.get(i).doubleValue();
        }
        return result;
    }

    private static int[] getRivalRecoveryBall(GameSituations gs) {
        int it = 0;
        boolean found = false;
        double dist0, dist;
        int idxFound = -1;
        LinkedList<Double> founds = new LinkedList<Double>();
        while (!found) {
            double[] posBalon = gs.getTrajectory(it);
        	Position posicionBalon = new Position(posBalon[0], posBalon[1]);
            if (!posicionBalon.isInsideGameField(2)) {
                return new int[]{};
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < gs.rivalPlayersDetail().length; i++) {
                    if (posBalon[2] <= (gs.rivalPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaRival(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	Position pJug = gs.rivalPlayers()[i];
                        dist0 = (double) it * Constants.getVelocidad(gs.rivalPlayersDetail()[i].getSpeed());
                        dist = pJug.distance(posicionBalon) - (gs.rivalPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaRival(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        if (dist0 >= dist && it >= gs.rivalIterationsToKick()[i]) {
                            found = true;
                            founds.add(dist);
                            founds.add((double) i);
                            idxFound = it;
                        }
                    }
                }
            }
            it++;
        }
        for (int i = 2; i < founds.size(); i = i + 2) {
            for (int j = 0; j < i; j = j + 2) {
                if (founds.get(i) < founds.get(j)) {
                    dist0 = founds.get(i);
                    dist = founds.get(i + 1);
                    founds.set(i, founds.get(j));
                    founds.set(i + 1, founds.get(j + 1));
                    founds.set(j, dist0);
                    founds.set(j + 1, dist);
                }
            }
        }
        for (int i = founds.size() - 1; i >= 0; i = i - 2) {
            founds.remove(i - 1);
        }
        founds.add(0, (double) idxFound);
        int[] result = new int[founds.size()];
        for (int i = 0; i < founds.size(); i++) {
            result[i] = (int) founds.get(i).doubleValue();
        }
        return result;
    }

}
