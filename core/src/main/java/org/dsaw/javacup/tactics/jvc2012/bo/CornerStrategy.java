/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.bo;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
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
    List<Command> commands = new ArrayList<>();

    if (gameSituation.isStarts()
        && (gameSituation.ballPosition() == Constants.cornerSupDer
            || gameSituation.ballPosition() == Constants.cornerSupDer)) {
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
