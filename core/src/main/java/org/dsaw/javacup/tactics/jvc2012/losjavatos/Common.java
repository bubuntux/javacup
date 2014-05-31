package org.dsaw.javacup.tactics.jvc2012.losjavatos;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.LinkedList;

public class Common {

  GameSituations commonSp;
  LinkedList<Command> comCom = new LinkedList<>();
  static int pCo = -1;
  static int[] eC = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

  public Common(GameSituations sp) {
    commonSp = sp;
  }

  public void sI() {
    comCom.add(new CommandMoveTo(9, new Position(0, 0)));
    if (commonSp.canKick().length > 0) {
      this.pase(9, 5);
    }
  }

  public void Co() {
    comCom.add(new CommandMoveTo(5, new Position(0, 20)));
    comCom.add(new CommandMoveTo(6, new Position(0, 50)));
    comCom.add(new CommandMoveTo(10, new Position(5, 47)));
    comCom.add(new CommandMoveTo(9, new Position(-5, 47)));
    comCom.add(new CommandMoveTo(8, new Position(10, 30)));
    comCom.add(new CommandMoveTo(7, new Position(-10, 30)));
    int jug = commonSp.ballPosition().nearestIndex(commonSp.myPlayers());
    comCom.add(new CommandMoveTo(jug, commonSp.ballPosition()));
    this.pase(jug, 6);
  }

  public void Ba() {
    int jug = commonSp.ballPosition().nearestIndex(commonSp.myPlayers());
    comCom.add(new CommandMoveTo(jug, commonSp.ballPosition()));
    if (commonSp.ballPosition().getY() > 0) {
      this.pCA();
    } else {
      this.pum();
    }
  }

  public void pC() {
    for (int i = 0; i < 11; i++) {
      if (commonSp.rivalPlayersDetail()[i].isGoalKeeper()) {
        pCo = i;
      }
    }
  }

  public void bB() {
    if (commonSp.getRecoveryBall().length > 0) {
      int it = commonSp.getRecoveryBall()[0];
      int jug = commonSp.getRecoveryBall()[1];
      double x = commonSp.getTrajectory(it)[0];
      double y = commonSp.getTrajectory(it)[1];
      Position pos = new Position(x, y);
      if (jug != 0) {
        comCom.add(new CommandMoveTo(jug, pos));
      } else if (x > -20 && x < 20 && y < -40) {
        comCom.add(new CommandMoveTo(jug, pos));
      }
    }
  }

  public boolean paC() {
    int s;
    int m = 10;
    double disFin = 100;
    double dis;
    if (commonSp.canKick().length != 0) {
      s = commonSp.canKick()[0];
      for (int i = 0; i < 11; i++) {
        dis = commonSp.myPlayers()[s].distance(commonSp.myPlayers()[i]);
        if ((dis != 0) && (dis < disFin)) {
          disFin = dis;
          m = i;
        }
      }
      this.pase(s, m);
      return true;
    }
    return false;
  }

  public void pCA() {
    int s;
    int[] m;
    if (commonSp.canKick().length != 0) {
      s = commonSp.canKick()[0];
      m = commonSp.myPlayers()[s].nearestIndexes(commonSp.myPlayers(), 0);
      for (int i = 0; i < m.length; i++) {
        if (commonSp.myPlayers()[m[i]].getY() > commonSp.myPlayers()[s].getY()) {
          this.pase(s, m[i]);
          break;
        }
      }

    }
  }

  public boolean paNC() {
    boolean p = false;
    int s;
    int m = 20;
    if (commonSp.canKick().length != 0) {
      s = commonSp.canKick()[0];
      for (int i = 1; i < 11; i++) {
        if (!cbie(i)) {
          m = i;
        }
      }
      if (m != 20) {
        p = true;
        this.pase(s, m);
      }
    }
    return p;
  }

  public boolean pum() {
    int s;
    if (commonSp.canKick().length != 0) {
      s = commonSp.canKick()[0];
      Position por = new Position(0, 50);
      int jug = por.nearestIndex(commonSp.myPlayers());
      this.pase(s, jug);
      return true;
    }
    return false;
  }

  public void pase(int jug1, int jug2) {
    double dis = commonSp.myPlayers()[jug1].distance(commonSp.myPlayers()[jug2]);
    comCom.add(new CommandHitBall(jug1, commonSp.myPlayers()[jug2], 1,
                                  calcAng(dis, commonSp.getMyPlayerPower(jug1))));
  }

  private double calcAng(double dist, double fuerzaJugador) {
    double grados = 0;
    double dif = 100;

    fuera:
    for (double i = 0; i < 60; i += 0.5) {
      for (int iteracion = 0; iteracion < 80; iteracion++) {
        double fuerzaRemate = 1;
        double angVer = i * Math.PI / 180;
        double vel = fuerzaRemate * Constants.getVelocidadRemate(fuerzaJugador);
        AbstractTrajectory
            trayectoria =
            new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
        double time = (double) iteracion / 60d;
        double
            desplazamientoHorizontal =
            trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double
            desplazamientoVertical =
            trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        if (Math.abs(desplazamientoHorizontal - dist) <= Constants.DISTANCIA_CONTROL_BALON) {
          if (desplazamientoVertical <= Constants.ALTURA_CONTROL_BALON) {
            if ((Constants.ALTURA_CONTROL_BALON - desplazamientoVertical) < dif) {
              dif = Constants.ALTURA_CONTROL_BALON - desplazamientoVertical;
              grados = i;
              if (Constants.ALTURA_CONTROL_BALON - desplazamientoVertical < 0.15) {
                break fuera;
              }
            }
          }
        } else if (desplazamientoHorizontal > (dist + Constants.DISTANCIA_CONTROL_BALON)) {
          break;
        }

      }
    }
    return grados;
  }

  private double calcAng2(double dist, double fuerzaJugador) {
    double grados;
    double v = (1 / 27) * dist;
    v = (v > 1 ? 1 : v);
    v = (v < 0 ? 0 : v);
    grados = 10 + (7 * v);
    return grados;
  }

  public boolean tBT() {
    for (int i : commonSp.canKick()) {
      if (i > 6 && commonSp.myPlayers()[i].getY() > 10) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  public void tAP() {
    double ang;
    Position pos;
    for (int i : commonSp.canKick()) {
      if (commonSp.rivalPlayers()[pCo].getX() >= 0) {
        pos = Position.medium(Constants.posteIzqArcoSup, commonSp.rivalPlayers()[pCo]);
        ang =
            calcAng(commonSp.myPlayers()[i].distance(Constants.posteIzqArcoSup),
                    commonSp.getMyPlayerPower(i));
      } else {
        pos = Position.medium(Constants.posteDerArcoSup, commonSp.rivalPlayers()[pCo]);
        ang =
            calcAng(commonSp.myPlayers()[i].distance(Constants.posteIzqArcoSup),
                    commonSp.getMyPlayerPower(i));
      }
      comCom.add(new CommandHitBall(i, pos, 1, ang));
    }
  }

  public void mH() {
    int[] Cub = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    for (int i = 0; i < 11; i++) {
      if (eC[i] != -1) {
        Cub[i] = eC[i];
      }
    }
    for (int i = 0; i < 11; i++) {
      double contY = commonSp.rivalPlayers()[i].getY();
      if (contY < -20) {
        if (eC[i] == -1) {
          int jug = commonSp.rivalPlayers()[i].nearestIndex(commonSp.myPlayers(), Cub);
          eC[i] = jug;
        }
        cub(eC[i], commonSp.rivalPlayers()[i]);
      } else {
        eC[i] = -1;
      }
    }
    if (commonSp.ballPosition().getY() < -36) {
      int jug = commonSp.ballPosition().nearestIndex(commonSp.myPlayers(), 0);
      comCom.add(new CommandMoveTo(jug, commonSp.ballPosition()));
    }
  }

  public boolean defense() {
    boolean t = (commonSp.rivalCanKick().length > 0);
    boolean s = (commonSp.isRivalStarts());
    boolean m = (commonSp.ballPosition().getY() < -20);
    if (t || s || m) {
      return true;
    } else {
      return false;
    }


  }

  public void mP() {
    double x = commonSp.ballPosition().getX();
    double y = commonSp.ballPosition().getY();
    double pX = 0;
    double pY = -51;
    if (x < -24) {
      pX = -6.0;
    } else if (x < -12) {
      pX = -3.0;
    } else if (x < 12) {
      pX = 0.0;
    } else if (x < 24) {
      pX = 3.0;
    } else if (x > 24) {
      pX = 6.0;
    }
    if (y < -36) {
      pY = -51;
    } else if (y < 0) {
      pY = -49;
    } else if (y > 36) {
      pY = -47;
    }
    comCom.add(new CommandMoveTo(0, new Position(pX, pY)));
  }

  private void cub(int jug, Position pos) {
    double ballX = commonSp.ballPosition().getX();
    double cubX = pos.getX();
    double disX = ballX - cubX;
    double cubY = pos.getY();
    if (disX < -10) {
      cubX -= 1.5;
    } else if (disX < -5) {
      cubX -= 0.75;
    } else if (disX < 5) {
      ;
    } else if (disX < 10) {
      cubX += 0.75;
    } else if (ballX > 10) {
      cubX += 1.5;
    }
    comCom.add(new CommandMoveTo(jug, new Position(cubX, (cubY - 1.75))));
  }

  private boolean cbie(int jug) {
    boolean cub = false;
    for (int i = 0; i < 11; i++) {
      if (commonSp.myPlayers()[jug].distance(commonSp.rivalPlayers()[i]) <= 1.0) {
        cub = true;
      }
    }
    return cub;
  }

  public void aPB() {
    if (commonSp.getRecoveryBall().length > 1) {
      double[] pos = commonSp.getTrajectory(commonSp.getRecoveryBall()[0]);
      for (int i = 1; i < commonSp.getRecoveryBall().length; i++) {
        comCom.add(new CommandMoveTo(commonSp.getRecoveryBall()[i],
                                     new Position(pos[0], pos[1])));
      }
    }
  }
}
    
    
