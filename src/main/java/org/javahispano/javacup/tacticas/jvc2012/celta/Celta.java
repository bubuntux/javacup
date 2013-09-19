package org.javahispano.javacup.tacticas.jvc2012.celta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

/**
 * Táctica para el torneo JavaCup 2010.
 * Celta de Vigo 
 */
public class Celta implements Tactic {
	
	public static final boolean DEBUG_ATTACK = false;
	
	public static final boolean DEBUG_PASS = false;
	
	public static final boolean DEBUG_GOAL_KEEPER = false;

	/** Detalle de la táctica (equipación, características de jugadores, etc.) */
	protected TacticDetail tacticDetail;

	/** Generadores de commandos que serán utilizados en las iteraciones */
	protected Collection<CommandGenerator> commandGenerators;

	/**
	 * Construye la clase, indicando que generadores de commandos se van a emplear.
	 */
	public Celta() {
		commandGenerators = new ArrayList<CommandGenerator>();
		commandGenerators.add(new MantainAlignment());
		commandGenerators.add(new Defense());
		commandGenerators.add(new Attackerl());		
		commandGenerators.add(new GoalKeeper());
	}
	
	/**
	 * Devuelve el detalle de la táctica (equipación, características de jugadores, etc.).
	 * @return Detalle de la táctica
	 */
	public TacticDetail getDetail() {
		if (tacticDetail == null)
			tacticDetail = new TacticDetailImpl();
		return tacticDetail;
	}

	/**
	 * Devuelve un array con las posiciones de saque de centro (cuando sacamos nosotros).
	 */
	public Position[] getStartPositions(GameSituations sp) {
		return Alignment.START_POSITIONS;
	}

	/**
	 * Devuelve un array con las posiciones de saque de centro (cuando saca el rival).
	 * @return Array de posiciones 
	 */
	public Position[] getNoStartPositions(GameSituations sp) {
		return Alignment.NO_START_POSITIONS;
	}

	/**
	 * Devuelve la lista de comandos a ejecutar en la iteración.
	 * @return Lista de commandos 
	 */
	public List<Command> execute(GameSituations sp) {
		List<Command> commands = new ArrayList<Command>();
		for (CommandGenerator generator : commandGenerators)
			commands.addAll(generator.getCommandList(sp));
		return commands;
	}

}