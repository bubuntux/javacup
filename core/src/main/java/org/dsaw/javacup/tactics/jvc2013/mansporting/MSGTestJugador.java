/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaManager;
import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MaN
 */
public class MSGTestJugador extends MSGAbstractJugadorPropio {

  boolean calcular = false;
  boolean stop = false;
  int interacionesStart;
  int interacionesEnd;
  Map<Integer, Punto3D> puntoCreenciaMax = new HashMap<>();
  Map<Integer, Punto3D> puntoCreenciaMin = new HashMap<>();
  Map<Integer, Punto3D> puntoCreencia = new HashMap<>();
  Map<Integer, Punto3D> puntoReal = new HashMap<>();

  public MSGTestJugador(int index) {
    super(
        index,
        Color.BLUE,
        Color.BLUE,
        "Test",
        99,
        new MSGEstadisticas.Builder().setPrecision(1).setRemate(1).setVelocidad(1)
            .getMSGEstadisticas());
  }

  boolean golpeado = false;

  @Override
  public List<Command> ejecuta() {
    Punto3D
        puntoBalon =
        new Punto3D(context.getSituacionPartido().ballPosition().getX(),
                    context.getSituacionPartido().ballPosition().getY(),
                    context.getSituacionPartido().ballAltitude());
    if (stop) {
      return Arrays.<Command>asList(
          new CommandMoveTo(index, new Position(puntoBalon.getX(), puntoBalon.getY())));
    }

    if (calcular && context.getSituacionPartido().iteration() > interacionesEnd) {
      stop = true;
      calcular = false;
      try {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("C:/txt.txt")));
        for (int i = interacionesStart; i <= interacionesEnd; i++) {
          if (!puntoCreencia.containsKey(i) && !puntoCreenciaMax.containsKey(i) && !puntoCreenciaMin
              .containsKey(i) && !puntoReal.containsKey(i)) {
            break;
          }
          writer.write(
              String.format("REAL{%s,%s,%s}, MAX{%s,%s,%s}, MED{%s,%s,%s}, MIN{%s,%s,%s}\n",
                            puntoReal.containsKey(i) ? puntoReal.get(i).getX() : null,
                            puntoReal.containsKey(i) ? puntoReal.get(i).getY() : null,
                            puntoReal.containsKey(i) ? puntoReal.get(i).getZ() : null,
                            puntoCreenciaMax.containsKey(i) ? puntoCreenciaMax.get(i).getX() : null,
                            puntoCreenciaMax.containsKey(i) ? puntoCreenciaMax.get(i).getY() : null,
                            puntoCreenciaMax.containsKey(i) ? puntoCreenciaMax.get(i).getZ() : null,
                            puntoCreencia.containsKey(i) ? puntoCreencia.get(i).getX() : null,
                            puntoCreencia.containsKey(i) ? puntoCreencia.get(i).getY() : null,
                            puntoCreencia.containsKey(i) ? puntoCreencia.get(i).getZ() : null,
                            puntoCreenciaMin.containsKey(i) ? puntoCreenciaMin.get(i).getX() : null,
                            puntoCreenciaMin.containsKey(i) ? puntoCreenciaMin.get(i).getY() : null,
                            puntoCreenciaMin.containsKey(i) ? puntoCreenciaMin.get(i).getZ()
                                                            : null));
        }
        writer.flush();
        writer.close();
        return Collections.emptyList();
      } catch (Throwable e) {
        return Collections.emptyList();
      }
    }

    if (calcular) {
      puntoReal.put(
          context.getSituacionPartido().iteration(),
          puntoBalon);
    }

    if (getPuedeRematar()) {
      if (puntoBalon.getY() > -3 * Constants.LARGO_CAMPO_JUEGO / 8) {
        return Arrays.<Command>asList(
            new CommandMoveTo(index, new Position(puntoBalon.getX(),
                                                  -3 * Constants.LARGO_CAMPO_JUEGO / 8)),
            new CommandHitBall(index));
      }

      calcular = true;
      Punto destino = new Punto(Constants.centroArcoSup);
      TrayectoriaPunto
          candidato = TrayectoriaManager.getInstance().getDisparosByDistancia(
          TrigonometriaUtils
              .getDistancia(context.getBalon().getPosicion().getPuntoXY(), destino));
      MSGTrayectoria.DisparoTrayectoria
          disparo =
          MSGTrayectoria.getInstance(candidato, this, puntoBalon,
                                     new Punto3D(Constants.centroArcoSup.getX(),
                                                 Constants.centroArcoSup.getY(), 0));
      interacionesStart = context.getSituacionPartido().iteration();
      interacionesEnd = interacionesStart + disparo.getIteracionMax();
      for (int i = 0; i < disparo.getIteracionMax(); i++) {
        puntoCreencia
            .put(context.getSituacionPartido().iteration() + i, disparo.getPosicion(i).getPunto());
        puntoCreenciaMax.put(context.getSituacionPartido().iteration() + i,
                             disparo.getPosicion(i).getPuntoMax());
        puntoCreenciaMin.put(context.getSituacionPartido().iteration() + i,
                             disparo.getPosicion(i).getPuntoMin());
      }

      double
          fuerza =
          candidato.getTrayectoria().getVelocidad() / Constants
              .getVelocidadRemate(getEstadisticas().getRemate());
      return Arrays.<Command>asList(new CommandHitBall(
          index,
          new Position(destino.getX(), destino.getY()),
          fuerza,
          candidato.getTrayectoria().getAnguloVertical()));
    } else {
      return Arrays.<Command>asList(
          new CommandMoveTo(index, new Position(puntoBalon.getX(), puntoBalon.getY())));
    }
  }

  @Override
  public double getIteracionesVentajaPase() {
    return MSGConstants.ITERACIONES_MIMIMAS_VENTAJA_DELANTERO;
  }
}
