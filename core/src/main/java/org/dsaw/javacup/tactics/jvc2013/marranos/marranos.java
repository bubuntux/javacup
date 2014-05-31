/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.marranos;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
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

/**
 * @author Jorge
 */

public class marranos implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.46564885496183, -31.6044776119403),
      new Position(0.2595419847328244, -31.082089552238806),
      new Position(19.984732824427482, -31.6044776119403),
      new Position(7.526717557251908, -11.753731343283583),
      new Position(-8.564885496183205, -11.753731343283583),
      new Position(-24.65648854961832, -2.3507462686567164),
      new Position(23.099236641221374, -2.873134328358209),
      new Position(-14.274809160305344, 30.559701492537314),
      new Position(-0.7786259541984732, 8.097014925373134),
      new Position(12.717557251908397, 29.51492537313433)
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

  class TacticaDetalleImpl implements TacticDetail {

    @Override
    public String getTacticName() {
      return "los marranos";
    }

    @Override
    public CountryCode getCountry() {
      return CountryCode.ES;
    }

    @Override
    public String getCoach() {
      return "sweqs";
    }

    @Override
    public Color getShirtColor() {
      return new Color(5, 113, 119);
    }

    @Override
    public Color getShortsColor() {
      return new Color(185, 47, 254);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(243, 120, 10);
    }

    @Override
    public Color getSocksColor() {
      return new Color(114, 233, 163);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(10, 156, 79);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.FRANJA_VERTICAL;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(129, 219, 5);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(40, 162, 75);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(171, 83, 42);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(174, 118, 189);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(120, 205, 220);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.FRANJA_HORIZONTAL;
    }

    class JugadorImpl implements PlayerDetail {

      String nombre;
      int numero;
      Color piel, pelo;
      double velocidad, remate, presicion;
      boolean portero;
      Position posicion;

      public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                         double velocidad, double remate, double presicion, boolean portero) {
        this.nombre = nombre;
        this.numero = numero;
        this.piel = piel;
        this.pelo = pelo;
        this.velocidad = velocidad;
        this.remate = remate;
        this.presicion = presicion;
        this.portero = portero;
      }

      @Override
      public String getPlayerName() {
        return nombre;
      }

      @Override
      public Color getSkinColor() {
        return piel;
      }

      @Override
      public Color getHairColor() {
        return pelo;
      }

      @Override
      public int getNumber() {
        return numero;
      }

      @Override
      public boolean isGoalKeeper() {
        return portero;
      }

      @Override
      public double getSpeed() {
        return velocidad;
      }

      @Override
      public double getPower() {
        return remate;
      }

      @Override
      public double getPrecision() {
        return presicion;
      }

    }

    @Override
    public PlayerDetail[] getPlayers() {
      return new PlayerDetail[]{
          new JugadorImpl("Jugador", 1, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, true),
          new JugadorImpl("Jugador", 2, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 3, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 4, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 5, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 9, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 10, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Jugador", 11, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.5d,
                          0.5d, false)
      };
    }
  }

  TacticDetail detalle = new TacticaDetalleImpl();

  @Override
  public TacticDetail getDetail() {
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

  LinkedList<Command> comandos = new LinkedList<>();

  @Override
  public List<Command> execute(GameSituations sp) {
    //Limpia la lista de comandos
    comandos.clear();
    //Obtiene las posiciones de tus jugadores
    Position[] jugadores = sp.myPlayers();
    for (int i = 0; i < jugadores.length; i++) {
      //Ordena a cada jugador que se ubique segun la alineacion1
      comandos.add(new CommandMoveTo(i, alineacion1[i]));
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
        for (int i = 1; i < recuperadores.length; i++) {
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
      if (i == 8 || i == 10 || i == 9) {
        //condicion aleatoria
        if (r.nextBoolean()) {
          //Ordena que debe rematar al centro del arco
          comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
        } else if (r.nextBoolean()) {
          //Ordena que debe rematar al poste derecho
          comandos.add(new CommandHitBall(i, Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
        } else {
          //Ordena que debe rematar al poste izquierdo
          comandos.add(new CommandHitBall(i, Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
        }
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
        comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, r.nextInt(45)));
      }
    }
    return comandos;
  }
}
