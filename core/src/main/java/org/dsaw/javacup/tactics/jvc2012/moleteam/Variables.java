/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.moleteam;

import java.util.LinkedList;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author jlosarcos
 */
public final class Variables {
    public static int ballInPlayerIndex = -1;
    public static int ballToPlayerIndex = -1;
    public static Position3D ballToEstimatedPosition3D = null;
    public static boolean ballIsOurs = false;
    public boolean canHandleBall = false;
    public static int[] rivalMatches = new int[]{-1,10,9,8,7,6,5,4,3,2,1};
    public Players myPlayers;
    public Players rivalPlayers;
    public GameSituations gs;
    public LinkedList<MCommand> mcommands = new LinkedList<>();
    public LinkedList<Command> finalCommands = new  LinkedList<>();
    public static boolean playerAloneEnabled = true;
    public Variables(GameSituations gs)
    {
        this.gs = gs;
        this.mcommands.clear();
       
        if(gs.iteration() == 1 || IsCenterStarts())
        {
            Variables.playerAloneEnabled  = true;
//            Variables.rivalMatches = new int[11];
//            Variables.rivalMatches[0] = -1;
//            int[] rivalPlayersIndexes = Constants.centroArcoInf.nearestIndexes(gs.rivalPlayers());
//            for(int i = 0; i<rivalPlayersIndexes.length - 1; i++)
//            {
//                Variables.rivalMatches[i+1] = rivalPlayersIndexes[i];
//            }
        }
        this.myPlayers = new Players(gs.myPlayers(), Variables.rivalMatches, false);
        this.rivalPlayers = new Players(gs.rivalPlayers(), true);
        
       
        int[] canKick = gs.canKick();
        int[] rivalCanKick = gs.rivalCanKick();
        for(int i: canKick)
        {
            this.myPlayers.GetPlayerByIndex(i).canKick = true;
        }
        for(int i: rivalCanKick)
        {
            this.rivalPlayers.GetPlayerByIndex(i).canKick = true;
        }
        Position ballPosition = gs.ballPosition();
        if(rivalCanKick.length == 0 && canKick.length != 0)
        {
            Variables.ballIsOurs = true;
            Variables.ballInPlayerIndex = canKick[0];
            if(Variables.ballInPlayerIndex == Variables.ballToPlayerIndex)
            {
                Variables.ballToPlayerIndex = -1;
            }
        }
        else if(rivalCanKick.length != 0 && canKick.length == 0)
        {
            Variables.ballIsOurs = false;
            Variables.ballInPlayerIndex = -1;
            Variables.ballToPlayerIndex = -1;
        }
        else if(rivalCanKick.length != 0 && canKick.length != 0)
        {
            Position nearestPlayerToBall = this.myPlayers.positions[canKick[0]];
            Position nearestRivalPlayerToBall = this.rivalPlayers.positions[rivalCanKick[0]];
            if(ballPosition.distance(nearestPlayerToBall) >= ballPosition.distance(nearestRivalPlayerToBall))
            {
                Variables.ballIsOurs = false;
                Variables.ballInPlayerIndex = -1;
                Variables.ballToPlayerIndex = -1;
            }
            else
            {
                Variables.ballIsOurs = true;
                Variables.ballInPlayerIndex = canKick[0];
                if(Variables.ballInPlayerIndex == Variables.ballToPlayerIndex)
                {
                    Variables.ballToPlayerIndex = -1;
                }
            }
        }
        else
        {
            Variables.ballInPlayerIndex = -1;
        }
    }
    public boolean IsCenterStarts()
    {
        if(gs.ballPosition().equals(Constants.centroCampoJuego))
        {
            if(gs.getTrajectory(2)[0] == 0 && gs.getTrajectory(2)[1] == 0)
            {
                return true;
            }
        }
        return false;
    }
    public void AddCommands(MCommand mcommand)
    {
        EnsureUniqueCommandsForPlayer(mcommand);
        this.mcommands.add(mcommand);
    }
    public void EnsureUniqueCommandsForPlayer(MCommand mcommand)
    {
        for(MCommand c : this.mcommands)
        {
            if(c.command.getPlayerIndex() == mcommand.command.getPlayerIndex() && c.command.getCommandType() == mcommand.command.getCommandType())
            {
                this.mcommands.remove(c);
                break;
            }
        }
    }
    public MCommand currentPlayerCommand(int playerIndex)
    {
        for(MCommand c : this.mcommands)
        {
            if(c.command.getPlayerIndex() == playerIndex)
            {
                return c;
            }
        }
        return null;
    }
    public boolean currentPlayerCommandIsForGo(int playerIndex)
    {
        for(MCommand c : this.mcommands)
        {
            if(c.command.getPlayerIndex() == playerIndex && c.commandType == MConstants.CommandType.Go)
            {
                return true;
            }
        }
        return false;
    }
    public boolean currentPlayerCommandIsForPass(int playerIndex)
    {
        for(MCommand c : this.mcommands)
        {
            if(c.command.getPlayerIndex() == playerIndex && c.commandType == MConstants.CommandType.Pass)
            {
                return true;
            }
        }
        return false;
    }
    public boolean currentPlayerCommandIsForShoot(int playerIndex)
    {
        for(MCommand c : this.mcommands)
        {
            if(c.command.getPlayerIndex() == playerIndex && c.commandType == MConstants.CommandType.Shoot)
            {
                return true;
            }
        }
        return false;
    }
    public LinkedList<Command> getCommands()
    {
        finalCommands.clear();
        for(MCommand mc : mcommands)
        {
            finalCommands.add(mc.command);
        }
        return finalCommands;
    }
}
