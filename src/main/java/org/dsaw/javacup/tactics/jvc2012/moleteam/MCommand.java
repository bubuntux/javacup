/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.moleteam;

import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.util.Position;
/**
 *
 * @author jlosarcos
 */
public abstract class MCommand {
    public Command command;
    public MConstants.CommandType commandType;
    public int playerIndex;
    public int destinationPlayerIndex;
    public double angle;
    public double power;
    public double angleZ;
    public Position destinationPosition;
  
}
