package org.javahispano.javacup.tacticas.jvc2012.celta;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;

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
