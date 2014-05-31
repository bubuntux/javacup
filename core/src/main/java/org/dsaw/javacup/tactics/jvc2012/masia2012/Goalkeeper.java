package org.dsaw.javacup.tactics.jvc2012.masia2012;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.List;

public class Goalkeeper {

  private List<Command> commands;
  private final Analysis analysis;

  public Goalkeeper(List<Command> commands, Analysis analysis) {
    super();
    this.commands = commands;
    this.analysis = analysis;
  }

  public void execute() {
    Position position = calculateMove();
    commands.add(new CommandMoveTo(analysis.goalkeeperIndex, position));
    analysis.isGoalDefended =
        analysis.myPlayers[analysis.goalkeeperIndex].distance(position) <= Constants.VELOCIDAD_MAX;
  }

  private Position calculateMove() {
    if (analysis.isGoalKick) {
      return analysis.recoveryBallPosition;
    }
    if (analysis.attacking && analysis.playerClosestToBall == analysis.goalkeeperIndex) {
      if (!analysis.isRivalGoal) {
        return analysis.recoveryBallPosition;
      }
      Position best = analysis.recoveryBallPosition;
      int i = analysis.iterationToBall + 1;
      double height;
      double ballControlDistance;
      while (i < analysis.ballPositions.length && i < analysis.rivalIterationToBall && !analysis
          .isRivalGoal(analysis.ballPositions[i], analysis.ballZ[i], analysis.ballPositions[i - 1],
                       analysis.ballZ[i - 1])) {
        if (analysis.ballPositions[i].isInsideGameField(0)) {
          if (Math.abs(analysis.ballPositions[i].getX()) <= Constants.LARGO_AREA_GRANDE / 2
              && Math.abs(analysis.ballPositions[i].getY())
                 > Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE) {
            height = Constants.ALTO_ARCO;
            ballControlDistance = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
          } else {
            height = Constants.ALTURA_CONTROL_BALON;
            ballControlDistance = Constants.DISTANCIA_CONTROL_BALON;
          }
          if (height >= analysis.ballZ[i]) {
            int
                it =
                (int) Math.ceil(Math.max(0, analysis.myPlayers[analysis.goalkeeperIndex]
                                                .distance(analysis.ballPositions[i])
                                            - ballControlDistance) / Constants.VELOCIDAD_MAX);
            if (it <= i) {
              best = analysis.ballPositions[i];
            }
          }
          i++;
        } else {
          break;
        }
      }
      return best;
    }
    double
        ratio =
        Math.max(Math.ceil((analysis.rivalRecoveryBallPosition.distance(Constants.centroArcoInf))
                           / Constants.REMATE_VELOCIDAD_MAX) * Constants.VELOCIDAD_MAX,
                 Constants.LARGO_ARCO / 2);
    return Constants.centroArcoInf
        .moveAngle(Constants.centroArcoInf.angle(analysis.rivalRecoveryBallPosition), ratio,
                   Constants.centroArcoInf.distance(analysis.rivalRecoveryBallPosition));
  }
}
