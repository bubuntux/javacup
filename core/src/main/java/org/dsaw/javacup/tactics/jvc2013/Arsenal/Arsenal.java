/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.Arsenal;


import com.neovisionaries.i18n.CountryCode;


import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Arsenal implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-13.076923076923078, -32.54524886877828),
      new Position(-2.6153846153846154, -32.07013574660634),
      new Position(6.895104895104895, -32.782805429864254),
      new Position(1.902097902097902, -32.782805429864254),
      new Position(-8.55944055944056, -32.30769230769231),
      new Position(-17.832167832167833, -32.07013574660634),
      new Position(11.412587412587413, -32.782805429864254),
      new Position(-11.412587412587413, -20.429864253393667),
      new Position(-1.188811188811189, -20.667420814479637),
      new Position(8.321678321678322, -20.90497737556561)
  };

  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -31.082089552238806),
      new Position(11.16030534351145, -31.6044776119403),
      new Position(27.251908396946565, -27.94776119402985),
      new Position(-29.84732824427481, -26.902985074626866),
      new Position(8.564885496183205, -7.574626865671642),
      new Position(-10.641221374045802, -7.052238805970149),
      new Position(27.251908396946565, 4.440298507462686),
      new Position(-29.32824427480916, 3.3955223880597014),
      new Position(-0.2595419847328244, 19.067164179104477),
      new Position(-0.2595419847328244, 35.78358208955224)
  };

  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -31.082089552238806),
      new Position(11.16030534351145, -31.6044776119403),
      new Position(26.732824427480914, -20.111940298507463),
      new Position(-29.32824427480916, -21.67910447761194),
      new Position(0.2595419847328244, -0.26119402985074625),
      new Position(-18.946564885496183, -0.26119402985074625),
      new Position(18.946564885496183, -0.26119402985074625),
      new Position(-19.46564885496183, 35.78358208955224),
      new Position(-0.2595419847328244, 19.067164179104477),
      new Position(18.946564885496183, 35.26119402985075)
  };

  Position alineacion4[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -31.082089552238806),
      new Position(11.16030534351145, -31.6044776119403),
      new Position(28.290076335877863, -28.470149253731343),
      new Position(-28.290076335877863, -28.470149253731343),
      new Position(11.16030534351145, -1.3059701492537314),
      new Position(-10.641221374045802, -0.7835820895522387),
      new Position(-27.251908396946565, 31.6044776119403),
      new Position(-10.641221374045802, 30.559701492537314),
      new Position(9.603053435114505, 28.992537313432837),
      new Position(25.69465648854962, 28.992537313432837)
  };

  Position alineacion5[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-5.706293706293707, -23.993212669683256),
      new Position(-1.6643356643356644, -24.468325791855204),
      new Position(8.797202797202797, -24.468325791855204),
      new Position(-15.692307692307693, -24.943438914027148),
      new Position(3.804195804195804, -24.468325791855204),
      new Position(-10.223776223776223, -24.468325791855204),
      new Position(-16.643356643356643, -16.628959276018097),
      new Position(5.969465648854961, -5.485074626865671),
      new Position(0.2595419847328244, -0.26119402985074625),
      new Position(7.846153846153847, -18.054298642533936)
  };

  Position alineacion6[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-9.986013986013985, -28.269230769230766),
      new Position(8.083916083916083, -28.98190045248869),
      new Position(15.692307692307693, -28.98190045248869),
      new Position(-14.74125874125874, -28.98190045248869),
      new Position(2.377622377622378, -28.50678733031674),
      new Position(-2.6153846153846154, -28.50678733031674),
      new Position(-19.020979020979023, -28.269230769230766),
      new Position(1.4265734265734267, -15.441176470588236),
      new Position(-7.37062937062937, -15.441176470588236),
      new Position(11.65034965034965, -14.96606334841629)
  };


  class TacticaDetalleImpl extends Team {

    @Override
    public String name() {
      return "Arsenal";
    }

    @Override
    public CountryCode countryCode() {
      return CountryCode.CO;
    }

    /*
    @Override
    public Color getShirtColor() {
      return new Color(255, 51, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(255, 51, 0);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(255, 51, 0);
    }

    @Override
    public Color getSocksColor() {
      return new Color(2, 206, 51);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 0, 0);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.LINEAS_VERTICALES;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(10, 171, 214);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.FRANJA_DIAGONAL;
    }
*/
    /*@Override
    public Player[] getPlayers() {
      return new Player[]{
          new Player("Christofer", (byte) 1, true, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                     1.0d, 1.0d),
          new JugadorImpl("Ruben", 2, new Color(0, 0, 0), new Color(0, 0, 0), 1.0d, 0.53d, 0.78d,
                          false),
          new JugadorImpl("Gabriel", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Gari", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Miguel", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Hugo", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Mark", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Mauricio", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d,
                          0.78d, false),
          new JugadorImpl("Humberto", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Mago", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.81d, false),
          new JugadorImpl("Alexis", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false)
      };
    }*/
  }

  ITeam detalle = new TacticaDetalleImpl();

  @Override
  public ITeam getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
  }

  //Lista de comandos
  LinkedList<Command> comandos = new LinkedList<>();

  private boolean enFormacion = false;
  private int jugadorConBalon = -1;

  @Override
  public List<Command> execute(GameSituations sp) {
    //Limpia la lista de comandos
    comandos.clear();

    //Si puede rematar el enemigo entonces debemos ponernos en defensa
    if (sp.rivalCanKick().length > 0) {
      enFormacion = false;
    }

    //Si podemos rematar significa que nos ponemos en formacion de ataque
    if (sp.canKick().length > 0) {
      enFormacion = true;
    }

    if (!enFormacion) {
      jugadorConBalon = -1;
    }

    //Si no estamos en formacion de ataque nos ponemos a defender
    if (enFormacion) {
      //Rodeamos al jugador que tiene el balon
      if (jugadorConBalon == -1) {
        jugadorConBalon = sp.canKick()[0];
      }

      Position posJugBalon = sp.myPlayers()[jugadorConBalon];

      //El jugador con el balon golpea y avanza con el balon
      comandos.add(new CommandHitBall(jugadorConBalon));
      comandos.add(new CommandMoveTo(jugadorConBalon, Constants.centroArcoSup));

      //El resto los posicionamos alrededor suya
      for (int x = 1; x < 11; ++x) {

      }
    } else {
      //Posicionamos al jugador 4 conforme a la posicion de la pelota y el resto lo siguen
      //excepto los delanteros que persiguen siempre al balon

      //Delantero 9

      Position
          nuevaPos =
          new Position(
              (sp.ballPosition().getX() < 0) ? sp.ballPosition().getX() : sp.myPlayers()[9].getX(),
              sp.ballPosition().getY());
      comandos.add(new CommandMoveTo(9, nuevaPos));
      //Delantero 8

      nuevaPos =
          new Position(
              (sp.ballPosition().getX() > 0) ? sp.ballPosition().getX() : sp.myPlayers()[8].getX(),
              sp.ballPosition().getY());
      comandos.add(new CommandMoveTo(8, nuevaPos));
      //Delantero 10

      nuevaPos =
          new Position((Math.abs(sp.ballPosition().getX()) < Constants.ANCHO_CAMPO_JUEGO / 4) ? sp
              .ballPosition().getX() : sp.myPlayers()[10].getX(), sp.ballPosition().getY());
      comandos.add(new CommandMoveTo(10, nuevaPos));

      //Posicionamos al jugador 4
      nuevaPos =
          new Position((Math.abs(sp.ballPosition().getX()) < Constants.ANCHO_CAMPO_JUEGO / 3) ? sp
              .ballPosition().getX() : sp.myPlayers()[4].getX(), sp.ballPosition().getY() - 0.5);
      comandos.add(new CommandMoveTo(4, nuevaPos));

      //Posicionamos al resto conforme al jugador cuatro

      //Distancia de separacion
      double sepDist = 0.75;
      //Jugador 1
      comandos.add(new CommandMoveTo(1, nuevaPos.movePosition(-sepDist * 3, 0)));

      //Jugador 2
      comandos.add(new CommandMoveTo(2, nuevaPos.movePosition(-sepDist * 2, 0)));

      //Jugador 3
      comandos.add(new CommandMoveTo(3, nuevaPos.movePosition(-sepDist, 0)));

      //Jugador 5
      comandos.add(new CommandMoveTo(5, nuevaPos.movePosition(sepDist, 0)));

      //Jugador 6
      comandos.add(new CommandMoveTo(6, nuevaPos.movePosition(sepDist * 2, 0)));

      //Jugador 7
      comandos.add(new CommandMoveTo(7, nuevaPos.movePosition(sepDist * 3, 0)));


    }

    //Vemos si podemos recuperar el balon
    if (!sp.isRivalStarts()) {

      int[] recuperadores = sp.getRecoveryBall();

      if (recuperadores.length > 1) {
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);

        for (int i = 1; i < 2; i++) {
          comandos.add(new CommandMoveTo(recuperadores[i],
                                         new Position(posRecuperacion[0], posRecuperacion[1])));
        }
      }
    }

    //Retorna la lista de comandos*/
    return comandos;
  }
}
