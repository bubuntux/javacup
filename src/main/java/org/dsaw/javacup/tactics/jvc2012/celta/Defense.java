package org.dsaw.javacup.tactics.jvc2012.celta;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

/**
 * Generador de commandos en situaciones de defense.
 */
public class Defense extends BasicCommandGenerator {

	/**
	 * Devuelve la lista de commandos en función de la situación del partido
	 * @param sp 	Situación del partido
	 * @return Lista de comandos
	 */
	@Override
	public List<Command> getCommandList(GameSituations sp) {
		super.getCommandList(sp);
		List<Command> commands = new LinkedList<>();
		
		if(!sp.isRivalStarts() && !isGoalKick()) {
			commands.addAll(coverAttackers());
			commands.addAll(recoveryBall());
		}
		
		return commands;
	}
	
	/**
	 * Indica a los defensas que traten de recuperar el balón si tienen posibilidad.
	 * @return	Lista de comandos de movimiento
	 */
	protected List<Command> recoveryBall() {
		LinkedList<Command> commands = new LinkedList<>();
		int[] recoveryBall = sp.getRecoveryBall();
		// si es posible recuperar el balón
		if (recoveryBall.length > 1) {
			double[] posRecovery = sp.getTrajectory(recoveryBall[0]);
			for (int i = 1; i < recoveryBall.length; i++) {
				commands.add(new CommandMoveTo(recoveryBall[i], 
						new Position(posRecovery[0], posRecovery[1] )));				
			}
		}
		return commands;
		
	}
	
	/**
	 * Ordena a los defensas que cubran a los delanteros rivales.
	 * @return	Lista de comandos de movimiento
	 */
	protected List<Command> coverAttackers() {
		LinkedList<Command> commands = new LinkedList<>();
		List<Position> attackers = getRivalAttackers();
		for (Position pos : attackers) {			
			// obtenemos el defensor más cercano
			int def = pos.nearestIndex(sp.myPlayers());
			if (def > 0) { // vamos a dejar al portero en paz...
				commands.add(new CommandMoveTo(def, pos.moveAngle(pos.angle(sp.ballPosition()), 0.5)));
			}
		}
		return commands;
	}

	/**
	 * Devuelve una lista con la posición de los delanteros rivales.
	 * @return	Posiciones de los delanteros rivales
	 */
	protected List<Position> getRivalAttackers() {
		LinkedList<Position> attackers = new LinkedList<>();
		Position ball = sp.ballPosition();
		
		Position[] rivalPositions = sp.rivalPlayers();
		for (int i = 0; i < rivalPositions.length; i++) {
			Position pos = rivalPositions[i];
			if (pos.getY() < (ball.getY()) || isInsideOwnLargeArea(pos))				
				attackers.add(pos);
		}
		return attackers;
	}
	
}
