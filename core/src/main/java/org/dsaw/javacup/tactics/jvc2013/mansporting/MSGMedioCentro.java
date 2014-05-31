/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaManager;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;

/**
 * @author MaN
 */
public class MSGMedioCentro extends MSGAbstractJugadorPropio {

  public MSGMedioCentro(int index, Color colorPelo, Color colorPiel, String nombre, int numero,
                        MSGEstadisticas estadisticas) {
    super(index, colorPelo, colorPiel, nombre, numero, estadisticas);
  }

  @Override
  protected Command despeja() {
    Command tiro = tira(new Punto(Constants.centroArcoSup));
    if (tiro == null) {
      double distancia = TrigonometriaUtils
          .getDistancia(posicion, new Punto(Constants.centroArcoSup));
      TrayectoriaPunto
          pase = TrayectoriaManager.getInstance().getDisparosByDistancia(distancia);
      double
          fuerza =
          pase.getTrayectoria().getVelocidad() / Constants
              .getVelocidadRemate(getEstadisticas().getRemate());
      tiro = new CommandHitBall(
          index,
          Constants.centroArcoSup,
          fuerza,
          pase.getTrayectoria().getAnguloVertical());

    }
    return tiro;
  }

  @Override
  public double getIteracionesVentajaPase() {
    return MSGConstants.ITERACIONES_MIMIMAS_VENTAJA_MEDIO;
  }


}
