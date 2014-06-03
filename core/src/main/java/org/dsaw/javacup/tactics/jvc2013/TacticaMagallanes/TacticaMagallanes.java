package org.dsaw.javacup.tactics.jvc2013.TacticaMagallanes;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TacticaMagallanes implements Tactic {

  public abstract class DSL {

    private GameSituations sp;
    private int player;

    public DSL(GameSituations sp, Integer player) {
      this.sp = sp;
      this.player = player;
    }

    public Position arcoRival() {
      return Constants.centroArcoSup;
    }

    public Position posicionBalon() {
      return sp.ballPosition();
    }

    public Position arcoPropio() {
      return Constants.centroArcoInf;
    }

    public Position media(Position p1, Position p2) {
      return Position.medium(p1, p2);
    }

    public double getX(Position p) {
      return p.getX();
    }

    public double getY(Position p) {
      return p.getY();
    }

    public abstract Position resolve();

    public Position posicionJugador() {
      return sp.myPlayers()[player];
    }

    public Position propio(int i) {
      return sp.myPlayers()[i];
    }

    public Position rival(int i) {
      return sp.rivalPlayers()[i];
    }

    public int me() {
      return player;
    }

    public Position alterarPosicion(Position pos, double i, double adelantoPase) {
      return new Position(pos.getX() + i, pos.getX() + adelantoPase);
    }
  }

  public class DSLAtaque extends DSL {

    public DSLAtaque(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return media(arcoRival(), posicionBalon());
    }
  }

  public class DSLDefensa extends DSL {

    DSLDefensa(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      Position p;
      p = (((0 < (getX(arcoPropio()))))) ?
          (arcoPropio())
                                         : ((media((media(arcoRival(),
                                                          (media((media(arcoPropio(), arcoRival())),
                                                                 posicionBalon())))),
                                                   arcoRival())));

      return p;
    }
  }

  public class DSLAtaque2 extends DSL {

    public DSLAtaque2(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return media(arcoRival(), posicionBalon());
    }
  }


  public class DSLDefensa2 extends DSL {

    public DSLDefensa2(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      Position p = null;
      p = (((0 < (getX(arcoPropio()))))) ?
          (arcoPropio())
                                         : ((media((media(arcoRival(),
                                                          (media((media(arcoPropio(), arcoRival())),
                                                                 posicionBalon())))),
                                                   arcoRival())));

      return media(arcoRival(), arcoPropio());
    }
  }

  public class DSLPase2 extends DSL {

    public DSLPase2(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return alterarPosicion(propio(me() + 2), 0, adelantoPase);
    }
  }

  /*
  inc((inc(2))) ==> 27 ==> 14 ==> 1.0 ==> 85 ==> 4 ==> 4 ==>
  posicionBalon() ==>
  media(arcoRival(),posicionBalon()) ==>
  propio( 6)
   */
  public class DSLDefensa3 extends DSL {

    public DSLDefensa3(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return posicionBalon();
    }
  }

  public class DSLAtaque3 extends DSL {

    public DSLAtaque3(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return media(arcoRival(), posicionBalon());
    }
  }

  public class DSLPase3 extends DSL {

    public DSLPase3(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return propio(6);
    }
  }

  public class DSLPase4 extends DSL {

    public DSLPase4(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return alterarPosicion((propio(2)), -2.4532437324523926, (getX((rival(me())))));
    }

  }

  public class DSLDefensa4 extends DSL {

    public DSLDefensa4(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return posicionJugador();
    }
  }

  public class DSLAtaque4 extends DSL {

    public DSLAtaque4(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return media((media(posicionBalon(), posicionJugador())),
                   ((((0 >= 0))) ? (arcoRival()) : (posicionBalon())));
    }
  }

  public class DSLPase5 extends DSL {

    public DSLPase5(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return alterarPosicion((propio(5)), 11.667728424072266, (getX((rival(me())))));

      //alterarPosicion((propio( 2)),-2.4532437324523926,(getX((rival( me())))));
    }

  }

  public class DSLDefensa5 extends DSL {

    public DSLDefensa5(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return posicionJugador();
    }
  }

  public class DSLAtaque5 extends DSL {

    public DSLAtaque5(GameSituations sp, Integer player) {
      super(sp, player);
    }

    @Override
    public Position resolve() {
      return
          media((media(posicionBalon(), posicionJugador())),
                ((((0 >= 0))) ? (arcoRival()) : ((media(posicionBalon(), posicionJugador())))));
      //media((media(posicionBalon(),posicionJugador())),((((0 >= 0))) ? (arcoRival()) :(posicionBalon())));
    }
  }


  public DSL instanciarDSL(TacticaMagallanes t, String dsl, GameSituations sp, Integer player) {
    DSL instance = null;

    try {
      instance = (DSL) Class.forName(
          "org.javahispano.javacup.tacticas.tacticas_aceptadas.TacticaMagallanes.TacticaMagallanes$"
          + dsl).getConstructor(TacticaMagallanes.class, GameSituations.class, Integer.class)
          .newInstance(t, sp, player);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return instance;
  }

  private int[] delanteros = new int[]{8, 9, 10};
  private int[] defensas = new int[]{1, 2, 3};

  private int maxrecuperadores = 3;
  private int adelantoPase = 30;
  private int adelantoPaseMiddle = 15;

  private int distanciaDisparo = 30;
  private double fuerzaDisparo = 1;

  private int costoLejania = 50;
  private int costoRivales = 5;
  private int costoAtaqueRivales = 5;

  private String dslDefensa = "DSLDefensa2";
  private String dslAtaque = "DSLAtaque2";
  private String dslPase = "DSLPase3";

  public TacticaMagallanes() {
                /*
		 * 6;45;49;1.0;63;7;6
		 * 3,30,15,30,1,50,5,5
		 *  inc((inc((inc((inc(2))))))) ==> 
		 *  dec((inc((inc((inc(14))))))) ==> 
		 *  inc(28) ==> 
		 *  1.0 ==> 
		 *  dec((dec((inc((inc(32))))))) ==> 
		 *  dec((dec((inc((dec(9))))))) ==> 
		 *  inc((inc((inc((inc(3)))))))
		 */
    //inc(2) ==> 21 ==> 47 ==> 1.0 ==> 46 ==> 6 ==> inc(7) ==>
    //this(3,21,47,1.0,46,6,8);
    //inc(1) ==> 28 ==> 34 ==> 1.0 ==> 27 ==> 3 ==> dec(9)
    //inc((inc(2))) ==> 27 ==> 14 ==> 1.0 ==> 85 ==> 4 ==> 4 ==>
    //	this(2,28,34,1.0,27,3,8,"DSLDefensa2","DSLAtaque2","DSLPase2");
    //this(3,27,14,1.0,85,4,4,"DSLDefensa3","DSLAtaque3","DSLPase3");
    //this(1,21,13,1.0,24,2,9,"DSLDefensa4","DSLAtaque4","DSLPase4");
		/* 
		 * inc((dec((inc(2))))) ==> 
			
			inc((inc((inc((inc(37))))))) ==> 
		
		inc((dec((inc((dec(39))))))) ==> 
		1.0 ==> 
		dec((dec((dec((dec((dec((dec((dec(90))))))))))))) 
		==> 7
		 ==> dec(2)
		*/
    this(6, 45, 49, 1.0, 63, 7, 6, "DSLDefensa3", "DSLAtaque3", "DSLPase3");
    //this(3,41,39,1.0,83,7,1,"DSLDefensa5","DSLAtaque5","DSLPase5");
  }

  public TacticaMagallanes(int maxrecuperadores, int adelantoPase,
                           int distanciaDisparo, double fuerzaDisparo, int costoLejania,
                           int costoRivales, int costoAtaqueRivales, String defensa, String ataque,
                           String pase) {
    this.maxrecuperadores = maxrecuperadores;
    this.adelantoPase = adelantoPase;
    this.adelantoPaseMiddle = (adelantoPase / 2);
    this.distanciaDisparo = distanciaDisparo;
    this.fuerzaDisparo = fuerzaDisparo;
    this.costoLejania = costoLejania;
    this.costoRivales = costoRivales;
    this.costoAtaqueRivales = costoAtaqueRivales;
    this.dslAtaque = ataque;
    this.dslDefensa = defensa;
    this.dslPase = pase;


  }


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
      new Position(-0.7786259541984732, 29.097014925373134),
      new Position(12.717557251908397, 29.51492537313433)};

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
      new Position(-0.2595419847328244, 35.78358208955224)};

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
      new Position(18.946564885496183, 35.26119402985075)};

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
      new Position(25.69465648854962, 28.992537313432837)};

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
      new Position(22.580152671755727, -1.3059701492537314)};

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
      new Position(22.580152671755727, -1.3059701492537314)};

  class TacticaDetalleImpl implements TacticDetail {

    @Override
    public String getTacticName() {
      return "Rep. de Magallanes";
    }

    @Override
    public CountryCode getCountry() {
      return CountryCode.CL;
    }

    @Override
    public String getCoach() {
      return "Mauricio N��ez";
    }

    @Override
    public Color getShirtColor() {
      return new Color(255, 0, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(0, 0, 255);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(37, 139, 109);
    }

    @Override
    public Color getSocksColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(115, 86, 236);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(101, 89, 147);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(182, 126, 41);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(74, 47, 38);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(85, 22, 111);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(91, 55, 68);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.FRANJA_DIAGONAL;
    }

    class JugadorImpl implements PlayerDetail {

      String nombre;
      int numero;
      Color piel, pelo;
      double velocidad, remate, presicion;
      boolean portero;
      Position posicion;

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
          new JugadorImpl("Christofer", 1, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, true),
          new JugadorImpl("Ruben", 2, new Color(0, 0, 0), new Color(
              0, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Gabriel", 3, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Gari", 4, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Miguel", 5, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Hugo", 6, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Mark", 7, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Mauricio", 8, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
          new JugadorImpl("Humberto", 9, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
          new JugadorImpl("Mago", 10, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 0.5d, 0.81d, false),
          new JugadorImpl("Alexis", 11, new Color(255, 200, 150),
                          new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false)};
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

  private Engine engine = new Engine();
  private Random r = new Random();

  class Engine {

    private GameSituations sp;

    private Engine() {

    }

    public void actualizarEstado(GameSituations sp) {
      this.sp = sp;
    }

    public double evaluar(Position posicion) {
      Position[] rivales = sp.rivalPlayers();
      Position[] amigos = sp.myPlayers();

      double cost;
      double min = posicion.norm(rivales[0]) + costoAtaqueRivales
                                               * rivales[0].getY();
      for (int i = 0; i < rivales.length; i++) {
        double c = posicion.norm(rivales[i]) + costoAtaqueRivales
                                               * rivales[i].getY();
        if (c < min) {
          min = c;
        }
      }
      cost = min * -costoRivales;
      cost += (costoLejania * Constants.penalSup.norm(posicion));
      return cost;
    }

    public Position getBestPase(Position origen, Position[] alts) {
      int[] idx = origen.nearestIndexes(alts);
      Position retorno = alts[idx[0]];
      double eval = evaluar(retorno);
      for (int i = 1; i < idx.length; i++) {
        // if(i>6)break;
        Position candidate = alts[idx[i]];
        double candidateEval = evaluar(candidate);
        if (eval > candidateEval) {
          eval = candidateEval;
          retorno = candidate;
        }
      }

      return retorno;
    }


    public Cmd paseDirecto(int i, int j) {
      Position destino = sp.myPlayers()[j];
      return new Pase(i, destino, false);
    }

    public Cmd poner(int i) {
      DSL dsl = instanciarDSL(TacticaMagallanes.this, dslPase, sp, i);
      Position destino = dsl.resolve();
      return new Pase(i, destino, true);
    }

    class Movimientos {

      Position actual = null;
      Position balon = null;
      int i = 0;
      Position retorno = null;

      Position arco = Constants.centroArcoInf;
      Position arcoRival = Constants.centroArcoSup;

      public Position[] yuxta(Position p1, Position p2) {
        return null;
      }

      public Position avance(Position p1, Position p2) {
        return null;
      }

      public Position media(Position p1, Position p2) {
        return Position.medium(p1, p2);
      }

      public double norma(Position p1, Position p2) {
        return p1.norm(p2);
      }

      public double distancia(Position p1, Position p2) {
        return p1.norm(p2);
      }


      public Position balon() {
        return null;
      }

      public Position actual() {
        return null;
      }

      public Position alineacion() {
        return null;
      }


    }

    public Cmd bestPosicion(int i) {
      Object[] a_data = new Object[]{sp, i};
      Position media = Position.medium(sp.ballPosition(),
                                       Constants.centroArcoInf);
      Position centro = Position.medium(
          new Position(0, sp.ballPosition().getY()),
          Constants.centroArcoSup);

      if (inside(i, defensas) && sp.ballPosition().getY() < -35 && r.nextBoolean()) {
        Position destino;

        DSL f = instanciarDSL(TacticaMagallanes.this, dslDefensa, sp, i);
        //new DSLDefensa2(sp,i);
        destino = f.resolve();

        return new Ir(i, destino);

      }

      if (inside(i, delanteros) && sp.ballPosition().getY() < 30
          && sp.ballPosition().getY() > -60) {

        Position destino;
        //DSL f=new DSLAtaque2(sp,i);
        DSL f = instanciarDSL(TacticaMagallanes.this, dslAtaque, sp, i);

        destino = f.resolve();
        return new Ir(i, destino);
      }
      return new Ir(i, alineacion1[i]);
    }


    public Cmd dispararArco(int i) {
      return new Shoot(i);
    }

    public Cmd correr(int i) {
      return new Run(i);
    }

    public double tiempoLlegada(Position origen, Position destino, double vel) {

      double tiempo = origen.distance(destino) / vel;
      return tiempo;
    }


    public Cmd bestPase(int i) {

      Position[] destinos = sp.myPlayers();

      Position[] alternativas;
      List<Position> alt = new ArrayList<>();

      for (int ii = 1; ii < 11; ii++) {
        if (ii != i) {
          alt.add(destinos[ii]);
        }
        alt.add(new Position(destinos[ii].getX()
                             + r.nextInt(adelantoPase) - adelantoPaseMiddle,
                             destinos[ii].getY() + r.nextInt(adelantoPaseMiddle)));
        alt.add(new Position(destinos[ii].getX()
                             + r.nextInt(adelantoPase) - adelantoPaseMiddle,
                             destinos[ii].getY() + r.nextInt(adelantoPaseMiddle)));
        alt.add(new Position(destinos[ii].getX()
                             + r.nextInt(adelantoPase) - adelantoPaseMiddle,
                             destinos[ii].getY() + r.nextInt(adelantoPaseMiddle)));
      }
      alternativas = alt.toArray(new Position[alt.size()]);

      Position destino = getBestPase(destinos[i], alternativas);
      if (destinos[i].distance(destino) > 15) {
        return new Pase(i, destino, true);
      }
      return new Pase(i, destino, false);
    }

  }

  abstract class Cmd {

    private Engine engine;

    public Cmd(Engine engine) {
      this.engine = engine;
    }

    public abstract void visit(List<Command> cmd);

    public abstract double evaluate();
  }

  class Nop extends Cmd {

    public Nop() {
      super(engine);
    }

    @Override
    public void visit(List<Command> cmd) {

    }

    @Override
    public double evaluate() {
      return 0;
    }
  }

  class Ir extends Cmd {

    private int i;
    private Position pos;

    private Ir(int i, Position pos) {
      super(engine);
      this.i = i;
      this.pos = pos;
    }

    @Override
    public void visit(List<Command> cmd) {

      cmd.add(new CommandMoveTo(i, pos));
    }

    @Override
    public double evaluate() {
      double cost = 15 - engine.sp.myPlayers()[i].distance(pos);
      return cost;
    }
  }

  class Run extends Cmd {

    private int i;

    private Run(int i) {
      super(engine);
      this.i = i;
    }

    @Override
    public void visit(List<Command> cmd) {
      cmd.add(new CommandHitBall(i));
    }

    @Override
    public double evaluate() {
      double cost = 15 * engine.sp.getMyPlayerSpeed(i);
      return cost;
    }
  }

  class Shoot extends Cmd {

    private int i;

    Shoot(int i) {
      super(engine);
      this.i = i;
    }

    @Override
    public void visit(List<Command> cmd) {
      Command comando;
      Random r = new Random();
      if (r.nextBoolean()) {
        // Ordena que debe rematar al centro del arco
        comando = new CommandHitBall(i, Constants.centroArcoSup,
                                     fuerzaDisparo, 12 + r.nextInt(6));
      } else if (r.nextBoolean()) {
        // Ordena que debe rematar al poste derecho
        comando = new CommandHitBall(i,
                                     Constants.posteDerArcoSup, fuerzaDisparo,
                                     12 + r.nextInt(6));
      } else {
        // Ordena que debe rematar al poste izquierdo
        comando = new CommandHitBall(i,
                                     Constants.posteIzqArcoSup, fuerzaDisparo,
                                     12 + r.nextInt(6));
      }
      cmd.add(comando);
    }

    @Override
    public double evaluate() {
      double cost = 45 - engine.sp.myPlayers()[i].distance(Constants.centroArcoSup);
      return cost;
    }
  }

  class Pase extends Cmd {

    private int i;
    private Position destino;
    private boolean alto;

    private Pase(int i, Position destino, boolean alto) {
      super(engine);
      this.i = i;
      this.destino = destino;
      this.alto = alto;
    }

    @Override
    public void visit(List<Command> cmd) {
      Command comando = new CommandHitBall(i, destino, 1, alto);
      cmd.add(comando);
    }

    @Override
    public double evaluate() {
      double cost = 15 * engine.sp.getMyPlayerPower(i) - 25 * engine.sp.getMyPlayerError(i);
      return cost;
    }
  }

  class Combinacion extends Cmd {

    private List<Cmd> childs;
    private int age;

    private Combinacion(List<Cmd> childs, int age) {
      super(engine);
      this.childs = childs;
      this.age = age;
    }

    @Override
    public void visit(List<Command> cmd) {
      age--;
      for (Cmd child : childs) {
        child.visit(cmd);
      }
    }

    @Override
    public double evaluate() {
      return 0;
    }

    public int getAge() {
      return age;
    }
  }

  public boolean inside(int i, int j[]) {
    boolean inside = false;
    for (int jj : j) {
      if (i == jj) {
        inside = true;
        break;
      }
    }
    return inside;
  }

  @Override
  public List<Command> execute(GameSituations sp) {

    List<Cmd> comandosAll = new ArrayList<>();
    List<Command> retorno = new ArrayList<>();
    engine.actualizarEstado(sp);
    int[] recuperadores = sp.getRecoveryBall();

    int cuantos = recuperadores.length < maxrecuperadores ? recuperadores.length
                                                          : maxrecuperadores;

    for (int player = 0; player < 11; player++) {

      List<Cmd> comandos = new ArrayList<>();
      comandos.add(engine.bestPosicion(player));
      if (recuperadores.length > 1) {
        int[] rec = new int[cuantos - 1];
        System.arraycopy(recuperadores, 1, rec, 0, cuantos - 1);
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        if (!sp.isRivalStarts() && inside(player, rec)) {
          comandos.add(new Ir(player, new Position(
              posRecuperacion[0], posRecuperacion[1])));
        }
      }

      if (inside(player, sp.canKick())) {
        comandos.add(engine.bestPase(player));
        if (inside(player, defensas)) {
          comandos.add(engine.poner(player));
        }
        if (inside(player, delanteros)) {
          comandos.add(engine.bestPase(player));
          if (sp.myPlayers()[player]
                  .distance(Constants.penalSup) <= distanciaDisparo
              || r.nextBoolean()) {
            comandos.add(engine.dispararArco(player));
          } else {
            if (r.nextInt(100) < 50) {
              comandos.add(engine.correr(player));
            }
          }
        }
      }
      comandosAll.addAll((comandos));

    }

    for (Cmd cmd : comandosAll) {
      cmd.visit(retorno);
    }
    return retorno;
  }

}
