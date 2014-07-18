/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.Sag;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.Random;

/**
 * @author pdsanchez
 */
public class Central extends Jugador {

  private Random rnd = new Random();

  private boolean isPaseAtras = false;

  public Central(int idx, Player detalle, Position posRef) {
    super(idx, detalle, posRef);
    this.setTipo(Jugador.TIPO_CENTRAL);
  }

  public Central(int idx, Player detalle) {
    super(idx, detalle);
    this.setTipo(Jugador.TIPO_CENTRAL);
  }

  @Override
  public Command irAPosicionDestino() {
    if (sp.isStarts()) {
      this.setPosicionDestino(Position.medium(this.getPosicionReferencia(), sp.ballPosition()));
    }
    //        else if (this.getRecuperador() != null) {
    //            this.setPosicionDestino(Posicion.media(this.getPosicionReferencia(), this.getRecuperador().getPosicionDestino()));
    //        }
    else {
      boolean isDentroArea = this.getPosicionActual().getY() > Constants.penalSup.getY();
      boolean
          isLateral =
          Math.abs(this.getPosicionActual().getX()) > Math
              .abs(Constants.posteDerArcoSup.getX() * 2);
      if (this.estaBalonEnCampoRival() && isDentroArea && !isLateral) {
        this.setPosicionDestino(this.getPosicionReferencia());
      } else {
        // El central oscila hacia la pelota
        double
            x =
            (this.getPosicionBalon().getX() * (Constants.ANCHO_CAMPO_JUEGO / 8)) / (
                Constants.ANCHO_CAMPO_JUEGO / 2);
        double
            y =
            (this.getPosicionBalon().getY() * (Constants.LARGO_CAMPO_JUEGO / 8)) / (
                Constants.LARGO_CAMPO_JUEGO / 2);
        this.setPosicionDestino(this.getPosicionReferencia().getX() + x,
                                this.getPosicionReferencia().getY() + y);
      }
    }
    return new CommandMoveTo(this.getIndice(), this.getPosicionDestino());
  }

  @Override
  public Command GolpearBalon() {
    // Distancia del rival mas cercano a este jugador
    double dist = this.getPosicionActual().distance(this.getRivalMasCercano().getPosicionActual());

    // Si la distancia es grande este jugador avanza con el ballPosition
    if (!sp.isStarts() && dist > 12) {
      return new CommandHitBall(this.getIndice(), Constants.centroArcoSup, 0.4, false);
    }

    // Determinar si el golpeador dispara a puerta o pasa

    // El golpeador esta en campo rival
    if (this.estaJugadorEnCampoRival()) {
      Position posPortero = this.getPorteroRival().getPosicionActual();

      // Posicion de este jugador respecto a la porteria
      boolean isDentroArea = this.getPosicionActual().getY() > Constants.penalSup.getY();
      boolean
          isLateral =
          Math.abs(this.getPosicionActual().getX()) > Math
              .abs(Constants.posteDerArcoSup.getX() * 2);

      if (sp.isStarts()) {
        // Intento pasar
        Command c = this.paseAdelantado();
        if (c != null) {
          return c;
        }

        //si no encontro ningun pase
        if (this.getPosicionActual().getX() < 0) {
          Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
          return new CommandHitBall(this.getIndice(), posDisparo, 1, true);
        } else {
          Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
          return new CommandHitBall(this.getIndice(), posDisparo, 1, true);
        }
      } else if (isDentroArea) {
        if (isPaseAtras) {
          isPaseAtras = false;
          // Disparar a gol
          return this.disparar(posPortero);
        } else if (isLateral) {
          Command c = this.paseAtras();
          if (c != null) {
            isPaseAtras = true;
            return c;
          }
        } else {
          // Disparar a gol
          return this.disparar(posPortero);
        }
      } else if (rnd.nextBoolean()) {
        // Intento pasar
        Command c = this.paseAdelantado();
        if (c != null) {
          return c;
        }
      }

      // Disparar
      return this.disparar(posPortero);

    }
    // El golpeador esta en campo propio
    else {
      // Saque de centro
      if (sp.ballPosition().getX() == 0 && sp.ballPosition().getY() == 0) {
        Command c = this.saqueCentro();
        if (c != null) {
          return c;
        }
      }

      // Intento pasar
      Command c = this.paseAdelantado();
      if (c != null) {
        return c;
      }

      //si no encontro ningun pase
      if (this.getPosicionActual().getX() < 0) {
        Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        return new CommandHitBall(this.getIndice(), posDisparo, 1, true);
      } else {
        Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        return new CommandHitBall(this.getIndice(), posDisparo, 1, true);
      }
    }
  }

  private Command disparar(Position posPortero) {
    double dist = this.getPosicionActual().distance(Constants.centroArcoSup);
    // Portero a la izda
    if (posPortero.distance(Constants.posteDerArcoSup) > posPortero
        .distance(Constants.posteIzqArcoSup)) {
      Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
      // Rematador a la izda
      if (this.getPosicionActual().getX() <= 0) {
        // Si el rematador esta muy cerca ajusta al palo izdo
        if (dist < 5) {
          posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        }
      }
      if (dist < 20) {
        return new CommandHitBall(this.getIndice(), posDisparo, .7 + Math.random() * .3, false);
      }
      return new CommandHitBall(this.getIndice(), posDisparo, 1, 12 + rnd.nextInt(6));
    }
    // Portero a la dcha
    else {
      Position posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
      // Rematador a la dcha
      if (this.getPosicionActual().getX() >= 0) {
        // Si el rematador esta muy cerca ajusta al palo dcho
        if (dist < 5) {
          posDisparo = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        }
      }

      if (dist < 20) {
        return new CommandHitBall(this.getIndice(), posDisparo, .7 + Math.random() * .3, false);
      }
      return new CommandHitBall(this.getIndice(), posDisparo, 1, 12 + rnd.nextInt(6));
    }
  }

  private Command paseAdelantado() {
    Jugador ppal = null;
    double diffPpal = -1;
    double distPpal = -1;
    Jugador sec = null;
    double diffSec = -1;
    double distSec = -1;

    //obtiene los jugadores mas cercanos, al jugador que puede rematar
    Jugador[] cercanos = this.getCompanerosCercanos();
    for (Jugador j : cercanos) {
      // El jugador que recibe esta mas adelantado
      if ((j.isDelantero() || j.isCentral())
          && this.getPosicionActual().getY() < j.getPosicionActual().getY()) {
        double dist = this.getPosicionActual().distance(j.getPosicionActual());
        // Distancia del rival mas cercano a este jugador
        double
            distRival =
            j.getPosicionActual().distance(j.getRivalMasCercano().getPosicionActual());

        if (!j.existeMarcaje()) {
          double diff = distRival - dist;
          if (diff > 0 && diff > diffPpal) {
            ppal = j;
            diffPpal = diff;
            distPpal = dist;
          } else {
            diff = distRival - dist / 3;
            if (diff > 0 && diff > diffSec) {
              sec = j;
              diffSec = diff;
              distSec = dist;
            }
          }
        }
      }
    }
    Jugador j;
    j = (ppal != null) ? ppal : (sec != null) ? sec : null;
    double dist = (ppal != null) ? distPpal : (sec != null) ? distSec : -1;
    if (j != null) {
      double fuerza = 0.6;
      boolean alto = false;
      if (Math.abs(this.getPosicionActual().getY()) < Constants.LARGO_CAMPO_JUEGO / 5) {
        if (dist > 17) {
          alto = true;
          fuerza = 0.8;
        }
      }

//            double distRival = j.getPosicionActual().distancia(j.getRivalMasCercano().getPosicionActual());
//            if (distRival < 20) {
//                System.out.println("PRUEBA!!!!!!!!!!!!!!!!!!! "+distRival);
//                double dx = j.getRivalMasCercano().getPosicionActual().getX() - j.getPosicionActual().getX();
//                double dy = j.getRivalMasCercano().getPosicionActual().getY() - j.getPosicionActual().getY();
//                double px = j.getPosicionActual().getX() - dx;
//                double py = j.getPosicionActual().getY() - dy;
//                Posicion pm = Posicion.media(j.getPosicionActual(), new Posicion(px,py)).setDentroCampoJuego();
//                return new CommandHitBall(this.getIndice(), pm, fuerza + Math.random() * (1 - fuerza), alto);
//            }

      return new CommandHitBall(this.getIndice(), j.getPosicionActual(),
                                fuerza + Math.random() * (1 - fuerza), alto);
    }
    return null;
  }


  private Command paseAtras() {
    Jugador ppal = null;
    double diffPpal = -1;
    double distPpal = -1;
    Jugador sec = null;
    double diffSec = -1;
    double distSec = -1;

    //obtiene los jugadores mas cercanos, al jugador que puede rematar
    Jugador[] cercanos = this.getCompanerosCercanos();
    for (Jugador j : cercanos) {
      // El jugador que recibe esta mas adelantado
      if ((j.isDelantero() || j.isCentral())) {
        double dist = this.getPosicionActual().distance(j.getPosicionActual());
        // Distancia del rival mas cercano a este jugador
        double
            distRival =
            j.getPosicionActual().distance(j.getRivalMasCercano().getPosicionActual());

        if ((!j.existeMarcaje() || j.getMarcaje().isPortero()) && dist < 15) {
          double diff = distRival - dist;
          if (diff > 0 && diff > diffPpal) {
            ppal = j;
            diffPpal = diff;
            distPpal = dist;
          } else {
            diff = distRival - dist / 3;
            if (diff > 0 && diff > diffSec) {
              sec = j;
              diffSec = diff;
              distSec = dist;
            }
          }
        }
      }
    }
    Jugador j;
    j = (ppal != null) ? ppal : (sec != null) ? sec : null;
    double dist = (ppal != null) ? distPpal : (sec != null) ? distSec : -1;
    if (j != null) {
      double fuerza = 0.6;
      boolean alto = false;
      if (dist > 17) {
        alto = true;
        fuerza = 0.8;
      }
      return new CommandHitBall(this.getIndice(), j.getPosicionActual(),
                                fuerza + Math.random() * (1 - fuerza), alto);
    }
    return null;
  }

  private Command saqueCentro() {
    Jugador ppal = null;
    double diffPpal = -1;
    double distPpal = -1;
    Jugador sec = null;
    double diffSec = -1;
    double distSec = -1;

    //obtiene los jugadores mas cercanos, al jugador que puede rematar
    Jugador[] cercanos = this.getCompanerosCercanos();
    for (Jugador j : cercanos) {
      // El jugador que recibe esta mas adelantado
      if (j.isDefensa()) {
        double dist = this.getPosicionActual().distance(j.getPosicionActual());
        // Distancia del rival mas cercano a este jugador
        double
            distRival =
            j.getPosicionActual().distance(j.getRivalMasCercano().getPosicionActual());

        if (!j.existeMarcaje()) {
          double diff = distRival - dist;
          if (diff > 0 && diff > diffPpal) {
            ppal = j;
            diffPpal = diff;
            distPpal = dist;
          } else {
            diff = distRival - dist / 3;
            if (diff > 0 && diff > diffSec) {
              sec = j;
              diffSec = diff;
              distSec = dist;
            }
          }
        }
      }
    }
    Jugador j;
    j = (ppal != null) ? ppal : (sec != null) ? sec : null;
    double dist = (ppal != null) ? distPpal : (sec != null) ? distSec : -1;
    if (j != null) {
      double fuerza = 0.6;
      boolean alto = false;
      if (dist > 17) {
        alto = true;
        fuerza = 0.8;
      }
      return new CommandHitBall(this.getIndice(), j.getPosicionActual(),
                                fuerza + Math.random() * (1 - fuerza), alto);
    }
    return null;
  }
}
