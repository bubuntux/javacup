package org.dsaw.javacup.tactics.jvc2013.Patones;

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

public class Patones implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-8.797202797202797, -36.58371040723982),
      new Position(1.4265734265734267, -35.8710407239819),
      new Position(10.6993006993007, -37.05882352941177),
      new Position(0.23776223776223776, -22.805429864253394),
      new Position(18.78321678321678, -23.28054298642534),
      new Position(-19.25874125874126, -22.56787330316742),
      new Position(-10.6993006993007, -10.214932126696834),
      new Position(8.797202797202797, -2.8506787330316743),
      new Position(0.7132867132867133, -1.4253393665158371),
      new Position(12.125874125874127, -9.97737556561086)
  };

  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-8.797202797202797, -36.58371040723982),
      new Position(1.4265734265734267, -35.8710407239819),
      new Position(10.6993006993007, -37.05882352941177),
      new Position(0.23776223776223776, -22.805429864253394),
      new Position(18.78321678321678, -23.28054298642534),
      new Position(-19.25874125874126, -22.56787330316742),
      new Position(-10.6993006993007, -10.214932126696834),
      new Position(7.132867132867133, -6.651583710407239),
      new Position(-0.23776223776223776, -12.828054298642533),
      new Position(15.454545454545453, -11.165158371040723)
  };

  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-8.797202797202797, -36.58371040723982),
      new Position(1.4265734265734267, -35.8710407239819),
      new Position(10.461538461538462, -37.05882352941177),
      new Position(0.23776223776223776, -15.441176470588236),
      new Position(17.832167832167833, -6.414027149321266),
      new Position(-19.25874125874126, -4.751131221719457),
      new Position(-13.552447552447552, 9.502262443438914),
      new Position(7.132867132867133, 22.56787330316742),
      new Position(-5.944055944055944, 20.90497737556561),
      new Position(12.125874125874127, 10.927601809954751)
  };

  class TacticaDetalleImpl implements ITeam {

    @Override
    public String getName() {
      return "patonea";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.CO;
    }

    @Override
    public String getCoachName() {
      return "Ferney Vallejo";
    }

    @Override
    public Color getShirtColor() {
      return new Color(229, 227, 250);
    }

    @Override
    public Color getShortsColor() {
      return new Color(64, 10, 9);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(0, 69, 239);
    }

    @Override
    public Color getSocksColor() {
      return new Color(158, 39, 194);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(188, 159, 59);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.FRANJA_HORIZONTAL;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(55, 211, 135);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(52, 143, 232);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(66, 165, 96);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(72, 101, 144);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(207, 199, 50);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }


    @Override
    public IPlayer[] getPlayers() {
      return new IPlayer[]{
          new JugadorImpl("Jugador", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.88d,
                          0.95d, true),
          new JugadorImpl("Jugador", 2, new Color(255, 200, 150), new Color(50, 0, 0), 0.74d, 0.72d,
                          0.81d, false),
          new JugadorImpl("Jugador", 3, new Color(255, 200, 150), new Color(50, 0, 0), 0.74d, 0.74d,
                          0.81d, false),
          new JugadorImpl("Jugador", 4, new Color(255, 200, 150), new Color(50, 0, 0), 0.78d, 0.67d,
                          0.74d, false),
          new JugadorImpl("Jugador", 5, new Color(255, 200, 150), new Color(50, 0, 0), 0.76d, 0.76d,
                          0.94d, false),
          new JugadorImpl("Jugador", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.69d, 0.76d,
                          0.9d, false),
          new JugadorImpl("Jugador", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.63d, 0.76d,
                          0.96d, false),
          new JugadorImpl("Jugador", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.71d, 0.96d,
                          0.85d, false),
          new JugadorImpl("Jugador", 9, new Color(255, 200, 150), new Color(50, 0, 0), 0.75d, 0.74d,
                          0.77d, false),
          new JugadorImpl("Jugador", 10, new Color(255, 200, 150), new Color(50, 0, 0), 0.98d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Jugador", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
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
    return alineacion1;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
  }

  LinkedList<Command> comandos = new LinkedList<>();
  int capo = 7;

  @Override
  public List<Command> execute(GameSituations sp) {
    comandos.clear();
    Position[] jugadores = sp.myPlayers();
    for (int i = 0; i < jugadores.length; i++) {
      comandos.add(new CommandMoveTo(i, alineacion3[i]));
    }

    Position
        porIr =
        Constants.centroArcoInf.moveAngle(Constants.centroArcoInf.angle(sp.ballPosition()), 5);
    comandos.add(new CommandMoveTo(0, porIr));

    if (!sp.isRivalStarts()) {
      int[] recuperadores = sp.getRecoveryBall();
      if (recuperadores.length > 1) {
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        for (int i = 1; i < recuperadores.length; i++) {
          comandos.add(new CommandMoveTo(recuperadores[i],
                                         new Position(posRecuperacion[0], posRecuperacion[1])));
        }
      }
    }
    int[] jug = sp.canKick();
    for (int i = 0; i < jug.length; i++) {
      comandos.add(new CommandHitBall(jug[i], Constants.centroArcoSup, 1.0, 15));
      Position mipos = sp.myPlayers()[jug[i]];
      int[] coates = mipos.nearestIndexes(sp.myPlayers());
      for (int j = 0; j < coates.length; j++) {
        if (sp.myPlayers()[coates[j]].getY() > mipos.getY()) {
          if (libre(jug[i], coates[j], sp)) {
            comandos.add(new CommandHitBall(jug[i], sp.myPlayers()[coates[j]], 0.9, false));
            break;
          }
        }
      }
    }

    return comandos;

  }


  public boolean libre(int mi, int micoate, GameSituations sp) {
    Position pco = sp.myPlayers()[micoate];
    Position pmi = sp.myPlayers()[mi];
    for (int i = 0; i < sp.rivalPlayers().length; i++) {
      Position posurival = sp.rivalPlayers()[i];
      if (posurival.distance(pco) < 10) {
        return false;
      }
      if (pmi.distance(posurival) < pmi.distance(pco)
          && (Math.abs(pmi.angle(pco) - pmi.angle(posurival))) < Math.toRadians(25)) {
        return false;
      }
    }
    return true;
  }

  public boolean libre2(int mi, Position pco, GameSituations sp) {
    Position pmi = sp.myPlayers()[mi];
    for (int i = 0; i < sp.rivalPlayers().length; i++) {
      Position posurival = sp.rivalPlayers()[i];
      if (posurival.distance(pco) < 10) {
        return false;
      }
      if (pmi.distance(posurival) < pmi.distance(pco)
          && (Math.abs(pmi.angle(pco) - pmi.angle(posurival))) < Math.toRadians(25)) {
        return false;
      }
    }
    return true;
  }
}