package org.dsaw.javacup.tactics.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;

import java.util.ArrayList;

public class Defensa extends Jugador {


  public Defensa(int x) {
    super(x);
    yMAX = 0;
    yMin = -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_CHICA;

  }


  @Override
  public ArrayList<Command> accionRemate() {
    ArrayList<Command> accion = new ArrayList<>();

    //Si estamos en las proximidades del area chica despejamos
    if (entrenador.Gs.ballPosition().getY() <= Constants.penalInf.getY() * 1.2) {
      accion.add(new CommandHitBall(numero, 90, 1, true));
      return accion;
    }

    //Seleccionamos el mejor Pase entre todos nuestros jugadores pero siempre hacia adelante

    //Comprobamos que haya algun pase posible
    PaseInfo paseInf = seleccionarPase(15, 165, 3);

    if (paseInf != null) {
      accion.add(new CommandMoveTo(numero,
                                   posicionJugador.moveAngle(paseInf.angulo * (180 / Math.PI), 1)));
      accion.add(new CommandHitBall(numero, paseInf.angulo * (180 / Math.PI),
                                    (entrenador.Gs.isStarts()) ? paseInf.fuerza / 0.75
                                                               : paseInf.fuerza,
                                    paseInf.anguloVert * (180 / Math.PI)));

      //el jugador m�s proximo al punto destino debe acercarse a por el balon

      int jugReceptorPase = paseInf.calidad.posFinalBalon.nearestIndex(entrenador.Gs.myPlayers());

      accion.add(new CommandMoveTo(jugReceptorPase, paseInf.calidad.posFinalBalon));

      entrenador.jugadoresDisponibles.remove(new Integer(jugReceptorPase));
    }

    if (accion.size() == 0) {
      //Si no hay pase posible despejamos
      accion.add(new CommandMoveTo(numero, posicionJugador.moveAngle(posicionJugador.angle(
          entrenador.Gs.myPlayers()[Constants.centroArcoSup
              .nearestIndex(entrenador.Gs.myPlayers())]) * (180 / Math.PI), 1)));
      accion.add(new CommandHitBall(numero, posicionJugador.angle(
          entrenador.Gs.myPlayers()[Constants.centroArcoSup
              .nearestIndex(entrenador.Gs.myPlayers())]) * (180 / Math.PI), 1, true));

    }
    return accion;
  }


}
