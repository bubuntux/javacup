/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.Arsenal3;


import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.IPlayer;
import org.dsaw.javacup.model.ITeam;
import org.dsaw.javacup.model.Tactic;
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
import java.util.Random;

public class Arsenal3 implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.951048951048951, -49.64932126696832),
      new Position(-19.020979020979023, -31.59502262443439),
      new Position(0.7132867132867133, -28.50678733031674),
      new Position(19.25874125874126, -31.59502262443439),
      new Position(1.6643356643356644, -7.126696832579185),
      new Position(-15.692307692307693, -7.364253393665159),
      new Position(-23.3006993006993, 11.877828054298643),
      new Position(17.594405594405593, 12.115384615384617),
      new Position(-26.867132867132867, 35.8710407239819),
      new Position(-5.468531468531468, 16.628959276018097),
      new Position(-1.188811188811189, 40.38461538461539)
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
      new Position(-11.16030534351145, -35.78358208955224),
      new Position(12.717557251908397, -35.26119402985075),
      new Position(28.290076335877863, -28.470149253731343),
      new Position(-28.290076335877863, -28.470149253731343),
      new Position(14.793893129770993, -18.544776119402986),
      new Position(-17.389312977099234, -19.58955223880597),
      new Position(-23.618320610687025, -0.7835820895522387),
      new Position(5.969465648854961, -5.485074626865671),
      new Position(0.2595419847328244, -0.26119402985074625),
      new Position(22.580152671755727, -1.3059701492537314)
  };
  Position alineacion6[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -35.78358208955224),
      new Position(12.717557251908397, -35.26119402985075),
      new Position(28.290076335877863, -28.470149253731343),
      new Position(-28.290076335877863, -28.470149253731343),
      new Position(14.793893129770993, -18.544776119402986),
      new Position(-17.389312977099234, -19.58955223880597),
      new Position(-23.618320610687025, -0.7835820895522387),
      new Position(6.4885496183206115, -6.529850746268657),
      new Position(-6.4885496183206115, -6.529850746268657),
      new Position(22.580152671755727, -1.3059701492537314)
  };

  class TacticaDetalleImpl implements ITeam {

    @Override
    public String getName() {
      return "Arsenal";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.CO;
    }

    @Override
    public String getCoachName() {
      return "Tales";
    }

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

    @Override
    public IPlayer[] getPlayers() {
      return new IPlayer[]{
          new JugadorImpl("Christofer", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, true),
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
    }
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

  @Override
  public List<Command> execute(GameSituations sp) {
    //Limpia la lista de comandos
    comandos.clear();
    //Obtiene las posiciones de tus jugadores
    Position[] jugadores = sp.myPlayers();
    Position posicionPorteroContrario = sp.rivalPlayers()[0];
    for (int i = 0; i < jugadores.length; i++) {
      //Ordena a cada jugador que se ubique segun la alineacion1
      if (i != 10) {
        comandos.add(new CommandMoveTo(i, alineacion1[i]));
      } else {
        Position posicionBalon = new Position(sp.getTrajectory(i)[0], sp.getTrajectory(i)[1]);
        //  comandos.add(new ComandoIrA(i,Posicion.media(posicionPorteroContrario, posicionBalon)));
        Position posicionMedia = Position.medium(posicionPorteroContrario, jugadores[8]);
        posicionMedia = Position.medium(posicionPorteroContrario, posicionMedia);
        comandos
            .add(new CommandMoveTo(i, Position.medium(posicionPorteroContrario, posicionMedia)));
      }

    }
    //Si no saca el rival
    if (!sp.isRivalStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde
        //se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        //Recorre la lista de jugadores que pueden recuperar
        for (int i = 1; i < 2; i++) {
          //Ordena a los jugadores recuperadores que se ubique
          //en la posicion de recuperacion
          comandos.add(new CommandMoveTo(recuperadores[i],
                                         new Position(posRecuperacion[0], posRecuperacion[1])));
        }
      }
    }
    //Instancia un generador aleatorio
    Random r = new Random();
    //Recorre la lista de mis jugadores que pueden rematar
    for (int i : sp.canKick()) {
      //Si el jugador es de indice 8 o 10
      if (i == 8 || i == 10) {
        comandos.add(new CommandHitBall(i, Position.medium(posicionPorteroContrario,
                                                           Constants.posteIzqArcoSup), 1,
                                        12 + r.nextInt(6)));
      } else {
        //inicia contador en cero
        int count = 0;
        int jugadorDestino;
        //Repetir mientras el jugador destino sea igual al jugador que remata
        while (((jugadorDestino = r.nextInt(11)) == i
                //o mientras la coordenada y del jugador que remata
                //es mayor que la coordenada y del que recibe
                || jugadores[i].getY() > jugadores[jugadorDestino].getY())
               //Y mientras el contador es menor a 20
               && count < 20) {
          //incrementa el contador
          count++;
        }
        //Si el receptor del ballPosition es el que remata
        if (i == jugadorDestino) {
          while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
          }
        }
        //Ordena que el jugador que puede rematar que de un pase
        //al jugador destino
        double anguloZ = 45;
        jugadorDestino = 8;
        //System.out.println("posicion 9 y 11 "+i+" a "+jugadores[8]+" "+jugadores[10]);
        comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, anguloZ));
      }
    }
    //Retorna la lista de comandos
    return comandos;
  }
}
