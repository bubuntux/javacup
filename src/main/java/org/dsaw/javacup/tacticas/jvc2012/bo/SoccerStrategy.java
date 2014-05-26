/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.bo;

import java.util.List;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;

/**
 *
 * @author jsebas
 */
public interface SoccerStrategy {
    
    public List<Command> makeStrategy();
    public void setCurrentGameSituations(GameSituations sp);
    
}
