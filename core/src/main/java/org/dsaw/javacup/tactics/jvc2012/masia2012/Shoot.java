package org.dsaw.javacup.tactics.jvc2012.masia2012;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoot {

  private List<Command> commands;
  private final Analysis analysis;

  public Shoot(List<Command> commands, Analysis analysis) {
    this.commands = commands;
    this.analysis = analysis;
  }

  ShootData execute(double power, double precision) {
    ShootData shootData = calculateShoot(power, precision, analysis.ball);
    double threshold = calculateThreshold(shootData.probability);
    if (shootData.probability >= threshold) {
      shoot(shootData);
    } else {
      shootData.probability = -1;
    }
    return shootData;
  }

  private void shoot(ShootData shootData) {
    for (int i = 0; i < analysis.canShoot.length; i++) {
      commands.add(new CommandHitBall(analysis.canShoot[i], shootData.angle * 180 / Math.PI, 1,
                                      shootData.verticalAngle * 180 / Math.PI));
    }
  }

  private List<Double> bestLongProbability = new ArrayList<>();
  private List<Double> bestMiddleProbability = new ArrayList<>();
  private List<Double> bestShortProbability = new ArrayList<>();

  private double calculateThreshold(double probability) {
    if (analysis.ball.distance(Constants.centroArcoSup) > 35) {
      return calculateThreshold(probability, 0.06, 0.04, bestLongProbability);
    }
    if (analysis.ball.distance(Constants.centroArcoSup) > 20) {
      return calculateThreshold(probability, 0.03, 0.02, bestMiddleProbability);
    }
    return calculateThreshold(probability, 0.02, 0.01, bestShortProbability);
  }

  private double calculateThreshold(double probability, double max,
                                    double min, List<Double> probabilities) {
    probability = Math.min(2 * max - min, Math.max(min, probability));
    probabilities.add(probability);
    if (probabilities.size() == 11) {
      Collections.sort(probabilities);
      probabilities.remove(0);
    }
    if (probabilities.size() > 1) {
      double avg = 0;
      for (int j = 0; j < probabilities.size(); j++) {
        avg += probabilities.get(j);
      }
      avg /= probabilities.size();
      return avg;
    }
    return max;
  }


  private ShootData calculateShoot(double power, double precision, Position ball) {
    double maxVerticalAngle = Constants.ANGULO_VERTICAL_MAX * Math.PI / 180;
    double verticalAngle = 0;
    double minAngle = ball.angle(Constants.posteDerArcoSup.movePosition(-Constants.RADIO_BALON, 0));
    double maxAngle = ball.angle(Constants.posteIzqArcoSup.movePosition(Constants.RADIO_BALON, 0));
    ShootData shoot = new ShootData(0, 0, 0, -1, 0);
    List<ShootData> shoots = new ArrayList<>();
    int errorAngle = (int) Math.floor(Constants.getErrorAngular(precision) * 90);
    double angle;
    while (verticalAngle <= maxVerticalAngle) {
      shoots.clear();
      angle = minAngle;
      while (angle <= maxAngle) {
        angle += Math.PI / 180;
        shoots.add(evaluateShoot(power, ball, angle, verticalAngle, precision, minAngle, maxAngle));
      }
      for (int i = 0; i < shoots.size(); i++) {
        shoot = shoot.compare(reevaluateShoot(i, shoots, errorAngle));
      }
      verticalAngle += Math.PI / 180;
    }
    return shoot;
  }

  private ShootData evaluateShoot(double power, Position ball, double angle, double verticalAngle,
                                  double precision, double minAngle, double maxAngle) {
    Trajectory
        trajectory =
        new Trajectory(ball, Constants.getVelocidadRemate(power), angle, verticalAngle);
    double probability = -1;
    double z = trajectory.z[trajectory.length - 1];
    for (int i = 0; i < trajectory.length; i++) {
      if (i == 0) {
        probability *=
            calculateProbability(trajectory.positions[i], trajectory.z[i], i + 1, ball, 0,
                                 precision);
      } else {
        probability *=
            calculateProbability(trajectory.positions[i], trajectory.z[i], i + 1,
                                 trajectory.positions[i - 1], trajectory.z[i - 1], precision);
      }
      if (probability >= 0) {
        z = trajectory.z[i];
        break;
      }

    }
    double angleRange = Math.min(Math.abs(angle - minAngle), Math.abs(maxAngle - angle));
    return new ShootData(angle, verticalAngle, z, probability,
                         Math.min(angleRange / (Constants.getErrorAngular(precision) * Math.PI / 2),
                                  1));
  }

  private ShootData reevaluateShoot(int i, List<ShootData> shoots, int errorAngle) {
    double probability = 0;
    for (int j = Math.max(i - errorAngle, 0); j <= Math.min(i + errorAngle, shoots.size() - 1);
         j++) {
      probability += shoots.get(j).probability;
    }
    probability /= (2 * errorAngle + 1);
    return new ShootData(shoots.get(i).angle, shoots.get(i).verticalAngle, shoots.get(i).z,
                         probability, shoots.get(i).shootOnGoalProbability);
  }

  private double calculateProbability(Position ball, double ballZ, int iteration,
                                      Position previusBall, double previusBallZ, double precision) {
    int rivalIterationsToBall = analysis.calculateRivalIterationsToBall(ball, ballZ);
    double Dx = ball.getX() - previusBall.getX();
    double Dy = ball.getY() - previusBall.getY();
    if (!ball.isInsideGameField(0)) {
      if (ball.getY() > Constants.LARGO_CAMPO_JUEGO / 2) {
        double posX = (Dx / Dy) * (Constants.LARGO_CAMPO_JUEGO / 2 - ball.getY()) + ball.getX();
        double Dz = ballZ - previusBallZ;
        double posZ = (Dz / Dy) * (Constants.LARGO_CAMPO_JUEGO / 2 - ball.getY()) + ballZ;
        if (posZ <= Constants.ALTO_ARCO &&
            Math.abs(posX) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON &&
            ballZ - Dz <= Constants.ALTO_ARCO) {
          return -1;
        }
      }
      return 0;
    }
    if (rivalIterationsToBall <= iteration) {
      return Math.sqrt(Dx * Dx + Dy * Dy) / 7d;
    }
    return 1;
  }
}
