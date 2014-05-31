package org.dsaw.javacup.tactics.jvc2013.robertiano;

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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Loberos implements Tactic {

  private LinkedList<Command> comandos = new LinkedList<>();

  /**
   * Por defecto
   */
  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-14.265734265734267, -33.73303167420815),
      new Position(0.7132867132867133, -31.59502262443439),
      new Position(14.74125874125874, -33.970588235294116),
      new Position(-14.74125874125874, -13.065610859728507),
      new Position(12.363636363636363, -11.64027149321267),
      new Position(-25.202797202797203, 18.29185520361991),
      new Position(25.44055944055944, 21.855203619909503),
      new Position(-0.7132867132867133, 7.601809954751132),
      new Position(-13.314685314685315, 33.970588235294116),
      new Position(13.314685314685315, 33.02036199095023)
  };

  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -31.082089552238806),
      new Position(11.16030534351145, -31.6044776119403),
      new Position(27.251908396946565, -27.94776119402985),
      new Position(-29.84732824427481, -26.902985074626866),
      new Position(-1.188811188811189, -17.57918552036199),
      new Position(-9.034965034965035, -4.751131221719457),
      new Position(3.090909090909091, -1.6628959276018098),
      new Position(0.23776223776223776, 0.0),
      new Position(-19.020979020979023, -0.47511312217194573),
      new Position(19.020979020979023, -0.23755656108597287)
  };

  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-8.55944055944056, -34.68325791855204),
      new Position(6.895104895104895, -35.15837104072398),
      new Position(24.013986013986013, -32.30769230769231),
      new Position(-20.20979020979021, -29.457013574660635),
      new Position(-4.27972027972028, -21.61764705882353),
      new Position(-19.25874125874126, -8.789592760180994),
      new Position(22.349650349650346, -11.402714932126697),
      new Position(3.090909090909091, -19.004524886877828),
      new Position(-4.9930069930069925, -7.839366515837104),
      new Position(8.797202797202797, -2.8506787330316743)
  };

  class TacticaDetalleImpl implements TacticDetail {

    @Override
    public String getTacticName() {
      return "Loberos";
    }

    @Override
    public CountryCode getCountry() {
      return CountryCode.EH;
    }

    @Override
    public String getCoach() {
      return "Maximo";
    }

    @Override
    public Color getShirtColor() {
      return new Color(0, 102, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(0, 0, 102);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(102, 0, 102);
    }

    @Override
    public Color getSocksColor() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 51, 51);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.FRANJA_VERTICAL;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(224, 93, 101);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(219, 128, 56);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(170, 224, 87);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(45, 87, 251);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(131, 16, 91);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
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
          new JugadorImpl("Portero", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.7d,
                          0.83d, true),
          new JugadorImpl("Jugador", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.5d, false),
          new JugadorImpl("Jugador", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Jugador", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Jugador", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.71d, false),
          new JugadorImpl("Jugador", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.62d, false),
          new JugadorImpl("Jugador", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.27d,
                          0.84d, false),
          new JugadorImpl("Jugador", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.27d,
                          0.85d, false),
          new JugadorImpl("Centro", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.3d,
                          0.76d, false),
          new JugadorImpl("DelanteroIzquierda", 10, new Color(255, 200, 150), new Color(50, 0, 0),
                          1.0d, 1.0d, 0.84d, false),
          new JugadorImpl("DelanteroDerecha", 11, new Color(255, 200, 150), new Color(50, 0, 0),
                          1.0d, 1.0d, 0.84d, false)
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
    return alineacion2;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion3;
  }

  /**
   * En un pase, ángulo máximo que debe formar el triángulo comapañero-jugador-rival. Asegurándote
   * que el rival no va a estar muy cerca de la trayectoria de la bola
   */
  private final static double ANGULO_MAX_PASO = 5.0D;
  private final static double DISTANCIA_TIRO_ALTO = 30.0D;

  //    TODO RGB mejorar el saque de centro (sale el jugador corriendo con el balón, no pasa)
  @Override
  public List<Command> execute(GameSituations sp) {
    //Limpia la lista de comandos
    comandos.clear();
    //Obtiene las posiciones de tus jugadores
    Position[] jugadores = sp.myPlayers();

//		Establecemos la posición por defecto de los jugadores
    setPosicionDefecto(jugadores, sp);

//		Manda a los jugadores que pueden recuperar el balón que lo recuperen y al resto a la posición de covertura.
    setPosicionesRecuperacionCovertura(sp);

    //Instancia un generador aleatorio
    Random r = new Random();
    //Recorre la lista de mis jugadores que pueden rematar
    for (int jugador : sp.canKick()) {
//			Si el jugador tiene indice 10 u 11
      if (jugador == 9 || jugador == 10) {
        setComandosDelanteros(jugadores, jugador, sp, r);

      } else if (jugador == 8) {
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{9, 10});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 7) {
//				intenta pasar primero al delantero de su banda
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{10, 9, 8});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 6) {
//				intenta pasar primero al delantero de su banda
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{9, 10, 8});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 5) {
//				intenta pasar primero al delantero de su banda
        int
            jugadorDestino =
            getJugadorDestino(r, jugador, jugadores, sp, new int[]{8, 7, 10, 9, 6, 4});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 4) {
//				intenta pasar primero al delantero de su banda
        int
            jugadorDestino =
            getJugadorDestino(r, jugador, jugadores, sp, new int[]{8, 6, 9, 10, 5});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 3) {
//				intenta pasar primero al delantero de su banda
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{6, 8, 7, 5, 4});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 2) {
//				intenta pasar primero al delantero de su banda
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{6, 8, 4, 5});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
      } else if (jugador == 1) {
//				intenta pasar primero al delantero de su banda
        int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, new int[]{7, 8, 5, 4});
        if (jugadorDestino != jugador) {
          addPase(jugadores, jugador, jugadorDestino);
        } else {
//					TODO RGB si no tengo a quien pasar, que hago?? paso al hueco!!--> tengo que hacerlo
          comandos.add(
              new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
          comandos.add(new CommandHitBall(jugador));
        }
//				Si es el portero
      } else if (jugador == 0) {
//				pasamos a los jugadores que no sean defensa
        int
            jugadorDestino =
            getJugadorDestino(r, jugador, jugadores, sp, new int[]{4, 5, 6, 7, 8, 9, 10});
        comandos.add(new CommandHitBall(jugador, jugadores[jugadorDestino], 1, 20 + r.nextInt(20)));
      }
    }
    return comandos;
  }

  private void setPosicionDefecto(Position[] jugadores, GameSituations sp) {
    int[] rivalesCubiertos = new int[11];
    int cont = 0;

    for (int i = 0; i < jugadores.length; i++) {

//			Rival más proximo a mi alineación que no este cubierto
      final int rivalProx = alineacion1[i].nearestIndex(sp.rivalPlayers(), rivalesCubiertos);
//			si el rival más cercano a mi alineación está a menos de 11
      if (alineacion1[i].distance(sp.rivalPlayers()[rivalProx]) < 11) {
        final Position posCobertura;
        final Position posRival = sp.rivalPlayers()[rivalProx];
        final double anguloRivalBalon = posRival.angle(sp.ballPosition());
//				obtenemos la posición de 5ud en la dirección de la bola
        posCobertura = posRival.moveAngle(anguloRivalBalon, 5);
        comandos.add(new CommandMoveTo(i, posCobertura));
        rivalesCubiertos[cont++] = rivalProx;
      } else {// El rival más cercano está lejos para cubrirle
//			Ordena al jugador que se ubique segun la alineacion1
        comandos.add(new CommandMoveTo(i, alineacion1[i]));
      }
    }
  }

  private void setPosicionesRecuperacionCovertura(GameSituations sp) {
    //Si no saca el rival
    if (!sp.isRivalStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde
        //se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        Position recuperacion = new Position(posRecuperacion[0], posRecuperacion[1]);
        //Recorre la lista de jugadores que pueden recuperar
        for (int i = 1; i < recuperadores.length; i++) {
//					Si es saque de banda y el jugador es un delantero (10 u 11)
          if (sp.isStarts() && (recuperadores[i] == 9 || recuperadores[i] == 10))
//						va a por el balón el lateral correspondiente (10 --> 7 y 11 --> 8)
          {
            comandos.add(new CommandMoveTo(recuperadores[i] == 9 ? 6 : 7,
                                           recuperacion));
          } else if (recuperadores[i] == 0) {
            int[]
                iteracionesParaRematarRivalAux =
                Arrays.copyOf(sp.rivalIterationsToKick(), sp.rivalIterationsToKick().length);
            Arrays.sort(iteracionesParaRematarRivalAux);
//						si mi portero va a tardar más iteraciones que el jugador rival en pillar la bola que no vaya a recuperarla
            if (sp.iterationsToKick()[0] > iteracionesParaRematarRivalAux[0]) {
//							TODO RGB no va a por la bola, se pone entre la posición de remate del rival y el centro de la portería
            } else {
              comandos.add(new CommandMoveTo(recuperadores[i], recuperacion));
            }
          } else //Si es saque de banda y el jugador no es un delantero
          //Ordena a los jugadores recuperadores que se ubique
          //en la posicion de recuperacion
          {
            comandos.add(new CommandMoveTo(recuperadores[i], recuperacion));
          }
        }
      }
    }
  }

  /**
   * jugador puede ser sólo 9 o 10 (10 u 11)
   */
  private void setComandosDelanteros(Position[] jugadores, int jugador, GameSituations sp,
                                     Random r) {
    final double distanciaArco = jugadores[jugador].distance(Constants.centroArcoSup);

//    	TODO RGB Comprobar la distancia a la que los tiros a puerta efectivos
    if (distanciaArco < 36.0D) {
      final Position
          dondeRematar =
          getADondeRematar(jugadores[jugador], sp.rivalPlayers(), sp.rivalPlayersDetail());

      if (dondeRematar != null) {
        final int angulo = 12 + r.nextInt(6);
        comandos.add(new CommandHitBall(jugador, dondeRematar, 1, angulo));
      } else {
        //condicion aleatoria
        if (r.nextBoolean()) {
          //Ordena que debe rematar al centro del arco
          final int angulo = 12 + r.nextInt(6);
          comandos.add(new CommandHitBall(jugador, Constants.centroArcoSup, 1, angulo));
        } else if (r.nextBoolean()) {
          //Ordena que debe rematar al poste derecho
          final Position derecho = new Position(
              Constants.posteDerArcoSup.getX() - Constants.LARGO_ARCO / 6,
              Constants.posteDerArcoSup.getY());
          final int angulo = 12 + r.nextInt(6);
          comandos.add(new CommandHitBall(jugador, derecho, 1, angulo));
        } else {
          //Ordena que debe rematar al poste izquierdo
          final Position izquierdo = new Position(
              Constants.posteIzqArcoSup.getX() + Constants.LARGO_ARCO / 6,
              Constants.posteIzqArcoSup.getY());
          final int angulo = 12 + r.nextInt(6);
          comandos.add(new CommandHitBall(jugador, izquierdo, 1, angulo));
        }
      }
    } else {// estamos lejos de la portería
      int[] jugadoresDestino;
      if (jugador == 10) {
        jugadoresDestino = new int[]{9, 7, 8};
      } else {
        jugadoresDestino = new int[]{10, 8, 8};
      }

      final int jugadorDestino = getJugadorDestino(r, jugador, jugadores, sp, jugadoresDestino);
      //Si el receptor del ballPosition es el que remata o el compañero está cubierto
      if (jugador == jugadorDestino) {
//				Corremos con el ballPosition
        comandos.add(
            new CommandMoveTo(jugador, direccionCorrer(jugadores[jugador], sp.rivalPlayers())));
        comandos.add(new CommandHitBall(jugador));
//				TODO RGB correr con el balón va a ser la última opción vamos a intentar pasar a una posición donde pueda llegar nuestro compañero
      } else {
//				Ordena que el jugador que puede rematar que de un pase
//				al jugador destino
        addPase(jugadores, jugador, jugadorDestino);
      }
    }
  }

  private void addPase(Position[] jugadores, int jugador, int jugadorDestino) {
    final double distancia = jugadores[jugador].distance(jugadores[jugadorDestino]);
    final int angulo;
    if (distancia > DISTANCIA_TIRO_ALTO && distancia < (2 * DISTANCIA_TIRO_ALTO)) {
      angulo = 20;
    } else if (distancia > (2 * DISTANCIA_TIRO_ALTO)) {
      angulo = 35;
    } else {
      angulo = 2;
    }
    //Ordena que el jugador que puede rematar que de un pase al jugador destino
    comandos.add(new CommandHitBall(jugador, jugadores[jugadorDestino], 1, angulo));
  }

  /**
   * Calcula la mejor dirección para correr. Si el rivál más cercano, está muy cerca, corren en
   * dirección perpendicular a él en el sentido que le acerque más a la portería.
   */
  private Position direccionCorrer(Position pJugador, Position[] rivales) {
    final Position direccion;
    boolean rivalEncontrado = false;
    final int[] rivalProx = pJugador.nearestIndexes(rivales);
    int cont = 0;
//    	Mientras no lo encontremos un rival que nos bloque y nos queden rivalPlayers por estudiar
    while (!rivalEncontrado && cont < rivalProx.length) {
//    		si el rival está por delante de nosotros
      if (pJugador.getY() < rivales[rivalProx[cont]].getY()) {
//        		si está lo suficientemente cerca como para intercertarme
        if (pJugador.norm(rivales[rivalProx[cont]]) < 25) {
          rivalEncontrado = true;
        } else {// si el jugador qu está más cerca está a más de 25 no hay peligro de que nos quiten la bola
          break;
        }
      } else {
        cont++;
      }
    }
//    	
    if (rivalEncontrado) {
      final double anguloRival = pJugador.angle(rivales[rivalProx[cont]]);
      final double anguloDireccion;
      if (anguloRival > pJugador.angle(Constants.centroArcoSup)) {
        anguloDireccion = anguloRival - (Math.PI / 2);
      } else {
        anguloDireccion = anguloRival + (Math.PI / 2);
      }
      direccion = pJugador.moveAngle(anguloDireccion, 10);
    } else {
//    		Corremos hacia la portería
      direccion = Constants.centroArcoSup;
    }

    return direccion;
  }

  /**
   * Comprueba donde está el portero. Calcula la mejor posición a la que rematar.
   */
  private Position getADondeRematar(Position pRematador, Position[] rivales,
                                    PlayerDetail[] detalleJugadoresRivales) {
    Position dondeRematar = null;
    boolean porteroEncontrado = false;
    int contador = 0;
    while (!porteroEncontrado && contador < detalleJugadoresRivales.length) {
      if (detalleJugadoresRivales[contador].isGoalKeeper()) {
        porteroEncontrado = true;
      } else {
        contador++;
      }
    }
//		Si hemos encontrado el portero del equipo contrario (contador indica el índice)
    if (porteroEncontrado) {
      final Position portero = rivales[contador];
//			calculamos el ángulo portero-rematador-palo_derecho
      final double
          anguloPRD =
          pRematador.angle(portero) - pRematador.angle(Constants.posteDerArcoSup);
//			calculamos el ángulo portero-rematador-palo_izquierdo
      final double
          anguloPRI =
          pRematador.angle(portero) - pRematador.angle(Constants.posteIzqArcoSup);
//			Si hay más hueco por la derecha (angulo derecho mayor) tiramos con a 3/4 del portero hacia la derecha
      if (Math.abs(anguloPRD) > Math.abs(anguloPRI)) {
        dondeRematar = pRematador.moveAngle(
            pRematador.angle(portero) - ((3 / 4) * anguloPRD), 10.0D);
      } else {
//				Si hay más hueco por la izquierda (ángulo izquierdo mayor) tiramos con a 3/4 del portero hacia la izquierda
        dondeRematar = pRematador.moveAngle(
            pRematador.angle(portero) - ((3 / 4) * anguloPRI), 10.0D);
      }
    }
    return dondeRematar;
  }

  /**
   * Compueba si no hay nadie que pueda robar la bola si pasa al compañero. Si hay algún jugador en
   * el triángulo isósceles con vertice el jugador, altura, la distancia al compañero y ángulo
   * distinto 2 * ANGULO_MAX_PASO. En el caso de que el compañero esté lejos sólo comprueba que no
   * haya nadie pegado al tirador, que podría robarle la bola.
   */
  private boolean isCompaCubierto(Position jugador, Position compa, GameSituations sp) {
    final boolean isCompaCubierto;
    final double distanciaCompa = jugador.distance(compa);
    Position[] rivales = sp.rivalPlayers();

    Position[] cubridores = getCubridores(rivales, jugador, compa, distanciaCompa);

    if (distanciaCompa > DISTANCIA_TIRO_ALTO && cubridores.length > 0) {
      final int cubridorPox = jugador.nearestIndex(cubridores);
      final double distanciaAlCubridor = jugador.norm(cubridores[cubridorPox]);
      if (distanciaAlCubridor > 4 && distanciaAlCubridor < 12) {
        isCompaCubierto = true;
      } else {
        isCompaCubierto = false;
      }
    } else {
      isCompaCubierto = cubridores.length > 0;
    }
    return isCompaCubierto;
  }

  private Position[] getCubridores(Position[] rivales, Position jugador, Position compa,
                                   double distanciaCompa) {
    final double anguloCompa = jugador.angle(compa);

    List<Position> cubridores = new ArrayList<>();
    for (Position rival : rivales) {
      final double anguloRival = jugador.angle(rival);

      final double anguloRelativo = anguloRival - anguloCompa;
      final double distanciaRival = jugador.norm(rival);

      if (-ANGULO_MAX_PASO < anguloRelativo && anguloRelativo < ANGULO_MAX_PASO
          && distanciaRival < distanciaCompa) {
        cubridores.add(rival);
      }
    }
    return cubridores.toArray(new Position[cubridores.size()]);
  }

  /**
   * Solo busca los jugadores cuyo numero este entre jugadoresDestino.
   *
   * @param jugadoresDestino lista con los número de los jugadores destino ordenados por
   *                         preferencia, no puede estar vacía.
   */
  private int getJugadorDestino(Random r, int tirador, Position[] jugadores, GameSituations sp,
                                int[] jugadoresDestino) {
    int count = 0;
    int jugadorDestino = -1;
    double distancia;
    boolean encontrado = false;
//		Mientras no se ha encontrado el jugador destino y el contador es menor al número de jugadores destino
    while (!encontrado && count < jugadoresDestino.length) {

      jugadorDestino = jugadoresDestino[count++];
      distancia = jugadores[tirador].norm(jugadores[jugadorDestino]);
      if (distancia > DISTANCIA_TIRO_ALTO) {
        encontrado = true;
      }
//			Tiene que comprobar si está libre
      if (!isCompaCubierto(jugadores[tirador], jugadores[jugadorDestino], sp))
//				hemos encontrado el jugador destino
      {
        encontrado = true;
      }
    }
//		Si no encontramos a quien pasar
    if (!encontrado)
//			nosotros somos el jugados destino
    {
      jugadorDestino = tirador;
    }
    return jugadorDestino;
  }

}