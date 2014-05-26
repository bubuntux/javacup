package org.dsaw.javacup.tactics.jvc2012.celta;

import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;

/** 
 * Interfaz para generadores de commandos  
 */
public interface CommandGenerator {

	/** 
	 * Devuelve la lista de commandos en función de la situación del partido
	 * @param sp 	Situación del partido
	 * @return Lista de comandos
	 */
	public List<Command> getCommandList(GameSituations sp);

}
