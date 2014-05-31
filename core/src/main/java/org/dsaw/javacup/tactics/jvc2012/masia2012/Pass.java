package org.dsaw.javacup.tactics.jvc2012.masia2012;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.List;

public class Pass {

  private List<Command> commands;
  private final Analysis analysis;
  private Shoot shoot;

  int[] iterationsToShoot;
  double power;
  double precision;
  boolean isGoalkeeper;

  PassData bestShootData;
  PassData bestPassData;

  boolean forward;
  boolean clearBall;

  double minAngleToGoal;
  double maxAngleToGoal;

  public Pass(List<Command> commands, Analysis analysis) {
    this.commands = commands;
    this.analysis = analysis;
    shoot = new Shoot(commands, analysis);
  }

  public void execute() {
    if (analysis.isGoalKick) {
      goalKick();
    } else if (analysis.canShoot.length > 0) {
      update();
      if (isGoalkeeper) {
        calculateGoalKeeperPass();
      } else {
        calculatePass();
      }
      ShootData shootData = shoot.execute(power, precision);
      if (bestShootData != null || shootData.probability > 0) {
        if (bestShootData != null && bestShootData.goalsAvg >= shootData.probability) {
          pass(bestShootData);
        }
        analysis.shootCount++;
      } else {
        pass(bestPassData);
      }
    }
  }

  private void goalKick() {
    commands.add(new CommandMoveTo(analysis.goalkeeperIndex, analysis.ball));
    if ((!analysis.goalKickWaiting && analysis.canShoot.length > 0) ||
        analysis.rivalPlayers[analysis.ball.nearestIndex(analysis.rivalPlayers)]
            .distance(analysis.ball) >= 11) {
      commands.add(new CommandHitBall(analysis.goalkeeperIndex, Constants.centroCampoJuego,
                                      0.25 / Constants.getVelocidadRemate(
                                          analysis.myPlayersDetail[analysis.goalkeeperIndex]
                                              .getPower()) / 0.75, 0));
    }
  }

  private void pass(PassData passData) {
    pass(passData.position, passData.shootVelocity, passData.verticalAngle);
  }

  private void pass(Position position, double velocity, double verticalAngle) {
    for (int i = 0; i < analysis.canShoot.length; i++) {
      pass(analysis.canShoot[i], position, velocity, verticalAngle);
    }
    commands.add(new CommandMoveTo(position.nearestIndex(analysis.myPlayers), position));
  }

  private void pass(int index, Position position, double velocity, double verticalAngle) {
    double
        powerShoot =
        velocity / Constants.getVelocidadRemate(analysis.myPlayersDetail[index].getPower());
    if (analysis.isService) {
      powerShoot /= .75;
    }
    commands.add(new CommandHitBall(index, position, powerShoot, verticalAngle * 180 / Math.PI));
  }

  private void update() {
    minAngleToGoal =
        analysis.ball.angle(Constants.posteDerArcoSup.movePosition(-Constants.RADIO_BALON, 0));
    maxAngleToGoal =
        analysis.ball.angle(Constants.posteIzqArcoSup.movePosition(Constants.RADIO_BALON, 0));
    power = 1;
    precision = 1;
    isGoalkeeper = false;
    iterationsToShoot = analysis.iterationsToShoot;
    for (int i = 0; i < analysis.canShoot.length; i++) {
      iterationsToShoot[analysis.canShoot[i]] = Constants.ITERACIONES_GOLPEAR_BALON;
      if (analysis.myPlayersDetail[analysis.canShoot[i]].getPower() < power) {
        power = analysis.myPlayersDetail[analysis.canShoot[i]].getPower();
      }
      if (analysis.myPlayersDetail[analysis.canShoot[i]].getPrecision() < precision) {
        precision = analysis.myPlayersDetail[analysis.canShoot[i]].getPrecision();
      }
      if (analysis.myPlayersDetail[analysis.canShoot[i]].isGoalKeeper()) {
        isGoalkeeper = true;
      }
    }
    bestPassData = null;
    bestShootData = null;
    forward =
        analysis.ball.getY() < 0 && (analysis.myGoals <= analysis.rivalGoals + 1
                                     || analysis.possession < 0.55)
        && analysis.iterations > Constants.ITERACIONES / 18;
  }

  private void calculatePass() {
    double maxVertAngle = Constants.ANGULO_VERTICAL_MAX * Math.PI / 180;
    double[]
        verticalAngle =
        new double[]{0, maxVertAngle / 8, maxVertAngle / 4, maxVertAngle / 2, 3 * maxVertAngle / 5,
                     3 * maxVertAngle / 4};
    calculatePass(verticalAngle, 4);
  }

  private void calculateGoalKeeperPass() {
    double maxVertAngle = Constants.ANGULO_VERTICAL_MAX * Math.PI / 180;
    double[]
        verticalAngle =
        new double[]{0, maxVertAngle / 4, maxVertAngle / 3, maxVertAngle / 2, 3 * maxVertAngle / 5,
                     maxVertAngle};
    calculatePass(verticalAngle, 1);
  }

  private void calculatePass(double[] verticalAngle, int nShootPower) {
    double maxPowerShoot = 1;
    if (analysis.isService) {
      maxPowerShoot = 0.75;
    }
    double
        minPowerShoot =
        Math.min((Constants.VELOCIDAD_MAX + .4) / Constants.getVelocidadRemate(power),
                 maxPowerShoot);
    double steep = (maxPowerShoot - minPowerShoot) / nShootPower;
    driving();
    for (int i = 0; i < nShootPower; i++) {
      for (int j = 0; j < verticalAngle.length; j++) {
        calculatePass((minPowerShoot + (i + 1) * steep) * Constants.getVelocidadRemate(power),
                      verticalAngle[j]);
      }
    }
  }

  private void driving() {
    calculatePass(Constants.VELOCIDAD_MAX + .4, 0);
  }

  private void calculatePass(double velocity, double verticalAngle) {
    int numAngles = 360;
    double dAngle = 360.0 / numAngles;
    Trajectory[] trajectory = new Trajectory[numAngles];
    for (int i = 0; i < numAngles; i++) {
      trajectory[i] =
          new Trajectory(analysis.ball, velocity, i * dAngle * Math.PI / 180, verticalAngle);
    }
    PassData[] datas = new PassData[numAngles];
    int errorAngle = (int) Math.floor(Constants.getErrorAngular(precision) * 90 / dAngle);
    int goals, received;
    for (int i = 0; i < numAngles; i++) {
      if (datas[i] == null) {
        datas[i] = calculatePass(trajectory[i]);
        datas[i].shootVelocity = velocity;
        datas[i].verticalAngle = verticalAngle;
      }
      goals = received = 0;
      for (int j = i - errorAngle; j <= i + errorAngle; j++) {
        int k = (numAngles + j) % numAngles;
        if (datas[k] == null) {
          datas[k] = calculatePass(trajectory[k]);
          datas[k].shootVelocity = velocity;
          datas[k].verticalAngle = verticalAngle;
        }
        goals += datas[k].state == PassDataState.Goal ? 1 : 0;
        received += datas[k].state == PassDataState.Received ? 1 : 0;
      }
      datas[i].goalsAvg = ((double) goals) / (2 * errorAngle + 1);
      datas[i].setReceivedAvg(((double) received) / (2 * errorAngle + 1));
      if (datas[i].state == PassDataState.Goal) {
        if (bestShootData == null) {
          bestShootData = datas[i];
        } else {
          bestShootData = bestShootData.compare(datas[i]);
        }
      } else if (bestPassData == null) {
        bestPassData = datas[i];
      } else {
        bestPassData = bestPassData.compare(datas[i]);
      }
    }
  }

  private PassData calculatePass(Trajectory trajectory) {
    int i = 0;
    PassData
        passData =
        calculatePass(trajectory.positions[i], trajectory.z[i], trajectory.angle, i + 1,
                      analysis.ball, 0);
    i++;
    while (passData.state == PassDataState.Undefine && i < trajectory.length) {
      passData =
          calculatePass(trajectory.positions[i], trajectory.z[i], trajectory.angle, i + 1,
                        trajectory.positions[i - 1], trajectory.z[i - 1]);
      i++;
    }
    return passData;
  }

  private PassData calculatePass(Position ballPosition, double z, double angle, int iterations,
                                 Position last, double lastZ) {
    int
        rivalIterationsToBall =
        analysis.calculateIterationsToBall(ballPosition, z, analysis.rivalPlayers,
                                           analysis.rivalPlayersDetail,
                                           analysis.rivalIterationsToShoot);
    double Dx = ballPosition.getX() - last.getX();
    double Dy = ballPosition.getY() - last.getY();
    if (!ballPosition.isInsideGameField(0)) {
      if (ballPosition.getY() > Constants.LARGO_CAMPO_JUEGO / 2) {
        double
            posX =
            (Dx / Dy) * (Constants.LARGO_CAMPO_JUEGO / 2 - ballPosition.getY()) + ballPosition
                .getX();//proyeccion x de la trayectoria del ballPosition en la linea de meta
        double Dz = z - lastZ;
        double
            posZ =
            (Dz / Dy) * (Constants.LARGO_CAMPO_JUEGO / 2 - ballPosition.getY())
            + z;//proyeccion z de la trayectoria del ballPosition en la linea de meta
        if (posZ <= Constants.ALTO_ARCO &&
            Math.abs(posX) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON &&
            z - Dz <= Constants.ALTO_ARCO) {
          return new PassData(PassDataState.Goal, ballPosition, Math.sqrt(Dx * Dx + Dy * Dy), z,
                              angle, Math.max(rivalIterationsToBall - iterations, 0));
        }
      }
      return new PassData(PassDataState.Large, ballPosition, Math.sqrt(Dx * Dx + Dy * Dy), z, angle,
                          Math.max(rivalIterationsToBall - iterations, 0));
    }
    if (rivalIterationsToBall <= iterations) {
      return new PassData(PassDataState.Interceptable, ballPosition, Math.sqrt(Dx * Dx + Dy * Dy),
                          z, angle, 0);
    }
    int
        iterationsToBall =
        analysis.calculateIterationsToBall(ballPosition, z, analysis.myPlayers,
                                           analysis.myPlayersDetail, iterationsToShoot);
    if (iterationsToBall > iterations) {
      return new PassData(PassDataState.Undefine, ballPosition, Math.sqrt(Dx * Dx + Dy * Dy), z,
                          angle, Math.max(rivalIterationsToBall - iterations, 0));
    }
    return new PassData(PassDataState.Received, ballPosition, Math.sqrt(Dx * Dx + Dy * Dy), z,
                        angle, Math.max(rivalIterationsToBall - iterations, 0));
  }


  enum PassDataState {
    Goal(0),
    Large(2),
    Interceptable(4),
    Undefine(3),
    Received(1);

    public int priority;

    private PassDataState(int priority) {
      this.priority = priority;
    }
  }

  class PassData {

    Position position;
    double shootVelocity;
    double verticalAngle;
    int rivalIterationsToBall;
    int receiver;

    PassDataState state;
    double ballVelocity;

    double receivedAvg;
    double goalsAvg;
    double shootOnGoalProbability;
    double z;
    int quartile;


    double shootAngleFactor = -1;
    boolean isDangerous;

    protected PassData(PassDataState state, Position position, double ballVelocity, double z,
                       double angle, int rivalIterationsToBall) {
      super();
      this.state = state;
      this.position = position;
      this.ballVelocity = ballVelocity;
      this.z = z;
      this.rivalIterationsToBall = rivalIterationsToBall;
      quartile =
          (int) Math.floor(Math.min(Constants.LARGO_CAMPO_JUEGO,
                                    Math.max(position.getY() - Constants.centroArcoInf.getY(), 0))
                           / Constants.LARGO_CAMPO_JUEGO * 4);
      receiver = position.nearestIndex(analysis.myPlayers);
      isDangerous = quartile <= 1 || playersBehind() < analysis.mentality.defenseCount;
      shootOnGoalProbability =
          Math.min(
              Math.min(Math.max(angle - minAngleToGoal, 0), Math.max(maxAngleToGoal - angle, 0)) / (
                  Constants.getErrorAngular(precision) * Math.PI / 2), 1);
    }

    public void setReceivedAvg(double receivedAvg) {
      this.receivedAvg = receivedAvg;
      if (isDangerous) {
        this.receivedAvg *= Math.min(rivalIterationsToBall, 10) / 10.0;
      } else {
        this.receivedAvg *= 0.7 + 0.3 * Math.min(rivalIterationsToBall, 3.0) / 3.0;
      }
    }

    private int playersBehind() {
      int countDefender = 0;
      for (int i = 0; i < analysis.myPlayers.length; i++) {
        if (position.getY() >= analysis.myPlayers[i].getY()) {
          countDefender++;
        }
      }
      return countDefender;
    }


    final PassData compare(PassData other) {
      if (state.priority < other.state.priority) {
        return this;
      }
      if (state.priority > other.state.priority) {
        return other;
      }
      if (goalsAvg > 0 && other.goalsAvg == 0) {
        return this;
      }
      if (goalsAvg == 0 && other.goalsAvg > 0) {
        return other;
      }
      if (goalsAvg > 0 && other.goalsAvg > 0) {
        if (shootOnGoalProbability > other.shootOnGoalProbability) {
          return this;
        }
        if (shootOnGoalProbability < other.shootOnGoalProbability) {
          return other;
        }
        if (goalsAvg > other.goalsAvg) {
          return this;
        }
        if (goalsAvg < other.goalsAvg) {
          return other;
        }
        if (z < other.z) {
          return this;
        }
        if (z > other.z) {
          return other;
        }
        if (ballVelocity >= other.ballVelocity) {
          return this;
        }
        return other;
      }
      if (analysis.myGoals <= analysis.rivalGoals + 1 || analysis.possession < 0.5) {
        if (receiver != analysis.goalkeeperIndex && other.receiver == analysis.goalkeeperIndex) {
          return this;
        }
        if (receiver == analysis.goalkeeperIndex && other.receiver != analysis.goalkeeperIndex) {
          return other;
        }
        if (quartile > 0 && other.quartile == 0) {
          return this;
        }
        if (quartile == 0 && other.quartile > 0) {
          return other;
        }
      }

      if (isGoalkeeper || (forward && analysis.isGoalDefended)) {
        if (quartile != other.quartile) {
          if (quartile > other.quartile) {
            return this;
          }
          return other;
        }
        if (quartile <= 1) {
          if (receiver != other.receiver) {
            if (analysis.myPlayers[receiver].distance(Constants.centroArcoInf)
                >= analysis.myPlayers[other.receiver].distance(Constants.centroArcoInf)) {
              return this;
            }
            return other;
          }
        }
      }
      if (receivedAvg > other.receivedAvg) {
        return this;
      }
      if (receivedAvg < other.receivedAvg) {
        return other;
      }
      if (quartile != other.quartile) {
        if (quartile > other.quartile) {
          return this;
        }
        return other;
      }
      if (quartile <= 1) {
        if (position.getY() > other.position.getY()) {
          return this;
        }
        if (position.getY() < other.position.getY()) {
          return other;
        }
        if (ballVelocity <= other.ballVelocity) {
          return this;
        }
        return other;
      }
      calculateShootAngleFactor();
      other.calculateShootAngleFactor();
      if (shootAngleFactor > other.shootAngleFactor) {
        return this;
      }
      if (shootAngleFactor < other.shootAngleFactor) {
        return other;
      }
      double distance = position.distance(Constants.centroArcoSup);
      double otherDistance = other.position.distance(Constants.centroArcoSup);
      if (distance < otherDistance) {
        return this;
      }
      if (distance > otherDistance) {
        return other;
      }
      if (ballVelocity <= other.ballVelocity) {
        return this;
      }
      return other;
    }

    private void calculateShootAngleFactor() {
      if (shootAngleFactor == -1) {
        precision = analysis.myPlayersDetail[receiver].getPrecision();
        shootAngleFactor =
            Math.min(Math.abs(position.angle(Constants.posteIzqArcoSup) - position
                .angle(Constants.posteDerArcoSup)) / (Constants.getErrorAngular(precision) * Math.PI
                                                      / 2), 1);
      }

    }
  }
}
