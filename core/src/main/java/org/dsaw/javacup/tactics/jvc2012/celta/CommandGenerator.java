package org.dsaw.javacup.tactics.jvc2012.celta;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.List;

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
