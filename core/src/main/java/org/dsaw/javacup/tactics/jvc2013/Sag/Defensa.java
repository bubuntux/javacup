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
 *
 * @author pdsanchez
 */
public class Defensa extends Jugador {

    private static final double QUINTO_DE_CAMPO = Constants.LARGO_CAMPO_JUEGO / 5;
    private Random rnd = new Random();
    private boolean desmarcado;

    public Defensa(int idx, PlayerDetail detalle, Position posRef) {
        super(idx, detalle, posRef);
        this.setTipo(Jugador.TIPO_DEFENSA);
    }

    public Defensa(int idx, PlayerDetail detalle) {
        super(idx, detalle);
        this.setTipo(Jugador.TIPO_DEFENSA);
    }

    @Override
    public Command irAPosicionDestino() {
        if (sp.isStarts() && this.getCompanero(sp.ballPosition()).isDefensa()) {
            this.setPosicionDestino(Position.medium(this.getPosicionReferencia(), sp.ballPosition()));
        }
        // Solo uno puede marcar
        else if (this.existeMarcaje() &&
                this.getMarcaje().existeMarcaje() &&
                this.getMarcaje().getMarcaje().getIndice() != this.getIndice()) {
            this.setPosicionDestino(this.getPosicionReferencia());
        }
        // No permitir que suban mucho
        else if(Math.abs(this.getPosicionActual().getY()) < QUINTO_DE_CAMPO) {
            this.setPosicionDestino(this.getPosicionReferencia());
        } else {
            if (this.existeMarcaje()) {
                // Cubrir por delante si el atacante esta lejos de la porteria
                if (Math.abs(this.getMarcaje().getPosicionActual().getY()) < Constants.LARGO_AREA_GRANDE) {
                    this.setPosicionDestino(this.getMarcaje().getPosicionActual().getX(), this.getMarcaje().getPosicionActual().getY() + 2);
                } // Cubrir por detras
                else {
                    this.setPosicionDestino(this.getMarcaje().getPosicionActual().getX(), this.getMarcaje().getPosicionActual().getY() - 3);
                }
            } else {
                Jugador rCercano = this.getRivalMasCercano();
                // Lista de rivalPlayers proximos a la porteria que no tengan marca
                for (Jugador rival : rivales) {
                    if (!rival.estaJugadorEnCampoRival()
                            && Math.abs(rival.getPosicionActual().getY()) > QUINTO_DE_CAMPO
                            && !rival.existeMarcaje()) {
                        // Si este rival es cercano a este jugador, los emparejo
                        if (rCercano.getIndice() == rival.getIndice()) {
                            this.setPosicionDestino(rival.getPosicionActual());
                            break;
                        } // Si no es asi,el jugador oscila
                        else {
                            double x = (this.getPosicionBalon().getX() * (Constants.ANCHO_CAMPO_JUEGO / 6)) / (Constants.ANCHO_CAMPO_JUEGO / 2);
                            // Avanzar o no segun random
                            if (rnd.nextBoolean()) {
                                double y = (this.getPosicionBalon().getY() * (Constants.LARGO_CAMPO_JUEGO / 8)) / (Constants.LARGO_CAMPO_JUEGO / 2);
                                this.setPosicionDestino(this.getPosicionReferencia().getX() + x, this.getPosicionReferencia().getY() + y);
                            } else {
                                this.setPosicionDestino(this.getPosicionReferencia().getX() + x, this.getPosicionReferencia().getY());
                            }
                            break;
                        }
                    }
                }
            }
        }
        // Limitar el retroceso de los defensas, que no se metan en la porteria

        return new CommandMoveTo(this.getIndice(), this.getPosicionDestino());
    }

    @Override
    public Command GolpearBalon() {
        Command c = this.paseAdelantado();
        if (c != null) {
            return c;
        }
        
        // Distancia del rival mas cercano a este jugador
        double dist = this.getPosicionActual().distance(this.getRivalMasCercano().getPosicionActual());
        if (!sp.isStarts() && dist > 10) {
            if (rnd.nextBoolean()) {
                return new CommandHitBall(this.getIndice(), Constants.cornerSupIzq, 0.4, false);
            }
            else {
                return new CommandHitBall(this.getIndice(), Constants.cornerSupDer, 0.4, false);
            }
        }
        else {
            return new CommandHitBall(this.getIndice(), Constants.posteDerArcoSup, 1, true);
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
            if (this.getPosicionActual().getY() < j.getPosicionActual().getY()) {
                double dist = this.getPosicionActual().distance(j.getPosicionActual());
                // Distancia del rival mas cercano a este jugador
                double distRival = j.getPosicionActual().distance(j.getRivalMasCercano().getPosicionActual());

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
        Jugador j = null;
        j = (ppal != null) ? ppal : (sec != null) ? sec : null;
        double dist = (ppal != null) ? distPpal : (sec != null) ? distSec : -1;
        if (j != null) {
            double fuerza = 0.6;
            boolean alto = false;
            if (dist > 17) {
                alto = true;
                fuerza = 0.8;
            }
            if (j.isDefensa() || j.isCentral()) {
                return new CommandHitBall(this.getIndice(), j.getPosicionActual(), fuerza + Math.random() * (1 - fuerza), alto);
            }
            else if (j.isDelantero()) {
                return new CommandHitBall(this.getIndice(), j.getPosicionActual(), 1, true);
            }
        }
        return null;
    }
}
