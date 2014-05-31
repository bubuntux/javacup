package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.LinkedList;

/**
 * Comportamientos individuales de los jugadores.
 */
public class ControladorJugador {

  // CONSTANTES ================================================================================//

  /**
   * Número experimental de iteraciones por cada dribbling obtenido al utilizar la constante
   * Garzón.
   */
  private static final int ITS_X_DRIBBLING = 14;

  private static final int ITS_X_CONTROL = Constants.ITERACIONES_GOLPEAR_BALON;

  private static final int ITS_X_PROYECCION = 20;

  // ATRIBUTOS =================================================================================//

  /**
   * Lista de comandos.
   */
  private LinkedList<Command> comandos;

  /**
   * Cuarto de la cancha en el que se encuentra el balón.
   */
  private int cuartoCancha;

  /**
   * Detalle de la táctica implementada.
   */
  private TacticaDetalleImpl detalle = new TacticaDetalleImpl();

  /**
   * Arreglo de contadores de dribbling para todos los jugadores.
   */
  private int[] dribbling = new int[Utilitarios.NUMERO_JUGADORES];

  /**
   * Arreglo de contadores de control del balón para todos los jugadores.
   */
  private int[] control = new int[Utilitarios.NUMERO_JUGADORES];

  private int[] proyectados = new int[Utilitarios.NUMERO_JUGADORES];

  /**
   * Indice del jugador al cual se le realizó un pase.
   */
  private int jugadorReceptor = -1;

  /**
   * Posición en que el arquero debe interceptar un remate al arco.
   */
  private Position posicionInterceptacionArquero = new Position();

  /**
   * Situación del partido en la iteración actual.
   */
  private GameSituations sp;

  /**
   * Caché de la situación del partido en la iteración anterior.
   */
  private SituacionPartidoCache spItAnterior;

  /**
   * Lista de rivalPlayers marcados.
   */
  private LinkedList<Integer> jugadoresMarcados;

  // BANDERAS ==================================================================================//

  /**
   * Indica si el balón va en dirección al arco
   */
  private boolean balonAlArco = false;
  private boolean atacando = false;

  // MÉTODOS ===================================================================================//

  /**
   * Configura la acción del arquero en la iteración actual de acuerdo a la situación del juego.
   */
  public void comportamientoArquero() {
    int arquero = Alineacion.INDICE_ARQUERO;

    // Si la pelota está en mi lado de la cancha se le configura un comportamiento. De otra forma
    // mantiene el comportamiento configurado globalmente.
    if (sp.ballPosition().getY() < 0d) {
      // Si el arquero puede rechazar, que le haga. Si no entonces que se mueva.
      for (int rematador : sp.canKick()) {
        if (rematador == arquero) {
          if (spItAnterior.balon().getX() < sp.ballPosition().getX()) {
            double[]
                fuerzaAnguloRechazo =
                obtenerFuerzaAnguloPase(arquero, sp.myPlayers()[Alineacion.INDICE_VOLANTE_D]);
            comandos.add(new CommandHitBall(arquero,
                                            sp.myPlayers()[Alineacion.INDICE_VOLANTE_D],
                                            fuerzaAnguloRechazo[0], fuerzaAnguloRechazo[1]));
          } else {
            double[]
                fuerzaAnguloRechazo =
                obtenerFuerzaAnguloPase(arquero, sp.myPlayers()[Alineacion.INDICE_VOLANTE_I]);
            comandos.add(new CommandHitBall(arquero,
                                            sp.myPlayers()[Alineacion.INDICE_VOLANTE_I],
                                            fuerzaAnguloRechazo[0], fuerzaAnguloRechazo[1]));
          }
          return;
        }
      }
      comandos.add(new CommandMoveTo(arquero, calcularPosicionArquero()));
    }
  }

  /**
   * Ordena a un jugador moverse hacia la posición del balón.
   *
   * @param jugador   Jugador al cual se le ordenará desplazarse
   * @param situacion Objeto {@link GameSituations } que representa la situación del partido
   */
  public void comportamientoDesplazarseHaciaBalon(int jugador) {
    Position posicionBalon = sp.ballPosition();
    comandos.add(new CommandMoveTo(jugador, posicionBalon));
  }

  public void comportamientoMarcarHombre(int jugador) {
    Position posicionMarcador = spItAnterior.misJugadores()[jugador];
    JugadorImpl jugadorImpl = Alineacion.getJugadores()[jugador];
    double[][] distancias = Utilitarios.distanciaJugadores(posicionMarcador, sp.rivalPlayers());
    for (int i = 0; i < Utilitarios.NUMERO_JUGADORES; i++) {
      boolean hombreMarcado = false;
      int jugadorAMarcar = (int) distancias[0][i];
      if (distancias[1][i] > jugadorImpl.getRadioMarcacion()) {
        jugadoresMarcados.add((int) distancias[0][0]);
        comandos.add(new CommandMoveTo(jugador, sp.rivalPlayers()[(int) distancias[0][0]]));
        break;
      } else {
        for (Integer indiceHombreMarcado : jugadoresMarcados) {
          if (jugadorAMarcar == indiceHombreMarcado) {
            hombreMarcado = true;
            break;
          }
        }
        if (!hombreMarcado) {
          jugadoresMarcados.add(jugadorAMarcar);
          comandos.add(new CommandMoveTo(jugador, sp.rivalPlayers()[jugadorAMarcar]));
          break;
        }
      }
    }
  }

  public void comportamientoMarcarPase(int jugador) {
    Position posicionBalon = spItAnterior.balon();
    JugadorImpl jugadorImpl = Alineacion.getJugadores()[jugador];
    double[][] distancias = Utilitarios.distanciaJugadores(posicionBalon, sp.rivalPlayers());
    for (int i = 1; i < Utilitarios.NUMERO_JUGADORES; i++) {
      boolean hombreMarcado = false;
      int jugadorAMarcar = (int) distancias[0][i];
      if (distancias[1][i] > jugadorImpl.getRadioMarcacion()) {
        jugadoresMarcados.add((int) distancias[0][0]);
        comandos.add(new CommandMoveTo(jugador, sp.rivalPlayers()[(int) distancias[0][0]]));
        break;
      } else {
        for (Integer indiceHombreMarcado : jugadoresMarcados) {
          if (jugadorAMarcar == indiceHombreMarcado) {
            hombreMarcado = true;
            break;
          }
        }
        if (!hombreMarcado) {
          jugadoresMarcados.add(jugadorAMarcar);
          comandos.add(new CommandMoveTo(jugador, sp.rivalPlayers()[jugadorAMarcar]));
          break;
        }
      }
    }
  }

  /**
   * Configura la acción de los jugadores que pueden tener el balón en la iteración actual.
   */
  public void comportamientoPoseedoresBalon() {
    boolean controlBalon[] = new boolean[Utilitarios.NUMERO_JUGADORES];

    // Revisa si el jugador tiene la pelota.
    for (int rematador : sp.canKick()) {
      if (rematador != Alineacion.INDICE_ARQUERO) {
        controlBalon[rematador] = true;
        if (rematador == jugadorReceptor) {
          jugadorReceptor = -1;
        }
      }
    }

    for (int i = 0; i < Utilitarios.NUMERO_JUGADORES; i++) {
      if (controlBalon[i]) {
        comportamientoPoseedorBalon(i);
      } else if (dribbling[i] > 0) {
        comportamientoDesplazarseHaciaBalon(i);
        dribbling[i]--;
      } else if (control[i] > 0) {
        comportamientoDesplazarseHaciaBalon(i);
        control[i]--;
      }
    }
  }

  public void comportamientoProyectados() {
    for (int i = 0; i < Utilitarios.NUMERO_JUGADORES; i++) {
      if (proyectados[i] > 0) {
        JugadorImpl jugadorImpl = Alineacion.getJugadores()[i];
        Position posicionJugador = sp.myPlayers()[i];
        Position[] referenciasDrible = jugadorImpl.getReferenciasDrible();
        Position posicionDesplazamiento = null;
        for (int j = 0; j < referenciasDrible.length; j++) {
          Position referenciaDrible = referenciasDrible[j];
          // Si la distancia al punto de referencia es menor a 2 metros
          // no se proyecta-
          if (posicionJugador.distance(referenciaDrible) < 2d) {
            proyectados[i] = 0;
            break;
          }
          double angHorizontal = posicionJugador.angle(referenciaDrible);
          double desplazamientoHorizontal = jugadorImpl.getSpeed();
          double x = posicionJugador.getX() + desplazamientoHorizontal * Math.cos(angHorizontal);
          double y = posicionJugador.getY() + desplazamientoHorizontal * Math.sin(angHorizontal);
          if (jugadorDesmarcado(new Position(x, y))) {
            posicionDesplazamiento = new Position(x, y);
          }
        }
        posicionDesplazamiento =
            (posicionDesplazamiento == null ? referenciasDrible[0] : posicionDesplazamiento);
        comandos.add(new CommandMoveTo((i), posicionDesplazamiento));
        proyectados[i]--;
      }
    }
  }

  public void comportamientoReceptor() {
    if (jugadorReceptor > 0) {
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
          if (recuperadores[i] == jugadorReceptor) {
            comandos.add(new CommandMoveTo(jugadorReceptor,
                                           new Position(posRecuperacion[0], posRecuperacion[1])));
            return;
          }
        }
      }
      comportamientoDesplazarseHaciaBalon(jugadorReceptor);
    }
  }

  /**
   * Configura la acción del jugador que realiza un saque en la iteración actual de acuerdo a la
   * situación del juego.
   *
   * @param jugador Índice del jugador que realiza el saque.
   */
  public void comportamientoSacador(int jugador) {
    boolean controlBalon = false;

    for (int rematador : sp.canKick()) {
      if (rematador == jugador) {
        controlBalon = true;
      }
    }

    if (controlBalon) {
      sacar(jugador);
    } else {
      comportamientoDesplazarseHaciaBalon(jugador);
    }
  }

  /**
   * Retorna la lista de comandos. Se recomienda emplear cuando la lista va a ser enviada al
   * framework para que los comandos sean ejecutados.
   *
   * @return Lista {@link LinkedList } de comandos a ejecutar.
   */
  public LinkedList<Command> getComandos() {
    return comandos;
  }

  /**
   * Asigna la situación del partido, el cache de la situación del partido y ls lista de comando a
   * manipular.
   *
   * @param sp                Objeto {@link GameSituations} que representa la situación del partido
   *                          en la iteración actual.
   * @param spItAnterior      Objeto {@link SituacionPartidoCache} que representa la situación del
   *                          partido en la iteración anterior.
   * @param comandos          Lista {@link LinkedList} de comandos a manipular.
   * @param cuartoCancha      cuarto de cancha en el cual se encuentra el balón actualmente. Toma
   *                          los valores {@link Utilitarios#PRIMER_CUARTO_CAMPO}, {@link
   *                          Utilitarios#SEGUNDO_CUARTO_CAMPO}, {@link Utilitarios#TERCER_CUARTO_CAMPO}
   *                          o {@link Utilitarios#CUARTO_CUARTO_CAMPO}.
   * @param jugadoresMarcados Lista {@link LinkedList} con los indices de los rivalPlayers que ya
   *                          recibieron marcaje.
   */
  public void update(GameSituations sp, SituacionPartidoCache spItAnterior,
                     LinkedList<Command> comandos, int cuartoCancha,
                     LinkedList<Integer> jugadoresMarcados, boolean atacando) {
    this.sp = sp;
    this.spItAnterior = spItAnterior;
    this.comandos = comandos;
    this.cuartoCancha = cuartoCancha;
    this.jugadoresMarcados = jugadoresMarcados;
    this.atacando = atacando;

    if (this.atacando != spItAnterior.isAtacando()) {
      transicion();
    }
  }

  public void update(LinkedList<Command> comandos) {
    this.comandos = comandos;
  }

  // MÉTODOS PRIVADOS ==========================================================================//

  /**
   * Calcula la posición del arquero de acuerdo a la posición de la pelota en nuestro lado de la
   * cancha.
   *
   * @param posicionBalon Ubicación del balón en el campo de juego.
   * @return La posición del arquero.
   */
  private Position calcularPosicionArquero() {
    Position posicionBalon = sp.ballPosition();
    // Por defecto la posición del arquero es la posición anterior.
    Position posicionArquero = spItAnterior.misJugadores()[Alineacion.INDICE_ARQUERO];
    // Pendiente de la recta marcada entre el balón y el centro del arco.
    double m = (posicionBalon.getY() + Constants.LARGO_CAMPO_JUEGO / 2) / posicionBalon.getX();
    // Pendiente de la recta sobre la cual se dirige el balón al ser rematado.
    double n = Utilitarios.obtenerPendienteRemate(posicionBalon, spItAnterior.balon());

    // Si el balón se dirige hacia el arco, siga dirigiéndose al punto de interceptación.
    if (balonAlArco) {
      // Si se interceptó la pelota o está en la línea final, se espera que ya no vaya al arco (tenga fe, mijo).
      if (Utilitarios.huboRemate(spItAnterior.puedenRematarRival(), sp.rivalIterationsToKick()) ||
          Utilitarios.huboRemate(spItAnterior.puedenRematar(), sp.iterationsToKick()) ||
          posicionBalon.getY() == -Constants.LARGO_CAMPO_JUEGO / 2) {
        balonAlArco = false;
      }

      // Si ya está en el punto de interpectación, retroceda para que no lo cuelguen.
      if (sp.myPlayers()[Alineacion.INDICE_ARQUERO].equals(posicionInterceptacionArquero)) {
        double xRetroceder = posicionInterceptacionArquero.getX();
        double yRetroceder;

        if (Utilitarios.obtenerPendienteRemate(posicionBalon, spItAnterior.balon()) > 0) {
          xRetroceder = posicionInterceptacionArquero.getX() - 0.1d;
        } else if (Utilitarios.obtenerPendienteRemate(posicionBalon, spItAnterior.balon()) < 0) {
          xRetroceder = posicionInterceptacionArquero.getX() + 0.1d;
        }

        yRetroceder = n * (xRetroceder - posicionBalon.getX()) + posicionBalon.getY();
        // Si puede retroceder sin meterse en el arco como un pendejo, entonces retroceda.
        if (yRetroceder > -Constants.LARGO_CAMPO_JUEGO / 2) {
          posicionInterceptacionArquero =
              posicionInterceptacionArquero.setPosition(xRetroceder, yRetroceder);
        }
      }

      return posicionInterceptacionArquero;
    }

    // Se calculan las dos raices y sus dos valores en y
    double
        x1 =
        (-6.71d * m - Math.sqrt(95.68d + 140.71d * Math.pow(m, 2))) / (2 * (1 + Math.pow(m, 2)));
    double
        x2 =
        (-6.71d * m + Math.sqrt(95.68d + 140.71d * Math.pow(m, 2))) / (2 * (1 + Math.pow(m, 2)));
    double y1 = m * x1 - 52.5d;
    double y2 = m * x2 - 52.5d;

    if (y1 > y2) {
      posicionArquero = posicionArquero.setPosition(x1, y1);
    } else {
      posicionArquero = posicionArquero.setPosition(x2, y2);
    }

    // Si hubo remate y si se dirige hacia el arco.
    if (Utilitarios.huboRemate(spItAnterior.puedenRematarRival(), sp.rivalIterationsToKick())) {
      if (Utilitarios.isRemateArco(posicionBalon, spItAnterior.balon())) {
        // Se calcula el punto de intersección entre la tangente del círculo y la recta del balón.
        double x3 = ((posicionArquero.getX() / m) + posicionArquero.getY() +
                     n * posicionBalon.getX() - posicionBalon.getY()) / (n + 1 / m);
        double y3 = n * (x3 - posicionBalon.getX()) + posicionBalon.getY();

        posicionInterceptacionArquero = posicionArquero.setPosition(x3, y3);
        balonAlArco = true;
        return posicionInterceptacionArquero;
      }
    }

    return posicionArquero;
  }

  /**
   * Configura la acción del jugador que pueden tener el balón en la iteración actual.
   *
   * @param jugador Índice del jugador que tiene el balón.
   */
  private void comportamientoPoseedorBalon(int jugador) {
    if (!driblar(jugador)) {
      if (!rematar(jugador)) {
        if (!controlar(jugador)) {
          if (!pasar(jugador)) {
            rechazar(jugador);
          }
        }
      }
    }
  }

  /**
   * Ejecuta la acción de controlar el balón.
   *
   * @param jugador Índice del jugador que debe controlar el balón.
   * @return <code>true</code>si se ejectua el control, <code>false</code> en otro caso.
   */
  private boolean controlar(int jugador) {
    boolean controlEfectuado = false;
    JugadorImpl jugadorImpl = Alineacion.getJugadores()[jugador];
    if (jugadorImpl.esControlador()) {
      if (controlSeguro(sp.myPlayers()[jugador])) {
        comandos
            .add(new CommandHitBall(jugador, sp.ballPosition().movePosition(0d, 0.1d), 0.1d, 0d));
        controlEfectuado = true;
        dribbling[jugador] = 0;
        control[jugador] = ITS_X_CONTROL;
      }
    }
    return controlEfectuado;
  }

  /**
   * Verifica que el control del balón es seguro, es decir, que el jugador va a poder controlar el
   * balón sin perderlo.
   *
   * @param posicion Objeto {@link Posicion} que representa la posición del jugador que va a
   *                 efectuar el control.
   */
  private boolean controlSeguro(Position posicion) {
    boolean controlSeguro = false;
    double[][] distanciaJugadores = Utilitarios.distanciaJugadores(posicion, sp.rivalPlayers());
    // Se considera que es un control es seguro si no existen jugadores a 5 metros
    // (10 iteraciones para volver a patear el balón a la máxima velocidad
    // de un jugador que es 0.5 metros por iteración da 5 metros)
    if (distanciaJugadores[1][0] > 6) {
      controlSeguro = true;
    }
    return controlSeguro;
  }

  /**
   * Ejecuta la acción de driblar.
   *
   * @param jugador Índice del jugador que debe driblar.
   * @return <code>true</code>si se ejectua el drible, <code>false</code> en otro caso.
   */
  private boolean driblar(int jugador) {
    boolean dribleEfectuado = false;
    if (jugadorDesmarcadoDribbling(jugador)) {
      double fuerzaDribbling = obtenerFuerzaDribbling(jugador);
      JugadorImpl jugadorImpl = Alineacion.getJugadores()[jugador];
      Position[] referenciasDrible = jugadorImpl.getReferenciasDrible();
      for (int i = 0; i < referenciasDrible.length; i++) {
        Position referenciaDrible = referenciasDrible[i];
        // Si la distancia al punto de referencia es menor a 7 metros (14 iteraciones)
        // no se dribla y se ejecuta la siguiente accion disponible
        if (sp.ballPosition().distance(referenciaDrible) < 7d) {
          break;
        }
        double angHorizontal = sp.ballPosition().angle(referenciaDrible);
        double vel = fuerzaDribbling * Constants.getVelocidadRemate(sp.getMyPlayerPower(jugador));
        AbstractTrajectory
            trayectoria = new AirTrajectory(Math.cos(0) * vel, Math.sin(0) * vel, 0, 0);
        double time = (double) ITS_X_DRIBBLING / 60d;
        double
            desplazamientoHorizontal =
            trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double x = sp.ballPosition().getX() + desplazamientoHorizontal * Math.cos(angHorizontal);
        double y = sp.ballPosition().getY() + desplazamientoHorizontal * Math.sin(angHorizontal);
        if (dribblingSeguro(new Position(x, y))) {
          comandos.add(new CommandHitBall(jugador, referenciaDrible, fuerzaDribbling, 0));
          dribleEfectuado = true;
          dribbling[jugador] = ITS_X_DRIBBLING;
          control[jugador] = 0;
          break;
        }
      }
    }
    return dribleEfectuado;
  }

  /**
   * Verifica que el dribbling es seguro, es decir, que el jugador va a poder driblar sin perder el
   * balón.
   *
   * @param posicion Objeto {@link Posicion} que representa la posición del jugador que va a
   *                 driblar.
   * @return <code>true</code>si driblar es seguro, <code>false</code> en otro caso.
   */
  private boolean dribblingSeguro(Position posicion) {
    boolean dribblingSeguro = false;
    double[][] distanciaJugadores = Utilitarios.distanciaJugadores(posicion, sp.rivalPlayers());
    // Se considera que es un dribbling seguro si
    // no existen jugadores a 7.5 metros (15 iteraciones a la máxima velocidad
    // de un jugador que es 0.5 metros por iteración da 7.5 metros)
    if (distanciaJugadores[1][0] >= 7.5) {
      dribblingSeguro = true;
    }
    return dribblingSeguro;
  }

  /**
   * Indica si la posición se encuentra desmarcada para realizarle un pase a un jugador.
   *
   * @param posicionPaseJugador Posición a la que se va a efectuar un pase.
   * @return <code>true</code>si la posición está desmarcada, <code>false</code> en otro caso.
   */
  private boolean jugadorDesmarcado(Position posicionPaseJugador) {
    boolean jugadorDesmarcado = false;

    double[][]
        distanciaJugadores =
        Utilitarios.distanciaJugadores(posicionPaseJugador, sp.rivalPlayers());
    // FIXME por el momento un jugador desmarcado es aquel que esta a
    // más de 2.5 metros de los rivalPlayers.
    if (distanciaJugadores[1][0] >= 2.5) {
      jugadorDesmarcado = true;
    }
    return jugadorDesmarcado;
  }

  /**
   * Indica si el jugador que está haciendo el dribbling se encuentra desmarcado.
   *
   * @param jugador Índice del jugador que dribla, sobre el cual se desea hacer la evalaución de
   *                marca.
   * @return <code>true</code>si el jugador está marcado, <code>false</code> en otro caso.
   */
  private boolean jugadorDesmarcadoDribbling(int jugador) {
    boolean jugadorDesmarcado = false;
    Position posicionPaseJugador = sp.myPlayers()[jugador];
    double[][]
        distanciaJugadores =
        Utilitarios.distanciaJugadores(posicionPaseJugador, sp.rivalPlayers());
    // FIXME por el momento un jugador desmarcado es aquel que esta a
    // más de 2 metros de los rivalPlayers.
    if (distanciaJugadores[1][0] >= 2) {
      jugadorDesmarcado = true;
    }
    return jugadorDesmarcado;
  }

  /**
   * Obtiene el ángulo óptimo para que el jugador realice un remate.
   *
   * @param jugador Índice del jugador que realiza el remate.
   * @return Ángulo con que el jugador debe golpear el balón.
   */
  private double obtenerAnguloRemate(int jugador) {
    // Se obtiene de dividir uno entre una máxima distancia hipotética de 27 metros.
    double CONSTANTE_GARZON = 0.0370370370d;

    Position origenRemate = sp.myPlayers()[jugador];
    double distanciaRemate = origenRemate.distance(Constants.centroArcoSup);
    double anguloRemate;
    double valorNormalizado = CONSTANTE_GARZON * distanciaRemate;
    valorNormalizado = (valorNormalizado > 1 ? 1 : valorNormalizado);
    valorNormalizado = (valorNormalizado < 0 ? 0 : valorNormalizado);
    // Angulo del pase
    anguloRemate = 10 + (7 * valorNormalizado);
    return anguloRemate;
  }

  /**
   * Obtiene la fuerza óptima para que el jugador realice el dribbling.
   *
   * @param jugador Índice del jugador que realiza el dribbling.
   * @return Fuerza con que el jugador debe golpear el balón.
   */
  private double obtenerFuerzaDribbling(int jugador) {
    double CONSTANTE_GARZON = 1.9d;

    PlayerDetail jugadorTemp = detalle.getPlayers()[jugador];
    double
        fuerzaDribbling =
        CONSTANTE_GARZON * (jugadorTemp.getSpeed() * 0.25d + 0.25d) / (jugadorTemp.getPower() * 1.2d
                                                                       + 1.2d); //jugadorTemp.getPower()/CONSTANTE_GARZON;
    return fuerzaDribbling;
  }

  /**
   * Obtiene la fuerza y ángulo óptimos para que el jugador realice el pase.
   *
   * @param jugador     Índice del jugador que realiza el pase.
   * @param destinoPase Posición hacia la que se quiere pasar el balón.
   * @return Un arreglo de dos posiciones en donde la primera posición es la fuerza óptima del pase
   * y la segunda posición es el ángulo óptimo del pase.
   */
  private double[] obtenerFuerzaAnguloPase(int jugador, Position destinoPase) {
    // Se obtiene de dividir una máxima distancia hipotética de 52.5 metros y
    // una velodidad de remate máximo hipotética de 2.4 m/it.
    double CONSTANTE_GARZON = 0.045714285d;

    PlayerDetail jugadorTemp = detalle.getPlayers()[jugador];
    Position origenPase = sp.myPlayers()[jugador];
    double distanciaPase = origenPase.distance(destinoPase);
    double remateJugador = Constants.getVelocidadRemate(jugadorTemp.getPower());
    double fuerzaAnguloPase[] = new double[2];
    double valorNormalizado = CONSTANTE_GARZON * distanciaPase / remateJugador;
    valorNormalizado = (valorNormalizado > 1 ? 1 : valorNormalizado);
    valorNormalizado = (valorNormalizado < 0 ? 0 : valorNormalizado);
    // Fuerza del pase
    fuerzaAnguloPase[0] = 0.5 + (0.5 * valorNormalizado);
    // Angulo del pase
    fuerzaAnguloPase[1] = 10 + (35 * valorNormalizado);
    return fuerzaAnguloPase;
  }

  /**
   * Ejecuta la acción de pasar.
   *
   * @param jugador Índice del jugador que debe realizar el pase.
   * @return <code>true</code>si se ejectua el pase, <code>false</code> en otro caso.
   */
  private boolean pasar(int jugador) {
    final double DISTANCIA_PASE_HOMBRE = 5;

    boolean paseEfectuado = false;
    JugadorImpl jugadorImpl = Alineacion.getJugadores()[jugador];
    int[] referenciasPase = jugadorImpl.getReferenciasPaseXCuarto(cuartoCancha);
    for (int i = 0; i < referenciasPase.length; i++) {
      int jugadorPase = referenciasPase[i];
      Position posicionDestinoPase = sp.myPlayers()[jugadorPase];
      Position posicionOrigenPase = sp.myPlayers()[jugador];
      Position posicionPaseJugador = sp.myPlayers()[jugadorPase];
      if (posicionDestinoPase.distance(posicionOrigenPase) > DISTANCIA_PASE_HOMBRE) {
        if ((sp.ballPosition().getX() - posicionPaseJugador.getX()) >= 0) {
          posicionPaseJugador =
              posicionPaseJugador.movePosition(jugadorImpl.getProyeccionPase().getX(),
                                               jugadorImpl.getProyeccionPase().getY());
        } else {
          posicionPaseJugador =
              posicionPaseJugador.movePosition(-jugadorImpl.getProyeccionPase().getX(),
                                               jugadorImpl.getProyeccionPase().getY());
        }
      }
      if (jugadorDesmarcado(posicionPaseJugador)) {
        double fuerzaAnguloPase[] = obtenerFuerzaAnguloPase(jugador, posicionPaseJugador);
        comandos.add(new CommandHitBall(jugador,
                                        posicionPaseJugador, fuerzaAnguloPase[0],
                                        fuerzaAnguloPase[1]));
        jugadorReceptor = jugadorPase;
        paseEfectuado = true;
        dribbling[jugador] = 0;
        control[jugador] = 0;
        if (jugadorImpl.esDriblador()) {
          proyectados[jugador] = ITS_X_PROYECCION;
        }
        break;
      }
    }
    return paseEfectuado;
  }

  /**
   * Ejecuta la acción de rechazar el balón.
   *
   * @param jugador Índice del jugador que rechaza.
   * @return <code>true</code>si se ejecuta el rechazo, <code>false</code> en otro caso.
   */
  private boolean rechazar(int jugador) {
    boolean rechaceEfectuado;
    int indiceReceptorRechazo;

    if (cuartoCancha >= Utilitarios.TERCER_CUARTO_CAMPO) {
      rematar(jugador);
      rechaceEfectuado = true;
      dribbling[jugador] = 0;
      return rechaceEfectuado;
    } else if (cuartoCancha == Utilitarios.SEGUNDO_CUARTO_CAMPO) {
      indiceReceptorRechazo = Alineacion.INDICE_DELANTERO;
    } else {
      indiceReceptorRechazo = Alineacion.INDICE_ARMADOR;
    }

    Position posicionPaseJugador = sp.myPlayers()[indiceReceptorRechazo];
    posicionPaseJugador = posicionPaseJugador.movePosition(0, 2);
    double fuerzaAnguloPase[] = obtenerFuerzaAnguloPase(jugador, posicionPaseJugador);
    comandos.add(new CommandHitBall(jugador,
                                    posicionPaseJugador, fuerzaAnguloPase[0], fuerzaAnguloPase[1]));

    rechaceEfectuado = true;
    dribbling[jugador] = 0;
    control[jugador] = 0;
    return rechaceEfectuado;
  }

  /**
   * Ejecuta la acción de rematar.
   *
   * @param jugador Índice del jugador que debe rematar.
   * @return <code>true</code>si se ejectua el remate, <code>false</code> en otro caso.
   */
  private boolean rematar(int jugador) {
    boolean remateEfectuado = false;

    if (jugador != Alineacion.INDICE_LATERAL_D || jugador != Alineacion.INDICE_LATERAL_I) {
      if (cuartoCancha == Utilitarios.CUARTO_CUARTO_CAMPO) {
        rematarAlArco(jugador);
        remateEfectuado = true;
        dribbling[jugador] = 0;
      } else if (sp.ballPosition().distance(Constants.centroArcoSup) <= 30) {
        for (int i = 0; i < Alineacion.getJugadores().length; i++) {
          if (i == jugador && Alineacion.getJugadores()[i].esRematador()) {
            rematarAlArco(jugador);
            remateEfectuado = true;
            dribbling[jugador] = 0;
            control[jugador] = 0;
          }
        }
      }
    }
    return remateEfectuado;
  }

  /**
   * Ejecuta el mejor remate posible hacia el arco contrario.
   *
   * @param jugador Jugador que efectuará el remate.
   */
  private void rematarAlArco(int jugador) {
    comandos.add(new CommandHitBall(jugador,
                                    Constants.centroArcoSup, 1.0d, obtenerAnguloRemate(jugador)));
  }

  /**
   * Ejecuta la acción de sacar (saques de banda, de esquina o de meta)
   *
   * @param jugador Índice del jugador que realiza el saque.
   */
  private void sacar(int jugador) {
    if (jugador == Alineacion.INDICE_ARQUERO) {
      if (sp.ballPosition().getX() > 0) {
        comandos.add(new CommandHitBall(jugador,
                                        sp.myPlayers()[Alineacion.INDICE_VOLANTE_D], 1.0d, 45d));
      } else {
        comandos.add(new CommandHitBall(jugador,
                                        sp.myPlayers()[Alineacion.INDICE_VOLANTE_I], 1.0d, 45d));
      }
    } else if (jugador == Alineacion.INDICE_LATERAL_I
               && sp.ballPosition().equals(Constants.cornerSupIzq)) {
      comandos.add(new CommandHitBall(jugador,
                                      Constants.penalSup, 1.0d, 45d));
    } else if (jugador == Alineacion.INDICE_LATERAL_D
               && sp.ballPosition().equals(Constants.cornerSupDer)) {
      comandos.add(new CommandHitBall(jugador,
                                      Constants.penalSup, 1.0d, 45d));
    } else if (jugador == Alineacion.INDICE_LATERAL_I) {
      comandos.add(new CommandHitBall(jugador,
                                      sp.myPlayers()[Alineacion.INDICE_VOLANTE_I], 1.0d, 15d));
    } else if (jugador == Alineacion.INDICE_LATERAL_D) {
      comandos.add(new CommandHitBall(jugador,
                                      sp.myPlayers()[Alineacion.INDICE_VOLANTE_D], 1.0d, 15d));
    } else if (jugador == Alineacion.INDICE_ARMADOR) {
      comandos.add(new CommandHitBall(jugador,
                                      sp.myPlayers()[Alineacion.INDICE_DELANTERO], 1.0d, 5d));
    }
  }

  private void transicion() {
    if (!atacando) {
      dribbling = new int[Utilitarios.NUMERO_JUGADORES];
      control = new int[Utilitarios.NUMERO_JUGADORES];
      proyectados = new int[Utilitarios.NUMERO_JUGADORES];
      jugadorReceptor = -1;
    }
  }

}
