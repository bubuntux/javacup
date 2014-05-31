package org.dsaw.javacup.tactics.jvc2013.meerkats;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 * Métodos utilitarios.
 */
public class Utilitarios {

  // CONSTANTES ================================================================================//

  public static final int NUMERO_JUGADORES = 11;
  public static final int PRIMER_CUARTO_CAMPO = 0;
  public static final int SEGUNDO_CUARTO_CAMPO = 1;
  public static final int TERCER_CUARTO_CAMPO = 2;
  public static final int CUARTO_CUARTO_CAMPO = 3;

  // ATRIBUTOS =================================================================================//

  /**
   * Estado de la consola para impresión de mensajes.
   */
  private static boolean consolaHabilitada = false;

  /**
   * Estado de ataque de MeerkatsFC.
   */
  private static boolean atacando = false;

  // MÉTODOS ===================================================================================//

  /**
   * Retorna las distancia de cada uno de los jugadores a la posición indicada ordenadas de menor a
   * mayor.
   *
   * @param posicion  La posición de referencia.
   * @param jugadores Las posiciones de los jugadores.
   * @return Una arreglo de <code>double</code> en donde la primer posición es el indice del jugador
   * y la segunda posición es la distancia al punto de referencia. El arreglo esta ordenado por las
   * distancias siendo el primero elemento el jugador mas cercano y el ultimo el mas lejano a la
   * posición de referencia.
   */
  public static double[][] distanciaJugadores(Position posicion, Position[] jugadores) {
    final int INDICE_JUGADOR = 0;
    final int INDICE_DISTANCIA = 1;
    double[][] distanciaJugadores = new double[2][NUMERO_JUGADORES];
    for (int i = 0; i < NUMERO_JUGADORES; i++) {
      distanciaJugadores[INDICE_JUGADOR][i] = i;
      distanciaJugadores[INDICE_DISTANCIA][i] = posicion.distance(jugadores[i]);
    }
    double auxIndiceJugador;
    double auxDistanciaJugador;
    for (int i = 0; i < NUMERO_JUGADORES; i++) {
      for (int j = i + 1; j < NUMERO_JUGADORES; j++) {
        if (distanciaJugadores[INDICE_DISTANCIA][i] > distanciaJugadores[INDICE_DISTANCIA][j]) {
          auxIndiceJugador = distanciaJugadores[0][j];
          auxDistanciaJugador = distanciaJugadores[INDICE_DISTANCIA][j];
          distanciaJugadores[INDICE_JUGADOR][j] = distanciaJugadores[INDICE_JUGADOR][i];
          distanciaJugadores[INDICE_DISTANCIA][j] = distanciaJugadores[INDICE_DISTANCIA][i];
          distanciaJugadores[INDICE_JUGADOR][i] = auxIndiceJugador;
          distanciaJugadores[INDICE_DISTANCIA][i] = auxDistanciaJugador;
        }
      }
    }
    return distanciaJugadores;
  }

  /**
   * Indica si en la iteraciòn anterior se efectuó un remate con base en los datos proporcionados
   *
   * @param puedenRematarItAnterior        Arreglo con los indices de los jugadores que podían
   *                                       efectuar un remate en la iteración anterior. Se puede
   *                                       obtener invocando a {@link GameSituations#canKick()} ó
   *                                       {@link GameSituations#rivalCanKick()}, dependiendo de si
   *                                       se desea saber si hubo un remate propio o de los
   *                                       rivalPlayers.
   * @param iteracionesParaRematarItActual Arreglo con las iteraciones que restan para que los
   *                                       rivalPlayers puedan rematar en la iteración actual. Se
   *                                       puede obtener invocando a {@link GameSituations#iterationsToKick()}
   *                                       ó {@link GameSituations#rivalIterationsToKick()},
   *                                       dependiendo de si se desea saber si hubo un remate propio
   *                                       o de los rivalPlayers.
   * @return <code>true</code> si en la iteración anterior se efectuó un remate, <code>false</code>
   * de lo contrario.
   */
  public static boolean huboRemate(int[] puedenRematarItAnterior,
                                   int[] iteracionesParaRematarItActual) {
    for (int rematador : puedenRematarItAnterior) {
      if (iteracionesParaRematarItActual[rematador] == Constants.ITERACIONES_GOLPEAR_BALON) {
        return true;
      }
    }
    return false;
  }

  /**
   * Indica si un remate está dirigido al arco propio (de mi equipo).
   *
   * @param posicionBalon         Posición del balón en la iteración actual.
   * @param posicionBalonAnterior Posición del balón en la iteración anterior. Se puede obtener
   *                              desde el método {@link SituacionPartidoCache#ballPosition()}.
   * @return <code>true</code> si el remate de la iteración anterior va dirigido al arco propio,
   * <code>false</code> de lo contrario.
   */
  public static boolean isRemateArco(Position posicionBalon, Position posicionBalonAnterior) {
    // Calcula el valor de x en la línea final del campo
    double x = posicionBalon.getX() + (-Constants.LARGO_CAMPO_JUEGO / 2d - posicionBalon.getY()) /
                                      obtenerPendienteRemate(posicionBalon, posicionBalonAnterior);
    if (x >= Constants.posteIzqArcoInf.getX() && x <= Constants.posteDerArcoInf.getX()) {
      return true;
    }
    return false;
  }

  /**
   * Indica si Meerkats FC está atacando en la iteración actual.
   *
   * @param situacion Objeto {@link GameSituations} que representa la situación del partido.
   * @return <code>true</code> si MeerkatsFC está atacando, <code>false</code> de lo contrario.
   */
  public static boolean isAtacando(GameSituations situacion) {
    int iteracionesMaximoPropias = 0;
    int iteracionesMaximoRival = 0;

    for (int iteraciones : situacion.iterationsToKick()) {
      if (iteraciones > iteracionesMaximoPropias) {
        iteracionesMaximoPropias = iteraciones;
      }
    }

    for (int iteraciones : situacion.rivalIterationsToKick()) {
      if (iteraciones > iteracionesMaximoRival) {
        iteracionesMaximoRival = iteraciones;
      }
    }

    // Si uno de mis jugadores necesita de más iteraciones que cualquiera del otro equipo, entonces ataca Meerkats.
    if (iteracionesMaximoPropias > iteracionesMaximoRival) {
      atacando = true;
    }
    // Si uno de los jugadores rivalPlayers necesita de más iteraciones que cualquiera de mi equipo, entonces ellos atacan.
    else if (iteracionesMaximoPropias < iteracionesMaximoRival) {
      atacando = false;
    }

    // Retorna si yo ataco. Nótese que si no se cumplen las dos condiciones anteriores entonces la situación
    // de ataque no se ha alterado, de manera que se retorna la situación de ataque almacenada anteriormente.
    return atacando;
  }

  /**
   * Indica el cuarto de cancha en el cual se encuentra el balón, de la siguiente manera: <ul>
   * <li>{@link #PRIMER_CUARTO_CAMPO} si se encuentra en el primer cuarto del campo de juego</li>
   * <li>{@link #SEGUNDO_CUARTO_CAMPO} si se encuentra en el segundo cuarto del campo de juego</li>
   * <li>{@link #TERCER_CUARTO_CAMPO} si se encuentra en el tercer cuarto del campo de juego</li>
   * <li>{@link #CUARTO_CUARTO_CAMPO} si se encuentra en el ùltimo cuarto del campo de juego</li>
   * </ul>
   *
   * @param posicioBalon Objeto {@link Posicion} que representa la posición actual del balón.
   * @return El valor entero que representa al cuarto de campo en el que se encuetra en balón.
   */
  public static int obtenerCuartoCancha(Position posicioBalon) {
    double lineaPrimerCuarto = -1d * Constants.LARGO_CAMPO_JUEGO / 4d;
    double lineaSegundoCuarto = 0;
    double lineaTercerCuarto = Constants.LARGO_CAMPO_JUEGO / 4d;

    if (posicioBalon.getY() < lineaPrimerCuarto) {
      return PRIMER_CUARTO_CAMPO;
    } else if (posicioBalon.getY() < lineaSegundoCuarto) {
      return SEGUNDO_CUARTO_CAMPO;
    } else if (posicioBalon.getY() < lineaTercerCuarto) {
      return TERCER_CUARTO_CAMPO;
    }
    return CUARTO_CUARTO_CAMPO;
  }

  /**
   * Obtiene el jugador que debe realizar el saque de acuerdo a la posición del balón, es decir, al
   * lugar del saque.
   *
   * @param posicionBalon Objeto {@link Posicion} que representa la posicón actual del balón.
   * @return Índice del jugador que debe realizar el saque.
   */
  public static int obtenerJugadorSaque(Position posicionBalon) {
    if (posicionBalon.getX() == (Constants.ANCHO_CAMPO_JUEGO / 2)) {
      return Alineacion.INDICE_LATERAL_D;
    } else if (posicionBalon.equals(Constants.centroCampoJuego)) {
      return Alineacion.INDICE_ARMADOR;
    } else if (posicionBalon.getX() == -(Constants.ANCHO_CAMPO_JUEGO / 2)) {
      return Alineacion.INDICE_LATERAL_I;
    } else {
      return Alineacion.INDICE_ARQUERO;
    }
  }

  /**
   * Obtiene la pendiente de un remate para efectos de cálculos matemáticos sobre el mismo.
   *
   * @param posicionBalon         Posición del balón en la iteración actual.
   * @param posicionBalonAnterior Posición del balón en la iteración anterior. Se puede obtener
   *                              desde el método {@link SituacionPartidoCache#ballPosition()}.
   * @return El valor de la pendiente de la recta descrita por los puntos <code>posicionBalon</code>
   * y <code>posicionBalonAnterior</code>.
   */
  public static double obtenerPendienteRemate(Position posicionBalon,
                                              Position posicionBalonAnterior) {
    return ((posicionBalonAnterior.getY() - posicionBalon.getY()) / (posicionBalonAnterior.getX()
                                                                     - posicionBalon.getX()));
  }

  // MÉTODOS PARA DEPURACIÓN ===================================================================//

  public static String arrayToString(int[] array) {
    String cadena = "";
    for (int entero : array) {
      cadena = cadena.concat("[" + entero + "]");
    }
    return cadena;
  }

  public static String arrayPosicionesToString(Position[] array) {
    String cadena = "";
    for (Position posicion : array) {
      cadena = cadena.concat("[" + posicion.toString() + "]");
    }
    return cadena;
  }

  /**
   * Habilita o deshabilita la impresión de mensajes en la consola.
   *
   * @param habilitar Flag para habilitar o deshabilitar la cosola.
   */
  public static void habilitarConsola(boolean habilitar) {
    consolaHabilitada = habilitar;
  }

  /**
   * Imprime el mensaje en la consola si esta se encuentra habilitada.
   *
   * @param mensaje El mensaje que se desea imprimir.
   */
  public static void consola(String mensaje) {
    if (consolaHabilitada) {
      //System.out.println(mensaje);
    }
  }

}
