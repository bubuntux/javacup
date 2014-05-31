package org.dsaw.javacup.tactics.jvc2012.rchavarria;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.List;

public class Recoveries {

  public void moveClosestPlayerToBall(GameSituations sp, List<Command> commands) {
    int[] recoveryPlayers = sp.getRecoveryBall();

    if (recoveryPlayers.length > 1) {
      double[] ballCoordinates = sp.getTrajectory(recoveryPlayers[0]);
      Position ballPosition = new Position(ballCoordinates[0], ballCoordinates[1]);
      int
          closestInRecoveryPlayers =
          computeClosestPlayerButGoalkeeper(sp, recoveryPlayers, ballPosition);
      int closestPlayer = recoveryPlayers[closestInRecoveryPlayers + 1];
      commands.add(new CommandMoveTo(closestPlayer, ballPosition));
            
            /*System.out.println("trying to recover ball in " +
                                       "[" + ballPosition.getX() + ", " + ballPosition.getY() + "] " +
            		           "with player [id='" + closestPlayer + "']");*/
    }
  }

  /**
   * @return indice del jugador m�s cercano, quitando al portero
   */
  private int computeClosestPlayerButGoalkeeper(GameSituations sp, int[] recoveryPlayers,
                                                Position ballPosition) {
    Position[] recoveryPlayersPosition = new Position[recoveryPlayers.length - 1];
    for (int i = 1; i < recoveryPlayers.length; i++) {
      int player = recoveryPlayers[i];
      recoveryPlayersPosition[i - 1] = sp.myPlayers()[player];
    }
    return ballPosition.nearestIndex(recoveryPlayersPosition);
  }
}
