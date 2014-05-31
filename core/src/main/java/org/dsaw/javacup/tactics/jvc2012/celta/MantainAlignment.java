package org.dsaw.javacup.tactics.jvc2012.celta;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Generador de commandos para que el equipo mantenga la alineación, teniendo en cuenta la posición del balón.
 */
public class MantainAlignment extends BasicCommandGenerator {

	@Override
	public List<Command> getCommandList(GameSituations sp) {
		super.getCommandList(sp);
		List<Command> commands = new ArrayList<>();
		Position[] players = sp.myPlayers();
		double[] ball = sp.getTrajectory(5);
		for (int i = 1; i < players.length; i++) {
			Position newPosition =  Position.medium(Alignment.INITIAL[i], 
					new Position(ball[0], ball[1]));;
					
			// si estamos atacando (es decir, si los rivales no pueden tirar 
			//		y la trayectoria del balón es hacia el campo contrario)
			if (sp.rivalCanKick().length == 0 &&
					sp.getTrajectory(1)[1] > sp.getTrajectory(0)[1] ) {
				if (sp.myPlayers()[i].getY() < Alignment.INITIAL[i].getY())
					newPosition = Alignment.INITIAL[i];
			}
			// si es saque de puerta, todos hacia arriba
			if (isGoalKick()) {
				newPosition = Alignment.INITIAL[i].movePosition(0, 10);
			}
			
			newPosition.setInsideGameField();
			commands.add(new CommandMoveTo(i, newPosition));
		}
		return commands;
	}
	
}
