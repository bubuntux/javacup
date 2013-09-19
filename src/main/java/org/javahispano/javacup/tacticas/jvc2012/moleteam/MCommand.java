/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.moleteam;

import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.util.Position;
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
