/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.bo;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jsebas
 */
public class GoalkeeperStrategy implements SoccerStrategy {

  private GameSituations currentSituation;
  private GameInformation data;
  private int[] indexCentralCamps;
  private final int goalKeeperIndex;

  public GoalkeeperStrategy(int goalKeeperIndex, int[] indexCentralCamps) {
    data = new GameInformation();
    this.goalKeeperIndex = goalKeeperIndex;
    this.indexCentralCamps = indexCentralCamps;
  }

  @Override
  public List<Command> makeStrategy() {
    List<Command> commands = new ArrayList<>();

    Position pos = moveItTo();
    commands.add(new CommandMoveTo(goalKeeperIndex, pos));

    Random r = new Random();
    int[] playersCanKick = currentSituation.canKick();
    Position[] myPlayersPosition = currentSituation.myPlayers();

    for (int iCanKick : playersCanKick) {
      if (iCanKick == goalKeeperIndex) {
        commands.add(
            new CommandHitBall(iCanKick, myPlayersPosition[indexCentralCamps[r.nextInt(2)]], 1,
                               true));
      }
    }

    return commands;
  }

  private Position moveItTo() {

    Position result = null;

    if (currentSituation.isStarts() && !inArea(currentSituation.ballPosition())) {
      result = move(currentSituation.ballPosition(), Integer.MAX_VALUE);

    } else {

      int iterBallOpponent = getOpponentIterToBall();
      if (iterBallOpponent == -1) {
        iterBallOpponent = Integer.MAX_VALUE;
      }

      int i = 0;
      while (i < iterBallOpponent && result == null) {
        if (data.getBallPosition(i).isInsideGameField(0)) {

          boolean inArea = inArea(data.getBallPosition(i));
          double
              dist =
              currentSituation.myPlayers()[goalKeeperIndex].distance(data.getBallPosition(i));

          if (dist <= i * Constants.VELOCIDAD_MAX + Constants.DISTANCIA_CONTROL_BALON_PORTERO
              && data.getBallAltitude(i) <= (inArea ? Constants.ALTO_ARCO
                                                    : Constants.ALTURA_CONTROL_BALON)) {
            result = data.getBallPosition(i);
          }
        } else {
          result = move(data.getBallPosition(i).setInsideGameField(), Integer.MAX_VALUE);
        }
        i++;
      }

      if (result == null) {
        result = move(data.getBallPosition(iterBallOpponent), iterBallOpponent);
      }
    }

    return result;
  }

  private Position move(Position posBall, int iter) {
    Position
        pos =
        Constants.centroArcoInf
            .moveAngle(Constants.centroArcoInf.angle(posBall), Constants.LARGO_ARCO / 2,
                       Constants.centroArcoInf.distance(posBall));
    int
        iter1 =
        (int) Math.ceil((currentSituation.myPlayers()[goalKeeperIndex].distance(pos)
                         - Constants.DISTANCIA_CONTROL_BALON_PORTERO) / Constants.VELOCIDAD_MAX);
    int count = 0;
    while (iter < iter1
           && count < 5000) {//Se intenta reparar Loop infinito poniendo condicion count<5000
      count++;
      pos = pos.moveAngle(pos.angle(Constants.centroArcoInf), Constants.REMATE_VELOCIDAD_MAX);
      if (!pos.isInsideGameField(0)) {
        pos =
            pos.moveAngle(pos.angle(Constants.centroArcoInf), Constants.REMATE_VELOCIDAD_MAX,
                          pos.distance(Constants.centroArcoInf));
        break;
      }
      iter1 =
          (int) Math.ceil((currentSituation.myPlayers()[goalKeeperIndex].distance(pos)
                           - Constants.DISTANCIA_CONTROL_BALON_PORTERO) / Constants.VELOCIDAD_MAX);
    }
    return pos;
  }

  @Override
  public void setCurrentGameSituations(GameSituations sp) {
    this.currentSituation = sp;
    data.update(currentSituation);
  }

  private boolean inArea(Position pos) {
    boolean isOnArea = false;

    if (Math.abs(pos.getX()) <= Constants.LARGO_AREA_GRANDE / 2
        && pos.getY() <= Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_GRANDE) {
      isOnArea = true;
    }

    return isOnArea;
  }

  public int getOpponentIterToBall() {

    int it = 0;
    int opponentIterToBall = -1;
    double reachDistance, distanceToBall;

    PlayerDetail details[] = currentSituation.rivalPlayersDetail();
    Position[] rivalsPositions = currentSituation.rivalPlayers();
    Position ballPosition = data.getBallPosition(it);

    if (ballPosition.isInsideGameField(2)) {
      boolean found = false;

      while (it <= Constants.ITERACIONES && !found) {
        ballPosition = data.getBallPosition(it);

        if (data.getBallAltitude(it) <= Constants.ALTO_ARCO) {
          for (int i = 0; i < rivalsPositions.length; i++) {

            if (data.getBallAltitude(it) <= (details[i].isGoalKeeper() ? Constants.ALTO_ARCO
                                                                       : Constants.ALTURA_CONTROL_BALON)) {
              double playerVelocity = Constants.getVelocidad(details[i].getSpeed());
              reachDistance =
                  (double) it * playerVelocity + (details[i].isGoalKeeper()
                                                  ? Constants.DISTANCIA_CONTROL_BALON_PORTERO
                                                  : Constants.DISTANCIA_CONTROL_BALON);
              distanceToBall = rivalsPositions[i].distance(ballPosition);

              if (reachDistance >= distanceToBall) {
                found = true;
                opponentIterToBall = it;
              }
            }
          }
        }
        it++;
      }
    }

    return opponentIterToBall;
  }
}
