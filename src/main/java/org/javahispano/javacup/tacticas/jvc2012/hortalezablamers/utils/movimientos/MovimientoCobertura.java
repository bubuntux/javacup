package org.javahispano.javacup.tacticas.jvc2012.hortalezablamers.utils.movimientos;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class MovimientoCobertura {

	protected HashMap<Integer, Integer> rivalesCubiertos;
	protected Vector<Integer> propiosCubridores;
	
	public MovimientoCobertura() {
		rivalesCubiertos = new HashMap<Integer, Integer>();
	}

	public List<Command> fijarCoberturas(GameSituations gs) {
	
		LinkedList<Command> comandos = new LinkedList<Command>();

		// ordeno los rivales que estan en mi campo en funcion de su distancia a mi porteria
		Vector<IndiceYDistancia> distanciaRivalesAPorteria = new Vector<IndiceYDistancia>();
		for(Integer i = 0; i < 11; i++) {
			if(gs.rivalPlayers()[i].getY() < 0)
				distanciaRivalesAPorteria.add(new IndiceYDistancia(i, gs.rivalPlayers()[i].distance(Constants.centroArcoInf)));
			// si no esta en mi campo lo saco de las asociaciones de cobertura
			else
				rivalesCubiertos.remove(i);
		}
		Collections.sort(distanciaRivalesAPorteria);
		
		// para cada rival busco mi jugador que mas cerca esta y le asocio para que le cubra
		// ordeno mis jugadores de su distancia al rival
		for(IndiceYDistancia iyd : distanciaRivalesAPorteria)
			if(!rivalesCubiertos.containsKey(iyd.getIndice())) {
				for(Integer i = 1; i < 11; i++)
					if(!rivalesCubiertos.containsValue(i)) {
						rivalesCubiertos.put(iyd.getIndice(), i);
						break;
					}
			}

		// para cada asociacion de cobertura
		for(Integer indiceRival : rivalesCubiertos.keySet()) {
			Position posicionRival = gs.rivalPlayers()[indiceRival];
			Position posicionCobertura = posicionRival.moveAngle(posicionRival.angle(Constants.centroArcoInf), 1);
			comandos.add(new CommandMoveTo(rivalesCubiertos.get(indiceRival), posicionCobertura));
		}
		
		return comandos;
	}

}
