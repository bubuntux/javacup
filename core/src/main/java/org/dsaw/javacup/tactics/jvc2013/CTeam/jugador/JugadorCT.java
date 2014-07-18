package org.dsaw.javacup.tactics.jvc2013.CTeam.jugador;

import org.dsaw.javacup.model.PlayerI;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.ICTeam2011;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.PosicionCT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JugadorCT extends AbstractJugador {

  public enum Tipo {
    DEFENSA, MEDIO, LATERAL, ATAQUE
  }

  protected final Tipo tipo;

  public JugadorCT(Equipo equipo, int indice, PlayerI detalle, ICTeam2011 tactica, Tipo tipo) {
    super(equipo, indice, detalle, tactica);
    this.tipo = tipo;
  }

  @Override
  public Collection<? extends Command> jugar() {
    List<Command> comandos = new ArrayList<>();
    if (destinoDisparo != null && disparo != null) {
      tactica.ultimoPase(destinoDisparo);
      comandos.add(new CommandHitBall(getIndice(), destinoDisparo.getPos(), disparo.fuerza,
                                      disparo.anguloZ));
    }

    if (isControlBalon() && partido.isStarts()) {
      if (Math.abs(partido.ballPosition().getX()) < Constants.LARGO_AREA_CHICA + 10) {
        int rc = partido.ballPosition().nearestIndex(partido.rivalPlayers());
        double dr = partido.ballPosition().distance(partido.rivalPlayers()[rc]);
        double angulo = dr < 5 ? 60 : 45;
        comandos.add(new CommandHitBall(getIndice(), Constants.centroArcoSup, 1.0, angulo));
      } else {
        double angulo = getActual().getPos().angle(Constants.centroArcoSup);
        Position p = getActual().getPos().moveAngle(angulo, 1.0);
        if (!p.isInsideGameField(0.0)) {
          p = getActual().getPos().moveAngle(-angulo, 1.0);
        }
        comandos.add(new CommandMoveTo(getIndice(), p));
        comandos.add(new CommandHitBall(getIndice(), p, 0.05, false));
      }
      return comandos;
    }

    PosicionCT arcoSup = new PosicionCT(Constants.centroArcoSup);

    if (isControlBalon() && getActual().getY() > Constants.ANCHO_AREA_GRANDE
        && Math.abs(getActual().getX()) > Constants.LARGO_AREA_GRANDE) {
      // hacer un centro
      comandos.add(new CommandHitBall(getIndice(), Constants.penalSup, 0.75, 45));
    } else if (isControlBalon() && getActual().distancia(arcoSup) < 18) {
      tactica.ultimoPase(arcoSup);
      comandos.add(disparoArco());
    } else if (isControlBalon() && getActual().distancia(arcoSup) < 25) {
      tactica.ultimoPase(arcoSup);
      comandos.add(new CommandHitBall(getIndice(), Constants.centroArcoSup, 1.0, 15));
    }

    if (comandos.isEmpty()) {
      if (isControlBalon()) {
        comandos.add(new CommandHitBall(getIndice(), Constants.centroArcoSup, 1.0, 20));
      } else {
        comandos.add(new CommandMoveTo(getIndice(), siguiente.getPos()));
      }
    }

    return comandos;
  }

  private CommandHitBall disparoArco() {
    final Position pd = Constants.posteDerArcoSup;
    final Position pi = Constants.posteIzqArcoSup;
    final PosicionCT pdct = new PosicionCT(pd);
    final PosicionCT pict = new PosicionCT(pi);
    final PosicionCT balon = new PosicionCT(partido.ballPosition());
    double delta = Constants.getErrorAngular(getDetalle().getPrecision());
    delta = (delta - delta / 2.0) * 1.2;
    PosicionCT des = new PosicionCT(Constants.centroArcoSup);
    double d = balon.distancia(des);
    int pa = Constants.centroArcoSup.nearestIndex(partido.rivalPlayers());
    PosicionCT k = new PosicionCT(partido.rivalPlayers()[pa]);
    double d1 = k.distanciaALinea(balon, pict, true);
    double d2 = k.distanciaALinea(balon, pdct, true);
    if (d1 < d2) {
      double a = balon.angulo(pdct);
      a = a + delta;
      des = balon.moverAngulo(a, d * 1.2);
    } else {
      double a = balon.angulo(pict);
      a = a - delta;
      des = balon.moverAngulo(a, d * 1.2);
    }
    return new CommandHitBall(getIndice(), des.getPos(), 1.0, 10.0);
  }
}
