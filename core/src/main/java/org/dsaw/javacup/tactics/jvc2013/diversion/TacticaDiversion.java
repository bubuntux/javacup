package org.dsaw.javacup.tactics.jvc2013.diversion;

/*
 * Licence: To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import java.util.Random;

/**
 * @author carlos
 */
public class TacticaDiversion implements Tactic {

  LinkedList<Command> comandos = new LinkedList<>();

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-21.874125874125873, -30.407239819004527),
      new Position(-0.7132867132867133, -35.15837104072398),
      new Position(19.25874125874126, -26.84389140271493),
      new Position(13.314685314685315, -7.601809954751132),
      new Position(-17.356643356643357, -7.839366515837104),
      new Position(-1.6643356643356644, -19.2420814479638),
      new Position(17.11888111888112, 14.015837104072398),
      new Position(-17.594405594405593, 30.6447963800905),
      new Position(0.0, 23.993212669683256),
      new Position(16.88111888111888, 30.6447963800905)
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

  Position ataqueNiupi[] = new Position[]{
      new Position(10.0, 10.0),
      new Position(-10.0, 15.0),
      new Position(10.0, 20.0),
      new Position(-10.0, 25.0),
      new Position(10.0, 30.0)
  };

  int ordenAvaliacao[][] = {
        /*0*/{1, 3, 6, 7, 9},
        /*1*/{4, 5, 6, 8, 9},
        /*2*/{6, 5, 4, 8, 9},
        /*4*/{8, 6, 9, 10},
        /*5*/{7, 6, 9, 10},
        /*6*/{9, 10, 8},
        /*7*/{9, 10, 8},
        /*8*/{9, 10},
        /*9*/{8, 10},
        /*10*/{9, 8}
  };

  public class TacticaDetalleImpl implements Team {

    @Override
    public String getName() {
      return "Jotitas";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.PE;
    }

    @Override
    public String getCoachName() {
      return "Autori";
    }

    @Override
    public Color getShirtColor() {
      return new Color(204, 0, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 0, 255);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(204, 0, 0);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(204, 0, 0);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(116, 165, 75);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.FRANJA_DIAGONAL;
    }

    class JugadorImpl implements Player {

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
      public String getName() {
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
    public Player[] getPlayers() {
      return new Player[]{
          new JugadorImpl("Goleiro", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.5d, true),
          new JugadorImpl("Defensa", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Defensa", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Defensa", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.5d, false),
          new JugadorImpl("Volante", 5, new Color(255, 200, 150), new Color(102, 0, 0), 1.0d, 0.5d,
                          0.75d, false),
          new JugadorImpl("Volante", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.75d, false),
          new JugadorImpl("MeioCampo", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          1.0d, false),
          new JugadorImpl("MeioAtacante", 8, new Color(255, 200, 150), new Color(255, 255, 0), 1.0d,
                          1.0d, 1.0d, false),
          new JugadorImpl("Atacante", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Atacante", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Atacante", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false)
      };
    }
  }


  Team detalle = new TacticaDetalleImpl();

  @Override
  public Team getDetail() {
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

  @Override
  public List<Command> execute(GameSituations sp) {
    Random r = new Random();

    //Limpia la listopta de comandos
    comandos.clear();
    //Obtiene las posiciones de tus jugadores
    Position[] jogadores = sp.myPlayers();
    Position[] rivais = sp.rivalPlayers();
    int[] meuAlcance = sp.iterationsToKick();
    int[] rivalAlcance = sp.rivalIterationsToKick();
    Player[] meusDetalhes = sp.myPlayersDetail();
    Player[] rivalDetalhes = sp.rivalPlayersDetail();

    if (sp.ballPosition().getX() < -9) {
      comandos.add(new CommandMoveTo(0,
                                     new Position(alineacion1[0].getX() - 2,
                                                  alineacion1[0].getY())));
    } else if (sp.ballPosition().getX() > 9) {
      comandos.add(new CommandMoveTo(0,
                                     new Position(alineacion1[0].getX() + 2,
                                                  alineacion1[0].getY())));
    } else {
      comandos.add(new CommandMoveTo(0, alineacion1[0]));
    }

    for (int i = 1; i < jogadores.length; i++) {
      double dist = NossoUtil.dist(jogadores[i], sp.ballPosition());
      if (dist > 50.0) {
        comandos.add(new CommandMoveTo(i,
                                       new Position(
                                           alineacion1[i].getX()
                                           + (sp.ballPosition().getX() - jogadores[i].getX()) * 0.3,
                                           alineacion1[i].getY()
                                           + (sp.ballPosition().getY() - jogadores[i].getY())
                                             * 0.3))
        );
      } else if (dist > 30) {
        comandos.add(new CommandMoveTo(i,
                                       new Position(
                                           alineacion1[i].getX()
                                           + (sp.ballPosition().getX() - jogadores[i].getX()) * 0.5,
                                           alineacion1[i].getY()
                                           + (sp.ballPosition().getY() - jogadores[i].getY())
                                             * 0.5))
        );

      } else if (dist > 5) {

        comandos.add(new CommandMoveTo(i,
                                       new Position(
                                           alineacion1[i].getX()
                                           + (sp.ballPosition().getX() - jogadores[i].getX()) * 0.8,
                                           alineacion1[i].getY()
                                           + (sp.ballPosition().getY() - jogadores[i].getY())
                                             * 0.8))
        );
      }
    }

    //Se o rival nao joga bola de um lateral
    if (!sp.isRivalStarts()) {
      //Obtem os dados de recuperacao
      int[] recuperadores = sp.getRecoveryBall();
      //Se existe possibilidade de recuperar bola
      if (recuperadores.length > 1) {
        //Calcula a trajetoria do mais perto ate a bola
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

    for (int i : sp.canKick()) {
      if (i == 0) {
        comandos.add(new CommandHitBall(i, jogadores[7], 1, 45));
      } else if (i > 7) {
        if (NossoUtil.possoChutar(jogadores[i])) {
          comandos.add(NossoUtil
                           .chutarGol(i, meusDetalhes[i].getPrecision(), meusDetalhes[i].getSpeed(),
                                      jogadores[i], rivais));
        } else {
          comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 0.35, true));
          comandos.add(new CommandMoveTo(i, Constants.centroArcoSup));
        }
      } else if (i < 8) {
        Command cmd = NossoUtil.tocarAoProximo(i, jogadores, rivais, ordenAvaliacao);
        if (cmd != null) {
          comandos.add(cmd);
        } else {
          comandos.add(new CommandHitBall(
              i,
              Constants.centroArcoSup,
              1.0,
              NossoUtil.elevacao(meusDetalhes[i].getSpeed())
          ));
        }
      }
    }

    for (int i : sp.rivalCanKick()) {
      if (i > 0) {
        Position alcance = new Position(
            (sp.ballPosition().getX() + Constants.centroArcoSup.getX()) / 2,
            (sp.ballPosition().getY() + Constants.centroArcoSup.getY()));
        comandos.add(new CommandMoveTo(NossoUtil.getProximoJogador(alcance, jogadores), alcance));
      }
    }

    //Retorna la lista de comandos
    return comandos;
  }

}