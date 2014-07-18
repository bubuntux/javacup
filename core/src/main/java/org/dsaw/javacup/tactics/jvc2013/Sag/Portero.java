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
public class Portero extends Jugador {

  private Random rnd = new Random();

  public Portero(int idx, Player detalle, Position posRef) {
    super(idx, detalle, posRef);
    this.setTipo(Jugador.TIPO_PORTERO);
  }

  public Portero(int idx, Player detalle) {
    super(idx, detalle);
    this.setTipo(Jugador.TIPO_PORTERO);
  }

  @Override
  public Command irAPosicionDestino() {
    // Si el portero sale de su zona vuelve a la pos de ref
    if (sp.isRivalStarts() ||
        sp.isStarts() ||
        Math.abs(this.getPosicionActual().getX()) > Constants.ANCHO_CAMPO_JUEGO / 8 ||
        Math.abs(this.getPosicionActual().getY()) < Constants.penalSup.getY()) {
      this.setPosicionDestino(this.getPosicionReferencia());
    } else {
      // El portero oscila hacia la pelota si el ballPosition esta en mi campo
      if (!this.estaBalonEnCampoRival()) {
        double
            x =
            (this.getPosicionBalon().getX() * (Constants.LARGO_ARCO / 2) - 0.5) / (
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

    // Si la distancia es peq patadon a central
    if (!sp.isStarts() || dist < 5) {
      int idx = rnd.nextInt(11);
      do {
        idx = rnd.nextInt(11);
      } while (!jugadores[idx].isCentral());
      return new CommandHitBall(this.getIndice(), jugadores[idx].getPosicionActual(), 1, true);
    } else {
      Command c = this.paseAdelantado();
      if (c != null) {
        return c;
      }

      // No hay pase => DESMARCAR CENTRALES
//            int idx = rnd.nextInt(11);
//            do {
//               idx = rnd.nextInt(11);
//            } while (!jugadores[idx].isCentral());
//            return new CommandHitBall(this.getIndice(), jugadores[idx].getPosicionActual(), 1, Constants.ANGULO_VERTICAL_MAX);

      if (rnd.nextBoolean()) {
        return new CommandHitBall(this.getIndice(), Constants.centroCampoJuego,
                                  .8 + Math.random() * .2, true);
      } else if (rnd.nextBoolean()) {
        return new CommandHitBall(this.getIndice(), Constants.cornerSupIzq, .8 + Math.random() * .2,
                                  true);
      } else {
        return new CommandHitBall(this.getIndice(), Constants.cornerSupDer, .8 + Math.random() * .2,
                                  true);
      }

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
      if (j.isDefensa() || j.isCentral() &&
                           this.getPosicionActual().getY() < j.getPosicionActual().getY()) {
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
