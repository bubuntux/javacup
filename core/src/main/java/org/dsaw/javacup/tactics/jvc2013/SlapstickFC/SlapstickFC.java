package org.dsaw.javacup.tactics.jvc2013.SlapstickFC;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerI;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SlapstickFC implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.734265734265733, -35.8710407239819),
      new Position(-0.4755244755244755, -35.8710407239819),
      new Position(19.734265734265733, -36.10859728506787),
      new Position(-8.55944055944056, -8.552036199095022),
      new Position(0.0, -0.23755656108597287),
      new Position(7.132867132867133, -7.839366515837104),
      new Position(-18.06993006993007, -14.25339366515837),
      new Position(14.265734265734267, -16.628959276018097),
      new Position(-24.251748251748253, -3.3257918552036196),
      new Position(22.11188811188811, -1.4253393665158371)
  };

  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.97202797202797, -35.8710407239819),
      new Position(0.7132867132867133, -36.10859728506787),
      new Position(19.97202797202797, -36.10859728506787),
      new Position(-21.3986013986014, -22.330316742081447),
      new Position(0.0, -22.092760180995477),
      new Position(19.496503496503497, -22.092760180995477),
      new Position(-17.832167832167833, -8.789592760180994),
      new Position(0.0, -11.402714932126697),
      new Position(21.636363636363637, -6.414027149321266),
      new Position(9.510489510489512, -6.651583710407239)
  };

  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-20.20979020979021, -33.257918552036195),
      new Position(-0.7132867132867133, -32.07013574660634),
      new Position(19.020979020979023, -32.54524886877828),
      new Position(-20.923076923076923, -9.502262443438914),
      new Position(-0.951048951048951, -9.502262443438914),
      new Position(19.020979020979023, -7.601809954751132),
      new Position(-21.874125874125873, 17.57918552036199),
      new Position(-1.6643356643356644, 18.054298642533936),
      new Position(20.20979020979021, 18.529411764705884),
      new Position(-0.951048951048951, 45.19683257918552)
  };

  public class TacticaDetalleImpl implements Team {

    @Override
    public String getName() {
      return "SlapstickFC";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.ES;
    }

    @Override
    public String getCoachName() {
      return "Ben Turpin";
    }

    @Override
    public Color getShirtColor() {
      return new Color(153, 0, 255);
    }

    @Override
    public Color getShortsColor() {
      return new Color(153, 0, 255);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(0, 255, 255);
    }

    @Override
    public Color getSocksColor() {
      return new Color(153, 51, 255);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(153, 0, 102);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.FRANJA_DIAGONAL;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(255, 255, 51);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(153, 51, 255);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(153, 51, 255);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(153, 102, 255);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(153, 0, 0);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }

    class JugadorImpl implements PlayerI {

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
    public PlayerI[] getPlayers() {
      return new PlayerI[]{
          new JugadorImpl("Max Linder", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, true),
          new JugadorImpl("Harold Lloyd", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.75d, false),
          new JugadorImpl("Harry Langdon", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.75d, false),
          new JugadorImpl("Larry Seamon", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.75d, false),
          new JugadorImpl("Charly Chase", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.5d, false),
          new JugadorImpl("Roscoe Arbuckle", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.75d, false),
          new JugadorImpl("Stan Laurel", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.5d, false),
          new JugadorImpl("Oliver Hardy", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, false),
          new JugadorImpl("Mack Sennett", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, false),
          new JugadorImpl("Buster Keaton", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, false),
          new JugadorImpl("Charles Chaplin", 11, new Color(255, 200, 150), new Color(50, 0, 0),
                          1.0d, 1.0d, 0.5d, false)
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
    return alineacion1;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
  }

  ArrayList<Command> comandos = new ArrayList<>();

  //ESTADO
  private final int PUEDE_REMATAR = 1;
  private final int PUEDE_RECUPERAR = 2;
  private final int DESMARCAR_IZQUIERDA = 3;
  private final int DESMARCAR_DERECHA = 4;
  private final int DESMARCAR_CENTRO = 5;
  private final int DESMARCAR_CENTRODERECHA = 6;
  private final int DESMARCAR_CENTROIZQUIERDA = 7;
  private final int MARCAR = 8;
  private final int DESMARCAR_LEJOS = 9;
  private final int DESMARCAR_LEJOSDERECHA = 10;
  private final int DESMARCAR_LEJOSIZQUIERDA = 11;

  //valores a calcular
  public int CONTROLY_PORTERO = 12;//
  public int CONTROLX_PORTERO = -42;//
  public int LIMITE_DEFENSA = -10;//
  public int DESMARQUE_LEJOS = 45;//
  public int POSICION_REMATE = 25;

  @Override
  public List<Command> execute(GameSituations sp) {

    comandos.clear();
    int[] estadoJugador = new int[11];

    double[] posicionRecuperacion = actualizarRecuperadores(sp, estadoJugador);
    actualizarRematadores(sp, estadoJugador);

    Position[] rivalesOrdenados = ordenarRivales(sp);

    jugarPortero(estadoJugador[0], posicionRecuperacion, sp);

    for (int i = 1; i < 4; i++) {
      jugarDefensa(i, estadoJugador[i], posicionRecuperacion, sp, rivalesOrdenados);
    }

    for (int i = 4; i < 7; i++) {
      jugarMedio(i, estadoJugador[i], posicionRecuperacion, sp, rivalesOrdenados);
    }

    for (int i = 7; i < 11; i++) {
      jugarDelantero(i, estadoJugador[i], posicionRecuperacion, sp, rivalesOrdenados);
    }

    return comandos;
  }

  private double[] actualizarRecuperadores(GameSituations sp, int[] estadoJugador) {

    for (int i = 0; i < 11; i++) {
      estadoJugador[i] = 0;
    }

    int[] recuperadores = sp.getRecoveryBall();
    int[] recuperadoresRivales = this.getRecuperacionBalonRival(sp);

    double[] posRecuperacion = null;
    if (recuperadores.length > 0) {
      posRecuperacion = sp.getTrajectory(recuperadores[0]);
    } else if (recuperadoresRivales.length > 0) {
      posRecuperacion = sp.getTrajectory(recuperadoresRivales[0]);
    }

    if (recuperadores.length > 0) {
      if (recuperadoresRivales.length == 0 || recuperadores[0] < recuperadoresRivales[0]) {
        estadoJugador[recuperadores[1]] = PUEDE_RECUPERAR;
        actualizarDesmarques(recuperadores[1], estadoJugador);
      } else {
        for (int i = 1; i < 10; i++) {
          estadoJugador[i] = MARCAR;
        }
        actualizarDesmarques(recuperadores[1], estadoJugador);

        if (recuperadoresRivales[0] == recuperadores[0]) {
          for (int i = 1; i < recuperadores.length; i++) {
            estadoJugador[recuperadores[i]] = PUEDE_RECUPERAR;
          }
        } else if (recuperadoresRivales[0] < recuperadores[0]) {
          estadoJugador[recuperadores[1]] = PUEDE_RECUPERAR;
        }
      }
    } else {
      for (int i = 1; i < 10; i++) {
        estadoJugador[i] = MARCAR;
      }
    }
    return posRecuperacion;
  }

  private void actualizarRematadores(GameSituations sp, int[] estadoJugador) {
    int[] rematadores = sp.canKick();
    for (int i = 0; i < rematadores.length; i++) {
      estadoJugador[rematadores[i]] = PUEDE_REMATAR;
    }
  }


  private void jugarPortero(int estado, double[] posicionRecuperacion, GameSituations sp) {
    switch (estado) {
      case 0:
        comandos.add(new CommandMoveTo(0, new Position(sp.ballPosition().getX() / 4, -50.41)));
        break;
      case PUEDE_REMATAR:
        comandos.add(elegirPase(sp, 0));
        break;
      case PUEDE_RECUPERAR:
        if ((posicionRecuperacion[1] < CONTROLX_PORTERO) &&
            posicionRecuperacion[0] < CONTROLY_PORTERO
            && posicionRecuperacion[0] > -CONTROLY_PORTERO) {
          comandos.add(
              new CommandMoveTo(0, new Position(posicionRecuperacion[0], posicionRecuperacion[1])));
        } else {
          comandos.add(new CommandMoveTo(0, new Position(sp.ballPosition().getX() / 4, -50.41)));
        }
        break;
      default:
        break;
    }
  }


  private void jugarDefensa(int jugador, int estado, double[] posicionRecuperacion,
                            GameSituations sp, Position[] rivalesOrdenados) {
    switch (estado) {
      case 0:
      case MARCAR:
        if (rivalesOrdenados[jugador - 1] != null
            && rivalesOrdenados[jugador - 1].getY() < LIMITE_DEFENSA) {
          comandos.add(new CommandMoveTo(jugador, rivalesOrdenados[jugador - 1]));
        } else {
          comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        }
        break;
      case PUEDE_REMATAR:
        comandos.add(elegirPase(sp, jugador));
        break;
      case PUEDE_RECUPERAR:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1])));
        break;
      case DESMARCAR_CENTRO:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1] + 18)));
        break;
      case DESMARCAR_CENTROIZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_CENTRODERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_DERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      case DESMARCAR_IZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      default:
        break;
    }
  }

  private void jugarMedio(int jugador, int estado, double[] posicionRecuperacion, GameSituations sp,
                          Position[] rivalesOrdenados) {
    switch (estado) {
      case 0:
        comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        break;
      case PUEDE_REMATAR:
        if (sp.ballPosition().getY() < 30) {
          comandos.add(elegirPase(sp, jugador));
        } else {
          comandos.add(new CommandHitBall(jugador, new Position(0, 50), 1, 10));
        }
        break;
      case PUEDE_RECUPERAR:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1])));
        break;
      case DESMARCAR_CENTRO:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1] + 18)));
        break;
      case DESMARCAR_CENTROIZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_CENTRODERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_DERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      case DESMARCAR_IZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      case MARCAR:
        if (rivalesOrdenados[jugador - 1] != null) {
          comandos.add(new CommandMoveTo(jugador, new Position(rivalesOrdenados[jugador - 1])));
        } else {
          comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        }
        break;
      case DESMARCAR_LEJOS:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      case DESMARCAR_LEJOSIZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 15,
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      case DESMARCAR_LEJOSDERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 15,
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      default:
        break;
    }
  }


  private void jugarDelantero(int jugador, int estado, double[] posicionRecuperacion,
                              GameSituations sp, Position[] rivalesOrdenados) {
    switch (estado) {
      case 0:
        comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        break;
      case PUEDE_REMATAR:
        if (sp.ballPosition().getY() > POSICION_REMATE) {
          comandos.add(new CommandHitBall(jugador, new Position(0, 50), 1, 10));
        } else {
          comandos.add(elegirPase(sp, jugador));
        }
        break;
      case PUEDE_RECUPERAR:
        if (posicionRecuperacion[1] > -10) {
          comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                               posicionRecuperacion[1])));
        } else {
          comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        }
        break;
      case DESMARCAR_CENTRO:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1] + 18)));
        break;
      case DESMARCAR_CENTROIZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_CENTRODERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 15,
                                                             posicionRecuperacion[1] + 18 * 0.7)));
        break;
      case DESMARCAR_DERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      case DESMARCAR_IZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 18,
                                                             posicionRecuperacion[1] + 4)));
        break;
      case DESMARCAR_LEJOS:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0],
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      case DESMARCAR_LEJOSIZQUIERDA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] - 15,
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      case DESMARCAR_LEJOSDERECHA:
        comandos.add(new CommandMoveTo(jugador, new Position(posicionRecuperacion[0] + 15,
                                                             posicionRecuperacion[1]
                                                             + DESMARQUE_LEJOS)));
        break;
      case MARCAR:
        if (rivalesOrdenados[jugador - 1] != null) {
          comandos.add(new CommandMoveTo(jugador, new Position(rivalesOrdenados[jugador - 1])));
        } else {
          comandos.add(new CommandMoveTo(jugador, alineacion3[jugador]));
        }
        break;
      default:
        break;
    }
  }


  private Command elegirPase(GameSituations sp, int jugador) {
    Position[] misJugadores = sp.myPlayers();
    Position[] rivales = sp.rivalPlayers();

    boolean[][] terrenoJuego = new boolean[36][2];
    //Calculamos las posicones de pase
    Position[] pase = new Position[36];
    for (int i = 0; i < 36; i++) {
      double x = sp.ballPosition().getX() + (Math.cos(i * 10 * Math.PI / 180)) * 20;
      double y = sp.ballPosition().getY() + (Math.sin(i * 10 * Math.PI / 180)) * 20;
      pase[i] = new Position(x, y);
    }

    for (int i = 0; i < 11; i++) {

      if (rivales[i].distance(sp.ballPosition()) <= 32) {
        double angulo = sp.ballPosition().angle(rivales[i]) * 180 / Math.PI;
        angulo = angulo < 0 ? angulo += 360 : angulo;
        int indice = ((int) (angulo / 10));
        terrenoJuego[indice % 36][1] = true;
        terrenoJuego[(indice + 1) % 36][1] = true;
        if (rivales[i].distance(sp.ballPosition()) <= 28) {
          terrenoJuego[(indice + 2) % 36][1] = true;
        }
        if (rivales[i].distance(sp.ballPosition()) <= 24) {
          terrenoJuego[(indice + 3) % 36][1] = true;
        }
        if (rivales[i].distance(sp.ballPosition()) <= 28) {
          indice = indice != 0 ? indice - 1 : 35;
          terrenoJuego[indice][1] = true;
        }
        if (rivales[i].distance(sp.ballPosition()) <= 24) {
          indice = indice != 0 ? indice - 1 : 35;
          terrenoJuego[indice % 36][1] = true;
        }
      }
    }

    for (int i = 1; i < 11; i++) {
      if (misJugadores[i].distance(sp.ballPosition()) <= 24 && i != jugador) {
        double angulo = sp.ballPosition().angle(misJugadores[i]) * 180 / Math.PI;
        angulo = angulo < 0 ? angulo += 360 : angulo;
        int indice = (int) (angulo / 10);
        terrenoJuego[indice % 36][0] = true;
        terrenoJuego[(indice + 1) % 36][0] = true;
        if (misJugadores[i].distance(sp.ballPosition()) <= 20 && i != jugador) {
          terrenoJuego[(indice + 2) % 36][0] = true;
        }
        if (misJugadores[i].distance(sp.ballPosition()) <= 16 && i != jugador) {
          terrenoJuego[(indice + 3) % 36][0] = true;
        }
        if (misJugadores[i].distance(sp.ballPosition()) <= 20 && i != jugador) {
          indice = indice != 0 ? indice - 1 : 35;
          terrenoJuego[indice][0] = true;
        }
        if (misJugadores[i].distance(sp.ballPosition()) <= 16 && i != jugador) {
          indice = indice != 0 ? indice - 1 : 35;
          terrenoJuego[indice][0] = true;
        }
      }
    }
    Position posicionPase = null;
    int[]
        ordenPase =
        {9, 8, 10, 7, 11, 6, 12, 5, 13, 4, 14, 3, 15, 2, 16, 1, 17, 0, 18, 35, 21, 34/*, 22, 33, 23, 32, 24*/};

    if (sp.ballPosition().getX() < -30) {
      for (int i = 0; i < 8; i++) {
        ordenPase[i] = 7 - i;
      }
      for (int i = 8; i < 22; i++) {
        ordenPase[i] = 43 - i;
      }
    }

    if (sp.ballPosition().getX() > 30) {
      for (int i = 0; i < 22; i++) {
        ordenPase[i] = 11 + i;
      }
    }

    for (int j = 0; j < ordenPase.length; j++) {
      int i = ordenPase[j];
      if (terrenoJuego[i][0] == true && terrenoJuego[i][1] == false) {
        posicionPase = pase[i];
        break;
      }
    }
    if (posicionPase == null) {
      if (jugador < 7) {
        return new CommandHitBall(jugador, new Position(0, 50), 1, 45);
      } else {
        return new CommandHitBall(jugador, new Position(0, 50), 1, 10);
      }
    } else if (jugador == 0 || jugador >= 7) {
      return new CommandHitBall(jugador, posicionPase, 0.75, 0);
    } else {
      return new CommandHitBall(jugador, posicionPase, 1, 0);
    }
  }

  public int[] getRecuperacionBalonRival(GameSituations sp) {
    int it = 0;
    boolean found = false;
    Position pJug;
    double dist0, dist;
    int idxFound = -1;
    LinkedList<Double> founds = new LinkedList<>();
    PlayerI detalles[] = sp.rivalPlayersDetail();
    Position[] jugadores = sp.rivalPlayers();
    while (!found) {
      double[] posBalon = sp.getTrajectory(it);
      if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
        return new int[]{};
      }
      if (posBalon[2] <= Constants.ALTO_ARCO) {
        for (int i = 0; i < jugadores.length; i++) {
          if (posBalon[2] <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO
                                                         : Constants.ALTURA_CONTROL_BALON)) {
            pJug = jugadores[i];
            dist0 = (double) it * Constants.getVelocidad(detalles[i].getSpeed());
            dist = pJug.distance(new Position(posBalon[0], posBalon[1]));
            if (dist0 >= dist) {
              found = true;
              founds.add(dist);
              founds.add((double) i);
              idxFound = it;
            }
          }
        }
      }
      it++;
    }
    for (int i = 2; i < founds.size(); i = i + 2) {
      for (int j = 0; j < i; j = j + 2) {
        if (founds.get(i) < founds.get(j)) {
          dist0 = founds.get(i);
          dist = founds.get(i + 1);
          founds.set(i, founds.get(j));
          founds.set(i + 1, founds.get(j + 1));
          founds.set(j, dist0);
          founds.set(j + 1, dist);
        }
      }
    }
    for (int i = founds.size() - 1; i >= 0; i = i - 2) {
      founds.remove(i - 1);
    }
    founds.add(0, (double) idxFound);
    int[] result = new int[founds.size()];
    for (int i = 0; i < founds.size(); i++) {
      result[i] = (int) founds.get(i).doubleValue();
    }
    return result;
  }

  private void actualizarDesmarques(int jugador, int[] estadoJugador) {

    switch (jugador) {
      case 0:
        estadoJugador[1] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[2] = DESMARCAR_CENTRO;
        estadoJugador[3] = DESMARCAR_CENTRODERECHA;
        estadoJugador[4] = DESMARCAR_LEJOSIZQUIERDA;
        estadoJugador[5] = DESMARCAR_LEJOS;
        estadoJugador[6] = DESMARCAR_LEJOSDERECHA;
        break;
      case 1:
        estadoJugador[2] = DESMARCAR_DERECHA;
        estadoJugador[4] = DESMARCAR_CENTRO;
        estadoJugador[5] = DESMARCAR_CENTRODERECHA;
        estadoJugador[7] = DESMARCAR_LEJOS;
        estadoJugador[5] = DESMARCAR_LEJOSDERECHA;
        break;
      case 2:
        estadoJugador[1] = DESMARCAR_IZQUIERDA;
        estadoJugador[3] = DESMARCAR_DERECHA;
        estadoJugador[4] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[5] = DESMARCAR_CENTRO;
        estadoJugador[6] = DESMARCAR_CENTRODERECHA;
        estadoJugador[7] = DESMARCAR_LEJOSIZQUIERDA;
        estadoJugador[8] = DESMARCAR_LEJOS;
        estadoJugador[9] = DESMARCAR_LEJOSDERECHA;
        break;
      case 3:
        estadoJugador[2] = DESMARCAR_IZQUIERDA;
        estadoJugador[5] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[6] = DESMARCAR_CENTRO;
        estadoJugador[8] = DESMARCAR_LEJOSIZQUIERDA;
        estadoJugador[9] = DESMARCAR_LEJOS;
        break;
      case 4:
        estadoJugador[5] = DESMARCAR_DERECHA;
        estadoJugador[7] = DESMARCAR_CENTRO;
        estadoJugador[8] = DESMARCAR_CENTRODERECHA;
        estadoJugador[10] = DESMARCAR_LEJOSDERECHA;
        break;
      case 5:
        estadoJugador[4] = DESMARCAR_IZQUIERDA;
        estadoJugador[6] = DESMARCAR_DERECHA;
        estadoJugador[7] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[8] = DESMARCAR_CENTRO;
        estadoJugador[9] = DESMARCAR_CENTRODERECHA;
        estadoJugador[10] = DESMARCAR_LEJOS;
        break;
      case 6:
        estadoJugador[5] = DESMARCAR_IZQUIERDA;
        estadoJugador[8] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[9] = DESMARCAR_CENTRO;
        estadoJugador[10] = DESMARCAR_LEJOSIZQUIERDA;
        break;
      case 7: //estadoJugador[8] = DESMARCAR_CENTRO;
        estadoJugador[9] = DESMARCAR_CENTRODERECHA;
        break;
      case 8:
        estadoJugador[7] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[9] = DESMARCAR_CENTRODERECHA;
        break;
      case 9: //estadoJugador[7] = DESMARCAR_CENTROIZQUIERDA;
        estadoJugador[8] = DESMARCAR_CENTROIZQUIERDA;
        break;
      default:
        for (int i = 1; i < 7; i++) {
          estadoJugador[i] = MARCAR;
        }
        break;
    }
  }

  private Position[] ordenarRivales(GameSituations sp) {

    Position[] rivalesOrdenados = new Position[9];

    Position[] rivales = sp.rivalPlayers();

    int izq = 0;
    int centro = 1;
    int derecha = 2;
    for (int i = 10; i > 0; i--) {
      for (int j = i; j > 0; j--) {
        if (rivales[j].getY() < rivales[i].getY()) {
          Position aux = rivales[i];
          rivales[i] = rivales[j];
          rivales[j] = aux;
        }
      }

      if (rivales[i].getX() < -10 && izq < 9) {
        rivalesOrdenados[izq] = new Position(rivales[i].getX(), rivales[i].getY());
        izq = izq + 3;
      } else if (rivales[i].getX() > 10 && derecha < 9) {
        rivalesOrdenados[derecha] = new Position(rivales[i].getX(), rivales[i].getY());
        derecha = derecha + 3;
      } else if (centro < 9) {
        rivalesOrdenados[centro] = new Position(rivales[i].getX(), rivales[i].getY());
        centro = centro + 3;
      }
    }
    return rivalesOrdenados;
  }
}