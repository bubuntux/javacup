/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.bo;

import java.util.ArrayList;
import java.util.List;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;

/**
 *
 * @author jsebas
 */
public class CornerStrategy implements SoccerStrategy {

    private GameSituations gameSituation;

    @Override
    public void setCurrentGameSituations(GameSituations gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public List<Command> makeStrategy() {
        List<Command> commands = new ArrayList<Command>();

        if (gameSituation.isStarts()
                && (gameSituation.ballPosition() == Constants.cornerSupDer
                || gameSituation.ballPosition() == Constants.cornerSupDer)) 
        {
            int jugador = gameSituation.ballPosition().nearestIndex(gameSituation.myPlayers());
            if (jugador != 10) {
                commands.add(new CommandHitBall(jugador, gameSituation.myPlayers()[10], 1, true));
            } else {
                commands.add(new CommandHitBall(jugador, Constants.centroArcoSup, 1, true));
            }
        }

        return commands;
    }
}
