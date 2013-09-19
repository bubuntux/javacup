/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.bo;

import java.util.List;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;

/**
 *
 * @author jsebas
 */
public interface SoccerStrategy {
    
    public List<Command> makeStrategy();
    public void setCurrentGameSituations(GameSituations sp);
    
}
