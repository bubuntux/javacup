package org.dsaw.javacup.tactics.jvc2013.tukutuku;

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
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;

public class Portero extends Jugador {


  public Portero(int x) {
    super(x);
  }


  @Override
  public ArrayList<Command> accionDefensa() {
    ArrayList<Command> accion = new ArrayList<>();

    //Colocamos el portero segun la posicion del balon

    Position posBalon = entrenador.Gs.ballPosition();
    Position posPortero = new Position();

    //Si el balon est� en la banda izquierda nos vamos al poste izquierdo
    if (posBalon.getX() < Constants.posteIzqArcoInf.getX()) {
      posPortero =
          posPortero.setPosition(Constants.posteIzqArcoInf.getX() + 1, (posBalon.getY() <
                                                                        Constants.posteDerArcoInf
                                                                            .getY()
                                                                        + Constants.ANCHO_AREA_CHICA)
                                                                       ? posBalon.getY() :
                                                                       Constants.posteDerArcoInf
                                                                           .getY()
                                                                       + Constants.ANCHO_AREA_CHICA);
      accion.add(new CommandMoveTo(numero, posPortero));
    } else {
      //Si est� en la banda derecha al poste derecho

      if (posBalon.getX() > Constants.posteDerArcoInf.getX()) {
        posPortero =
            posPortero.setPosition(Constants.posteDerArcoInf.getX() - 1, (posBalon.getY() <
                                                                          Constants.posteDerArcoInf
                                                                              .getY()
                                                                          + Constants.ANCHO_AREA_CHICA)
                                                                         ? posBalon.getY() :
                                                                         Constants.posteDerArcoInf
                                                                             .getY()
                                                                         + Constants.ANCHO_AREA_CHICA);
        accion.add(new CommandMoveTo(numero, posPortero));
      } else {

        //Si est� en el centro del campo nos ponemos frente a �l

        posPortero =
            posPortero.setPosition(posBalon.getX(),
                                   Constants.posteDerArcoInf.getY() + Constants.ANCHO_AREA_CHICA);
        accion.add(new CommandMoveTo(numero, posPortero));

      }
    }
    return accion;
  }

  @Override
  public ArrayList<Command> accionRecuperacion(Position recuperacionPos) {

    ArrayList<Command> accion = new ArrayList<>();

    accion.add(new CommandMoveTo(numero, recuperacionPos));

    return accion;
  }

  @Override
  public ArrayList<Command> accionRemate() {

    ArrayList<Command> accion = new ArrayList<>();
    accion.add(new CommandHitBall(numero, posicionJugador.angle(
        entrenador.Gs.myPlayers()[Constants.centroArcoSup.nearestIndex(entrenador.Gs.myPlayers())])
                                          * (180 / Math.PI), 1, true));
    return accion;
  }


}