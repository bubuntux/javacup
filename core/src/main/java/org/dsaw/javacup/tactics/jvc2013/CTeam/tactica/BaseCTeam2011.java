package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.JugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.PorteroCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.PorteroRivalCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.RivalCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.DelaunayPanel;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Pnt;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Triangle;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Triangulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.dsaw.javacup.model.util.Constants.ALTO_ARCO;
import static org.dsaw.javacup.model.util.Constants.ALTURA_CONTROL_BALON;
import static org.dsaw.javacup.model.util.Constants.ANCHO_CAMPO_JUEGO;
import static org.dsaw.javacup.model.util.Constants.cornerInfDer;
import static org.dsaw.javacup.model.util.Constants.cornerInfIzq;
import static org.dsaw.javacup.model.util.Constants.cornerSupDer;
import static org.dsaw.javacup.model.util.Constants.cornerSupIzq;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.NEUTRAL;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.PROPIO;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.RIVAL;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.JugadorCT.Tipo.ATAQUE;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.JugadorCT.Tipo.DEFENSA;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.JugadorCT.Tipo.LATERAL;
import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.JugadorCT.Tipo.MEDIO;

public abstract class BaseCTeam2011 implements ICTeam2011 {

  List<IJugadorCT> jugadores = new ArrayList<IJugadorCT>();

  List<IJugadorCT> rivales = new ArrayList<IJugadorCT>();

  List<IJugadorCT> jugadoresRecuperacion = new ArrayList<IJugadorCT>();

  List<IJugadorCT> rivalesRecuperacion = new ArrayList<IJugadorCT>();

  TacticDetail detalle = new TacticaDetalleImpl();

  int itRecuperacion;

  int itRecuperacionRival;

  GameSituations sp;

  DelaunayPanel panel;

  DelaunayPanel panelRivales;

  protected boolean cambioPosesion;

  protected IJugadorCT.Equipo ultimaPosesion = PROPIO;

  protected void inicializar(GameSituations sp) {
    this.sp = sp;
    if (sp.iteration() == 0) {
      // JFrame f = new JFrame("Voronoi Todos");
      // JFrame f2 = new JFrame("Voronoi Rivales");
      // panel = new DelaunayPanel(sp, this);
      // panelRivales = new DelaunayPanel(sp, this);
      // f.setSize(320, 380);
      // f2.setSize(320, 380);
      // f.setContentPane(panel);
      // f2.setContentPane(panelRivales);
      // f.setVisible(true);
      // f2.setVisible(true);
      // f2.setLocation(325, 0);
      for (int i = 0; i < 11; i++) {
        PlayerDetail detalleMio = sp.myPlayersDetail()[i];
        if (detalleMio.isGoalKeeper()) {
          jugadores.add(new PorteroCT(PROPIO, i, detalleMio, this));
        } else {
          JugadorCT.Tipo tipo = ATAQUE;
          if (i < 5) {
            tipo = DEFENSA;
          } else if (i < 7) {
            tipo = MEDIO;
          } else if (i < 9) {
            tipo = LATERAL;
          }

          jugadores.add(new JugadorCT(PROPIO, i, detalleMio, this, tipo));
        }

        PlayerDetail detalleRival = sp.rivalPlayersDetail()[i];
        if (detalleMio.isGoalKeeper()) {
          rivales.add(new PorteroRivalCT(RIVAL, i, detalleRival, this));
        } else {
          rivales.add(new RivalCT(RIVAL, i, detalleRival, this));
        }
      }
    }
  }

  protected void inicializarIteracion() {
    boolean pr[] = new boolean[11];
    for (int i : sp.canKick()) {
      pr[i] = true;
    }

    int idx = 0;
    for (IJugadorCT jugador : jugadores) {
      jugador.iniciarTurno(sp, pr[idx++]);
      jugador.setPuedeRecuperarBalon(false, -1);
    }
    pr = new boolean[11];
    for (int i : sp.rivalCanKick()) {
      pr[i] = true;
    }
    idx = 0;
    for (IJugadorCT rival : rivales) {
      rival.iniciarTurno(sp, pr[idx++]);
      rival.setPuedeRecuperarBalon(false, -1);
    }

    analizarRecuperacionBalon();
    analizarRecuperacionBalonRival();
    calcularTriangulacion();

    cambioPosesion = posesionBalon().equals(ultimaPosesion);
  }

  protected void finalizarIteracion() {
    IJugadorCT.Equipo posesion = posesionBalon();
    ultimaPosesion = posesion.equals(NEUTRAL) ? ultimaPosesion : posesion;
  }

  @Override
  public boolean cambioPosesion() {
    if (cambioPosesion && !posesionBalon().equals(NEUTRAL)) {
      return true;
    }
    return false;
  }

  protected Triangulation voronoiTodos, voronoiJugadores, voronoiRivales;

  private void calcularTriangulacion() {
    try {
      final Triangulation vorJugadores = new Triangulation(trianguloInicial());
      final Triangulation vorRivales = new Triangulation(trianguloInicial());
      // final Triangulation vorRivales2 = new
      // Triangulation(trianguloInicial());
      final Triangulation vorTodos = new Triangulation(trianguloInicial());
      // final Triangulation vorTodos2 = new
      // Triangulation(trianguloInicial());

      for (IJugadorCT jug : jugadores) {
        vorJugadores.agregarPunto(jug.getActual());
        vorTodos.agregarPunto(jug.getActual());
        // vorTodos2.agregarPunto(jug.getActual());
      }
      for (IJugadorCT jug : rivales) {

        vorRivales.agregarPunto(jug.getActual());
        // vorRivales2.agregarPunto(jug.getActual());
        vorTodos.agregarPunto(jug.getActual());
        // vorTodos2.agregarPunto(jug.getActual());
      }
      Position[] adicionales = {cornerSupDer, cornerSupIzq, cornerInfDer, cornerInfIzq,
                                new Position(ANCHO_CAMPO_JUEGO / 2.0, 0),
                                new Position(0.0 - ANCHO_CAMPO_JUEGO / 2.0, 0)};
      for (Position p : adicionales) {
        vorRivales.agregarPunto(new PosicionCT(p));
        // vorRivales2.agregarPunto(new PosicionCT(p));
      }

      for (IJugadorCT jug : jugadores) {
        jug.setVoronoi(vorJugadores, vorRivales, vorTodos);
      }
      for (IJugadorCT jug : rivales) {
        jug.setVoronoi(vorRivales, vorJugadores, vorTodos);
      }

      vorJugadores.voronoi();
      vorRivales.voronoi();
      // vorRivales2.voronoi();
      vorTodos.voronoi();
      // vorTodos2.voronoi();

      // SwingUtilities.invokeLater(new Runnable() {
      //
      // @Override
      // public void run() {
      // panel.setTriangulation(vorTodos2, sp);
      // panel.repaint();
      // panel.invalidate();
      //
      // panelRivales.setTriangulation(vorRivales2, sp);
      // panelRivales.repaint();
      // panelRivales.invalidate();
      // }
      // });

      voronoiTodos = vorTodos;
      voronoiRivales = vorRivales;
      voronoiJugadores = vorJugadores;

    } catch (Exception ex) {
    }
  }

  @Override
  public IJugadorCT.Equipo posesionBalon() {
    if (sp.isStarts()) {
      return PROPIO;
    }
    if (sp.isRivalStarts()) {
      ultimoPase = null;
      return RIVAL;
    }

    if (jugadoresRecuperacion.isEmpty()) {
      return RIVAL;
    }

    int itDiff = itRecuperacion - itRecuperacionRival;
    if (itDiff < 0 || itRecuperacionRival == -1) {
      return PROPIO;
    }
    if (itDiff > 0 || itRecuperacion == -1) {
      ultimoPase = null;
      return RIVAL;
    }

    int diff = jugadoresRecuperacion.size() - rivalesRecuperacion.size();
    if (diff == 0) {
      return NEUTRAL;
    } else if (diff > 0) {
      return PROPIO;
    }

    ultimoPase = null;
    return RIVAL;
  }

  public void analizarRecuperacionBalon() {
    jugadoresRecuperacion = new ArrayList<IJugadorCT>();
    int[] recuperar = sp.getRecoveryBall();
    itRecuperacion = -1;
    if (recuperar != null && recuperar.length > 0) {
      int it = recuperar[0];
      boolean pr[] = new boolean[11];
      for (int i = 1; i < recuperar.length; i++) {
        pr[recuperar[i]] = true;
      }

      int idx = 0;
      for (IJugadorCT jugador : jugadores) {
        if (pr[idx]) {
          jugadoresRecuperacion.add(jugador);
        }
        jugador.setPuedeRecuperarBalon(pr[idx++], it);
      }
      itRecuperacion = recuperar[0];
    }
    Collections.reverse(jugadoresRecuperacion);
  }

  public void analizarRecuperacionBalonRival() {
    int i = 0;
    boolean fin = false;

    rivalesRecuperacion = new ArrayList<IJugadorCT>();
    itRecuperacionRival = -1;
    if (sp.isStarts()) {
      return;
    }

    while (!fin) {
      double[] posBalon = sp.getTrajectory(i);
      if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
        return;
      }
      if (posBalon[2] <= ALTO_ARCO) {
        for (IJugadorCT rival : rivales) {
          if (rival.getDetalle().isGoalKeeper() || posBalon[2] <= ALTURA_CONTROL_BALON) {
            PosicionCT posRival = rival.getActual();
            double dist0 = rival.distanciaEn(i);
            PosicionCT pBalon = new PosicionCT(posBalon[0], posBalon[1]);
            double dist = posRival.distancia(pBalon);
            if (dist0 >= dist) {
              fin = true;
              itRecuperacionRival = i;
              rivalesRecuperacion.add(rival);
              rival.setPuedeRecuperarBalon(true, i);
            }
          }
        }
      }
      i++;
    }
  }

  private PosicionCT ultimoPase;

  @Override
  public PosicionCT getUltimoPase() {
    return ultimoPase;
  }

  @Override
  public void ultimoPase(PosicionCT ultimo) {
    this.ultimoPase = ultimo;
  }

  @Override
  public int iteracionesRecuperacion() {
    return itRecuperacion;
  }

  @Override
  public int iteracionesRecuperacionRival() {
    return itRecuperacionRival;
  }

  @Override
  public List<IJugadorCT> jugadoresRecuperacion() {
    if (sp.isStarts() && jugadoresRecuperacion.isEmpty()) {
      int idx = sp.ballPosition().nearestIndex(sp.myPlayers());
      jugadoresRecuperacion.add(jugadores.get(idx));
    }
    return jugadoresRecuperacion;
  }

  @Override
  public List<IJugadorCT> rivalesRecuperacion() {
    return rivalesRecuperacion;
  }

  @Override
  public List<IJugadorCT> getJugadores(IJugadorCT.Equipo equipo) {
    if (equipo == PROPIO) {
      return jugadores;
    }
    return rivales;
  }

  private Triangle trianguloInicial() {
    final int limite = 10000;
    return new Triangle(new Pnt(-limite, -limite), new Pnt(limite, -limite), new Pnt(0, limite));
  }

  public TacticDetail getDetail() {
    return detalle;
  }

  public Position[] getStartPositions(GameSituations sp) {
    return alineacion8;
  }

  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion7;
  }

  @Override
  public List<IJugadorCT> recuperando() {
    return recuperando;
  }

  @Override
  public Position[] alineacionActual() {
    return alineaciones[alineacion];
  }

  @Override
  public boolean posicionUsada(PosicionCT p) {
    return usadas.contains(p);
  }

  @Override
  public void usada(PosicionCT p) {
    usadas.add(p);
  }

  protected final List<PosicionCT> usadas = new ArrayList<PosicionCT>();

  protected final List<IJugadorCT> recuperando = new ArrayList<IJugadorCT>();

  protected final List<IJugadorCT> ultRecuperando = new ArrayList<IJugadorCT>();

  @Override
  public List<IJugadorCT> previoJugadoresRecuperacion() {
    return ultRecuperando;
  }

  protected int alineacion;

  Position alineacion1[] = new Position[]{new Position(0.2595419847328244, -50.41044776119403),
                                          new Position(-6.181818181818182, -40.62217194570136),
                                          new Position(5.468531468531468, -40.62217194570136),
                                          new Position(17.594405594405593, -37.53393665158371),
                                          new Position(-18.307692307692307, -36.82126696832579),
                                          new Position(4.041958041958042, -28.031674208144796),
                                          new Position(-9.510489510489512, -27.794117647058822),
                                          new Position(19.97202797202797, -22.805429864253394),
                                          new Position(-21.874125874125873, -22.56787330316742),
                                          new Position(-2.8531468531468533, -15.91628959276018),
                                          new Position(7.608391608391608, 17.104072398190045)};

  Position alineacion2[] = new Position[]{new Position(0.0, -46.3235294117647),
                                          new Position(-7.608391608391608, -36.58371040723982),
                                          new Position(7.608391608391608, -36.58371040723982),
                                          new Position(22.825174825174827, -28.031674208144796),
                                          new Position(-21.16083916083916, -26.368778280542987),
                                          new Position(8.083916083916083, -20.667420814479637),
                                          new Position(-7.608391608391608, -20.667420814479637),
                                          new Position(24.965034965034967, -13.778280542986426),
                                          new Position(-24.727272727272727, -10.452488687782806),
                                          new Position(-1.188811188811189, -1.1877828054298643),
                                          new Position(-11.65034965034965, 15.203619909502263)};

  Position alineacion3[] = new Position[]{new Position(0.0, -46.3235294117647),
                                          new Position(-10.937062937062937, -24.468325791855204),
                                          new Position(11.412587412587413, -25.418552036199095),
                                          new Position(24.013986013986013, -12.59049773755656),
                                          new Position(-25.44055944055944, -12.59049773755656),
                                          new Position(8.55944055944056, -5.226244343891403),
                                          new Position(-6.895104895104895, -4.513574660633484),
                                          new Position(24.965034965034967, 6.414027149321266),
                                          new Position(-25.202797202797203, 7.364253393665159),
                                          new Position(-7.846153846153847, 15.91628959276018),
                                          new Position(8.321678321678322, 31.119909502262445)};

  Position alineacion4[] = new Position[]{new Position(0.0, -46.3235294117647),
                                          new Position(-5.944055944055944, -19.479638009049776),
                                          new Position(10.6993006993007, -16.866515837104075),
                                          new Position(25.44055944055944, -0.7126696832579186),
                                          new Position(-24.48951048951049, -3.5633484162895925),
                                          new Position(9.272727272727272, 2.8506787330316743),
                                          new Position(-8.797202797202797, 2.6131221719457014),
                                          new Position(20.923076923076923, 25.18099547511312),
                                          new Position(-21.636363636363637, 24.230769230769234),
                                          new Position(-4.9930069930069925, 28.744343891402718),
                                          new Position(7.132867132867133, 35.39592760180996)};

  Position alineacion5[] = new Position[]{new Position(0.0, -46.3235294117647),
                                          new Position(-5.944055944055944, -19.479638009049776),
                                          new Position(10.6993006993007, -16.866515837104075),
                                          new Position(19.496503496503497, 0.0),
                                          new Position(-21.3986013986014, 0.23755656108597287),
                                          new Position(-10.223776223776223, 22.330316742081447),
                                          new Position(10.6993006993007, 22.092760180995477),
                                          new Position(1.4265734265734267, 31.119909502262445),
                                          new Position(-20.447552447552447, 30.88235294117647),
                                          new Position(8.55944055944056, 38.95927601809955),
                                          new Position(-4.27972027972028, 40.38461538461539)};

  Position alineacion6[] = new Position[]{new Position(0.0, -46.3235294117647),
                                          new Position(-9.986013986013985, 0.0),
                                          new Position(9.986013986013985, 0.7126696832579186),
                                          new Position(18.545454545454543, 12.59049773755656),
                                          new Position(-19.734265734265733, 12.59049773755656),
                                          new Position(7.846153846153847, 22.092760180995477),
                                          new Position(-6.419580419580419, 22.092760180995477),
                                          new Position(14.97902097902098, 34.920814479638004),
                                          new Position(-8.321678321678322, 34.20814479638009),
                                          new Position(-3.090909090909091, 39.90950226244344),
                                          new Position(6.6573426573426575, 40.62217194570136)};

  Position alineacion7[] = new Position[]{new Position(0.2595419847328244, -50.41044776119403),
                                          new Position(-3.5664335664335667, -38.4841628959276),
                                          new Position(3.3286713286713288, -38.72171945701358),
                                          new Position(19.020979020979023, -32.30769230769231),
                                          new Position(-17.594405594405593, -31.59502262443439),
                                          new Position(4.755244755244756, -22.56787330316742),
                                          new Position(-7.846153846153847, -22.56787330316742),
                                          new Position(15.692307692307693, -13.065610859728507),
                                          new Position(-19.020979020979023, -13.303167420814479),
                                          new Position(-6.895104895104895, -6.414027149321266),
                                          new Position(3.804195804195804, -9.027149321266968)};

  Position alineacion8[] = new Position[]{new Position(0.2595419847328244, -50.41044776119403),
                                          new Position(-7.846153846153847, -26.606334841628957),
                                          new Position(9.748251748251748, -27.31900452488688),
                                          new Position(26.391608391608393, -16.391402714932127),
                                          new Position(-24.013986013986013, -15.91628959276018),
                                          new Position(6.181818181818182, -7.839366515837104),
                                          new Position(-6.419580419580419, -8.552036199095022),
                                          new Position(24.251748251748253, -1.1877828054298643),
                                          new Position(-24.48951048951049, -1.1877828054298643),
                                          new Position(-3.5664335664335667, -0.7126696832579186),
                                          new Position(1.188811188811189, -0.47511312217194573)};

  Position[][] alineaciones = {alineacion1, alineacion2, alineacion3, alineacion4, alineacion5,
                               alineacion6};

}
