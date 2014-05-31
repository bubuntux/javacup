package org.dsaw.javacup.tactics.jvc2012.vnavarro;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Pequeo implements org.dsaw.javacup.model.Tactic {

  /**
   * Inicio Sacando
   */
  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-17.594405594405593, -38.009049773755656),
      new Position(-4.755244755244756, -33.970588235294116),
      new Position(4.27972027972028, -34.20814479638009),
      new Position(18.545454545454543, -37.77149321266968),
      new Position(0.23776223776223776, -27.556561085972852),
      new Position(-2.377622377622378, -0.9502262443438915),
      new Position(-0.23776223776223776, -10.452488687782806),
      new Position(1.6643356643356644, -0.47511312217194573),
      new Position(-17.832167832167833, -0.47511312217194573),
      new Position(15.216783216783217, -0.47511312217194573)};

  /**
   * Inicio Recibiendo
   */
  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-17.594405594405593, -38.009049773755656),
      new Position(-4.755244755244756, -33.970588235294116),
      new Position(4.27972027972028, -34.20814479638009),
      new Position(18.545454545454543, -37.77149321266968),
      new Position(0.23776223776223776, -27.556561085972852),
      new Position(-9.692307692307693, -3.088235294117647),
      new Position(-0.23776223776223776, -10.452488687782806),
      new Position(9.734265734265733, -3.088235294117647),
      new Position(-26.867132867132867, 0.0),
      new Position(26.153846153846157, 0.0)};

  /**
   * Normal
   */
  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.01044776119403),
      new Position(-17.594405594405593, -38.009049773755656),
      new Position(-4.755244755244756, -33.970588235294116),
      new Position(4.27972027972028, -34.20814479638009),
      new Position(18.545454545454543, -37.77149321266968),
      new Position(-0.7132867132867133, -11.877828054298643),
      new Position(-22.58741258741259, 1.6628959276018098),
      new Position(-0.4755244755244755, 12.352941176470589),
      new Position(26.391608391608393, 1.900452488687783),
      new Position(-13.314685314685315, 35.15837104072398),
      new Position(16.643356643356643, 35.15837104072398)};

  /**
   * Defensiva
   */
  Position alineacion4[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.496503496503497, -35.8710407239819),
      new Position(-7.608391608391608, -42.047511312217196),
      new Position(5.706293706293707, -41.80995475113122),
      new Position(19.020979020979023, -35.63348416289593),
      new Position(-1.188811188811189, -33.970588235294116),
      new Position(-27.81818181818182, -25.893665158371043),
      new Position(-0.23776223776223776, -11.402714932126697),
      new Position(25.678321678321677, -26.606334841628957),
      new Position(-14.503496503496503, -6.889140271493213),
      new Position(13.314685314685315, 22.092760180995477)
  };

  private boolean first = true;


  class TacticDetailImpl implements TacticDetail {

    @Override
    public String getTacticName() {
      return "Pequeo";
    }

    @Override
    public CountryCode getCountry() {
      return CountryCode.ES;
    }

    @Override
    public String getCoach() {
      return "Victor";
    }

    @Override
    public Color getShirtColor() {
      return new Color(153, 0, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getSocksColor() {
      return new Color(153, 0, 0);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 204, 0);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.FRANJA_DIAGONAL;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(0, 102, 0);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(0, 0, 102);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(0, 0, 153);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(0, 102, 0);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(18, 89, 54);
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
      Position Position;

      public JugadorImpl(String nombre, int numero, Color piel,
                         Color pelo, double velocidad, double remate,
                         double presicion, boolean portero) {
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
          new JugadorImpl("Casillas", 1, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 0.63d, true),
          new JugadorImpl("Capdevila", 2, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.82d, 0.5d, 0.51d, false),
          new JugadorImpl("Puyol", 3, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.85d, 0.5d, 0.8d, false),
          new JugadorImpl("Piqu�", 4, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.84d, 0.5d, 0.5d, false),
          new JugadorImpl("S. Ramos", 5, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.86d, 0.5d, 0.5d, false),
          new JugadorImpl("Xabi Alonso", 6, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.81d, 0.63d, 0.65d, false),
          new JugadorImpl("Silva", 7, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.79d, 0.64d, 0.72d, false),
          new JugadorImpl("Xavi", 8, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.86d, 0.67d, 0.69d, false),
          new JugadorImpl("Iniesta", 9, new Color(255, 200, 150),
                          new Color(50, 0, 0), 0.87d, 0.75d, 0.82d, false),
          new JugadorImpl("Villa", 10, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
          new JugadorImpl("Torres", 11, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false)};
    }
  }

  TacticDetail detalle = new TacticDetailImpl();

  @Override
  public TacticDetail getDetail() {
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

  private LinkedList<Integer> posiciones = new LinkedList<>();
  private LinkedList<Position> desmarcados = new LinkedList<>();
  private int jugadormascercano = 0;
  private int jugadormascercano2 = 0;
  private int marcajes[] = new int[11];

  private boolean solo(GameSituations sp, Position jugador) {
    boolean condicion = true;
    int cu = 0;
    Position[] rivales = sp.rivalPlayers();
    for (int j = 1; j < rivales.length && condicion; j++) {
      if (jugador.getY() < rivales[j].getY()) {
        if (cu != 0) {
          condicion = false;
        }
        cu++;
      }
    }
    return condicion;
  }


  private void Desmarcados(GameSituations sp) {
    posiciones = new LinkedList<>();
    desmarcados = new LinkedList<>();
    Position[] jugadores = sp.myPlayers();
    Position[] rivales = sp.rivalPlayers();
    for (int i = 0; i < jugadores.length; i++) {
      for (int j = 0; j < rivales.length; j++) {
        if (jugadores[i].distance(rivales[j]) > 0.5) {
          posiciones.add(i);
          desmarcados.add(jugadores[i]);
        }
      }
    }
  }

  private void pase(int jug, GameSituations sp, LinkedList<Command> comandos, boolean defensivo) {
    Desmarcados(sp);
    Position[] jugadores = sp.myPlayers();
    Position jugador = jugadores[jug];
    Position p;
    boolean cond;
    Random r = new Random();
    Position cercano = new Position(200, 200);
    Iterator<Position> iterador = desmarcados.iterator();
    if (defensivo && sp.ballPosition().getY() < -9) {
      comandos.add(new CommandHitBall(jug, r.nextInt(50), 1, true));
    } else if (
        (((desmarcados.contains(jugadores[jug]) || desmarcados.isEmpty()) && jug != 0) || solo(sp,
                                                                                               jugador))
        &&
        jugadores[jug].getY() < sp.ballPosition().getY()) {
      comandos.add(new CommandHitBall(jug));
    } else {
      while (iterador.hasNext()) {
        p = iterador.next();
        if (jugador.distance(cercano) < jugador.distance(p)
            || (jugador.distance(cercano) * 1.5 < jugador
            .distance(p) && p.getY() > cercano.getY())) {
          cercano = p;
        }
      }
      if (first) {
        cond = true;
        first = false;
      } else {
        cond = r.nextBoolean();
      }
      comandos.add(new CommandHitBall(jug, cercano, 0.838, cond));
    }
  }

  private Position MasCercano(Position[] rivales, Position jugador, int no) {
    Position posicion = rivales[0];
    for (int i = 0; i < rivales.length; i++) {
      if (jugador.distance(rivales[i]) < jugador.distance(posicion)) {
        if (i != no) {
          if (jugadormascercano2 != jugadormascercano) {
            jugadormascercano2 = jugadormascercano;
          }
          posicion = rivales[i];
          jugadormascercano = i;
        }
      }
    }
    return posicion;
  }


  private boolean Defensiva(Position[] rivales) {
    int jug = 0;
    for (int i = 0; i < rivales.length; i++) {
      if (rivales[i].getY() < -5) {
        jug++;
      }
    }
    if (jug >= 6) {
      return true;
    } else {
      return false;
    }
  }

  private void MarcarJugadores(GameSituations sp,
                               LinkedList<Command> comandos, int zona) {
    if (Defensiva(sp.rivalPlayers())) {
      zona = -14;
    }
    for (int y = 1; y < sp.rivalPlayers().length; y++) {
      marcajes[y] = 0;
    }
    Position[] jugadores = sp.myPlayers();
    portero(sp, comandos);
    for (int i = 1; i < jugadores.length; i++) {
      if (jugadores[i].getY() < zona) {
        int j = 0;
        // Ordena a cada jugador que se ubique con el adbersario m�s
        // cercano
        Position marc = MasCercano(sp.rivalPlayers(), jugadores[i], -1);
        while (marcajes[jugadormascercano] > 1 && j < sp.rivalPlayers().length) {
          marc = MasCercano(sp.rivalPlayers(), jugadores[i], jugadormascercano);
          j++;
        }
        if (j != sp.rivalPlayers().length) {
          comandos.add(new CommandMoveTo(i, marc));
          marcajes[jugadormascercano] = marcajes[jugadormascercano] + 1;
        }
      }
    }
  }

  private void Recuperar(GameSituations sp,
                         LinkedList<Command> comandos) {
    // Obtiene los datos de recuperacion del balon
    int[] recuperadores = sp.getRecoveryBall();
    // Si existe posibilidad de recuperar el balon
    if (recuperadores.length > 0) {
      // Obtiene las coordenadas del balon en el instante donde
      // se puede recuperar el balon
      double[] posRecuperacion = sp
          .getTrajectory(recuperadores[0]);
      // Recorre la lista de jugadores que pueden recuperar
      for (int i = 1; i < recuperadores.length; i++) {
        // Ordena a los jugadores recuperadores que se ubique
        // en la posicion de recuperacion
                        /*	if (!(recuperadores[i]==0 && Math.abs(sp.ballPosition().getX())>9 &&
						sp.ballPosition().getY()>-40 ))
				*/
        comandos.add(new CommandMoveTo(recuperadores[i],
                                       new Position(posRecuperacion[0],
                                                    posRecuperacion[1])));
      }
    }
  }


  private void portero(GameSituations sp,
                       LinkedList<Command> comandos) {
    if (sp.ballPosition().getX() <= 6 && sp.ballPosition().getX() >= -6) {
      if (sp.ballPosition().getY() > -35) {
        comandos.add(new CommandMoveTo(0, new Position(sp.ballPosition().getX(), -45)));
      } else {
        comandos.add(new CommandMoveTo(0, new Position(sp.ballPosition().getX(), -50)));
      }
    } else {
      double x = 6 * (sp.ballPosition().getX() - 1) / 37;
      if (x > 6) {
        x = 6;
      } else if (x < -6) {
        x = -6;
      }
      if (sp.ballPosition().getY() > -35) {
        comandos.add(new CommandMoveTo(0, new Position(x, -45)));
      } else {
        comandos.add(new CommandMoveTo(0, new Position(x, -50)));
      }
    }

  }


  @Override
  public List<Command> execute(GameSituations sp) {
    // Lista de comandos
    boolean defensivo = Defensiva(sp.rivalPlayers());
    Position ali[];
    if (defensivo) {
      ali = alineacion4;
    } else {
      ali = alineacion3;
    }
    LinkedList<Command> comandos = new LinkedList<>();
    // Obtiene las posiciones de tus jugadores
    Position[] jugadores = sp.myPlayers();
    for (int i = 0; i < jugadores.length; i++) {
      // Ordena a cada jugador que se ubique segun la alineacion normal
      comandos.add(new CommandMoveTo(i, ali[i]));
    }

    if (sp.isStarts()) {
      MasCercano(sp.myPlayers(), sp.ballPosition(), -1);
      Position Player = sp.myPlayers()[jugadormascercano];
			/*if (Player.getX()>sp.ballPosition().getX() && Player.getY()>sp.ballPosition().getY())
				comandos.add(new CommandMoveTo(jugadormascercano, new Position(sp
					.ballPosition().getX() + 0.75, sp.ballPosition().getY()+0.25)));
			else if (Player.getX()>sp.ballPosition().getX() && Player.getY()<sp.ballPosition().getY())
				comandos.add(new CommandMoveTo(jugadormascercano, new Position(sp
					.ballPosition().getX() + 0.75, sp.ballPosition().getY()-0.25)));
			else if (Player.getX()<sp.ballPosition().getX() && Player.getY()>sp.ballPosition().getY())
				comandos.add(new CommandMoveTo(jugadormascercano, new Position(sp
					.ballPosition().getX() - 0.75, sp.ballPosition().getY()+0.5)));
			else if (Player.getX()<sp.ballPosition().getX() && Player.getY()<sp.ballPosition().getY())
				comandos.add(new CommandMoveTo(jugadormascercano, new Position(sp
					.ballPosition().getX() - 0.75, sp.ballPosition().getY()-0.25)));
			*/
      comandos.add(new CommandMoveTo(jugadormascercano, new Position(sp
                                                                         .ballPosition().getX(),
                                                                     sp.ballPosition().getY())));
      MasCercano(sp.myPlayers(), Player, -1);
      comandos.add(new CommandMoveTo(jugadormascercano2, new Position(sp.ballPosition().getX() - 2,
                                                                      sp.ballPosition().getY()
                                                                      + 2)));

    }
    if (sp.isRivalStarts()) {
      portero(sp, comandos);
      MarcarJugadores(sp, comandos, 10);
    }
    // Si no saca el rival
    if (!sp.isStarts()) {
      // Actitud defensiba
      if ((sp.canKick().length <= 0 && sp.rivalCanKick().length > 0) || defensivo) {
        if (sp.ballPosition().getY() < 10) {
          MarcarJugadores(sp, comandos, 10);
        }
        Recuperar(sp, comandos);
      } else {
        MasCercano(sp.myPlayers(), sp.ballPosition(), -1);
        comandos.add(new CommandMoveTo(jugadormascercano, sp.ballPosition()));
        //comandos.add(new CommandMoveTo(0, new Position(9 * sp.ballPosition().getX() / 34, jugadores[0].getY())));
        portero(sp, comandos);
        Recuperar(sp, comandos);
      }

    }

    // Instancia un generador aleatorio
    Random r = new Random();
    // Recorre la lista de mis jugadores que pueden rematar
    for (int i : sp.canKick()) {
      // Si el jugador es de indice 8 o 10
      if (((i == 8 || i == 10 || i == 11) && sp.ballPosition().getY() > 25)
          || jugadores[i].getY() > 30) {
        // condicion aleatoria
				/*if (r.nextBoolean()) {
					// Ordena que debe rematar al centro del arco
					comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
							1, 12 + r.nextInt(6)));
				} else if (r.nextBoolean()) {
					// Ordena que debe rematar al poste derecho
					comandos.add(new CommandHitBall(i,
							Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
				} else {
					// Ordena que debe rematar al poste izquierdo
					comandos.add(new CommandHitBall(i,
							Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
				}*/
        Position port = sp.rivalPlayers()[0];
        if (port.getX() > 4 || port.getX() < -4) {
          // Ordena que debe rematar al centro del arco
          comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
                                          1, 12 + r.nextInt(6)));
        } else if (port.getX() < 4 && port.getX() > -4) {
          // Ordena que debe rematar al poste derecho
          if (r.nextBoolean()) {
            comandos.add(new CommandHitBall(i,
                                            Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
          } else {
            comandos.add(new CommandHitBall(i,
                                            Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
          }
        } else {
          // Ordena que debe rematar al poste izquierdo
          comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
                                          1, 12 + r.nextInt(6)));
        }
      } else {
        pase(i, sp, comandos, defensivo);
      }
    }
    // Retorna la lista de comandos
    return comandos;
  }
}