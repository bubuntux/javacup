/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.Sag;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.Random;

/**
 * @author pdsanchez
 */
public class Delantero extends Jugador {

  private Random rnd = new Random();

  private boolean isPaseAtras = false;

  public Delantero(int idx, PlayerDetail detalle, Position posRef) {
    super(idx, detalle, posRef);
    this.setTipo(Jugador.TIPO_DELANTERO);
  }

  public Delantero(int idx, PlayerDetail detalle) {
    super(idx, detalle);
    this.setTipo(Jugador.TIPO_DELANTERO);
  }

  @Override
  public Command irAPosicionDestino() {
    //throw new UnsupportedOperationException("Not supported yet.");
    if (sp.isStarts() && this.getCompanero(sp.ballPosition()).isDelantero()) {
      this.setPosicionDestino(Position.medium(this.getPosicionReferencia(), sp.ballPosition()));
    } else if (sp.isStarts() &&
               this.getCompanero(sp.ballPosition()).isCentral() &&
               Math.abs(sp.ballPosition().getY()) > Constants.LARGO_CAMPO_JUEGO / 4) {
      this.setPosicionDestino(Position.medium(this.getPosicionReferencia(), sp.ballPosition()));
    }
    // El delantero oscila hacia la pelota
    else {
      if (this.estaBalonEnCampoRival()) {
        double
            x =
            (this.getPosicionBalon().getX() * (Constants.ANCHO_CAMPO_JUEGO / 6)) / (
                Constants.ANCHO_CAMPO_JUEGO / 2);
        this.setPosicionDestino(this.getPosicionReferencia().getX() + x,
                                this.getPosicionReferencia().getY());
      } else {
        this.setPosicionDestino(this.getPosicionReferencia());
      }
    }
    return new CommandMoveTo(this.getIndice(), this.getPosicionDestino());
  }

  @Override
  public Command GolpearBalon() {
    // Distancia del rival mas cercano a este jugador
    double dist = this.getPosicionActual().distance(this.getRivalMasCercano().getPosicionActual());
    // Donde esta el portero rival
    Position posPortero = this.getPorteroRival().getPosicionActual();
    // Si la distancia es grande este jugador avanza con el ballPosition
    if (!sp.isStarts() && dist > 20) {
      if (posPortero.distance(Constants.posteDerArcoSup) > posPortero
          .distance(Constants.posteDerArcoInf)) {
        return new CommandHitBall(this.getIndice(), Constants.posteIzqArcoSup, 0.3, false);
      } else {
        return new CommandHitBall(this.getIndice(), Constants.posteDerArcoSup, 0.3, false);
      }
    }
    // Saque de centro
    else if (sp.ballPosition().getX() == 0 && sp.ballPosition().getY() == 0) {
      Command c = this.saqueCentro();
      if (c != null) {
        return c;
      }
    }

    // Posicion de este jugador respecto a la porteria
    boolean isDentroArea = this.getPosicionActual().getY() > Constants.penalSup.getY();
    boolean
        isLateral =
        Math.abs(this.getPosicionActual().getX()) > Math.abs(Constants.posteDerArcoSup.getX() * 2);

    if (isDentroArea) {
      if (isPaseAtras) {
        isPaseAtras = false;
        // Disparar a gol
        return this.disparar(posPortero);
      }
      if (isLateral) {
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

    // Disparar a gol
    return this.disparar(posPortero);
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

        if (!j.existeMarcaje() || j.getMarcaje().isPortero()) {
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
      if (this.getPosicionActual().getY() < j.getPosicionActual().getY()) {
        double dist = this.getPosicionActual().distance(j.getPosicionActual());

        if (!j.existeMarcaje()) {
          // Distancia del rival mas cercano a este jugador
          double
              distRival =
              j.getPosicionActual().distance(j.getRivalMasCercano().getPosicionActual());

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
//            if (dist > 25) {
//                alto = true;
//                fuerza = 0.7;
//            }
      return new CommandHitBall(this.getIndice(), j.getPosicionActual(),
                                fuerza + Math.random() * (1 - fuerza), alto);
    }
    return null;
  }
}
