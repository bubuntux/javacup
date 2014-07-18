/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.tacticaMonsters;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.trajectory.FloorTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Goteck
 */
public class Simulador {

  static int PaseBueno = 1;
  static int PaseMalo = 2;
  static int PaseMediano = 5;
  static int Gol = 3;
  static int Fuera = 4;
  static int Nuse = 6;


  static boolean invertir = false;
  int iteracion;
  AbstractTrajectory trayectoria;
  Position posBalAct;
  float angH;

  public void simularTiro(double fuerza, double angV, double pangH, Position posBalon) {
    angH = (float) pangH;
    posBalAct = posBalon;
    double vX = (Math.cos(angV) * fuerza);
    double vY = Math.sin(angV) * fuerza;
    if (vY != 0 || posBalon.getY() != 0) {
      trayectoria = new AirTrajectory(vX, vY, posBalon.getX(), posBalon.getY());
    } else {
      trayectoria = new FloorTrajectory(vX, posBalon.getX());
    }

  }

  public double[] obtPosTiroSimulado(int iteracion) {
    double time = (iteracion) / 60d;
    double radio = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
    double z = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
    double x = posBalAct.getX() + radio * Math.cos(angH);
    double y = posBalAct.getY() + radio * Math.sin(angH);
    if (invertir) {
      x = -x;
      y = -y;
    }
    return new double[]{x, y, z};
  }

  public int calcularResultadoTiro(double fuerza, double angV, double pangH, Position posBalon,
                                   GameSituations sp) {

    simularTiro(fuerza, angV, pangH, posBalon);
    int limite = 100;
    boolean encontrado = false;
    int intento = 1;
    while (!encontrado & intento < limite) {
      double[] posActB = obtPosTiroSimulado(intento);
      if (Math.abs(posActB[0]) <= Constants.LARGO_ARCO
          && posActB[1] >= Constants.LARGO_CAMPO_JUEGO & posActB[2] <= Constants.ALTO_ARCO) {
        return Gol;
      }
      if (Math.abs(posActB[0]) > Constants.ANCHO_CAMPO_JUEGO / 2.0
          || Math.abs(posActB[1]) > Constants.LARGO_CAMPO_JUEGO / 2.0) {
        return Fuera;
      }

      int cuantosAliados = calcularCuantosLlegan(intento, posActB, true, sp);
      int cuantosEnemigos = calcularCuantosLlegan(intento, posActB, false, sp);
      if (cuantosAliados + cuantosEnemigos > 0) {
        if (cuantosEnemigos == 0 & cuantosAliados > 0) {
          return PaseBueno;
        }
        if (cuantosEnemigos == 0 & cuantosAliados > 0) {
          return PaseBueno;
        }
        if (cuantosAliados > 0 && cuantosEnemigos > 0) {
          return PaseMediano;
        }
      }

      intento++;


    }

    return Nuse;
  }

  private int calcularCuantosLlegan(int intento, double[] posActB, boolean equipoPropio,
                                    GameSituations sp) {
    double x = posActB[0];
    double y = posActB[1];
    double z = posActB[2];
    if (z > Constants.ALTO_ARCO) {
      return 0;
    }
    Position[] posJugadores;
    Player[] detallesJugador;
    if (equipoPropio) {
      posJugadores = sp.myPlayers();
      detallesJugador = sp.myPlayersDetail();
    } else {
      posJugadores = sp.rivalPlayers();
      detallesJugador = sp.rivalPlayersDetail();
    }
    int cuantos = 0;
    for (int i = 0; i < 11; i++) {
      if (z > Constants.ALTURA_CONTROL_BALON) {
        if (detallesJugador[i].isGoalKeeper()) {
          double distancia = posJugadores[i].distance(new Position(x, y));
          if (((distancia - Constants.DISTANCIA_CONTROL_BALON_PORTERO) / intento)
              <= detallesJugador[i].getSpeed()) {
            cuantos++;
          }
        }
      } else {
        double distancia = posJugadores[i].distance(new Position(x, y));
        double distControl = Constants.DISTANCIA_CONTROL_BALON;
        if (detallesJugador[i].isGoalKeeper()) {
          distControl = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
        }
        if (((distancia - distControl) / intento) <= detallesJugador[i].getSpeed()) {
          cuantos++;
        }
      }
    }

    return cuantos;


  }

  public datosTiro calcularPase(GameSituations sp, Player jd, Position posAct,
                                Position posObj) {
    datosTiro tiro = new datosTiro();
    tiro.tipo = PaseMalo;

    for (double angulo = 0; angulo <= Constants.ANGULO_VERTICAL_MAX; angulo = angulo + 10) {
      for (double fuerza = 0.2f; fuerza <= 1; fuerza = fuerza + 0.2f) {
        double fuerzaTotal = jd.getPower() * fuerza;
        double anguloH = posObj.angle(posAct);
        int res = calcularResultadoTiro(fuerzaTotal, angulo, anguloH, sp.ballPosition(), sp);
        if (res == Gol || res == PaseBueno) {
          datosTiro t = new datosTiro();
          t.anguloH = anguloH;
          t.anguloV = angulo;
          t.fuerza = fuerza;
          t.tipo = res;

        } else if (res == PaseMediano) {
          tiro.anguloH = anguloH;
          tiro.anguloV = angulo;
          tiro.fuerza = fuerza;
          tiro.tipo = res;
        }


      }
    }

    return tiro;
  }

  double[] calcularTiroCercano(double alturaBalon, double distancia, double pvelocidad) {
    double[] res = new double[2];

    for (double angulo = 5; angulo < 50; angulo += 1.0) {
      //direccion;
      double vV = pvelocidad * Math.sin(Math.toRadians(angulo));
      double vH = pvelocidad * Math.cos(Math.toRadians(angulo));
      AbstractTrajectory trayectoriaAct = new AirTrajectory(vH, vV, 0, alturaBalon);
      double alturaAct;
      double distanciaRecorrida;
      boolean aire = false;
      boolean encontrado = false;
      int iteraciones = 1;
      double distAnt = 0;
      double velMax = -1;
      while (iteraciones < 500) {
        distanciaRecorrida =
            trayectoriaAct.getX((double) iteraciones / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        alturaAct =
            trayectoriaAct.getY((double) iteraciones / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double vel = distanciaRecorrida - distAnt;

        if (vel < 2) {
          break;
        }
        if (distanciaRecorrida >= distancia) {
          if (vel > velMax) {
            velMax = vel;
            res[0] = angulo;
          }
          break;
        }


      }

      iteraciones++;

    }

    return res;
  }

  double[] calcularPaseRaso(double alturaBalon, double distancia, double fuerzaJug) {
    double tiro[] = new double[2];
    for (double fuerza = 1; fuerza > 0.1; fuerza = fuerza - 0.05) {
      FloorTrajectory trayectoria = new FloorTrajectory(fuerza * fuerzaJug, 0);
      double posAnt = 0;
      double vel;
      for (int i = 1; i < 500; i++) {
        double posAct = trayectoria.getX(i);
        vel = posAct - posAnt;
        if (vel < 2) {
          tiro[0] = -1;
          return tiro;
        }
        posAnt = posAct;
        if (Math.abs(distancia - posAct) < Constants.DISTANCIA_CONTROL_BALON) {
          if (vel < 2.5) {
            tiro[0] = fuerza;
            return tiro;
          } else {
            break;
          }
        }
      }
    }
    tiro[0] = -1;

    return tiro;
  }


  public class datosTiro {

    double fuerza;
    double anguloV;
    double anguloH;
    int tipo;
  }


  public double[] distanciaHastaControlBalon(double alturaIni, double pvelocidad, double angulo,
                                             boolean portero) {
    double[] res = new double[2];
    double alturaControl = Constants.ALTURA_CONTROL_BALON;
    if (portero) {
      alturaControl = Constants.ALTO_ARCO;
    }
    double alturaAct = alturaIni;
    double distanciaRecorrida = 0;

    double vV = Math.sin(Math.toRadians(angulo)) * pvelocidad;
    double vH = Math.cos(Math.toRadians(angulo)) * pvelocidad;

    boolean aire = false;
    boolean encontrado = false;
    int iteraciones = 0;

    while (vV >= 0 || aire) {

      if (alturaAct <= alturaControl & aire) {
        res[0] = distanciaRecorrida;
        res[1] = iteraciones;
        return res;
      }

      distanciaRecorrida += vH;
      alturaAct += vV;
      if (alturaAct > alturaControl & !aire) {
        aire = true;
      }

      vV = (vV * Constants.FACTOR_DISMINUCION_VEL_BALON_AIRE) - Constants.G;
      vH = vH * Constants.FACTOR_DISMINUCION_VEL_BALON_AIRE;
      iteraciones++;
    }
    res[0] = -1;
    res[1] = -1;
    return res;
  }

  public double[] calcularPaseAire(double fuerzaJugador, double distancia, double alturaIni) {
    //1er valor fuerza 2do valor ang
    double[] res = new double[2];
    double mejorDist = 999999;
    for (double fuerza = 1; fuerza >= 0.1; fuerza = fuerza - 0.05) {
      for (double angulo = 15; angulo <= 60; angulo = angulo + 2.5) {
        double[]
            distAct =
            distanciaHastaControlBalon2(alturaIni, fuerza * fuerzaJugador, angulo, false);
        if (Math.abs(distAct[0] - distancia) < mejorDist) {
          mejorDist = Math.abs(distAct[0] - distancia);
          res[0] = fuerza;
          res[1] = angulo;
          if (mejorDist < Constants.DISTANCIA_CONTROL_BALON * 3) {
            return res;
          }

        }
      }
    }

    return res;
  }

  public static void main(String args[]) {

    Simulador sim = new Simulador();
    try {
      sim.simularPartido();
    } catch (Exception ex) {
      Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
    }

  }


  public double[] distanciaHastaControlBalon2(double alturaIni, double pvelocidad, double angulo,
                                              boolean portero) {
    double[] res = new double[2];
    double alturaControl = Constants.ALTURA_CONTROL_BALON;
    if (portero) {
      alturaControl = Constants.ALTO_ARCO;
    }
    //direccion;
    double vV = pvelocidad * Math.sin(Math.toRadians(angulo));
    double vH = pvelocidad * Math.cos(Math.toRadians(angulo));
    AbstractTrajectory trayectoriaAct = new AirTrajectory(vH, vV, 0, alturaIni);
    double alturaAct;
    double distanciaRecorrida;
    boolean aire = false;
    boolean encontrado = false;
    int iteraciones = 1;

    while (iteraciones < 500) {
      distanciaRecorrida =
          trayectoriaAct.getX((double) iteraciones / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
      alturaAct =
          trayectoriaAct.getY((double) iteraciones / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;

      if (alturaAct <= alturaControl & aire) {
        res[0] = distanciaRecorrida;
        res[1] = iteraciones;

        return res;
      }

      //alturaAct+=vV;
      if (alturaAct > alturaControl & !aire) {
        aire = true;
      }

      //vV=(vV*Constants.FACTOR_DISMINUCION_VEL_BALON_AIRE)-Constants.G;
      //vH=vH*Constants.FACTOR_DISMINUCION_VEL_BALON_AIRE;
      iteraciones++;

    }
    //res[0]=-1;
    //res[1]=-1;

    return res;
  }

  public double[] calcularTiroAlto(double alturaIni, double dist, double fuerzaTotal) {

    double[] res = new double[2];
    double mejorDist = 9999999;
    for (float angulo = 30; angulo <= 60; angulo += 5) {
      for (float i = 1; i >= 0.1f; i = i - 0.025f) {
        double[] act = distanciaHastaControlBalon2(alturaIni, i * fuerzaTotal, angulo, true);
        if (Math.abs(act[0] - dist) < mejorDist && act[0] < dist) {
          res[0] = i;
          res[1] = angulo;
          mejorDist = Math.abs(act[0] - dist);
        }
      }
    }

    return res;
  }

  public void simularPartido() throws Exception {

  }


}
